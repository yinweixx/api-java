package com.anyun.cloud.service.impl;

import com.anyun.cloud.common.RandomUtils;
import com.anyun.cloud.common.ZipUtils;
import com.anyun.cloud.common.context.Context;
import com.anyun.cloud.common.etcd.spi.entity.api.ApiEntity;
import com.anyun.cloud.common.json.JsonUtil;
import com.anyun.cloud.service.RamlApiRamlParser;
import com.anyun.cloud.common.string.StringUtils;
import com.anyun.cloud.common.sys.Response;
import com.anyun.cloud.common.FileUtil;
import com.anyun.cloud.dao.ProductDao;
import com.anyun.cloud.model.ErrorCode;
import com.anyun.cloud.model.dto.PageDto;
import com.anyun.cloud.model.entity.ProductEntity;
import com.anyun.cloud.model.param.ProductCreateParam;
import com.anyun.cloud.model.param.ProductUpdateParam;
import com.anyun.cloud.service.ProductService;
import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class ProductServiceImpl implements ProductService {
    private final static Logger LOGGER = LoggerFactory.getLogger(ProductService.class);
    private ProductDao productDao;
    private Context context;
    private Response response;
    private RamlApiRamlParser ramlApiRamlParser;

    @Inject
    public ProductServiceImpl(Context context) {
        this.context = context;
        this.productDao = context.getBeanByClass(ProductDao.class);
        this.ramlApiRamlParser = context.getBeanByClass(RamlApiRamlParser.class);
    }

    @Override
    public Response getDetails(int id) {
        LOGGER.debug("id:[{}]", id);
        if (id == 0) {
            response = new Response<String>();
            response.setCode(ErrorCode.PARAMETER_ERROR.code());
            response.setContent(ErrorCode.PARAMETER_ERROR.name() + "id is empty");
        }

        try {
            response = new Response<ProductEntity>();
            ProductEntity productEntity = productDao.selectDetailsById(id);
            response.setCode(ErrorCode.NO_ERROR.code());
            response.setContent(productEntity);
        } catch (Exception e) {
            response = new Response<String>();
            response.setCode(ErrorCode.QUERY_RESOURCE_ERROR.code());
            response.setContent(ErrorCode.QUERY_RESOURCE_ERROR.name() + e.getMessage());
        }
        return response;
    }

    @Override
    public Response delete(int id) {
        LOGGER.debug("id:[{}]", id);
        if (id == 0) {
            response = new Response<String>();
            response.setCode(ErrorCode.PARAMETER_ERROR.code());
            response.setContent(ErrorCode.PARAMETER_ERROR.name() + "id is empty");
        }
        try {
            response = new Response<ProductEntity>();
            productDao.deleteById(id);
            response.setCode(ErrorCode.NO_ERROR.code());
            response.setContent(null);
        } catch (Exception e) {
            response = new Response<String>();
            response.setCode(ErrorCode.DELETE_RESOURCE_ERROR.code());
            response.setContent(ErrorCode.DELETE_RESOURCE_ERROR.name() + e.getMessage());
        }
        return response;
    }

    @Override
    public Response create(String body) {
        LOGGER.debug("body:[{}]", body);
        if (StringUtils.isEmpty(body)) {
            response = new Response<String>();
            response.setCode(ErrorCode.PARAMETER_ERROR.code());
            response.setContent(ErrorCode.PARAMETER_ERROR.name() + "body is empty");
            return response;
        }
        ProductCreateParam param;
        try {
            param = JsonUtil.fromJson(ProductCreateParam.class, body);
        } catch (Exception e) {
            response.setCode(ErrorCode.PARAMETER_ERROR.code());
            response.setContent(ErrorCode.PARAMETER_ERROR.name() + e.getMessage());
            return response;
        }

        try {
            response = new Response<>();
            ProductEntity p;
            p = new ProductEntity();
            p.setCategory(param.getCategory());
            p.setProductName(param.getProductName());
            p.setUnitPrice(param.getUnitPrice());
            p.setUnitsInStock(param.getUnitsInStock());
            p.setReorderQuantity(param.getReorderQuantity());
            p = productDao.insert(p);
            response.setCode(ErrorCode.NO_ERROR.code());
            response.setContent(p);
        } catch (Exception e) {
            response = new Response<String>();
            response.setCode(ErrorCode.PUT_RESOURCE_ERROR.code());
            response.setContent(ErrorCode.PUT_RESOURCE_ERROR.name() + e.getMessage());
        }
        return response;
    }

    @Override
    public Response batchCreate(String body) {
        LOGGER.debug("body:[{}]", body);
        if (StringUtils.isEmpty(body)) {
            response = new Response<String>();
            response.setCode(ErrorCode.PARAMETER_ERROR.code());
            response.setContent(ErrorCode.PARAMETER_ERROR.name() + "body is empty");
            return response;
        }

        List<ProductEntity> entityList = new ArrayList<>();
        try {
            List<Map> list = JsonUtil.fromJson(List.class, body);
            for (Map m : list) {
                ProductCreateParam param = JsonUtil.fromJson(ProductCreateParam.class, JsonUtil.toJson(m));
                ProductEntity productEntity = new ProductEntity();
                productEntity.setReorderQuantity(param.getReorderQuantity());
                productEntity.setUnitsInStock(param.getUnitsInStock());
                productEntity.setProductName(param.getProductName());
                productEntity.setUnitPrice(param.getUnitPrice());
                entityList.add(productEntity);
            }
        } catch (Exception e) {
            response = new Response<String>();
            response.setCode(ErrorCode.PARAMETER_ERROR.code());
            response.setContent(ErrorCode.PARAMETER_ERROR.name() + e.getMessage());
            return response;
        }

        try {
            response = new Response<List>();
            response.setCode(ErrorCode.NO_ERROR.code());
            response.setContent(productDao.insert(entityList));
        } catch (Exception e) {
            response = new Response<String>();
            response.setCode(ErrorCode.PUT_RESOURCE_ERROR.code());
            response.setContent(ErrorCode.PUT_RESOURCE_ERROR.name() + e.getMessage());
        }
        return response;
    }

    @Override
    public Response update(String body) {
        LOGGER.debug("body:[{}]", body);
        if (StringUtils.isEmpty(body)) {
            response = new Response<String>();
            response.setCode(ErrorCode.PARAMETER_ERROR.code());
            response.setContent(ErrorCode.PARAMETER_ERROR.name() + "body is empty");
            return response;
        }

        ProductEntity p = new ProductEntity();
        try {
            ProductUpdateParam up = JsonUtil.fromJson(ProductUpdateParam.class, body);
            p.setProductId(up.getProductId());
            p.setUnitPrice(up.getUnitPrice());
            p.setProductName(up.getProductName());
            p.setUnitsInStock(up.getUnitsInStock());
            p.setReorderQuantity(up.getReorderQuantity());
            p.setCategory(up.getCategory());
        } catch (Exception e) {
            response = new Response<String>();
            response.setCode(ErrorCode.PARAMETER_ERROR.code());
            response.setContent(ErrorCode.PARAMETER_ERROR.name() + "格式转换错误！");
            return response;
        }

        try {
            response = new Response<ProductEntity>();
            response.setContent(productDao.update(p));
            response.setCode(ErrorCode.NO_ERROR.code());
            return response;
        } catch (Exception e) {
            response = new Response<String>();
            response.setCode(ErrorCode.POST_RESOURCE_ERROR.code());
            response.setContent(ErrorCode.POST_RESOURCE_ERROR.name() + e.getMessage());
            return response;
        }
    }

    @Override
    public Response batchUpdate(String body) {
        LOGGER.debug("body:[{}]", body);
        if (StringUtils.isEmpty(body)) {
            response = new Response<String>();
            response.setCode(ErrorCode.PARAMETER_ERROR.code());
            response.setContent(ErrorCode.PARAMETER_ERROR.name() + "body is empty");
            return response;
        }

        List<ProductEntity> l = new ArrayList<>();
        try {
            response = new Response<List>();
            List<Map> list = JsonUtil.fromJson(List.class, body);
            for (Map m : list) {
                ProductUpdateParam param = JsonUtil.fromJson(ProductUpdateParam.class, JsonUtil.toJson(m));
                ProductEntity p = new ProductEntity();
                p.setProductId(param.getProductId());
                p.setUnitPrice(param.getUnitPrice());
                p.setProductName(param.getProductName());
                p.setUnitsInStock(param.getUnitsInStock());
                p.setReorderQuantity(param.getReorderQuantity());
                p.setCategory(param.getCategory());
                l.add(p);
            }
        } catch (Exception e) {
            response = new Response<String>();
            response.setCode(ErrorCode.PARAMETER_ERROR.code());
            response.setContent(ErrorCode.PARAMETER_ERROR.name() + "格式转换错误！");
            return response;
        }

        try {
            response = new Response<List<ProductEntity>>();
            response.setCode(ErrorCode.NO_ERROR.code());
            response.setContent(productDao.update(l));
            return response;
        } catch (Exception e) {
            response = new Response<String>();
            response.setCode(ErrorCode.POST_RESOURCE_ERROR.code());
            response.setContent(ErrorCode.POST_RESOURCE_ERROR.name() + e.getMessage());
            return response;
        }
    }

    @Override
    public Response getPageList(int index, int limit, String sortBy, String sortDirection) {
                try {
                    response = new Response<PageDto<ProductEntity>>();
                    response.setCode(ErrorCode.NO_ERROR.code());
                    response.setContent(productDao.selectPageList(index, limit, sortBy, sortDirection));
                    return response;
                } catch (Exception e) {
                    response = new Response<String>();
                    response.setCode(ErrorCode.QUERY_RESOURCE_ERROR.code());
                    response.setContent(ErrorCode.QUERY_RESOURCE_ERROR.name() + e.getMessage());
                    return response;
        }
    }


    @Override
    public Response upload(HttpServletRequest request) {
        int contentSize = request.getContentLength();//文件流
        LOGGER.debug("Upload content size:[{}]", contentSize);
        if (contentSize > 0) {
            String uploadDir = "/tmp/upload"; //上传文件目录
            FileUtil.mkdir(uploadDir, false);//创建上传文件目录（已存在则不创建）
            String expression = "yyyyMMdd-HHmmss";
            String filePrefix = "/" + StringUtils.formatDate(new Date(), expression) + "-";//文件前缀
            String randomFileName = RandomUtils.generateByhashId(4);//获取生成文件名
            String fileName = uploadDir + filePrefix + randomFileName;//生成文件全路径（包含文件名称和路径）
            LOGGER.debug("Upload file [{}]", fileName);
            try {
                FileUtil.write(fileName, request.getInputStream(), false);//将文件流写入指定文件
            } catch (IOException e) {
                e.printStackTrace();
                return new Response<String>() {
                    {
                        setCode(ErrorCode.POST_RESOURCE_ERROR.code());
                        setContent(ErrorCode.POST_RESOURCE_ERROR.name() + e.getMessage() + " " + "FileUtil.write()  fail！");
                    }
                };
            }
            String extractDir = uploadDir + "/../extract/" + filePrefix + randomFileName; //获取文件解压目录
            LOGGER.debug("Unzip directory: {}", extractDir);
            FileUtil.mkdir(extractDir, false);//创建文件解压目录
            try {
                ZipUtils.extractZipData(fileName, new File(extractDir));//解压zip 包
            } catch (Exception e) {
                e.printStackTrace();
                if (!(e instanceof FileNotFoundException)) {
                    try {//删除错误格式的文件和目录
                        FileUtil.rm(fileName, true);
                        FileUtil.rm(extractDir, true);
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
                return new Response<String>() {
                    {
                        setCode(ErrorCode.POST_RESOURCE_ERROR.code());
                        setContent(ErrorCode.POST_RESOURCE_ERROR.name() + e.getMessage() + " " + "ZipUtils.extractZipData fail ！");
                    }
                };
            }

            try {
                File[] files = new File(extractDir).listFiles(new FilenameFilter() {
                    @Override
                    public boolean accept(File dir, String name) {
                        if (name.toLowerCase().trim().endsWith(".raml"))
                            return true;
                        return false;
                    }
                });
                if (files == null || files.length == 0) {
                    LOGGER.error("Not find api define file");
                    return new Response<String>() {
                        {
                            setCode(ErrorCode.POST_RESOURCE_ERROR.code());
                            setContent(ErrorCode.POST_RESOURCE_ERROR.name() + " " + "ZNot find api define file ！");
                        }
                    };
                }
                List<ApiEntity> list = new ArrayList<>();
                for (File file : new File(extractDir).listFiles()) {
                    ApiEntity apiEntity = ramlApiRamlParser.withEncoding(RamlApiRamlParser.ENCODING).withRamlFile(file).buildApi();
                    list.add(apiEntity);
                    LOGGER.debug(apiEntity.toString());
                }





                LOGGER.debug("上传成功");
                //TODO  批量插库操作
                return new Response<List<ApiEntity>>() {
                    {
                        setCode(ErrorCode.NO_ERROR.code());
                        setContent(list);
                    }
                };
            } catch (Exception e) {
                e.printStackTrace();
                return new Response<String>() {
                    {
                        setCode(ErrorCode.POST_RESOURCE_ERROR.code());
                        setContent(ErrorCode.POST_RESOURCE_ERROR.name() + e.getMessage() + " " + "deploy() fail ！");
                    }
                };
            }

        } else {
            return new Response<String>() {
                {
                    setCode(ErrorCode.POST_RESOURCE_ERROR.code());
                    setContent(ErrorCode.POST_RESOURCE_ERROR.name() + " " + "zip 包上传失败 内容为空!");
                }
            };
        }
    }
}