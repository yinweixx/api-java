package test.com.anyun.cloud.Task;

import com.anyun.cloud.model.param.TaskCronParam;
import com.anyun.cloud.model.param.TaskProjectCreateParam;
import com.anyun.cloud.model.param.TaskRollbackParam;
import com.anyun.cloud.model.param.TaskUpdateParam;
import org.apache.commons.collections.map.HashedMap;
import org.junit.Test;
import test.com.anyun.cloud.BaseApiTest;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class TestTaskApi extends BaseApiTest {


    /**
     * 查询任务详情
     */
    @Test
    public void taskDetail() throws Exception {
        Map<String,Object> param = new HashedMap();
        param.put("id","768B7F958033E3B548F4E4B221354940");
        String json = hostWebClient.get("/task/detail", param);
        System.out.println(json);
    }


    /**
     * 任务目录(分页查询)
     * @throws Exception
     */
    @Test
    public void getPageList() throws Exception {
        Map<String,Object> param=new HashMap<>();
        param.put("index",1);
        param.put("limit",10);
        param.put("sortBy","");
        param.put("sortDirection","");
        String json = hostWebClient.get("/task/page" , param);
        System.out.println("json:" + json);
    }

    /**
     * 任务编辑
     * @throws Exception
     */
    @Test
    public void update() throws Exception {
        TaskUpdateParam param = new TaskUpdateParam();
        param.setId("768B7F958033E3B548F4E4B221354940");
        param.setStatus(false);
        String json = hostWebClient.post("/task/update/task", param.asJson());
        System.out.println(json);
    }

    @Test
    public void QueryByCondition()throws Exception {
        Map<String,Object> param=new HashMap<>();
        param.put("index",1);
        param.put("limit",4);
        param.put("sortBy","");
        param.put("sortDirection","asc");

        param.put("name","");
        param.put("projectName","");
        param.put("branchName","");
        param.put("shortUrl","");
        String json = hostWebClient.get("/task/queryTaskByCondition", param);
        System.out.println(json);
    }


    @Test
    public void getCron() throws Exception{
        TaskCronParam param= new TaskCronParam();
        param.setId("768B7F958033E3B548F4E4B221354940");
        param.setCron("0 0 2,3 * * ?");
        String json = hostWebClient.post("/task/cronAnalysis", param.asJson());
        System.out.println(json);
    }
}
