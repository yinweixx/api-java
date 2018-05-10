package test.com.anyun.cloud.nats.wagnjian;

import io.nats.client.Connection;
import io.nats.client.ConnectionFactory;
import io.nats.client.Message;
import io.nats.client.MessageHandler;

import java.io.IOException;

public class ReceiveMsg {
    public static String url = "nats://message-business.service.dc-anyuncloud.consul:4222";
    public static String subject = "API_CONTROLLER_CHANNEL_TEST";

    public static void main(String[] args) throws Exception {
        Connection connection = new ConnectionFactory(url).createConnection();
        connection.subscribe(subject, new MessageHandler() {
            @Override
            public void onMessage(Message msg) {
                System.out.println("Message msg:"+ new String(msg.getData()));
                try {
                    connection.publish(msg.getReplyTo(), "jack".getBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }


}
