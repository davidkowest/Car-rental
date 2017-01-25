package com.epam.carrental.config;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.destination.BeanFactoryDestinationResolver;

import javax.jms.ConnectionFactory;
import javax.jms.JMSException;

@EnableJms
@Configuration
public class JmsConfiguration {

    @Bean
    public ConnectionFactory connectionFactory(@Value("${remote.jms.brokerURL}") String brokerURL) {
        return new ActiveMQConnectionFactory(brokerURL);
    }

    @Bean
    public ActiveMQTopic topic(@Value("${remote.jms.topic}") String topic) {
        return new ActiveMQTopic(topic);
    }

    @Bean
    public JmsTemplate jmsTemplate(ConnectionFactory connectionFactory) throws JMSException {
        return new JmsTemplate(connectionFactory);
    }

    @Bean
    public DefaultJmsListenerContainerFactory containerFactory(ConnectionFactory connectionFactory, BeanFactory springContextBeanFactory) {
        DefaultJmsListenerContainerFactory containerFactory = new DefaultJmsListenerContainerFactory();
        containerFactory.setConnectionFactory(connectionFactory);
        containerFactory.setDestinationResolver(new BeanFactoryDestinationResolver(springContextBeanFactory));
        containerFactory.setSubscriptionDurable(false);
        return containerFactory;
    }
}
