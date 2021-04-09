package com.melexis.test.eventconsumer;

import javax.jms.ConnectionFactory;

import org.apache.activemq.spring.ActiveMQConnectionFactory;
import org.apache.camel.component.activemq.ActiveMQComponent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ActiveMQComponentConfig {
	
	@Value("${camel.component.activemq.broker-url}")
	private String activemqBrokerUrl;	

	@Bean(name = "activemq")
    public ActiveMQComponent createComponent(ConnectionFactory factory) {
        ActiveMQComponent activeMQComponent = new ActiveMQComponent();
        ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory();
        cf.setBrokerURL(activemqBrokerUrl);
        activeMQComponent.setConnectionFactory(cf);
        return activeMQComponent;
    }
}
