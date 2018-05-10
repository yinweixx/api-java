package test.com.anyun.cloud.nats.wagnjian;


import io.nats.client.Connection;
import io.nats.client.ConnectionFactory;
import io.nats.client.Message;

public class SendMessage {
    public static String url = "nats://message-business.service.dc-anyuncloud.consul:4222";
    public static String subject = "API_CONTROLLER_CHANNEL_TEST";

    public static void main(String[] args) throws Exception {
        Connection connection = new ConnectionFactory(url).createConnection();
        Message msg = new Message();
        msg.setData("hello".getBytes("UTF-8"));
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