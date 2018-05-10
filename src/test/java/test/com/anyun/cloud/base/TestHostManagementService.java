package test.com.anyun.cloud.base;

import com.anyun.cloud.common.context.ControllerIOC;
import com.anyun.cloud.common.sys.Response;
import com.anyun.cloud.service.HostManagementService;
import org.junit.Before;
import org.junit.Test;
import test.com.anyun.cloud.BaseTest;

/**
 * @author myb  mayanbin@proxzone.com
 * @version 1.0
 * @date 2018/1/29 20:35
 */
public class TestHostManagementService extends BaseTest {
    private HostManagementService hostManagementService;

    @Before
    public void init0() {
        hostManagementService= ControllerIOC.getIOC().getInstance(HostManagementService.class);
    }
    @Test
    public void getPageList() {
        int index=1;
        int limit=10;
        Response r = hostManagementService.getUnregisteredHostsList(index,limit);
        System.out.println(r.asJson());
    }

    }
