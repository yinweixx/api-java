package test.com.anyun.cloud.apiManagement;

import com.anyun.cloud.common.context.ControllerIOC;
import com.anyun.cloud.common.sys.Response;
import com.anyun.cloud.model.entity.ApiInfoEntity;
import com.anyun.cloud.service.ApiManagementService;
import org.junit.Before;
import org.junit.Test;
import test.com.anyun.cloud.BaseTest;

import java.util.List;

/**
 * Created by jt on 18-1-18.
 */
public class TestApiManagementService extends BaseTest {
    private ApiManagementService service;

    @Before
    public void init0() {
        service = ControllerIOC.getIOC().getInstance(ApiManagementService.class);
    }


    @Test
    public void testdeleteApiById(){

    }



    @Test
    public void testqueryApiList(){
        int index=1;
        int limit=10;
        String sortBy="";
        String sortDirection="";
        Response r = service.queryApiList(index,limit,sortBy,sortDirection);
        System.out.println(r.asJson());
    }




    @Test
    public void testqueryApiById(){
        long id =81;
        Response response = service.queryApi(id);
        System.out.print(response.asJson());
    }

    @Test
    public void queryApiByAppIdIsNull(){

        String appId = "321312";
        Response r = service.queryFinalpath(appId);
        System.out.println(r.asJson());
    }



    @Test
    public void testExitapiVersion(){
        String baseUrl = null;
        String appId = "27";
        String apiVersionName = "v1.0.0";
        String path = "/3213dsaasda" ;
        Response r = service.existApiVersion ( baseUrl, appId, apiVersionName, path);
        System.out.print(r.asJson());
    }

    @Test
    public void testapiVersion(){
        int index = 1;
        int limit = 10;
        String sortBy = "";
        String sortDirection = "";
        String displayName = "";
        String appName = null;
        Response r = service.queryApiVersionListByCondition( index, limit, sortBy,sortDirection,  displayName, appName);
        System.out.print(r.asJson());
    }


}
