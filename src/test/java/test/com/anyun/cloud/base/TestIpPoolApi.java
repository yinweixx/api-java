package test.com.anyun.cloud.base;

import com.anyun.cloud.model.param.IpPoolCreateParam;
import com.anyun.cloud.model.param.IpPoolUpdateParam;
import org.junit.Test;
import test.com.anyun.cloud.BaseApiTest;

import java.util.HashMap;
import java.util.Map;

/**
 * @author myb  mayanbin@proxzone.com
 * @version 1.0
 * @date 2018/1/19 10:42
 */
public class TestIpPoolApi extends BaseApiTest {
    @Test
    public void getDetails() throws Exception {
        String id = "11";
        String json = hostWebClient.get("/ip_pool/details/" + id, null);
        System.out.println("json:" + json);
    }
    @Test
    public void delete()throws Exception{
        String json = hostWebClient.delete("/ip_pool/delete/2", null);
        System.out.println(json);

    }
    @Test
    public void create ()throws Exception {
        IpPoolCreateParam p = new IpPoolCreateParam();
        p.setEnvironment("192.168.1.78");
        p.setPoolName("是个ip池");
        p.setCategory("255.255.255.0");
      //  p.setCenterName("数据");
        p.setCenterId(1);
        String json = hostWebClient.put("ip_pool/create/", p.asJson());
        System.out.println(json);
    }
     @Test
     public void update ()throws Exception{
        IpPoolUpdateParam p =new IpPoolUpdateParam();
        p.setPoolId(9);
        p.setPoolName("融合池");
        p.setEnvironment("生产环境");
        p.setCategory("融合");
      //  p.setCenterName("调用接口");
        p.setCenterId(1);
        String json=hostWebClient.post("ip_pool/update/",p.asJson());
        System.out.println(json);


        }
    @Test
    public void getPageList() throws Exception {
        Map<String,Object> param=new HashMap<>();
        param.put("index",1);
        param.put("limit",10);
        param.put("sortBy","");
        param.put("sortDirection","");
        param.put("centerId","1");
        String json = hostWebClient.get("/ip_pool/page" , param);
        System.out.println("json:" + json);
    }
    @Test
    public void getPoolListByCondition()throws  Exception{
        Map<String,Object> param=new HashMap<>();
        param.put("index",1);
        param.put("limit",3);
        param.put("sortBy","");
        param.put("sortDirection","desc");
        param.put("poolName","");
        param.put("environment","");
        param.put("category","");
        param.put("centerId","1");
        String json = hostWebClient.get("/ip_pool/query/condition",param);
        System.out.println("json:" + json);
    }

}



