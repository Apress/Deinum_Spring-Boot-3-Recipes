package com.apress.springboot3recipes.scheduling;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class HelloWorldService {

    private static final Logger logger = LoggerFactory.getLogger(
				    HelloWorldService.class);

    @Async
    public void printMessage() throws InterruptedException {
        Thread.sleep(500);
        logger.info("Hello World, from Spring Boot 3!");
    }
}
