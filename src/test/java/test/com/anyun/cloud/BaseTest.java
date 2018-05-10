package test.com.anyun.cloud;

import com.anyun.cloud.common.context.*;
import com.anyun.cloud.common.context.module.SystemEnvironmentBuilder;
import org.junit.Assert;
import org.junit.Before;

import java.util.HashMap;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 06/12/2017
 */
public class BaseTest extends Assert {
    protected Context context;

    @Before
    public void init() {
        /**
         * 定义系统环境配置
         */
        SystemEnvironment environment = null;
        try {
            environment = SystemEnvironment.getSystemEnvironment();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage() + "获取系统环境配置失败！");
        }
        System.out.println("SystemEnvironment environment:" + environment.asJson());
        ControllerIOC module = ControllerIOC.getIOC().build(new SystemModule(environment)
                , new DatabaseModule(environment)
                , new ServiceModule()
                , new EtcdModule(environment)
                , new NatsModule(environment)
                , new ElasticsearchModule(environment)
        );
        module.getInjector().getInstance(Context.class)
                .init();

    }
}
