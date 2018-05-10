package test.com.anyun.cloud.VideoSourceTest;

import com.anyun.cloud.common.json.JsonUtil;
import org.junit.Test;
import test.com.anyun.cloud.BaseApiTest;

import java.util.HashMap;
import java.util.Map;

public class TestVideoSourceApi extends BaseApiTest {

    @Test
    public void getPageList() throws Exception {
        Map<String,Object> param=new HashMap<>();
        param.put("index",1);
        param.put("limit",10);
        param.put("sortBy","");
        param.put("sortDirection","asc");
        String json = hostWebClient.get("/videosource/pagelist" , param);
        System.out.println("json:" + json);
    }


    @Test
    public void queryVideoByCondition() throws Exception {
        Map<String,Object> param=new HashMap<>();
        param.put("index",1);
        param.put("limit",10);
        param.put("sortBy","");
        param.put("sortDirection","desc");
        param.put("intersection","");
        param.put("orientation","");
        param.put("desc","");
        param.put("product","");
        param.put("vender","");
        param.put("dataCenter","");
        String json = hostWebClient.get("/videosource/queryVideoByCondition",param);
        System.out.println("json:" + JsonUtil.toJson(json));
    }

    @Test
    public void deleteAllVideo() throws Exception{
        Map<String,Object> param=new HashMap<>();
        String json = hostWebClient.delete("/videosource/deleteall",param);
        System.out.println("json:" + json);
    }
}
