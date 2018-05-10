package test.com.anyun.cloud.base;

import com.anyun.cloud.model.param.DataCenterCreateParam;
import com.anyun.cloud.model.param.DataCenterUpdateParam;
import com.anyun.cloud.service.DataCenterService;
import org.junit.Test;
import test.com.anyun.cloud.BaseApiTest;

import java.util.HashMap;
import java.util.Map;

/**
 * @author myb  mayanbin@proxzone.com
 * @version 1.0
 * @date 2018/1/17 11:18
 */
public class  TestDataCenterApi extends BaseApiTest {
    @Test
    public void getDetails() throws Exception {
        String id = "1";
        String json = hostWebClient.get("/dataCenter/details/" + id, null);
        System.out.println("json:" + json);
    }
    @Test
    public void getPageList() throws Exception {
        Map<String,Object> param=new HashMap<>();
        param.put("index",1);
        param.put("limit",10);
        param.put("sortBy","");
        param.put("sortDirection","");
        String json = hostWebClient.get("/dataCenter/page" , param);
        System.out.println("json:" + json);
    }
    @Test
    public void delete() throws Exception {
        String json = hostWebClient.delete("/dataCenter/delete/6", null);
        System.out.println(json);
    }
    @Test
    public void create() throws Exception {
        DataCenterCreateParam param = new DataCenterCreateParam();
        param.setCenterName("公安局");
        param.setDnsName("salve");
        param.setCenterAddress("九原");
        param.setCenterCategory("分中心");
        String json = hostWebClient.put("/dataCenter/create/single", param.asJson());
        System.out.println(json);
    }
    @Test
    public void update() throws Exception {
        DataCenterUpdateParam param = new DataCenterUpdateParam();
        param.setCenterId(1);
        param.setCenterName("交管所");
        param.setCenterAddress("包头市");
        param.setCenterCategory("主中心");
        String json = hostWebClient.post("/dataCenter/update/single", param.asJson());
        System.out.println(json);
    }
    //首页获取数据中心宿主机信息
    @Test
    public void getHostsDetails() throws Exception {
        String id = "7";
        String json = hostWebClient.get("/dataCenter/hosts/details/" + id, null);
        System.out.println("json:" + json);
    }

}
