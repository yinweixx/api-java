package test.com.anyun.cloud;

import com.anyun.cloud.common.HostWebClient;
import org.junit.Before;

public class BaseApiTest {
    protected HostWebClient hostWebClient ;

    @Before
    public void init() {
        System.out.println("==================================开始测试=======================================");
        HostWebClient.Configuration configuration = new HostWebClient.Configuration();
        configuration.setPlatformAddress("localhost");
//        configuration.setPlatformAddress("192.168.254.231");
        configuration.setPort(8080);
        configuration.setBaseUrl("/api/v1.0");
        if (this.hostWebClient == null)
            this.hostWebClient = HostWebClient.build(configuration);
        System.out.println("hostWebClient:"+hostWebClient);
    }
}
