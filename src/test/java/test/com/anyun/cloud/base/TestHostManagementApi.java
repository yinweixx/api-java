package test.com.anyun.cloud.base;

import org.junit.Test;
import test.com.anyun.cloud.BaseApiTest;
import test.com.anyun.cloud.BaseTest;

import java.util.HashMap;
import java.util.Map;

/**
 * @author myb  mayanbin@proxzone.com
 * @version 1.0
 * @date 2018/1/29 20:36
 */
public class TestHostManagementApi extends BaseApiTest {
    @Test
    //显示未注册宿主机列表
    public void getUnregisteredHosts() throws Exception {
        String json = hostWebClient.get("/hosts/unregistered", null);
        System.out.println(json);
    }


    @Test
    //注册宿主机
    public void registeredHosts() throws Exception {
        String json = hostWebClient.put("/hosts/register/192.168.254.32", "");
        System.out.println(json);
    }

    //注销宿主机
    @Test
    public void cancel() throws Exception {
        String json = hostWebClient.delete("/hosts/delete/5", null);
        System.out.println(json);

    }

    @Test
    public void getPageList() throws Exception {
        Map<String, Object> param = new HashMap<>();
        param.put("index", 1);
        param.put("limit", 10);
        param.put("sortBy", "");
        param.put("sortDirection", "");
        String json = hostWebClient.get("/hosts/page", param);
        System.out.println("json:" + json);
    }

    @Test
    //显示宿主机Lxd信息
    public void getHostLxdDetails() throws Exception {
        String id = "1";
        String json = hostWebClient.get("/hosts/lxd/details/" + id, null);
        System.out.println("json:" + json);
    }

    @Test
    //显示宿主机Docker信息
    public void getHostDockerDetails() throws Exception {
        String id = "1";
        String json = hostWebClient.get("/hosts/docker/details/" + id, null);
        System.out.println("json:" + json);
    }

    @Test
    //显示宿主机软件信息
    public void getHostSoftwareDetails() throws Exception {
        String id = "1";
        String json = hostWebClient.get("/hosts/software/details/" + id, null);
        System.out.println("json:" + json);
    }

    @Test
    //显示宿主机硬件信息
    public void getHostHardwareDetails() throws Exception {
        String id = "13";
        String json = hostWebClient.get("/hosts/hardware/details/" + id, null);
        System.out.println("json:" + json);
    }

    @Test
    public void getHostsListByCondition() throws Exception {
        Map<String, Object> param = new HashMap<>();
        param.put("index", 1);
        param.put("limit", 10);
        param.put("sortBy", "");
        param.put("sortDirection","desc");
        param.put("ip","");
        param.put("environment", "");
        param.put("category", "");
        param.put("centerName", "");
        String json = hostWebClient.get("/hosts/list/condition", param);
        System.out.println("json:" + json);


    }
    @Test
    //根据数据中心名字查询宿主机
    public void getHostsByCenterName() throws Exception {
        Map<String, Object> param = new HashMap<>();
        param.put("centerId","1");
        String json = hostWebClient.get("/hosts/query/center", param);
        System.out.println("json:" + json);
    }
    @Test
    //分页显示未注册宿主机列表
    public void UnregisteredHosts() throws Exception {
        Map<String, Object> param = new HashMap<>();
        param.put("index", 1);
        param.put("limit", 2);
        String json = hostWebClient.get("/hosts/unregistered/paging",param);
        System.out.println(json);
    }
}