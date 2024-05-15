package com.apress.springboot3recipes.scheduling;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.VirtualThreadTaskExecutor;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;

import java.io.IOException;
import java.util.concurrent.Executor;

@SpringBootApplication
@EnableAsync
public class ThreadingApplication implements AsyncConfigurer {

    public static void main(String[] args) throws IOException {
        SpringApplication.run(ThreadingApplication.class, args);

        System.out.println("Press [ENTER] to quit:");
        System.in.read();
    }

    @Bean
    public VirtualThreadTaskExecutor taskExecutor() {
        return new VirtualThreadTaskExecutor();
    }

    @Override
    public Executor getAsyncExecutor() {
        return taskExecutor();
    }

    @Bean
    public ApplicationRunner startupRunner(HelloWorld hello) {
        return (args) -> hello.printMessage();
    }
}
