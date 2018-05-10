package test.com.anyun.cloud.Service;

import com.anyun.cloud.common.context.ControllerIOC;
import com.anyun.cloud.common.string.StringUtils;
import com.anyun.cloud.common.json.JsonUtil;
import com.anyun.cloud.common.sys.Response;
import com.anyun.cloud.model.param.ServiceCreateParam;
import com.anyun.cloud.model.param.ServiceRollBackParam;
import com.anyun.cloud.model.param.ServiceUpdateParam;
import com.anyun.cloud.service.ServiceManagementService;
import org.junit.Before;
import org.junit.Test;
import test.com.anyun.cloud.BaseTest;

public class TestSerService extends BaseTest {
    private ServiceManagementService service;

    @Before
    public void init0() {
        service = ControllerIOC.getIOC().getInstance(ServiceManagementService.class);
    }


}
