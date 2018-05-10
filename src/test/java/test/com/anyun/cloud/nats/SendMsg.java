package test.com.anyun.cloud.nats;


import com.anyun.cloud.model.param.ApiVerificationParam;
import io.nats.client.Connection;
import io.nats.client.ConnectionFactory;
import io.nats.client.Message;

import java.util.HashMap;
import java.util.Map;

public class SendMsg {
    public static String url = "nats://message-business.service.dc-anyuncloud.consul:4222";
    public static String subject = "API_CONTROLLER_CHANNEL_TEST1";

    public static void main(String[] args) throws Exception {
        Connection connection = new ConnectionFactory(url).createConnection();
        Message msg = new Message();
        ApiVerificationParam param=new ApiVerificationParam();
        Map<String,Object> map = new HashMap<>();
        map.put("_service_name","dao.datacenter.query_id_by_name");
        map.put("name","dc-anyuncloud");
        param.setUrl("/api/camera-app-rtp/dc/v1");
        param.setAcceptType("application/json");
        param.setContentType("application/json");
        param.setMethod("GET");
        param.setParams(map);
        System.out.println("ApiVerificationParam param:"+param.asJson());
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