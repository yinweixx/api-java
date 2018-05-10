package test.com.anyun.cloud;

import com.anyun.cloud.common.HostWebClient;
import org.junit.Before;

/**
 * Created by jt on 17-12-29.
 */
public class TestService {
    private static HostWebClient hostWebClient =null;

    @Before
    public void test(){
        System.out.println("==================================开始测试=======================================");
        HostWebClient.Configuration configuration = new HostWebClient.Configuration();
        configuration.setPlatformAddress("192.168.252.30");
        configuration.setPort(8080);
        configuration.setBaseUrl("/api/v1.0");
        hostWebClient = HostWebClient.build(configuration);
    }

}
