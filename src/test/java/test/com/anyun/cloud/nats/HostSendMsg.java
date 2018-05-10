package test.com.anyun.cloud.nats;

import com.anyun.cloud.model.param.HostDistributionParam;
import io.nats.client.Connection;
import io.nats.client.ConnectionFactory;
import io.nats.client.Message;


public class HostSendMsg {
    public static String url = "nats://message-business.service.dc-anyuncloud.consul:4222";
    public static String subject = "API_CONTROLLER_CHANNEL_TEST1";

    public static void main(String[] args) throws Exception {
        Connection connection = new ConnectionFactory(url).createConnection();
        Message msg = new Message();
        HostDistributionParam param=new HostDistributionParam();
        /*param.setDnsname("dc-anyuncloud-slave");
        param.setBlockcategory("宿主机");
        param.setHostcategory("docker");
        param.setEnvironment("生产环境");
        param.setNumber(0);*/
        System.out.println("HostDistributionParam param:"+param.asJson());
        msg.setData(param.asJson().getBytes("UTF-8"));
        msg.setSubject(subject);
        Message r = null;
        try {
            r = connection.request(msg.getSubject(), msg.getData());
            System.out.println(new String(r.getData(), "UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
