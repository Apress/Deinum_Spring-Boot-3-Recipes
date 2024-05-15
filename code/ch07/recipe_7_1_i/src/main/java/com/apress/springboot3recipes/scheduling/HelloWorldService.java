package com.apress.springboot3recipes.scheduling;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class HelloWorldService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Async
    public void printMessage() {
	    try {
		    Thread.sleep(500);
	    } catch (InterruptedException ex) {
		    Thread.currentThread().interrupt();
	    }
	    logger.info("Hello World, from Spring Boot 3!");
    }
}
