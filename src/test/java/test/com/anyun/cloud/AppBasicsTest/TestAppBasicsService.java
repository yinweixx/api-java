package test.com.anyun.cloud.AppBasicsTest;

import com.anyun.cloud.common.context.ControllerIOC;
import com.anyun.cloud.common.sys.Response;
import com.anyun.cloud.model.param.AppBasicsCreateParam;
import com.anyun.cloud.model.param.AppBasicsUpdateParam;
import com.anyun.cloud.service.AppBasicsService;
import org.junit.Before;
import org.junit.Test;
import test.com.anyun.cloud.BaseTest;

public class TestAppBasicsService extends BaseTest {
    private AppBasicsService appBasicsService;

    @Before
    public void init0() {
        appBasicsService = ControllerIOC.getIOC().getInstance(AppBasicsService.class);
    }

    @Test
    public void create(){
        AppBasicsCreateParam param = new AppBasicsCreateParam();
        param.setName("serviceTest1");
        param.setShortName("service-test1");
        param.setDesc("应用的service测试1");
        Response r = appBasicsService.create(param.asJson());
        System.out.println(r.asJson());
    }

    @Test
    public void update(){
        AppBasicsUpdateParam param = new AppBasicsUpdateParam();
        param.setAppId((long) 1);
        param.setName("收录应用");
        param.setDesc("应用的service修改2");
        Response r = appBasicsService.update(param.asJson());
        System.out.println(r.asJson());
    }

    @Test
    public void getPageList() {
        int index=2;
        int limit=5;
        String sortBy="";
        String sortDirection="";
        Response r = appBasicsService.getPageList(index,limit,sortBy,sortDirection);
        System.out.println(r.asJson());
    }

    @Test
    public void delete(){
        Response r = appBasicsService.delete(2);
        System.out.println(r.asJson());
    }

    @Test
    public void getVagueList(){
        String condition = "测";
        Response r = appBasicsService.getVagueList(condition);
        System.out.println(r.asJson());
    }

    @Test
    public void getAppListByCondition(){
        int index=1;
        int limit=5;
        String sortBy="";
        String sortDirection="";
        String name = "";
        String shortName="a";
        String startTime = "";
        String endTime = "";
        Response r = appBasicsService.getAppListByCondition(index,limit,sortBy,sortDirection,name,shortName,startTime,endTime);
        System.out.println(r.asJson());
    }

}
