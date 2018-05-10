package test.com.anyun.cloud.ApiGateway;

import com.anyun.cloud.common.context.ControllerIOC;
import com.anyun.cloud.common.json.JsonUtil;
import com.anyun.cloud.common.sys.Response;
import com.anyun.cloud.common.tools.db.AbstractEntity;
import com.anyun.cloud.model.param.ElasticSettingParam;
import com.anyun.cloud.model.param.ElasticSettingUpdateParam;
import com.anyun.cloud.service.ApiGatewayService;
import org.junit.Before;
import org.junit.Test;
import test.com.anyun.cloud.BaseTest;

import java.util.UUID;

public class TestApiGatewayService extends BaseTest {
    private ApiGatewayService apiGatewayService;

    @Before
    public void init0() {
        apiGatewayService = ControllerIOC.getIOC().getInstance(ApiGatewayService.class);
    }

    @Test
    public void getDetails(){
        String name = "网关1";
        AbstractEntity abstractEntity = apiGatewayService.getDetails(name);
        System.out.println(abstractEntity.asJson());
    }

    @Test
    public void getPageList() {
        int index=1;
        int limit=10;
        String sortBy="";
        String sortDirection="";
        Response r = apiGatewayService.getPageList(index,limit,sortBy,sortDirection);
        System.out.println(r.asJson());
    }

    public class ApiParam {
        private String uuid = UUID.randomUUID().toString();
        private String desc = "描述";
        private String version ="v1";
        private String method ="GET";
        private String params = "String param1,String param2";

        public String getUuid() {
            return uuid;
        }

        public void setUuid(String uuid) {
            this.uuid = uuid;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public String getMethod() {
            return method;
        }

        public void setMethod(String method) {
            this.method = method;
        }

        public String getParams() {
            return params;
        }

        public void setParams(String params) {
            this.params = params;
        }
    }

    @Test
    public void getValue(){
        //键:/meta/api_meta/B2481F3B-4146-4251-AC01-58205CB589BB/v1/request/GET/params/param1:0
        String url ="https://api.gateway.service.anyuncloud.consul/sample_app1/v1/api1";

        String url1 = "/sample_app1/v1/api1?param1=value1&param1=value2&param2=value&?param3=value1";


        String[] str = url.split("/");
        int l = str.length-1;
        System.out.println(JsonUtil.toJson(str));
        System.out.println(l);
        System.out.println("\n");
        String a = null;
        for (int i = 0;i<=str.length;i++){
            if (i==l){
               a = str[i];
            }
        }
        ApiParam param = new ApiParam();
        System.out.println(a);
        System.out.println("\n");
        String key = "/meta/api/"+a;
        System.out.println("key:"+key);

        String value = param.getUuid();
        System.out.println("value:"+value);

        String key1 = "/meta/api/"+value +"/desc";
        System.out.println("key:"+key1);

        String value1 = param.getDesc();
        System.out.println("value:"+value1);

        String key2 = "/meta/api/"+value +"/version";
        System.out.println("key:"+key2);

        String value2 = param.getVersion();
        System.out.println("value:"+value2);

        String key3 ="/meta/api/"+value +"/method";
        System.out.println("key:"+key3);

        String value3= param.getMethod();
        System.out.println("value:"+value3);


    }

    @Test
    public void queryElasticSetting(){
        /*ElasticSettingParam param = new ElasticSettingParam();
        param.setMinLink(10);
        param.setMaxLink(100);*/
        Response response = apiGatewayService.queryElasticSetting();
        System.out.println(response.asJson());
    }

    @Test
    public void updateElasticSetting(){
        ElasticSettingUpdateParam param = new ElasticSettingUpdateParam();
        param.setId((long) 2);
        param.setMinLink(20);
        param.setMaxLink(150);
        Response r = apiGatewayService.updateElasticSetting(param.asJson());
        System.out.println(r.asJson());
    }
}
