package com.melexis.test;

import static org.junit.Assert.assertNotNull;

import java.time.LocalDateTime;
import java.time.format.FormatStyle;
import java.util.concurrent.TimeUnit;

import org.apache.camel.CamelContext;
import org.apache.camel.EndpointInject;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.NotifyBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.spring.CamelSpringBootRunner;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.melexis.test.eventconsumer.Application;

@RunWith(CamelSpringBootRunner.class)
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@SpringBootTest(classes = {Application.class})
class MachineEventIntegrationTest {
		
	@Autowired
    protected CamelContext camelContext;
	
	@Produce
	protected ProducerTemplate eventQueue;
	
	@Value("${activemq.queue}")
	private String activemqQueue;
	
	@Value("${camel.component.activemq.broker-url}")
	private String activemqBrokerUrl;
	
	@EndpointInject("mock:test")
	protected MockEndpoint testMock;
	
	private static final ObjectMapper objectMapper = new ObjectMapper();
	
	@Test
	void testSendToMachineEvent() throws Exception {
		MachineEventVO machineEventVO = new MachineEventVO();
        machineEventVO.setMachineID("MACHINE_1");
        machineEventVO.setDateTime(LocalDateTime.now());
        machineEventVO.setErrorType(ErrorType.TEMPERATURE_ERROR);
        machineEventVO.setMachineType(MachineType.MACHINE_TYPE2);

        testMock.expectedMessageCount(1);

		MessageHandler messageHandler = new MessageHandler();
        messageHandler.initialize(activemqBrokerUrl, activemqQueue);
        messageHandler.postMessage(objectMapper.writeValueAsString(machineEventVO));
		messageHandler.tearDown();
        
		testMock.assertIsSatisfied();
		
		String test = objectMapper.writeValueAsString(machineEventVO);

		MachineEventVO test2 = objectMapper.readValue(test, MachineEventVO.class);
				
		
		assertNotNull(test2);
	}
	
}
