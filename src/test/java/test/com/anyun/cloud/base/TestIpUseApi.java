package test.com.anyun.cloud.base;
import org.junit.Test;
import test.com.anyun.cloud.BaseApiTest;

import java.util.HashMap;
import java.util.Map;


/**
 * @author myb  mayanbin@proxzone.com
 * @version 1.0
 * @date 2018/2/2 16:17
 */
public class TestIpUseApi extends BaseApiTest {
    @Test
    public void getDetails() throws Exception {
        String id = "1";
        String json = hostWebClient.get("/ip_use/details/" + id, null);
        System.out.println("json:" + json);
    }

    @Test
    public void getIpList() throws Exception {
        Map<String, Object> param = new HashMap<>();
        param.put("count",5);
        param.put("blockId", 1);
        param.put("des","用于");
        String json = hostWebClient.get("/ip_use/get/list", param);
        System.out.println(json);
    }

    @Test
    public void getPageList() throws Exception {
        Map<String, Object> param = new HashMap<>();
        param.put("blockId", 1);
        param.put("poolId",1);
        param.put("index",1);
        param.put("limit",100);
        param.put("sortBy","");
        param.put("sortDirection","");

        String json = hostWebClient.get("/ip_use/page/list", param);
        System.out.println(json);

    }

    @Test
    public void delete() throws Exception {
        String json = hostWebClient.delete("/ip_use/unbundling/132", null);
        System.out.println(json);

    }
}