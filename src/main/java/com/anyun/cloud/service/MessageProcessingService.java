package com.anyun.cloud.service;

import com.anyun.cloud.model.param.BUSINESS_ENUM;
import io.nats.client.Connection;
import io.nats.client.Message;
import io.nats.client.MessageHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;

public class MessageProcessingService {
    private final static Logger LOGGER = LoggerFactory.getLogger(MessageProcessingService.class);
    private Connection connect;
    private String subject;

    public MessageProcessingService(Connection connect, String subject) {
        this.connect = connect;
        this.subject = subject;
    }

    /**
     * 回复消息
     *
     * @param reply
     * @param data
     */
    private void replyMessage(String reply, byte[] data) {
        try {
            connect.publish(reply, data);
        } catch (IOException e) {
            LOGGER.debug("IOException:[{}]", e);
        }
    }

    /**
     * 获取业务枚举类中 业务名称列表
     *
     * @return List<String>
     */
    List<String> getEnumList() {
        List<String> list = null;
        BUSINESS_ENUM[] businessEnums = BUSINESS_ENUM.values();
        //枚举中有业务存在
        if (businessEnums != null && businessEnums.length > 0) {
            list = new LinkedList<>();
            for (BUSINESS_ENUM business_enum : businessEnums) {
                list.add(business_enum.name());
            }
        }
        return list;
    }

    public void start() {
        connect.subscribe(subject, new MessageHandler() {
            @Override
            public void onMessage(Message msg) {
                LOGGER.debug("Message msg:[{}]", msg);
                LOGGER.debug("Message getData():[{}]", msg.getData());
                LOGGER.debug("Message getReplyTo():[{}]", msg.getReplyTo());
                String dataToString = "";
                try {
                    dataToString = new String(msg.getData(), "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                LOGGER.debug("dataToString:[{}]", dataToString);
                List<String> list = getEnumList();
                if (list == null) {
                    LOGGER.debug("服务为空！");
                    return;
                }

                String business_name = "";
                for (int i = 0; i < list.size(); i++) {
                    LOGGER.debug("共:{}-----第:{}-----名称:{}", list.size(), i + 1, list.get(i));
                    if (dataToString.contains("BUSINESS") && dataToString.contains(list.get(i))) {
                        business_name = list.get(i);
                    }
                }

                //业务不存在
                if (business_name.trim().equals("")) {
                    LOGGER.debug("业务不存在!");
                    if (msg.getReplyTo() != null && !msg.getReplyTo().trim().equals("")) {
                        try {
                            replyMessage(msg.getReplyTo(), "业务不存在!".getBytes("UTF-8"));
                        } catch (UnsupportedEncodingException e) {
                            LOGGER.debug("UnsupportedEncodingException e:[{}]");
                        }
                    }
                    return;
                }
                LOGGER.debug("存在业务:[{}]", business_name);

                Class<?> aClass = null;
                try {
                    aClass = Class.forName("com.anyun.cloud.service.ApiDataSupportService");
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                    LOGGER.debug("ClassNotFoundException e:[{}]", e);
                    return;
                }

                Method[] methods = aClass.getDeclaredMethods();
                for (Method method : methods) {
                    //找到对应的方法的
                    if (method.getName().equals(business_name)) {
                        //调用服务
                        try {
                            //输入参数
                            String[] inPutArgs = {dataToString};
                            //返回结果
                            String result;
                            LOGGER.debug("inPutArgs:[{}]", inPutArgs[0]);
                            result = (String) method.invoke(aClass.newInstance(), inPutArgs);
                            LOGGER.debug("result:[{}]");
                            if (msg.getReplyTo() != null && !msg.getReplyTo().trim().equals("")) {
                                try {
                                    replyMessage(msg.getReplyTo(), result.getBytes("UTF-8"));
                                } catch (UnsupportedEncodingException e) {
                                    LOGGER.debug("UnsupportedEncodingException e:[{}]");
                                }
                            }
                        } catch (IllegalAccessException e) {
                            LOGGER.debug("IllegalAccessException e:[{}]", e.getMessage());
                        } catch (InvocationTargetException e) {
                            LOGGER.debug("InvocationTargetException e:[{}]", e.getMessage());
                        } catch (InstantiationException e) {
                            LOGGER.debug("InstantiationException e:[{}]", e.getMessage());
                        }
                        break;
                    }
                }
            }
        });
    }
}
