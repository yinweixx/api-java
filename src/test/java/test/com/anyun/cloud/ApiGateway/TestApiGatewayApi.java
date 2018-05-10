package test.com.anyun.cloud.ApiGateway;

import com.anyun.cloud.common.json.JsonUtil;
import com.anyun.cloud.common.sys.Response;
import com.anyun.cloud.
        model.entity.ApiGatewayEntity;
import com.anyun.cloud.model.param.ElasticSettingParam;
import com.anyun.cloud.model.param.ElasticSettingUpdateParam;
import org.junit.Test;
import test.com.anyun.cloud.BaseApiTest;

import java.util.HashMap;
import java.util.Map;

public class TestApiGatewayApi extends BaseApiTest{

    @Test
    public void getDetails() throws Exception {
        /*Map<String,Object> param= new HashMap<>();
        param.put("name","erwe");*/
        String name = "网关1";
        String json = hostWebClient.get("/gateway/details/"+name,null);
        System.out.println("json:" + json);
        Response<ApiGatewayEntity> response = JsonUtil.fromJson(Response.class, json);
        System.out.println(response.asJson());
    }

    @Test
    public  void  getPageList() throws Exception{
        Map<String,Object>  param=new HashMap<>();
        param.put("index",1);
        param.put("limit",10);
        param.put("sortBy","SADAS");
        param.put("sortDirection","DSADA");
        String json = hostWebClient.get("/gateway/pagelist" +
                "" , param);
        System.out.println("json:" + json);
        Response<ApiGatewayEntity> response = JsonUtil.fromJson(Response.class, json);
        System.out.println(response.asJson());
    }

    @Test
    public void   createElasticSetting() throws Exception {
        Map<String,Object>  param=new HashMap<>();
        String json = hostWebClient.get("/gateway/elasticsetting/query",param);
        System.out.println(json);
    }

    @Test
    public void updateElasticSetting() throws Exception{
        ElasticSettingUpdateParam param = new ElasticSettingUpdateParam();
        param.setId((long) 1);
        param.setMinLink(20);
        param.setMaxLink(151);
        String json = hostWebClient.post("/gateway/elasticsetting/update",param.asJson());
        System.out.println(json);
    }
}
