package com.epam.carrental.config;

import com.epam.carrental.jms.DataChangedInterceptor;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.ConnectionFactory;

@Configuration
@EnableJms
public class JmsConfiguration {

    @Value("${remote.jms.brokerURL}")
    private String brokerURL;

    @Bean(initMethod = "start", destroyMethod = "stop")
    public BrokerService brokerService() throws Exception {
        BrokerService brokerService = new BrokerService();
        brokerService.setPersistent(false);
        brokerService.setUseJmx(false);
        brokerService.addConnector(brokerURL);
        brokerService.setUseShutdownHook(false);
        return brokerService;
    }

    @Bean
    public ConnectionFactory connectionFactory() {
        return new ActiveMQConnectionFactory(brokerURL);
    }

    @Bean
    public ActiveMQTopic topic(@Value("${remote.jms.topic}") String topic) {
        return new ActiveMQTopic(topic);
    }

    @Bean
    public JmsTemplate jmsTemplate(ConnectionFactory connectionFactory,ActiveMQTopic defaultDestination) {
        JmsTemplate jmsTemplate = new JmsTemplate(connectionFactory);
        jmsTemplate.setDefaultDestination(defaultDestination);
        jmsTemplate.setSessionTransacted(true);
        return jmsTemplate;
    }

    @Bean
    DataChangedInterceptor dataChangedInterceptor() {
        return new DataChangedInterceptor();
    }
}