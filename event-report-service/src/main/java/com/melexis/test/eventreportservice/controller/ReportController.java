package com.melexis.test.eventreportservice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.melexis.test.eventreportservice.model.Machine;

@RestController
@RequestMapping("/api/v1")
public class ReportController {
	
	private static final Logger logger = LoggerFactory.getLogger(ReportController.class);

	@GetMapping("/machine")
	public Machine getMachine() {
		logger.info("Received GET request - getMachine");
		Machine m = new Machine();
		m.setId("Test123");
		return m;
	}
}
