package com.melexis.test.eventconsumer;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class EventRoute extends RouteBuilder {

	@Value("${activemq.queue}")
	private String activemqQueue;

	@Override
	public void configure() throws Exception {
		from("activemq:" + activemqQueue)
		.to("mock:test");
	}

}
