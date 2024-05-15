package com.apress.springboot3recipes;

import org.springframework.boot.SpringBootVersion;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadLocalRandom;

@RestController
public class HelloWorldController {

	private final AsyncTaskExecutor taskExecutor;

	public HelloWorldController(AsyncTaskExecutor taskExecutor) {
		this.taskExecutor = taskExecutor;
	}

	@GetMapping
	public CompletableFuture<String> hello() {
		return taskExecutor.submitCompletable(() -> {
			randomDelay();
			var version = SpringBootVersion.getVersion();
			return String.format("Hello World, from Spring Boot %s!", version);
		});
	}

	private void randomDelay() {
		try {
			Thread.sleep(ThreadLocalRandom.current().nextInt(5000));
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}
}
