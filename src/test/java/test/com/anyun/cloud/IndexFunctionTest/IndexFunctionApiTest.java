package test.com.anyun.cloud.IndexFunctionTest;

import com.anyun.cloud.common.json.JsonUtil;
import com.anyun.cloud.common.sys.Response;
import org.junit.Test;
import test.com.anyun.cloud.BaseApiTest;

import java.util.HashMap;
import java.util.Map;

public class IndexFunctionApiTest extends BaseApiTest {

    @Test
    public void  getFunctionSituation() throws Exception{
        Map<String,Object> param=new HashMap<>();
        param.put("id",1);
        String json = hostWebClient.get("/index/function",param);
        System.out.println("json:" + JsonUtil.toJson(json));
    }
}
