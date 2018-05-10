package com.anyun.cloud.service.impl;

import com.anyun.cloud.common.context.Context;
import com.anyun.cloud.common.etcd.Etcd;
import com.anyun.cloud.common.json.JsonUtil;
import com.anyun.cloud.common.string.StringUtils;
import com.anyun.cloud.common.sys.Response;
import com.anyun.cloud.dao.ServiceManagementDao;
import com.anyun.cloud.model.ErrorCode;
import com.anyun.cloud.model.dto.*;
import com.anyun.cloud.model.dto.host.Host;
import com.anyun.cloud.model.entity.ServiceBranchEntity;
import com.anyun.cloud.model.entity.ServiceEntity;
import com.anyun.cloud.model.entity.ServiceProjectEntity;
import com.anyun.cloud.model.entity.TaskBasicsEntity;
import com.anyun.cloud.model.param.*;
import com.anyun.cloud.service.ServiceManagementService;
import com.anyuncloud.common.FileUtil;
import com.anyuncloud.common.packages.ApplicationConfig;
import com.anyuncloud.common.packages.CloudBuilder;
import com.anyuncloud.common.packages.DefaultGitProjectPackageBuilderFactory;
import com.anyuncloud.common.packages.PackageBuilderFactory;
import com.coreos.jetcd.data.KeyValue;
import com.google.inject.Inject;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class ServiceManagementServiceImpl implements ServiceManagementService {
    private final static Logger LOGGER = LoggerFactory.getLogger(ServiceManagementServiceImpl.class);
    private ServiceManagementDao serviceManagementDao;
    private Context context;
    private Response response;
    private File tmpDirectory;
    static Etcd etcd;

    @Inject
    public ServiceManagementServiceImpl(Context context) {
        this.context = context;
        this.etcd = context.getEtcd();
        this.serviceManagementDao = context.getBeanByClass(ServiceManagementDao.class);
    }

    @Override
    public Response update(String body) {
        //判断body是否为空
        LOGGER.debug("body:[{}]", body);
        if (StringUtils.isEmpty(body)) {
            response = new Response<String>();
            response.setCode(ErrorCode.PARAMETER_ERROR.code());
            response.setContent(ErrorCode.PARAMETER_ERROR.name() + "body is empty");
            return response;
        }
        ServiceUpdateParam param = JsonUtil.fromJson(ServiceUpdateParam.class, body);
        try {
            response = new Response<>();
            serviceManagementDao.updateService(param.getId(), param.isStatus());
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
    public Response getDetails(String id, String user) {
        //判断id是否为空
        LOGGER.debug("id:[{}]", id);
        if ("null".equals(String.valueOf(id))) {
            response = new Response<String>();
            response.setCode(ErrorCode.PARAMETER_ERROR.code());
            response.setContent(ErrorCode.PARAMETER_ERROR.name() + "id is empty");
        }
        if (StringUtils.isEmpty(user)) {
            response = new Response<String>();
            response.setCode(ErrorCode.PARAMETER_ERROR.code());
            response.setContent(ErrorCode.PARAMETER_ERROR.name() + "user is empty");
        }

        try {
            response = new Response<ServiceDetailDto>();
            response.setCode(ErrorCode.NO_ERROR.code());
            response.setContent(serviceManagementDao.selectServiceDetail(id, user));
            return response;
        } catch (Exception e) {
            response = new Response<String>();
            response.setCode(ErrorCode.QUERY_RESOURCE_ERROR.code());
            response.setContent(ErrorCode.QUERY_RESOURCE_ERROR.name() + e.getMessage());
            return response;
        }

    }

    @Override
    public Response delete(String id) {
        //判断
        LOGGER.debug("id:[{}]", id);
        if ("null".equals(String.valueOf(id))) {
            response = new Response<String>();
            response.setCode(ErrorCode.PARAMETER_ERROR.code());
            response.setContent(ErrorCode.PARAMETER_ERROR.name() + "id is empty");
        }
        try {
            response = new Response<>();
            serviceManagementDao.deletedService(id);//在删除服务
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
    public Response getPageList(int index, int limit, String sortBy, String sortDirection) {
        try {
            response = new Response<PageDto<ServiceListDto>>();
            response.setCode(ErrorCode.NO_ERROR.code());
            response.setContent(serviceManagementDao.selectPageList(index, limit, sortBy, sortDirection));
            return response;
        } catch (Exception e) {
            response = new Response<String>();
            response.setCode(ErrorCode.QUERY_RESOURCE_ERROR.code());
            response.setContent(ErrorCode.QUERY_RESOURCE_ERROR.name() + e.getMessage());
            return response;
        }
    }

    @Override
    public Response getPageListByUser(int index, int limit, String sortBy, String sortDirection, String user) {

        try {
            response = new Response<PageDto<ServiceListDto>>();
            response.setCode(ErrorCode.NO_ERROR.code());
            response.setContent(serviceManagementDao.selectPageListByUser(index, limit, sortBy, sortDirection, user));
            return response;
        } catch (Exception e) {
            response = new Response<String>();
            response.setCode(ErrorCode.QUERY_RESOURCE_ERROR.code());
            response.setContent(ErrorCode.QUERY_RESOURCE_ERROR.name() + e.getMessage());
            return response;
        }

    }


    /**
     * 检查操作系统
     *
     * @return
     */
    private boolean getSysVer() {
        Properties prop = System.getProperties();
        String os = prop.getProperty("os.name");
        LOGGER.debug("os:[{}]", os);
        if (os != null && os.toUpperCase().contains("LINUX") || os.toUpperCase().contains("MAC")) {
            return true;
        } else {
            return false;
        }
    }


    @Override
    public Response getBranch(URL remote) {
        try {
            String dirPath = System.getProperty("user.dir");
            LOGGER.debug("dirPath:[{}]", dirPath);
            if (getSysVer()) {
                dirPath = dirPath.substring(0, dirPath.lastIndexOf("/")) + "/temp";
                LOGGER.debug("dirPath:[{}]", dirPath);
                tmpDirectory = new File(dirPath);
            } else {
                dirPath = dirPath.substring(0, dirPath.lastIndexOf("\\")) + "\\temp";
                LOGGER.debug("dirPath:[{}]", dirPath);
                tmpDirectory = new File(dirPath);
            }

            if (!tmpDirectory.exists()) {
                tmpDirectory.mkdirs();
            }
            LOGGER.debug("tmpDirectory.getName():[{}]", tmpDirectory.getName());
            LOGGER.debug("tmpDirectory.getAbsolutePath():[{}]", tmpDirectory.getAbsolutePath());
            LOGGER.debug("tmpDirectory.getPath():[{}]", tmpDirectory.getPath());
            response = new Response<ServceBranchDto>();
            List<ServiceBranchDto> list = null;
            PackageBuilderFactory factory = new DefaultGitProjectPackageBuilderFactory()
                    .withGit(remote, tmpDirectory);
            StringBuilder sb = new StringBuilder();
            List<String> versions = null;
            try {
                versions
                        = factory.getRemoteSecondVersion();
            } catch (Exception e) {
                LOGGER.debug("Exception e:[{}]", e);
                response = new Response<String>();
                response.setCode(ErrorCode.QUERY_RESOURCE_ERROR.code());
                response.setContent(ErrorCode.QUERY_RESOURCE_ERROR.name() + e.getMessage() + "地址出错");
                return response;
            }

            for (String v : versions) {
                sb.append(",").append(v);
//            if (v.contains("release-"))
//                version = v;
            }

            String branch = sb.toString();
            response.setCode(ErrorCode.NO_ERROR.code());
            response.setContent(sb);
            return response;
        } catch (Exception e) {
            response = new Response<String>();
            response.setCode(ErrorCode.QUERY_RESOURCE_ERROR.code());
            response.setContent(ErrorCode.QUERY_RESOURCE_ERROR.name() + e.getMessage());
            return response;
        }
    }


    @Override
    public Response getService(URL remote, String branch) {
        String dirPath = System.getProperty("user.dir");
        LOGGER.debug("dirPath:[{}]", dirPath);
        if (getSysVer()) {
            dirPath = dirPath.substring(0, dirPath.lastIndexOf("/")) + "/temp";
            LOGGER.debug("dirPath:[{}]", dirPath);
            tmpDirectory = new File(dirPath);
        } else {
            dirPath = dirPath.substring(0, dirPath.lastIndexOf("\\")) + "\\temp";
            LOGGER.debug("dirPath:[{}]", dirPath);
            tmpDirectory = new File(dirPath);
        }

        if (!tmpDirectory.exists()) {
            tmpDirectory.mkdirs();
        }
        LOGGER.debug("tmpDirectory.getName():[{}]", tmpDirectory.getName());
        LOGGER.debug("tmpDirectory.getAbsolutePath():[{}]", tmpDirectory.getAbsolutePath());
        LOGGER.debug("tmpDirectory.getPath():[{}]", tmpDirectory.getPath());

        try {
            response = new Response<>();
            //查看所有的分支
            PackageBuilderFactory factory = new DefaultGitProjectPackageBuilderFactory()
                    .withGit(remote, tmpDirectory);
            StringBuilder sb = new StringBuilder();
            List<String> versions
                    = factory.getRemoteSecondVersion();
            for (String v : versions) {
                sb.append(",").append(v);
            }

            LOGGER.debug("all branch :[{}]", sb);
            LOGGER.debug("param.getBranch():[{}]", branch);
            if (!sb.toString().contains(branch)) {
                return new Response<String>() {{
                    response.setCode(ErrorCode.NO_ERROR.code());
                    response.setContent(ErrorCode.NO_ERROR.name() + "分支不存在");
                }};
            }

            ApplicationConfig applicationConfig = null;
            CloudBuilder builder = null;

            try {
                builder = factory.getBuilder(branch);
            } catch (Exception e) {
                LOGGER.debug("Exception e:[{}]", e);
                response = new Response<String>();
                response.setCode(ErrorCode.QUERY_RESOURCE_ERROR.code());
                response.setContent(ErrorCode.QUERY_RESOURCE_ERROR.name() + e.getMessage() + "分支出错");
                return response;
            }

            LOGGER.debug("git项目类型: " + (factory.isServiceProject() ? "服务项目" : "任务项目"));
            applicationConfig = builder.build();
            List<ServiceGetDto> list = new ArrayList<>();
            if (factory.isServiceProject()) {
                applicationConfig.getServiceConfigs().forEach(service -> {
                    ServiceGetDto s = new ServiceGetDto();
                    s.setDesc(service.getDescribe());
                    s.setName(service.getName());
                    s.setType("SERVICE");
                    list.add(s);

                });
            } else if (factory.isTaskProject()) {
                applicationConfig.getTaskConfigs().forEach(task -> {
                    ServiceGetDto s = new ServiceGetDto();
                    s.setDesc(task.getDescribe());
                    s.setName(task.getName());
                    s.setType("TASK");
                    list.add(s);
                });
            }

            response.setContent(list);

            response.setCode(ErrorCode.NO_ERROR.code());
            LOGGER.debug(response.asJson());
            return response;
        } catch (Exception e) {
            response = new Response<String>();
            response.setCode(ErrorCode.QUERY_RESOURCE_ERROR.code());
            response.setContent(ErrorCode.QUERY_RESOURCE_ERROR.name() + e.getMessage());
            return response;
        }
    }

    @Override
    public Response createProject(String body) {
        String dirPath = System.getProperty("user.dir");
        LOGGER.debug("dirPath:[{}]", dirPath);
        if (getSysVer()) {
            dirPath = dirPath.substring(0, dirPath.lastIndexOf("/")) + "/temp";
            LOGGER.debug("dirPath:[{}]", dirPath);
            tmpDirectory = new File(dirPath);
        } else {
            dirPath = dirPath.substring(0, dirPath.lastIndexOf("\\")) + "\\temp";
            LOGGER.debug("dirPath:[{}]", dirPath);
            tmpDirectory = new File(dirPath);
        }

        if (!tmpDirectory.exists()) {
            tmpDirectory.mkdirs();
        }
        LOGGER.debug("tmpDirectory.getName():[{}]", tmpDirectory.getName());
        LOGGER.debug("tmpDirectory.getAbsolutePath():[{}]", tmpDirectory.getAbsolutePath());
        LOGGER.debug("tmpDirectory.getPath():[{}]", tmpDirectory.getPath());

        //判断body是否为空
        LOGGER.debug("body:[{}]", body);
        if (StringUtils.isEmpty(body)) {
            response = new Response<String>();
            response.setCode(ErrorCode.PARAMETER_ERROR.code());
            response.setContent(ErrorCode.PARAMETER_ERROR.name() + "body is empty");
            LOGGER.debug(response.asJson());

            return response;
        }
        ServiceCreateParam param = JsonUtil.fromJson(ServiceCreateParam.class, body);
        try {
            response = new Response<>();
            //查看所有的分支
            PackageBuilderFactory factory = new DefaultGitProjectPackageBuilderFactory()
                    .withGit(param.getRemote(), tmpDirectory);
            StringBuilder sb = new StringBuilder();
            List<String> versions
                    = factory.getRemoteSecondVersion();
            for (String v : versions) {
                sb.append(",").append(v);
            }

            LOGGER.debug("all branch :[{}]", sb);
            LOGGER.debug("param.getBranch():[{}]", param.getBranch());
            if (!sb.toString().contains(param.getBranch())) {
                response = new Response<String>();
                response.setCode(ErrorCode.NO_ERROR.code());
                response.setContent(ErrorCode.NO_ERROR.name() + "版本不存在");
                LOGGER.debug(response.asJson());
                return response;
            }


            //检查项目是否存在
            try {
                int a = serviceManagementDao.selectProjectName(param.getRemote().toString());
                LOGGER.debug("----count:[{}]", a);
                if (a != 0) {
                    response = new Response<String>();
                    response.setCode(ErrorCode.QUERY_RESOURCE_ERROR.code());
                    response.setContent(ErrorCode.QUERY_RESOURCE_ERROR.name() + "项目已存在");
                    return response;
                }
            } catch (Exception e) {
                response = new Response<String>();
                response.setCode(ErrorCode.PUT_RESOURCE_ERROR.code());
                response.setContent(ErrorCode.PUT_RESOURCE_ERROR.name() + e.getMessage() + "项目已存在");
                LOGGER.debug(response.asJson());

                return response;
            }
            //截取项目名称
            String gitUrl = param.getRemote().toString();
            LOGGER.debug("gitUrl:[{}]------------------------------", gitUrl);
            String subUrl = gitUrl.substring(gitUrl.lastIndexOf("/") + 1);
            LOGGER.debug("subUrl:[{}]------------------------------", subUrl);

            String projectName = "";
            if (subUrl.contains(".git")) {
                projectName = subUrl.split(".git")[0].trim();
            } else {
                projectName = subUrl.trim();
            }
            LOGGER.debug("projectName:[{}]------------------------------", projectName);

            //街区项目短名称
            String shortUrl = "";
            int i = 0;
            while (i < 2) {
                int lastFirst = gitUrl.lastIndexOf('/');
                LOGGER.debug("lastFirst:[{}]------------------------------", lastFirst);

                shortUrl = gitUrl.substring(lastFirst) + shortUrl;
                gitUrl = gitUrl.substring(0, lastFirst);
                LOGGER.debug("gitUrl212121:[{}]------------------------------", gitUrl);

                i++;
            }

            LOGGER.debug("shortUrl:[{}]------------------------------", shortUrl.substring(1));


            //截取分支

            String branch = param.getBranch();
            String ver = "";
            if (branch.contains("/")) {
                ver = branch.substring(param.getBranch().lastIndexOf("/") + 1);
            } else {
                ver = branch;
            }
            LOGGER.debug("ver[{}]",ver);
            //项目入库
            ServiceProjectEntity p = new ServiceProjectEntity();
            p.setName(projectName);
            p.setGitUrl(param.getRemote().toString());
            p.setShortUrl(shortUrl.substring(1));
            p.setAppId(param.getAid());
            p.setUser(param.getUser());
            p.setBranch(ver);
            int pid = serviceManagementDao.insertProject(p); // 插入项目

            //版本入库
            List<ServiceBranchEntity> entityList = new ArrayList<>();
            for (int m = 0; m < versions.size(); m++) {
                String version = versions.get(m);
                ServiceBranchEntity b = new ServiceBranchEntity();
                b.setName(version);
                b.setProjectId(pid);
                entityList.add(b);
            }
            serviceManagementDao.insertBranch(entityList);//插入版本

            ApplicationConfig applicationConfig = null;
            CloudBuilder builder = null;
            try {
                //服务入库
                builder = factory.getBuilder(param.getBranch());
            } catch (Exception e) {
                LOGGER.debug("Exception e:[{}]", e);
                //如果版本出错，删除有关的项目表和版本表
                //先删除分支
                serviceManagementDao.deletedBranch(pid);
                //在删除项目
                serviceManagementDao.deleteProject(pid);
                response = new Response<String>();
                response.setCode(ErrorCode.PUT_RESOURCE_ERROR.code());
                response.setContent(ErrorCode.PUT_RESOURCE_ERROR.name() + e.getMessage() + "分支出错");
                LOGGER.debug(response.asJson());
                return response;
            }
            LOGGER.debug("git项目类型: " + (factory.isServiceProject() ? "服务项目" : "任务项目"));
            applicationConfig = builder.build();

            List<ServiceEntity> serviceList = new ArrayList<>();//服务
            List<TaskBasicsEntity> taskList = new ArrayList<>();//任务
            String type = null;
            if (factory.isServiceProject()) {
                //更新类型
                type = "SERVICE";
                serviceManagementDao.updateProjectType(type, pid);

                applicationConfig.getServiceConfigs().forEach(service -> {

                    //先查找对应版本的id
                    int bid = serviceManagementDao.selectBranchId(param.getBranch(), pid);
                    ServiceEntity s = new ServiceEntity();
                    s.setId(service.getId());
                    s.setBranchId(bid);
                    s.setDesc(service.getDescribe());
                    s.setName(service.getName());
                    s.setPrivate(service.isPrivate());
                    serviceList.add(s);

                });
            } else if (factory.isTaskProject()) {
                //更新类型
                type = "TASK";
                serviceManagementDao.updateProjectTypeIsTask(type, pid);

                applicationConfig.getTaskConfigs().forEach(task -> {
                    //先查找对应版本的id
                    int bid = serviceManagementDao.selectBranchId(param.getBranch(), pid);
                    TaskBasicsEntity s = new TaskBasicsEntity();
                    s.setId(task.getId());
                    s.setBranchId(bid);
                    s.setDesc(task.getDescribe());
                    s.setName(task.getName());
//                    s.setCrontabExpression(task.getCrontabExpression());
                    taskList.add(s);
                });
            }
            if (factory.isServiceProject()) {
                serviceManagementDao.insertService(serviceList);//插入服务
            } else if (factory.isTaskProject()) {
                serviceManagementDao.insertTask(taskList);//插入服务
            }
            response.setCode(ErrorCode.NO_ERROR.code());
            response.setContent(response);
            return response;
        } catch (Exception e) {
            response = new Response<String>();
            response.setCode(ErrorCode.PUT_RESOURCE_ERROR.code());
            response.setContent(ErrorCode.PUT_RESOURCE_ERROR.name() + e.getMessage());
            LOGGER.debug(response.asJson());

            return response;
        }
    }


    @Override
    public Response rollback(String body) {
        //判断body是否为空
        LOGGER.debug("body:[{}]", body);
        if (StringUtils.isEmpty(body)) {
            response = new Response<String>();
            response.setCode(ErrorCode.PARAMETER_ERROR.code());
            response.setContent(ErrorCode.PARAMETER_ERROR.name() + "body is empty");
            return response;
        }

        ServiceRollBackParam param = JsonUtil.fromJson(ServiceRollBackParam.class, body);
        try {
            response = new Response<>();
            //根据新版本查询id
            int a = serviceManagementDao.selectBranchIdByName(param.getName(), param.getPid(), param.getUser());
            //更新
            serviceManagementDao.updateBranchId(a, param.getBid());
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
    public Response queryByCondition(int index, int limit, String sortBy, String sortDirection, String sname, String bname, String pname, String shortUrl, String user) {
        try {
            response = new Response<PageDto<ServiceConditionDto>>();
            response.setCode(ErrorCode.NO_ERROR.code());
            response.setContent(serviceManagementDao.QueryConditionService(index, limit, sortBy, sortDirection, sname, bname, pname, user, shortUrl));
            return response;
        } catch (Exception e) {
            response = new Response<String>();
            response.setCode(ErrorCode.QUERY_RESOURCE_ERROR.code());
            response.setContent(ErrorCode.QUERY_RESOURCE_ERROR.name() + e.getMessage());
            return response;
        }
    }

    @Override
    public Response deleteProject(int id, String type, String user) {
        //判断
        LOGGER.debug("id:[{}]", id);
        if ("null".equals(String.valueOf(id))) {
            response = new Response<String>();
            response.setCode(ErrorCode.PARAMETER_ERROR.code());
            response.setContent(ErrorCode.PARAMETER_ERROR.name() + "id is empty");
        }

        LOGGER.debug("id", id);
        try {
            response = new Response<>();
            //查询项目下所有的分支
            List<Integer> list = serviceManagementDao.queryBranchId(id);
            LOGGER.debug("list[{}]", list);
            LOGGER.debug("type[{}]", type);

            if (type.equals("SERVICE")) {
                String users = serviceManagementDao.queryUserById(id);
                LOGGER.debug("users--------------[{}]", users);
                LOGGER.debug("user----------------[{}]", user);
                if (!user.equals(users)) {
                    response = new Response<>();
                    response.setCode(ErrorCode.QUERY_RESOURCE_ERROR.code());
                    response.setContent(ErrorCode.QUERY_RESOURCE_ERROR.name() + "用户不对");
                    return response;
                }
                for (int m = 0; m < list.size(); m++) {
                    LOGGER.debug("m[{}]", list.get(m));
                    serviceManagementDao.deletedService1(list.get(m));//先删除服务
                }
                LOGGER.debug("========================================");
                serviceManagementDao.deletedBranch1(id);//再删除版本
                serviceManagementDao.deleteProject1(id);//最后删除项目
            } else if (type.equals("TASK")) {
                for (int m = 0; m < list.size(); m++) {
                    LOGGER.debug("m[{}]", list.get(m));
                    serviceManagementDao.deleteTask(list.get(m));
                }
                serviceManagementDao.deletedBranch1(id);//再删除版本
                serviceManagementDao.deleteProject1(id);//最后删除项目
            }
            response.setCode(ErrorCode.NO_ERROR.code());
            response.setContent(response);
        } catch (Exception e) {
            response = new Response<String>();
            response.setCode(ErrorCode.DELETE_RESOURCE_ERROR.code());
            response.setContent(ErrorCode.DELETE_RESOURCE_ERROR.name() + e.getMessage());
        }
        return response;
    }

    @Override
    public Response getPageListProject(int index, int limit, String sortBy, String sortDirection) {
        try {
            response = new Response<PageDto<ProjectListDto>>();
            response.setCode(ErrorCode.NO_ERROR.code());
            response.setContent(serviceManagementDao.selectPageProjectList(index, limit, sortBy, sortDirection));
            return response;
        } catch (Exception e) {
            response = new Response<String>();
            response.setCode(ErrorCode.QUERY_RESOURCE_ERROR.code());
            response.setContent(ErrorCode.QUERY_RESOURCE_ERROR.name() + e.getMessage());
            return response;
        }
    }

    @Override
    public Response rollbackService(String body) {
        String dirPath = System.getProperty("user.dir");
        LOGGER.debug("dirPath:[{}]", dirPath);
        if (getSysVer()) {
            dirPath = dirPath.substring(0, dirPath.lastIndexOf("/")) + "/temp";
            LOGGER.debug("dirPath:[{}]", dirPath);
            tmpDirectory = new File(dirPath);
        } else {
            dirPath = dirPath.substring(0, dirPath.lastIndexOf("\\")) + "\\temp";
            LOGGER.debug("dirPath:[{}]", dirPath);
            tmpDirectory = new File(dirPath);
        }

        if (!tmpDirectory.exists()) {
            tmpDirectory.mkdirs();
        }

        //判断body是否为空
        LOGGER.debug("body:[{}]", body);
        if (StringUtils.isEmpty(body)) {
            response = new Response<String>();
            response.setCode(ErrorCode.PARAMETER_ERROR.code());
            response.setContent(ErrorCode.PARAMETER_ERROR.name() + "body is empty");
            return response;
        }
        ServiceRollbackServiceParam param = JsonUtil.fromJson(ServiceRollbackServiceParam.class, body);
        ApplicationConfig applicationConfig = null;
        CloudBuilder builder = null;
        try {
            response = new Response<>();
            //根据项目id查出git地址
            String gitUrl = serviceManagementDao.selectProjectGitId(param.getId());
            LOGGER.debug("gitUrl[{}]-----------", gitUrl);
            //查出git地址下所有的版本
            PackageBuilderFactory factory = new DefaultGitProjectPackageBuilderFactory()
                    .withGit(new URL(gitUrl), tmpDirectory);
            StringBuilder sb = new StringBuilder();
            List<String> versions = factory.getRemoteSecondVersion();

            System.out.println("========versions:" + versions);
            for (String v : versions) {
                sb.append(",").append(v);
            }
            LOGGER.debug("Branch[{}]-----------", sb);
            //判断回滚版本里是否有服务
            try {
                builder = factory.getBuilder(param.getBranch());
            } catch (Exception e) {
                response = new Response<String>();
                response.setCode(ErrorCode.PUT_RESOURCE_ERROR.code());
                response.setContent(ErrorCode.PUT_RESOURCE_ERROR.name() + e.getMessage() + "分支出错");
                return response;
            }

            //先删除原来服务
            List<Integer> list = serviceManagementDao.queryBranchId(param.getId());
            LOGGER.debug("list[{}]", list);
            for (int m = 0; m < list.size(); m++) {
                LOGGER.debug("m[{}]", list.get(m));
                //先删除服务或版本
                serviceManagementDao.deletedService1(list.get(m));
                serviceManagementDao.deleteTask(list.get(m));
            }


            LOGGER.debug("git项目类型: " + (factory.isServiceProject() ? "服务项目" : "任务项目"));
            applicationConfig = builder.build();
            //截取分支

            String branch = param.getBranch();
            String ver = "";
            if (branch.contains("/")) {
                ver = branch.substring(param.getBranch().lastIndexOf("/") + 1);
            } else {
                ver = branch;
            }
            LOGGER.debug("ver[{}]",ver);


            //更新项目中的版本信息
            serviceManagementDao.updateProjectBranch(ver, param.getId());
            //添加回滚版本的服务
            List<ServiceEntity> serviceList = new ArrayList<>();//服务
            List<TaskBasicsEntity> taskList = new ArrayList<>();//任务
            String type = null;
            if (factory.isServiceProject()) {
                applicationConfig.getServiceConfigs().forEach(service -> {

                    //先查找对应版本的id
                    int bid = serviceManagementDao.selectBranchId(param.getBranch(), param.getId());
                    ServiceEntity s = new ServiceEntity();
                    s.setId(service.getId());
                    s.setBranchId(bid);
                    s.setDesc(service.getDescribe());
                    s.setName(service.getName());
                    s.setPrivate(service.isPrivate());
                    serviceList.add(s);

                });
            } else if (factory.isTaskProject()) {

                applicationConfig.getTaskConfigs().forEach(task -> {
                    //先查找对应版本的id
                    int bid = serviceManagementDao.selectBranchId(param.getBranch(), param.getId());
                    TaskBasicsEntity s = new TaskBasicsEntity();
                    s.setId(task.getId());
                    s.setBranchId(bid);
                    s.setDesc(task.getDescribe());
                    s.setName(task.getName());
                    s.setCrontabExpression(task.getCrontabExpression());
                    taskList.add(s);
                });
            }
            if (factory.isServiceProject()) {
                serviceManagementDao.insertService(serviceList);//插入服务
            } else if (factory.isTaskProject()) {
                serviceManagementDao.insertTask(taskList);//插入服务
            }
            response.setCode(ErrorCode.NO_ERROR.code());
            response.setContent(response);
            return response;

        } catch (Exception e) {
            response = new Response<String>();
            response.setCode(ErrorCode.PUT_RESOURCE_ERROR.code());
            response.setContent(ErrorCode.PUT_RESOURCE_ERROR.name() + e.getMessage());
            return response;
        }
    }

    @Override
    public Response updatePrivate(String body) {
        //判断body是否为空
        LOGGER.debug("body:[{}]", body);
        if (StringUtils.isEmpty(body)) {
            response = new Response<String>();
            response.setCode(ErrorCode.PARAMETER_ERROR.code());
            response.setContent(ErrorCode.PARAMETER_ERROR.name() + "body is empty");
            return response;
        }

        ServiceUpdatePrivateParam param = JsonUtil.fromJson(ServiceUpdatePrivateParam.class, body);
        try {
            //查到版本id
            int id = serviceManagementDao.queryUserByServiceId(param.getId());
            LOGGER.debug("id[{}]", id);
            //查用户
            String users = serviceManagementDao.queryUserByBranchId(id);
            LOGGER.debug("users[{}]", users);
            if (!param.getUser().equals(users)) {
                LOGGER.debug("getuser[{}]", param.getUser());
                return new Response<String>() {{
                    response.setCode(ErrorCode.NO_ERROR.code());
                    response.setContent(ErrorCode.NO_ERROR.name() + "用户不对");
                }};
            }
        } catch (Exception e) {
            response = new Response<String>();
            response.setCode(ErrorCode.QUERY_RESOURCE_ERROR.code());
            response.setContent(ErrorCode.QUERY_RESOURCE_ERROR.name() + e.getMessage() + "用户不对");
            return response;
        }
        try {
            response = new Response<>();
            serviceManagementDao.updatePrivate(param.getId(), param.isPrivate(), param.getUser());
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
    public Response queryBranchByProjectId(int id) {
        LOGGER.debug("id:[{}]", id);
        if ("null".equals(String.valueOf(id))) {
            response = new Response<String>();
            response.setCode(ErrorCode.PARAMETER_ERROR.code());
            response.setContent(ErrorCode.PARAMETER_ERROR.name() + "id is empty");
            return response;
        }
        try {
            response = new Response<List<ServiceBranchDto>>();
            List<String> versions
                    = serviceManagementDao.queryBranchByProjectId(id);
            StringBuilder sb = new StringBuilder();
            for (String v : versions) {
                sb.append(",").append(v);
//            if (v.contains("release-"))
//                version = v;
            }
            response.setContent(sb);
            response.setCode(ErrorCode.NO_ERROR.code());
            LOGGER.debug(response.asJson());
            return response;
        } catch (Exception e) {
            response = new Response<String>();
            response.setCode(ErrorCode.QUERY_RESOURCE_ERROR.code());
            response.setContent(ErrorCode.QUERY_RESOURCE_ERROR.name() + e.getMessage());
            return response;
        }
    }


    @Override
    public Response queryServiceByProjectId(int id, String type, String user) {
        LOGGER.debug("id:[{}]", id);
        if ("null".equals(String.valueOf(id))) {
            response = new Response<String>();
            response.setCode(ErrorCode.PARAMETER_ERROR.code());
            response.setContent(ErrorCode.PARAMETER_ERROR.name() + "id is empty");
            return response;
        }
        try {
            response = new Response<List<ServiceListDto>>();
            List<ServiceListDto> serviceListDtos = new ArrayList<>();
            List<TaskListDto> taskListDtos = new ArrayList<>();
            //查询项目下所有的分支
            List<Integer> list = serviceManagementDao.queryBranchId(id);
            LOGGER.debug("list[{}]", list);
            LOGGER.debug("type[{}]", type);
            if (type.equals("SERVICE")) {
                for (int m = 0; m < list.size(); m++) {
                    String users = serviceManagementDao.queryUserById(id);
                    LOGGER.debug("users--------------[{}]", users);
                    LOGGER.debug("user----------------[{}]", user);
                    if (!user.equals(users)) {
                        response = new Response<String>();
                        response.setCode(ErrorCode.QUERY_RESOURCE_ERROR.code());
                        response.setContent(ErrorCode.QUERY_RESOURCE_ERROR.name() + "用户不对");
                        return response;
                    }

                    LOGGER.debug("m[{}]", list.get(m));
                    serviceListDtos = serviceManagementDao.selectServiceList(list.get(m));
                    response.setContent(serviceListDtos);
                }
            } else if (type.equals("TASK")) {
                for (int m = 0; m < list.size(); m++) {
                    LOGGER.debug("m[{}]", list.get(m));
                    taskListDtos = serviceManagementDao.selectTypeList(list.get(m));
                    response.setContent(taskListDtos);
                }
            }
            response.setCode(ErrorCode.NO_ERROR.code());
            LOGGER.debug(response.asJson());
            return response;
        } catch (Exception e) {
            response = new Response<String>();
            response.setCode(ErrorCode.QUERY_RESOURCE_ERROR.code());
            response.setContent(ErrorCode.QUERY_RESOURCE_ERROR.name() + e.getMessage());
            return response;
        }

    }

    @Override
    public Response queryProjectByCondition(int index, int limit, String sortBy, String sortDirection, String pname, String shortUrl, String type) {
        try {
            response = new Response<PageDto<ProjectListDto>>();
            response.setCode(ErrorCode.NO_ERROR.code());
            response.setContent(serviceManagementDao.QueryConditionProject(index, limit, sortBy, sortDirection, pname, shortUrl, type));
            return response;
        } catch (Exception e) {
            response = new Response<String>();
            response.setCode(ErrorCode.QUERY_RESOURCE_ERROR.code());
            response.setContent(ErrorCode.QUERY_RESOURCE_ERROR.name() + e.getMessage());
            return response;
        }
    }

    @Override
    public Response rollbackGetService(String gitUrl, String branch) {
        String dirPath = System.getProperty("user.dir");
        LOGGER.debug("dirPath:[{}]", dirPath);
        if (getSysVer()) {
            dirPath = dirPath.substring(0, dirPath.lastIndexOf("/")) + "/temp";
            LOGGER.debug("dirPath:[{}]", dirPath);
            tmpDirectory = new File(dirPath);
        } else {
            dirPath = dirPath.substring(0, dirPath.lastIndexOf("\\")) + "\\temp";
            LOGGER.debug("dirPath:[{}]", dirPath);
            tmpDirectory = new File(dirPath);
        }

        if (!tmpDirectory.exists()) {
            tmpDirectory.mkdirs();
        }
        LOGGER.debug("tmpDirectory.getName():[{}]", tmpDirectory.getName());
        LOGGER.debug("tmpDirectory.getAbsolutePath():[{}]", tmpDirectory.getAbsolutePath());
        LOGGER.debug("tmpDirectory.getPath():[{}]", tmpDirectory.getPath());

        try {
            response = new Response<>();
            //查看所有的分支
            PackageBuilderFactory factory = new DefaultGitProjectPackageBuilderFactory()
                    .withGit(new URL(gitUrl), tmpDirectory);
            StringBuilder sb = new StringBuilder();
            List<String> versions
                    = factory.getRemoteSecondVersion();
            for (String v : versions) {
                sb.append(",").append(v);
            }

            LOGGER.debug("all branch :[{}]", sb);
            LOGGER.debug("param.getBranch():[{}]", branch);
            if (!sb.toString().contains(branch)) {
                return new Response<String>() {{
                    response.setCode(ErrorCode.NO_ERROR.code());
                    response.setContent(ErrorCode.NO_ERROR.name() + "分支不存在");
                }};
            }

            ApplicationConfig applicationConfig = null;
            CloudBuilder builder = null;

            try {
                builder = factory.getBuilder(branch);
            } catch (Exception e) {
                LOGGER.debug("Exception e:[{}]", e);
                response = new Response<String>();
                response.setCode(ErrorCode.QUERY_RESOURCE_ERROR.code());
                response.setContent(ErrorCode.QUERY_RESOURCE_ERROR.name() + e.getMessage() + "分支出错");
                return response;
            }
            LOGGER.debug("git项目类型: " + (factory.isServiceProject() ? "服务项目" : "任务项目"));
            applicationConfig = builder.build();


            List<ServiceGetDto> list = new ArrayList<>();
            if (factory.isServiceProject()) {
                applicationConfig.getServiceConfigs().forEach(service -> {
                    ServiceGetDto s = new ServiceGetDto();
                    s.setDesc(service.getDescribe());
                    s.setName(service.getName());
                    s.setType("SERVICE");
                    list.add(s);

                });
            } else if (factory.isTaskProject()) {
                applicationConfig.getTaskConfigs().forEach(task -> {
                    ServiceGetDto s = new ServiceGetDto();
                    s.setDesc(task.getDescribe());
                    s.setName(task.getName());
                    s.setType("TASK");
                    list.add(s);
                });
            }

            response.setContent(list);

            response.setCode(ErrorCode.NO_ERROR.code());
            LOGGER.debug(response.asJson());
            return response;
        } catch (Exception e) {
            response = new Response<String>();
            response.setCode(ErrorCode.PUT_RESOURCE_ERROR.code());
            response.setContent(ErrorCode.PUT_RESOURCE_ERROR.name() + e.getMessage());
            return response;
        }
    }

    @Test
    public void test() {
        //传入参数key值
        List<KeyValue> list = etcd.getWithPrefix("/discovery/node/", true);
        List<String> hosts = new ArrayList<>();
        list.forEach(l -> {
            String s = new String(l.getKey().getBytes());
            LOGGER.debug("------s:[{}]", s);
            String host = "/discovery/node/" + s.split("/discovery/node/")[1].split("/")[0];
            LOGGER.debug("-----host:[{}]", host);
            if (!hosts.contains(host))
                hosts.add(host);
        });
    }


    private  List<ServiceCatalogDto>   getEtcdData(){
        //传入参数key值
        List<KeyValue> list = etcd.getWithPrefix("/container_services/", true);
        List<ServiceCatalogDto> sList = new ArrayList<>();
        for (KeyValue kv : list) {
            ServiceCatalogDto sDto = new ServiceCatalogDto();
            String s = new String(kv.getKey().getBytes());
            String sid = s.split("/container_services/")[1].split("/")[0];
            if (sid == null || sid.trim().equals("")) {
                continue;
            }
            sid = sid.trim();
            if (sList.isEmpty()) {
                sDto.setId(sid);
                List<KeyValue> nodeKvList = etcd.getWithPrefix("/container_services/" + sid, true);
                sDto.setNodes(nodeKvList.size());
                KeyValue node = nodeKvList.get(0);
                String nodeKey = new String(node.getKey().getBytes());
                List<KeyValue> l = etcd.getWithPrefix(nodeKey, false);
                l.forEach(z -> {
                    String k = new String(z.getKey().getBytes());
                    String v = new String(z.getValue().getBytes());
                    LOGGER.debug("key:[{}]", k);
                    LOGGER.debug("value:[{}]", v);
                    Map<String, String> map = JsonUtil.fromJson(Map.class, v);
                    map.forEach((mk, mv) -> {
                        if ("info".equals(mk)) {
                            sDto.setMessagePipeline(mv);
                        }
                        if ("data_center".equals(mk)) {
                            sDto.setDatacenter(mv);
                        }

                    });
                });
                sList.add(sDto);
                continue;
            }

            boolean mark = false;
            for (ServiceCatalogDto serviceCatalogDto : sList) {
                if (serviceCatalogDto.getId().equals(sid)) {
                    mark = true;
                }
            }

            if (mark == true) {
                continue;
            }

            sDto.setId(sid);
            List<KeyValue> nodeKvList = etcd.getWithPrefix("/container_services/" + sid, true);
            sDto.setNodes(nodeKvList.size());
            KeyValue node = nodeKvList.get(0);
            String nodeKey = new String(node.getKey().getBytes());
            List<KeyValue> l = etcd.getWithPrefix(nodeKey, false);
            l.forEach(z -> {
                String k = new String(z.getKey().getBytes());
                String v = new String(z.getValue().getBytes());
                LOGGER.debug("key:[{}]", k);
                LOGGER.debug("value:[{}]", v);
                Map<String, String> map = JsonUtil.fromJson(Map.class, v);
                map.forEach((mk, mv) -> {
                    if ("info".equals(mk)) {
                        sDto.setMessagePipeline(mv);
                    }
                    if ("data_center".equals(mk)) {
                        sDto.setDatacenter(mv);
                    }

                });
            });
            sList.add(sDto);
        }

        LOGGER.debug(JsonUtil.toJson(sList));
        return  sList;
    }



    @Override
    public Response queryServiceCatalogByCondition(int index, int limit, String sortBy, String sortDirection, String sname, String pname, String environmental) {
        List<ServiceCatalogDto>  sList=  getEtcdData();
        try {
            response = new Response<PageDto<ServiceConditionDto>>();
            response.setCode(ErrorCode.NO_ERROR.code());
            PageDto   pageDto= serviceManagementDao.QueryConditionService(index, limit, sortBy, sortDirection, sname,pname,environmental);
            List<ServiceConditionDto>  dtoList=pageDto.getData();
           if(dtoList==null || dtoList.isEmpty()){
               response.setContent(pageDto);
               return response;
           }

            List<ServiceEtedDto> serviceEtedDto = new ArrayList<>();
            for(ServiceConditionDto  s: dtoList){
                ServiceEtedDto dto = new ServiceEtedDto();
                    dto.setServiceConditionDto(s);
                    for(ServiceCatalogDto service: sList){
                        if(service.id.equals(s.getId())){
                            dto.setServiceCatalogDto(service);
                            break;
                        }
                    }
                    serviceEtedDto.add(dto);
            }


            pageDto.setData(serviceEtedDto);
            response.setContent(pageDto);
            LOGGER.debug(response.asJson());
            return response;

        } catch (Exception e) {
            response = new Response<String>();
            response.setCode(ErrorCode.QUERY_RESOURCE_ERROR.code());
            response.setContent(ErrorCode.QUERY_RESOURCE_ERROR.name() + e.getMessage());
            return response;
        }
    }

    @Override
    public Response updateProject(String body) {
        //判断body是否为空
        LOGGER.debug("body:[{}]", body);
        if (StringUtils.isEmpty(body)) {
            response = new Response<String>();
            response.setCode(ErrorCode.PARAMETER_ERROR.code());
            response.setContent(ErrorCode.PARAMETER_ERROR.name() + "body is empty");
            return response;
        }
        ProjectUpdateParam param = JsonUtil.fromJson(ProjectUpdateParam.class, body);
        try {
            response = new Response<>();
            LOGGER.debug("isEnvironmental[{}]:", param.isEnvironmental());
            serviceManagementDao.updateProject(param.getId(), param.isEnvironmental());
            response.setCode(ErrorCode.NO_ERROR.code());
            return response;
        } catch (Exception e) {
            response = new Response<String>();
            response.setCode(ErrorCode.POST_RESOURCE_ERROR.code());
            response.setContent(ErrorCode.POST_RESOURCE_ERROR.name() + e.getMessage());
            return response;
        }
    }


}
