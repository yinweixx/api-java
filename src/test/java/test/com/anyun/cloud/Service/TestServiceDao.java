package test.com.anyun.cloud.Service;

import com.anyun.cloud.common.context.ControllerIOC;
import com.anyun.cloud.dao.ServiceManagementDao;
import com.anyun.cloud.model.dto.PageDto;
import com.anyun.cloud.model.dto.ServiceConditionDto;
import com.anyun.cloud.model.entity.ServiceEntity;
import com.anyun.cloud.model.param.ServiceUpdateParam;
import org.junit.Before;
import org.junit.Test;
import test.com.anyun.cloud.BaseTest;

import java.util.HashMap;
import java.util.Map;

public class TestServiceDao extends BaseTest {
    private ServiceManagementDao dao;

    @Before
    public void init0() {
        dao = ControllerIOC.getIOC().getInstance(ServiceManagementDao.class);
    }

    @Test
    public void selectServiceListByCondition() {
        int index = 2;
        int limit = 10;
        String sortBy = "";
        String sortDirection = "asc";
        String sname = "3";
        String bname = "";
        String pname = "";
        String gitUrl = "";
        String user ="";
        PageDto<ServiceConditionDto> pageDto = dao.QueryConditionService(index,limit,sortBy,sortDirection,sname,bname,pname,gitUrl,user);
        System.out.print(pageDto.asJson());
    }
}
