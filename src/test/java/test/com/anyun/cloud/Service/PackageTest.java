package test.com.anyun.cloud.Service;

import com.anyuncloud.common.packages.ApplicationConfig;
import com.anyuncloud.common.packages.CloudBuilder;
import com.anyuncloud.common.packages.DefaultGitProjectPackageBuilderFactory;
import com.anyuncloud.common.packages.PackageBuilderFactory;
import org.junit.Test;
import test.com.anyun.cloud.BaseTest;

import java.io.File;
import java.net.URL;
import java.util.List;

public class PackageTest extends BaseTest {
    @Test
    public void testService1() throws Exception {
        File gitTmp = new File("/Users/tangweihua/Downloads/tmp");
        URL remote = new URL("http://scm.service.dc-anyuncloud.consul:80/anyuncloud/service-video-data.git");
        getInfo(remote,gitTmp);

//        remote = new URL("http://scm.service.dc-anyuncloud.consul:80/anyuncloud/example-task1.git");
//        getInfo(remote,gitTmp);
    }

    private void getInfo(URL remote, File gitTmp) throws Exception {
        PackageBuilderFactory factory = new DefaultGitProjectPackageBuilderFactory()
                .withGit(remote, gitTmp);
        ///////////////////////////////////////////////////////////  ////////////////////////////////
        StringBuilder sb = new StringBuilder();
        List<String> versions = factory.getRemoteSecondVersion();

        System.out.println("========versions:"+versions);
//        String version = "refs/tags/release-20180124001";
//        String version = "release";

        for (String v : versions) {
            sb.append(",").append(v);
//            if (v.contains("release-"))
//                version = v;
        }
        System.out.println("============sb:"+sb.toString());


        String version = "refs/tags/release-20180305001";
        System.out.println("项目远程版本信息: " + sb.toString().substring(1));
        CloudBuilder builder = factory.getBuilder(version);
        System.out.println("git项目类型: " + (factory.isServiceProject() ? "服务项目" : "任务项目"));
        ApplicationConfig applicationConfig = builder.build();
        if (factory.isServiceProject()) {
            applicationConfig.getServiceConfigs().forEach(service -> {
                System.out.println(service);
            });
        } else if (factory.isTaskProject()) {
            applicationConfig.getTaskConfigs().forEach(task -> {
                System.out.println(task.getCrontabExpression());
                System.out.println(task.getName());
            });
        }
    }
}
