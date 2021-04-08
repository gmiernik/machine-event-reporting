package com.melexis.test.eventconsumer;

import org.apache.camel.CamelContext;
import org.apache.camel.EndpointInject;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.spring.CamelSpringBootRunner;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

@RunWith(CamelSpringBootRunner.class)
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@SpringBootTest(classes = {Application.class})
class EventRouteTest {
		
	@Autowired
    protected CamelContext camelContext;
	
	@Produce("activemq:machine.event")
	protected ProducerTemplate eventQueue;
	
	@EndpointInject("mock:test")
	protected MockEndpoint testMock;
	
	@Test
	void testSendToQueue() throws Exception {
		testMock.expectedMessageCount(1);
		eventQueue.sendBody("test");
		testMock.assertIsSatisfied();
	}

}
