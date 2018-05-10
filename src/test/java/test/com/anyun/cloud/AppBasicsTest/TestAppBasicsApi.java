package test.com.anyun.cloud.AppBasicsTest;

import com.anyun.cloud.model.param.AppBasicsCreateParam;
import com.anyun.cloud.model.param.AppBasicsUpdateParam;
import org.junit.Test;
import test.com.anyun.cloud.BaseApiTest;

import java.util.HashMap;
import java.util.Map;

public class TestAppBasicsApi extends BaseApiTest {

    /**
     * 创建应用
     * @throws Exception
     */
    @Test
    public void  create() throws Exception{
        AppBasicsCreateParam param = new AppBasicsCreateParam();
        param.setName("测试1");
        param.setShortName("app-test");
        param.setDesc("这是一个应用测试");
        String json = hostWebClient.put("/appbasics/create",param.asJson());
        System.out.println(json);
    }

    /**
     * 应用更新
     * @throws Exception
     */
    @Test
    public void update() throws Exception{
        AppBasicsUpdateParam param = new AppBasicsUpdateParam();
        param.setAppId((long) 1);
        param.setName("测试更新2");
        param.setDesc("应用更新测试");
        String json = hostWebClient.post("/appbasics/update",param.asJson());
        System.out.println(json);

    }

    /**
     * 分页查询应用
     * @throws Exception
     */
    @Test
    public void getPageList() throws Exception {
        Map<String,Object> param=new HashMap<>();
        param.put("index",1);
        param.put("limit",10);
        param.put("sortBy","");
        param.put("sortDirection","asc");
        String json = hostWebClient.get("/appbasics/page" , param);
        System.out.println("json:" + json);
    }

    /**
     * 应用删除
     * @throws Exception
     */
    @Test
    public void delete() throws Exception{
        String json = hostWebClient.delete("/appbasics/delete/3" , null);
        System.out.println("json:" + json);
    }

    /**
     * 根据名称查询应用--模糊查询
     * @throws Exception
     */
    @Test
    public void getVagueList() throws Exception{
        Map<String,Object> param=new HashMap<>();
        param.put("condition","测");
        String json = hostWebClient.get("/appbasics/vaguelist",param);
        System.out.println("json:" + json);
    }

    /**
     *根据条件查询应用列表
     * @throws Exception
     */
    @Test
    public void getAppListByCondition() throws Exception{
        Map<String,Object> param=new HashMap<>();
        param.put("index",1);
        param.put("limit",3);
        param.put("sortBy","");
        param.put("sortDirection","desc");
        param.put("name","");
        param.put("shortName","");
        param.put("startTime","");
        param.put("endTime","");
        String json = hostWebClient.get("/appbasics/queryAppByCondition",param);
        System.out.println("json:" + json);
    }
}
