package com.melexis.test.eventconsumer;

import static org.junit.Assert.assertNotNull;

import java.time.LocalDateTime;

import org.apache.camel.CamelContext;
import org.apache.camel.EndpointInject;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.spring.CamelSpringBootRunner;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.melexis.test.eventconsumer.Application;

@RunWith(CamelSpringBootRunner.class)
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@SpringBootTest(classes = { Application.class })
class EventRouteTest {

	@Autowired
	protected CamelContext camelContext;

	@Produce
	protected ProducerTemplate eventQueue;

	@Value("${activemq.queue}")
	private String activemqQueue;

	@EndpointInject("mock:test")
	protected MockEndpoint testMock;
	
   
	void testSendToQueue() throws Exception {
		testMock.expectedMessageCount(1);
		eventQueue.sendBody("activemq:" + activemqQueue, "test");
		testMock.assertIsSatisfied();
	}

	@Test
	void testSendExampleEvent() throws Exception {
		String eventStr = "{\"machineType\":\"MACHINE_TYPE2\",\"machineID\":\"MACHINE_1\",\"dateTime\":{\"nano\":134268000,\"year\":2021,\"monthValue\":4,\"dayOfMonth\":9,\"hour\":7,\"minute\":30,\"second\":18,\"dayOfWeek\":\"FRIDAY\",\"dayOfYear\":99,\"month\":\"APRIL\",\"chronology\":{\"calendarType\":\"iso8601\",\"id\":\"ISO\"}},\"errorType\":\"TEMPERATURE_ERROR\"}";
		
		testMock.expectedMessageCount(1);
		eventQueue.sendBody("activemq:" + activemqQueue, eventStr);
		testMock.assertIsSatisfied();
		
	}

}
