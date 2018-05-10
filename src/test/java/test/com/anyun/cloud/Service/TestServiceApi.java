package test.com.anyun.cloud.Service;


import com.anyun.cloud.model.param.*;
import org.apache.commons.collections.map.HashedMap;
import org.junit.Test;
import test.com.anyun.cloud.BaseApiTest;

import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class TestServiceApi extends BaseApiTest {

    /**
     * 根据id删除服务（弃用）
     * @throws Exception
     */
    @Test
    public void delete() throws Exception {
        String id = "0AAEB6F5B4112BCC79D6C270451E63E8";
        String json = hostWebClient.delete("/service/delete/"+id, null);
        System.out.println(json);
    }

    /**
     * 服务编辑
     * @throws Exception
     */
    @Test
    public void update() throws Exception {
        ServiceUpdateParam param = new ServiceUpdateParam();
        param.setId("0AAEB6F5B4112BCC79D6C270451E63E8");
        param.setStatus(false);
        String json = hostWebClient.post("/service/update/service", param.asJson());
        System.out.println(json);
    }

    /**
     * 服务目录(弃用)
     * @throws Exception
     */
    @Test
    public void getPageList() throws Exception {
        Map<String,Object> param=new HashMap<>();
        param.put("index",1);
        param.put("limit",10);
        param.put("sortBy","");
        param.put("sortDirection","");
        String json = hostWebClient.get("/service/page" , param);
        System.out.println("json:" + json);
    }

    /**
     * 服务列表(弃用)
     * @throws Exception
     */
    @Test
    public void getPageListByUser() throws Exception {
        Map<String,Object> param=new HashMap<>();
        param.put("index",1);
        param.put("limit",21);
        param.put("sortBy","");
        param.put("user","huahua");
        param.put("sortDirection","");

        String json = hostWebClient.get("/service/page/user" , param);
        System.out.println("json:" + json);
    }

    /**
     * 查询服务详情
     */
    @Test
    public void serviceDetail() throws Exception {
        Map<String,Object> param = new HashedMap();
        param.put("id","0AAEB6F5B4112BCC79D6C270451E63E8");
        param.put("user","huahua");
        String json = hostWebClient.get("/service/details", param);
        System.out.println(json);
    }

    /**
     * 根据url查询所有的分支
     *
     */
    @Test
    public void getBranch() throws Exception{
        URL remote = new URL("http://scm.service.dc-anyuncloud.consul:80/anyuncloud/example-service1.git");
        Map<String,Object> param=new HashMap<>();
        param.put("remote",remote);
        String json = hostWebClient.get("/service/getbranch" , param);
        System.out.println("json:" + json);
    }


    /**
     * 预览
     * @throws Exception
     */
    @Test
    public void getService() throws Exception{
        URL remote = new URL("http://scm.service.dc-anyuncloud.consul:80/anyuncloud/example-service1.git");
        Map<String,Object> param=new HashMap<>();
        param.put("remote",remote);
        param.put("branch","refs/tags/release-20180124001");
        String json = hostWebClient.get("/service/getservice", param);
        System.out.println(json);
    }


    /**
     * 创建项目
     * @throws Exception
     */
    @Test
    public void createProject() throws Exception{
//        URL remote = new URL("http://scm.service.dc-anyuncloud.consul:80/anyuncloud/example-service1.git");
        ServiceCreateParam param = new ServiceCreateParam();
        param.setRemote(new URL("http://scm.service.dc-anyuncloud.consul:80/anyuncloud/example-service1.git"));
        param.setBranch("refs/tags/release-20180124001");
        param.setUser("huahua");
        param.setAid(1212);
        String json = hostWebClient.put("/service/create/project", param.asJson());
        System.out.println(json);
    }

    /**
     * 服务回滚（弃用）
     * @throws Exception
     */
    @Test
    public void rollback() throws Exception{
        ServiceRollBackParam param = new ServiceRollBackParam();
        param.setName("refs/tags/build_20171113001");
        param.setPid(3);
        param.setBid(7);
        param.setUser("huahua");
        String json = hostWebClient.post("/service/rollback", param.asJson());
        System.out.println(json);
    }

    /**
     * 根据条件查询服务
     * @throws Exception
     */
    @Test
    public void QueryByCondition()throws Exception {
        Map<String,Object> param=new HashMap<>();
        param.put("index",1);
        param.put("limit",4);
        param.put("sortBy","");
        param.put("sortDirection","desc");

        param.put("sname","");
//        param.put("pname","");
//        param.put("bname","");
//        param.put("gitUrl","");
//        param.put("user","");
        String json = hostWebClient.get("/service/queryByCondition", param);
        System.out.println(json);
    }


    /**
     * 服务目录
     * @throws Exception
     */
    @Test
    public void ServiceCatalogByCondition()throws Exception {
        Map<String,Object> param=new HashMap<>();
        param.put("index",1);
        param.put("limit",4);
        param.put("sortBy","");
        param.put("sortDirection","desc");

        param.put("sname","");
        param.put("pname","video");
        param.put("environmental","");
        String json = hostWebClient.get("/service/queryServiceCatalog", param);
        System.out.println(json);
    }

    /**
     * 删除项目
     * @throws Exception
     */
    @Test
    public void deleteProject() throws Exception {
        Map<String,Object> param=new HashMap<>();
        param.put("id",59);
        param.put("type","SERVICE");
        param.put("user","huahua");
        String json = hostWebClient.delete("/service/delete/project", param);
        System.out.println(json);
    }


    /**
     * 项目列表（弃用）
     * @throws Exception
     */
    @Test
    public void ProjectList() throws Exception{
        Map<String,Object> param=new HashMap<>();
        param.put("index",1);
        param.put("limit",21);
        param.put("sortBy","");
        param.put("sortDirection","");
        param.put("user","huahua");
        String json = hostWebClient.get("/service/page/project" , param);
        System.out.println("json:" + json);
    }

    /**
     * 服务回滚
     * @throws Exception
     */
    @Test
    public void rollbackService() throws Exception{
        ServiceRollbackServiceParam param = new ServiceRollbackServiceParam();
        param.setBranch("refs/tags/release-20180124001");
        param.setId(12);
        String json = hostWebClient.put("/service/rollbackService", param.asJson());
        System.out.println(json);
    }

    /**
     * 修改是否私有（弃用）
     */
    @Test
    public void updatePrivate()throws Exception {
        ServiceUpdatePrivateParam param = new ServiceUpdatePrivateParam();
        param.setId("0AAEB6F5B4112BCC79D6C270451E63E8");
        param.setPrivate(true);
        param.setUser("huahua");
        String json = hostWebClient.post("/service/update/Private", param.asJson());
        System.out.println(json);
    }

    /**
     * 版本列表查询
     */
    @Test
    public void queryBranchByProjectId()throws Exception {
        Map<String,Object> param=new HashMap<>();
        param.put("id",15);
        String json = hostWebClient.get("/service/queryBranch" , param);
        System.out.println("json:" + json);
    }

    /**
     *根据项目id查询所有服务
     */
    @Test
    public void queryServiceByProjectId()throws Exception {
        Map<String,Object> param=new HashMap<>();
        param.put("type","TASK");
        param.put("id",12);
        param.put("user","huahua");
        String json = hostWebClient.get("/service/queryService" , param);
        System.out.println("json:" + json);
    }


    /**
     * 根据条件查询项目
     * @throws Exception
     */
    @Test
    public void QueryProjectByCondition()throws Exception {
        Map<String,Object> param=new HashMap<>();
        param.put("index",1);
        param.put("limit",111);
        param.put("sortBy","");
        param.put("sortDirection","desc");

//        param.put("type","service");
//        param.put("pname","");
        param.put("shortUrl","ser");
        String json = hostWebClient.get("/service/queryProjectByCondition", param);
        System.out.println(json);
    }


    /**
     *
     * @throws Exception
     */
    @Test
    public void rollbackGetService() throws Exception{
        String gitUrl = "http://scm.service.dc-anyuncloud.consul:80/anyuncloud/example-service1.git";
        Map<String,Object> param=new HashMap<>();
        param.put("gitUrl",gitUrl);
        param.put("branch","refs/tags/release-20180124001");
        String json = hostWebClient.get("/service/rollback/getservice", param);
        System.out.println(json);
    }


    @Test
    public void updateProject() throws Exception {
        ProjectUpdateParam param = new ProjectUpdateParam();
        param.setId(3);
        param.setEnvironmental(false);
        String json = hostWebClient.post("/service/update/project", param.asJson());
        System.out.println(json);
    }
}
