package test.com.anyun.cloud.base;

import com.anyun.cloud.model.param.IpBlockCreateParam;
import com.anyun.cloud.model.param.IpBlockUpdateParam;
import org.junit.Test;
import test.com.anyun.cloud.BaseApiTest;

import java.util.HashMap;
import java.util.Map;

/**
 * @author myb  mayanbin@proxzone.com
 * @version 1.0
 * @date 2018/1/22 11:19
 */
public class TestIpBlockApi extends BaseApiTest {
    @Test
    public void getDetails() throws Exception {
        String id = "2";
        String json = hostWebClient.get("/ip_block/details/" + id, null);
        System.out.println("json:" + json);
    }

    @Test
    public void delete() throws Exception {
        String json = hostWebClient.delete("/ip_block/delete/1", null);
        System.out.println(json);
    }

    @Test
    public void create() throws Exception {
        IpBlockCreateParam param = new IpBlockCreateParam();
        param.setBlockGateway("192.168.252.254.");
        param.setBlockNetMask("255.255.255.0");
        param.setBlockStartIp("192.168.252.25");
        param.setBlockEndIp("192.168.252.89");
        param.setPoolId(1);
        String json = hostWebClient.put("/ip_block/create/single", param.asJson());
        System.out.println(json);
    }
    @Test
    public void update()throws Exception{
        IpBlockUpdateParam param=new IpBlockUpdateParam();
        param.setBlockId(2);
        param.setBlockGateway("192.168.20.52/89");
        param.setBlockNetMask("s45");
        param.setBlockStartIp("785");
        param.setBlockEndIp("789");
        param.setPoolId(2);
        String json = hostWebClient.post("/ip_block/update/single", param.asJson());
        System.out.println(json);
    }
    @Test
    public void getPageList() throws Exception {
        Map<String,Object> param=new HashMap<>();
        param.put("index",2);
        param.put("limit",3);
        param.put("sortBy","");
        param.put("sortDirection","");
        String json = hostWebClient.get("/ip_block/page" , param);
        System.out.println("json:" + json);
    }
    @Test
    public void getBlockList()throws Exception{
        Map<String,Object> param=new HashMap<>();
        param.put("index",1);
        param.put("limit",10);
        param.put("sortBy","");
        param.put("sortDirection","");
        param.put("poolId",7);
        String json = hostWebClient.get("/ip_block/query" ,param);
        System.out.println("json:" + json);
    }
    @Test
    public void getBlockListByCondition()throws  Exception{
        Map<String,Object> param=new HashMap<>();
        param.put("index",1);
        param.put("limit",2);
        param.put("sortBy","");
        param.put("sortDirection","desc");
        param.put("poolId",1);
        param.put("startIp","");
        param.put("endIp","");
        param.put("gateway","");
        param.put("netMask","");
        param.put("category","");
        String json = hostWebClient.get("/ip_block/query/condition",param);
        System.out.println("json:" + json);
    }
}