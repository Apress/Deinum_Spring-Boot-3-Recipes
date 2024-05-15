package com.apress.springboot3recipes.scheduling;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class ThreadingApplication {

    public static void main(String[] args) {
        SpringApplication.run(ThreadingApplication.class, args);
    }

    @Bean
    public ApplicationRunner startupRunner(HelloWorldService hello) {
        return (args) -> hello.printMessage();
    }
}
