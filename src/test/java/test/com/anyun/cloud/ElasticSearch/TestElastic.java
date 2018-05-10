package test.com.anyun.cloud.ElasticSearch;

import org.apache.commons.collections.map.HashedMap;
import org.junit.Test;
import test.com.anyun.cloud.BaseApiTest;

import java.util.HashMap;
import java.util.Map;

public class TestElastic extends BaseApiTest {

    @Test
    public void QueryByCondition()throws Exception {
        Map<String,Object> param=new HashMap<>();
        param.put("index",1);
        param.put("limit",10);
        param.put("sortBy","");
        param.put("sortDirection","asc");

//        param.put("id","3GgVCmIBT4cBJkcxq809");
//        param.put("host","twh");
//        param.put("source","kern");
//        param.put("message","192.168.252.15:37198");
//        param.put("startTimestamp","2018-03-08");
//        param.put("endTimestamp","2018-03-30");
        String json = hostWebClient.get("/elastic/queryLogByCondition", param);
        System.out.println(json);
    }


    @Test
    public void serviceDetail() throws Exception {
        Map<String,Object> param = new HashedMap();
        param.put("id","YTJnCGIB9HG1OVgfqoI_");
        String json = hostWebClient.get("/elastic/details", param);
        System.out.println(json);
    }

}
