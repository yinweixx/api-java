package com.anyun.cloud.service.impl;

import com.anyun.cloud.common.FileUtil;
import com.anyun.cloud.common.RandomUtils;
import com.anyun.cloud.common.ZipUtils;
import com.anyun.cloud.common.context.Context;
import com.anyun.cloud.common.json.JsonUtil;
import com.anyun.cloud.common.string.StringUtils;
import com.anyun.cloud.common.sys.Response;
import com.anyun.cloud.dao.VideoSourceDao;
import com.anyun.cloud.model.ErrorCode;
import com.anyun.cloud.model.dto.PageDto;
import com.anyun.cloud.model.entity.VideoSourceEntity;
import com.anyun.cloud.model.param.VideoSourceCreateParam;
import com.anyun.cloud.model.param.VideoSourceUpdateParam;
import com.anyun.cloud.service.RamlApiRamlParser;
import com.anyun.cloud.service.VideoSourceService;
import com.csvreader.CsvReader;
import com.google.inject.Inject;
import org.hashids.Hashids;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class VideoSourceServiceImpl implements VideoSourceService {
    private final static Logger LOGGER = LoggerFactory.getLogger(VideoSourceServiceImpl.class);
    private Context context;
    private Response response;
    private VideoSourceDao videoSourceDao;
    private RamlApiRamlParser ramlApiRamlParser;


    @Inject
    public VideoSourceServiceImpl(Context context) {
        this.context = context;
        this.videoSourceDao = context.getBeanByClass(VideoSourceDao.class);
        this.ramlApiRamlParser = context.getBeanByClass(RamlApiRamlParser.class);
    }


    @Override
    public Response upload(HttpServletRequest request) {
        int contentSize = request.getContentLength();//文件流
        LOGGER.debug("Upload content size:[{}]", contentSize);
        if (contentSize > 0) {
            String userHomeDir = System.getProperty("user.home");
            LOGGER.debug("userHomeDir:[{}]", System.getProperty("user.home"));
            //定义上传文件目录
            String uploadDir = userHomeDir + "/upload/video/csv".trim();
            LOGGER.debug("uploadDir:[{}]", uploadDir);
            File fileDir = new File(uploadDir);
            if (fileDir.exists()) {
                //存在
                LOGGER.debug("FileDir already exist");
            } else {
                //不存在
                LOGGER.debug("FileDir does not exists");
                fileDir.mkdirs(); //级联创建
                if (fileDir.exists()) {
                    LOGGER.debug("FileDir create success!");
                } else {
                    response = new Response<String>();
                    response.setCode(ErrorCode.POST_RESOURCE_ERROR.code());
                    response.setContent(ErrorCode.POST_RESOURCE_ERROR.name() + " " + "FileDir create fail!");
                    LOGGER.debug("response:[{}]", response.asJson());
                    return response;
                }
            }
            LOGGER.debug("AbsolutePath:[{}]", fileDir.getAbsolutePath());
            LOGGER.debug("Path:[{}]", fileDir.getPath());
            LOGGER.debug("isDirectory:[{}]", fileDir.isDirectory());
            String expression = "yyyyMMdd-HHmmss";
            String filePrefix = "/" + StringUtils.formatDate(new Date(), expression) + "-";//文件前缀
            String randomFileName = StringUtils.uuidGen().trim().toUpperCase();//获取生成文件名
            LOGGER.debug("randomFileName:[{}]", randomFileName);
            String fileName = uploadDir + filePrefix + randomFileName;//生成文件全路径（包含文件名称和路径）
            LOGGER.debug("Upload file [{}]", fileName);
            try {
                FileUtil.write(fileName, request.getInputStream(), false);//将文件流写入指定文件
            } catch (IOException e) {
                response = new Response<String>();
                response.setCode(ErrorCode.POST_RESOURCE_ERROR.code());
                response. setContent(ErrorCode.POST_RESOURCE_ERROR.name() + e.getMessage() + " " + "FileUtil.write()  fail！");
                return response;
            }
            String extractDir = uploadDir + "/../extract" + filePrefix + randomFileName; //获取文件解压目录
            LOGGER.debug("Unzip directory: {}", extractDir);
            FileUtil.mkdir(extractDir, false);//创建文件解压目录
            try {
                ZipUtils.extractZipData(fileName, new File(extractDir));//解压zip 包
            } catch (Exception e) {
                if (!(e instanceof FileNotFoundException)) {
                    try {//删除错误格式的文件和目录
                        FileUtil.rm(fileName, true);
                        FileUtil.rm(extractDir, true);
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
                response = new Response<String>();
                response.setCode(ErrorCode.POST_RESOURCE_ERROR.code());
                response. setContent(ErrorCode.POST_RESOURCE_ERROR.name() + e.getMessage() + " " + "ZipUtils.extractZipData fail ！");
                return response;
            }
            LOGGER.debug("==================================================");
            try {
                File[] files = new File(extractDir).listFiles(new FilenameFilter() {
                    @Override
                    public boolean accept(File dir, String name) {
                        if (name.toLowerCase().trim().endsWith(".csv"))
                            return true;
                        return false;
                    }
                });
                if (files == null || files.length == 0) {
                    LOGGER.error("Not find csv file");
                    response =  new Response<String>();
                    response.setCode(ErrorCode.POST_RESOURCE_ERROR.code());
                    response.setContent(ErrorCode.POST_RESOURCE_ERROR.name() + " ：" + "上传格式错误 ！");
                    return response;
                }
                ArrayList<String[]> csvList = new ArrayList<String[]>();
                VideoSourceEntity v;
                for (File file : new File(extractDir).listFiles()) {

                    try {
//                        BufferedReader  br = new BufferedReader(new FileReader(file));
                        LOGGER.debug("file.isFile():[{}]",file.isFile());
                       CsvReader cr = new CsvReader(file.getAbsolutePath() ,',', Charset.forName("GBK"));
                       cr.readHeaders();
                        while (cr.readRecord()) {
                            csvList.add(cr.getValues());
                        }
                        cr.close();
                        LOGGER.debug("csvList:[{}]", JsonUtil.toJson(csvList));
                        for (int row = 0; row<csvList.size();row++){
                            v = new VideoSourceEntity();
                            Hashids hashids = new Hashids(StringUtils.uuidGen());
                            LOGGER.debug("hashids:[{}]", JsonUtil.toJson(hashids));
                            String softId = hashids.encode(new Date().getTime()).toUpperCase().substring(0,8);
                            LOGGER.debug("softId:[{}]", softId);
                            v.setCameraId(softId);
                            v.setShortNumber(new String(csvList.get(row)[0].getBytes()).trim());
                            v.setNationalId(new String(csvList.get(row)[1].getBytes()).trim());
                            v.setIntersection(new String(csvList.get(row)[2].getBytes()).trim());
                            v.setOrientation(new String(csvList.get(row)[3].getBytes()).trim());
                            v.setDescription(new String(csvList.get(row)[4].getBytes()).trim());
                            v.setIpAddress(new String(csvList.get(row)[5].getBytes()).trim());
                            v.setSourceAddress(new String(csvList.get(row)[6].getBytes()).trim());
                            v.setTcp(new String(csvList.get(row)[7].getBytes()).trim());
                            v.setUserName(new String(csvList.get(row)[8].getBytes()).trim());
                            v.setPassword(new String(csvList.get(row)[9].getBytes()).trim());
                            v.setIfForword(new String(csvList.get(row)[10].getBytes()).trim());
                            v.setForwardUrl(new String(csvList.get(row)[11].getBytes()).trim());
                            v.setIfLink(new String(csvList.get(row)[12].getBytes()).trim());
                            v.setIfInclude(new String(csvList.get(row)[13].getBytes()).trim());
                            v.setStorageUrl(new String(csvList.get(row)[14].getBytes()).trim());
                            v.setDataCenter(new String(csvList.get(row)[15].getBytes()).trim());
                            v.setWarrantyDate(new String(csvList.get(row)[16].getBytes()).trim());
                            v.setProduct(new String(csvList.get(row)[17].getBytes()).trim());
                            v.setVender(new String(csvList.get(row)[18].getBytes()).trim());
                            v.setBitRate(new String(csvList.get(row)[19].getBytes()).trim());
                            v.setResolvingPower(new String(csvList.get(row)[20].getBytes()).trim());
                            v.setType(new String(csvList.get(row)[21].getBytes()).trim());
                            LOGGER.debug("VideoSourceEntity:[{}]", v);
                            if (videoSourceDao.selectVideoByNationalId(v.getNationalId()) ==null){
                                videoSourceDao.insertVideoSource(v);
                            }
                        }
                    } catch (Exception e) {
                        response = new Response<String>();
                        response.setCode(ErrorCode.POST_RESOURCE_ERROR.code());
                        response.setContent(ErrorCode.POST_RESOURCE_ERROR.name() + e.getMessage() + " " + "csv file read lose");
                        return  response;
                    }

                }
                response = new Response<String>();
                response.setCode(ErrorCode.NO_ERROR.code());
                response.setContent("上传成功");
                return response;
            } catch (Exception e) {
                response = new Response<String>();
                response.setCode(ErrorCode.POST_RESOURCE_ERROR.code());
                response.setContent(ErrorCode.POST_RESOURCE_ERROR.name() + e.getMessage() + " " + "deploy() fail ！");
                return response;
            }
        } else {
            response = new Response<String>();

            response.setCode(ErrorCode.POST_RESOURCE_ERROR.code());
            response.setContent(ErrorCode.POST_RESOURCE_ERROR.name() + " " + "csv文件读取失败");
            return response;
        }
    }


    @Override
    public Response getPageList(int index, int limit, String sortBy, String sortDirection) {
        try {
            response = new Response<PageDto<VideoSourceEntity>>();
            response.setCode(ErrorCode.NO_ERROR.code());
            response.setContent(videoSourceDao.selectPageList(index, limit, sortBy, sortDirection));
            return response;
        } catch (Exception e) {
            response = new Response<String>();
            response.setCode(ErrorCode.QUERY_RESOURCE_ERROR.code());
            response.setContent(ErrorCode.QUERY_RESOURCE_ERROR.name() + e.getMessage());
            return response;
        }
    }

    @Override
    public Response deleteAllVideo() {
        try {
            response = new Response<VideoSourceEntity>();
            List<VideoSourceEntity> data = videoSourceDao.selectVideoAll();
            LOGGER.debug("data[{}]",data.size());
            List<String> ids = new ArrayList<String>();
            if (data != null && data.size() !=0){
                for (VideoSourceEntity v: data) {
                    ids.add(v.getNationalId());
                }
                LOGGER.debug("ids:[{}]",JsonUtil.toJson(ids));
                videoSourceDao.deleteAll(ids);
                response.setCode(ErrorCode.NO_ERROR.code());
                response.setContent(null);
                return response;
            }else {
                response = new Response<String>();
                response.setCode(ErrorCode.QUERY_RESOURCE_ERROR.code());
                response.setContent(ErrorCode.QUERY_RESOURCE_ERROR.name() +":"+"没有视频数据");
                return response;
            }

        }catch (Exception e) {
            response = new Response<String>();
            response.setCode(ErrorCode.DELETE_RESOURCE_ERROR.code());
            response.setContent(ErrorCode.DELETE_RESOURCE_ERROR.name() + e.getMessage());
            return response;
        }
    }

    @Override
    public Response queryVideoByCondition(int index, int limit, String sortBy, String sortDirection, String intersection, String orientation, String description, String product, String vender, String dataCenter) {
        try {
            response = new Response<PageDto<VideoSourceEntity>>();
            response.setCode(ErrorCode.NO_ERROR.code());
            response.setContent(videoSourceDao.selectVideoByCondition(index,limit,sortBy,sortDirection,intersection,orientation,description,product,vender,dataCenter));
            return response;

        } catch (Exception e) {
            response = new Response<String>();
            response.setCode(ErrorCode.QUERY_RESOURCE_ERROR.code());
            response.setContent(ErrorCode.QUERY_RESOURCE_ERROR.name() + e.getMessage());
            return response;
        }
    }

    @Override
    public Response insertVideo(String body) {
        LOGGER.debug("body:[{}]", body);
        if (StringUtils.isEmpty(body)) {
            response = new Response<String>();
            response.setCode(ErrorCode.PARAMETER_ERROR.code());
            response.setContent(ErrorCode.PARAMETER_ERROR.name() + "body is empty");
            return response;
        }
        VideoSourceCreateParam param;
        try {
            param = JsonUtil.fromJson(VideoSourceCreateParam.class, body);
            LOGGER.debug("param:[{}]", param.asJson());
        } catch (Exception e) {
            response.setCode(ErrorCode.PARAMETER_ERROR.code());
            response.setContent(ErrorCode.PARAMETER_ERROR.name() + e.getMessage());
            return response;
        }
        VideoSourceEntity video = videoSourceDao.selectVideoByNationalId(param.getNationalId());
        if (video != null) {
            response = new Response<String>();
            response.setCode(ErrorCode.PARAMETER_ERROR.code());
            response.setContent(ErrorCode.PARAMETER_ERROR.name() + ":此视频数据已存在，不可重复添加");
            return response;
        }
        try {
            response = new Response<>();
            VideoSourceEntity v = new VideoSourceEntity();
            Hashids hashids = new Hashids(StringUtils.uuidGen());
            LOGGER.debug("hashids:[{}]", JsonUtil.toJson(hashids));
            String softId = hashids.encode(new Date().getTime()).toUpperCase().substring(0,8);
            LOGGER.debug("softId:[{}]", softId);
            v.setCameraId(softId);
            v.setNationalId(param.getNationalId());
            v.setShortNumber(param.getShortNumber());
            v.setIntersection(param.getIntersection());
            v.setOrientation(param.getOrientation());
            v.setDescription(param.getDescription());
            v.setIpAddress(param.getIpAddress());
            v.setSourceAddress(param.getSourceAddress());
            v.setTcp(param.getTcp());
            v.setUserName(param.getUserName());
            v.setPassword(param.getPassword());
            v.setIfForword(param.getIfForword());
            v.setForwardUrl(param.getForwardUrl());
            v.setIfLink(param.getIfLink());
            v.setIfInclude(param.getIfInclude());
            v.setStorageUrl(param.getStorageUrl());
            v.setDataCenter(param.getDataCenter());
            v.setWarrantyDate(param.getWarrantyDate());
            v.setProduct(param.getProduct());
            v.setVender(param.getVender());
            v.setBitRate(param.getBitRate());
            v.setResolvingPower(param.getResolvingPower());
            v.setType(param.getType());
            LOGGER.debug("VideoSourceEntity:[{}]", v.asJson());
            VideoSourceEntity vs;
            vs =videoSourceDao.insertVideoSourceByCondition(v);
            response.setCode(ErrorCode.NO_ERROR.code());
            response.setContent(vs.asJson());

        }catch (Exception e){
            response = new Response<String>();
            response.setCode(ErrorCode.PUT_RESOURCE_ERROR.code());
            response.setContent(ErrorCode.PUT_RESOURCE_ERROR.name() + e.getMessage());
        }
        return response;
    }

    @Override
    public Response updateVideo(String body) {
        LOGGER.debug("body:[{}]", body);
        if (StringUtils.isEmpty(body)) {
            response = new Response<String>();
            response.setCode(ErrorCode.PARAMETER_ERROR.code());
            response.setContent(ErrorCode.PARAMETER_ERROR.name() + "body is empty");
            return response;
        }
        VideoSourceUpdateParam param;
        try {
            param = JsonUtil.fromJson(VideoSourceUpdateParam.class, body);
            LOGGER.debug("param:[{}]", param.asJson());
        } catch (Exception e) {
            response.setCode(ErrorCode.PARAMETER_ERROR.code());
            response.setContent(ErrorCode.PARAMETER_ERROR.name() + e.getMessage());
            return response;
        }
        VideoSourceEntity video = videoSourceDao.selectVideoById(param.getCameraId());
        if (video == null) {
            response = new Response<String>();
            response.setCode(ErrorCode.PARAMETER_ERROR.code());
            response.setContent(ErrorCode.PARAMETER_ERROR.name() + ":此视频数据不存在");
            return response;
        }
        try {
            response = new Response<>();
            VideoSourceEntity v;
            v = new VideoSourceEntity();
            v.setCameraId(param.getCameraId());
            v.setNationalId(param.getNationalId());
            v.setShortNumber(param.getShortNumber());
            v.setIntersection(param.getIntersection());
            v.setOrientation(param.getOrientation());
            v.setDescription(param.getDescription());
            v.setIpAddress(param.getIpAddress());
            v.setSourceAddress(param.getSourceAddress());
            v.setTcp(param.getTcp());
            v.setUserName(param.getUserName());
            v.setPassword(param.getPassword());
            v.setIfForword(param.getIfForword());
            v.setForwardUrl(param.getForwardUrl());
            v.setIfLink(param.getIfLink());
            v.setIfInclude(param.getIfInclude());
            v.setStorageUrl(param.getStorageUrl());
            v.setDataCenter(param.getDataCenter());
            v.setWarrantyDate(param.getWarrantyDate());
            v.setProduct(param.getProduct());
            v.setVender(param.getVender());
            v.setBitRate(param.getBitRate());
            v.setResolvingPower(param.getResolvingPower());
            v.setType(param.getType());
            LOGGER.debug("VideoSourceEntity:[{}]", v.asJson());
            v= videoSourceDao.updateVideoSource(v);
            response.setCode(ErrorCode.NO_ERROR.code());
            response.setContent(v.asJson());

        }catch (Exception e){
            response = new Response<String>();
            response.setCode(ErrorCode.PUT_RESOURCE_ERROR.code());
            response.setContent(ErrorCode.PUT_RESOURCE_ERROR.name() + e.getMessage());
        }
        return response;

    }
}
