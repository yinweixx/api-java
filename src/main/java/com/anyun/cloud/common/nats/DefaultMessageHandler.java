package com.anyun.cloud.common.nats;

import com.anyun.cloud.common.context.Context;
import com.anyun.cloud.common.context.ControllerIOC;
import com.anyun.cloud.common.json.JsonUtil;
import com.anyun.cloud.common.nats.format.DefaultErrorMessageFormat;
import com.anyun.cloud.common.nats.format.MessageFormater;
import com.anyun.cloud.common.nats.params.ParamsBuilder;
import com.anyun.cloud.common.nats.protocol.RequestMessage;
import com.anyun.cloud.common.nats.proxy.ProxyServiceMethodBuilder;
import com.anyun.cloud.common.nats.proxy.ProxyServiceMethodInstance;
import com.iciql.util.StringUtils;
import io.nats.client.Message;
import io.nats.client.MessageHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 08/12/2017
 */
public class DefaultMessageHandler implements MessageHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultMessageHandler.class);
    private Map<Class<? extends ParamsBuilder>, ParamsBuilder> builders;
    private ProxyServiceMethodBuilder proxyServiceMethodBuilder;
    private Context context;

    @Inject
    public DefaultMessageHandler(ProxyServiceMethodBuilder proxyServiceMethodBuilder, Context context) {
        this.context = context;
        builders = new HashMap<>();
        this.proxyServiceMethodBuilder = proxyServiceMethodBuilder;
    }

    @Override
    public void onMessage(Message message) {
        String recivedMessage = new String(message.getData());
        LOGGER.debug("recived message: {}", recivedMessage);
        String _business = "";
        try {
            RequestMessage<Map<String, List<String>>> requestMessage =
                    JsonUtil.fromJson(RequestMessage.class, recivedMessage);
            String business = _business = requestMessage.getBusiness();
            ProxyServiceMethodInstance<Map<String, List<String>>> instance =
                    proxyServiceMethodBuilder.build(
                            ControllerIOC.getIOC().getInjector().getBindings(),
                            business, requestMessage.getContent());
            if (instance == null) {
                throw new Exception("not find service by [" + business + "]");
            }
            Object invokeValue = instance.invoke();
            if (StringUtils.isNullOrEmpty(message.getReplyTo())) {
                LOGGER.debug("could not find reply to inbox");
                return;
            }
            MessageFormater formater = instance.getFormater();
            Message response = new Message();
            response.setSubject(message.getReplyTo());
            response.setData(formater.format(business, invokeValue));
            LOGGER.debug("response: {}", new String(response.getData()));
            context.getNats().getConnection().publish(response);
        } catch (Exception ex) {
            if (StringUtils.isNullOrEmpty(message.getReplyTo())) {
                LOGGER.debug("could not find reply to inbox");
                return;
            }
            MessageFormater formater = new DefaultErrorMessageFormat();
            byte[] error = formater.format(_business, ex);
            Message response = new Message(message.getReplyTo(), null, error);

            try {
                context.getNats().getConnection().publish(response);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        String replyTo = message.getReplyTo();
        if (StringUtils.isNullOrEmpty(replyTo))
            return;
    }
}
