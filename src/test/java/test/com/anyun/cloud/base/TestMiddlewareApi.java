package test.com.anyun.cloud.base;

import org.junit.Test;
import test.com.anyun.cloud.BaseApiTest;

import java.util.HashMap;
import java.util.Map;

/**
 * @author myb  mayanbin@proxzone.com
 * @version 1.0
 * @date 2018/1/23 15:06
 */
public class TestMiddlewareApi extends BaseApiTest {
    @Test
    public void getDetails() throws Exception {
        String id = "1";
        String json = hostWebClient.get("/middleware/details/" + id, null);
        System.out.println("json:" + json);
    }

    @Test
    public void getPageList() throws Exception {
        Map<String, Object> param = new HashMap<>();
        param.put("index", 1);
        param.put("limit", 10);
        param.put("sortBy", "asc");
        param.put("sortDirection", "");
        String json = hostWebClient.get("/middleware/page", param);
        System.out.println("json:" + json);

    }
    @Test
    public void getMiddlewareListByCondition()throws  Exception{
        Map<String,Object> param=new HashMap<>();
        param.put("index",1);
        param.put("limit",3);
        param.put("sortBy","");
        param.put("sortDirection","desc");
        param.put("middlewareIp","");
        param.put("middlewareName","");
        param.put("middlewareState","");
        param.put("middlewareType","");
        String json = hostWebClient.get("/middleware/query/condition",param);
        System.out.println("json:" + json);
    }

}
