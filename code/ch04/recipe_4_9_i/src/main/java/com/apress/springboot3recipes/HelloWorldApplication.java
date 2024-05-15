package com.apress.springboot3recipes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.task.SimpleAsyncTaskExecutorBuilder;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.BlockingExecutionConfigurer;
import org.springframework.web.reactive.config.WebFluxConfigurer;

@SpringBootApplication
public class HelloWorldApplication {

	public static void main(String[] args) {
		SpringApplication.run(HelloWorldApplication.class, args);
	}

	@Configuration
	public static class WebfluxConfiguration implements WebFluxConfigurer {

		private final SimpleAsyncTaskExecutorBuilder builder;

		public WebfluxConfiguration(SimpleAsyncTaskExecutorBuilder builder) {
			this.builder = builder;
		}

		@Override
		public void configureBlockingExecution(BlockingExecutionConfigurer configurer) {
			configurer.setExecutor(builder.threadNamePrefix("blocking-").build());
		}
	}
}
