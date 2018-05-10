package test.com.anyun.cloud.base;

import com.anyun.cloud.common.context.ControllerIOC;
import com.anyun.cloud.common.sys.Response;
import com.anyun.cloud.service.DataCenterService;
;
import com.fasterxml.jackson.databind.ser.Serializers;
import org.junit.Before;
import org.junit.Test;
import test.com.anyun.cloud.BaseTest;

/**
 * @author myb  mayanbin@proxzone.com
 * @version 1.0
 * @date 2018/1/17 11:19
 */
public class TestDataCenterService extends BaseTest{
    private DataCenterService dataCenterService;

    @Before
    public void init0() {
        dataCenterService = ControllerIOC.getIOC().getInstance(DataCenterService.class);
    }

    @Test
    public void getDetails() {
        Response r =dataCenterService.getDetails(1);
        System.out.println(r.asJson());


    }
    @Test
    public void getPageList() {
        int index=1;
        int limit=10;
        String sortBy="";
        String sortDirection="";
        Response r = dataCenterService.getPageList(index,limit,sortBy,sortDirection);
        System.out.println(r.asJson());
    }
}