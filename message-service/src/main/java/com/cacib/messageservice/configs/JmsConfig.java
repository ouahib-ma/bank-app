package com.cacib.messageservice.configs;

import com.ibm.mq.jms.MQQueueConnectionFactory;
import com.ibm.msg.client.wmq.WMQConstants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;

import jakarta.jms.ConnectionFactory;


@Configuration
@EnableJms
public class JmsConfig {
    @Value("${ibm.mq.queue-manager}")
    private String queueManager;

    @Value("${ibm.mq.channel}")
    private String channel;

    @Value("${ibm.mq.conn-name}")
    private String connName;

    @Bean
    public MQQueueConnectionFactory mqQueueConnectionFactory() throws Exception {
        MQQueueConnectionFactory factory = new MQQueueConnectionFactory();
        factory.setHostName(connName.split("\\(")[0]);
        factory.setPort(Integer.parseInt(connName.split("\\(")[1].replace(")", "")));
        factory.setQueueManager(queueManager);
        factory.setChannel(channel);
        factory.setTransportType(WMQConstants.WMQ_CM_CLIENT);
        return factory;
    }

    @Bean
    public DefaultJmsListenerContainerFactory jmsListenerContainerFactory(
            ConnectionFactory connectionFactory) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setConcurrency("3-10");
        return factory;
    }
}