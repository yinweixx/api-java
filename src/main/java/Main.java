import com.anyun.cloud.common.context.*;
import com.anyun.cloud.common.startup.daemon.JettyRestServer;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;

/**
 * Created by sxt on 8/28/17.
 */
public class Main {
    public static void main(String[] args) {
        //获取进程
        RuntimeMXBean runtime = ManagementFactory.getRuntimeMXBean();
        String name = runtime.getName();
        System.out.println("当前进程的标识为:" + name);
        int index = name.indexOf("@");
        if (index != -1) {
            int pid = Integer.parseInt(name.substring(0, index));
            System.out.println("当前进程的PID为:" + pid);
        }

        //获取配置
        SystemEnvironment environment = null;
        try {
            environment = SystemEnvironment.getSystemEnvironment();
            System.out.println("获取系统环境配置成功---->:" + environment.asJson());
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("获取系统环境配置失败 ------>:" + e.getMessage());
            return;
        }

        //初始化各模块
        ControllerIOC module = ControllerIOC.getIOC().build(new SystemModule(environment)
                , new DatabaseModule(environment)
                , new ServiceModule()
                , new EtcdModule(environment)
                , new NatsModule(environment)
                , new ElasticsearchModule(environment)
        );
        module.getInjector().getInstance(Context.class)
                .init();

        //启动jetty
        JettyRestServer.start();
    }
}
