package test.com.anyun.cloud.apiManagement;

import com.anyun.cloud.common.context.ControllerIOC;
import com.anyun.cloud.dao.ApiManagementDao;
import com.anyun.cloud.model.dto.ApiInfoDto;
import com.anyun.cloud.model.entity.ApiInfoEntity;
import com.anyun.cloud.model.entity.ApiInfoVersionEntity;
import com.anyun.cloud.model.entity.AppBasicsEntity;
import org.junit.Before;
import org.junit.Test;
import test.com.anyun.cloud.BaseTest;

import java.util.List;

/**
 * Created by jt on 18-1-18.
 */
public class TestApiManagementDao extends BaseTest {


    private ApiManagementDao apiManagementDao;

    @Before
    public void init0() {
        apiManagementDao = ControllerIOC.getIOC().getInstance(ApiManagementDao.class);
    }


    @Test
    public void createApi() {


        System.out.println();
    }



    @Test
    public void testdeleteApiById(){
        long  id =  1;
        apiManagementDao.deleteApiById(id);
    }



    @Test
    public void testqueryApiList()
    {
        int index=1;
        int limit=10;
        String sortBy="";
        String sortDirection="";
//        PageDto<ApiEntity> pageDto= apiManagementDao.queryApiList( index,  limit,  sortBy,  sortDirection);
//        System.out.print(pageDto.asJson());
    }



    @Test
    public void testqueryApiVersionByApiId(){
        long id = 81;
        List<ApiInfoVersionEntity> versionEntity = apiManagementDao.queryApiVersionByApiId(id);
        System.out.println(versionEntity.toString());
    }


    @Test
    public void testcreateApiVersion(){
    }




    @Test
    public void testupdateApiVersion(){

    }



    @Test
    public void testdeleteApiVersion(){
    }

    @Test
    public void testExit(){
        long id = 22;
        String name = "v1.0";
        ApiInfoVersionEntity p = apiManagementDao.queryApiVersionByApiIdAndName(id,name);
        System.out.println(p.asJson());
    }

    @Test
    public void queryApiByAppIdIsNull(){

        List<ApiInfoEntity> a = apiManagementDao.queryApiByAppIdIsNull();
        System.out.println(a.toString());
    }
}
