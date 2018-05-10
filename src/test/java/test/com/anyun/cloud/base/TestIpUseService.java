package test.com.anyun.cloud.base;

import com.anyun.cloud.common.context.ControllerIOC;
import com.anyun.cloud.common.sys.Response;
import com.anyun.cloud.service.IpUseService;
import org.junit.Before;
import org.junit.Test;
import test.com.anyun.cloud.BaseTest;

import java.util.List;

/**
 * @author myb  mayanbin@proxzone.com
 * @version 1.0
 * @date 2018/2/2 16:12
 */
public class TestIpUseService extends BaseTest {
    private IpUseService ipUseService;

    @Before
    public void init0() {
        ipUseService = ControllerIOC.getIOC().getInstance(IpUseService.class);
    }

    @Test
    public void getDetails() {
        Response r = ipUseService.getDetails(1);
        System.out.println(r.asJson());

    }

   @Test
    public void getIpList(){
       Response r =ipUseService.getIpList(5,2,"用于");
       System.out.println(r.asJson());
   }

}