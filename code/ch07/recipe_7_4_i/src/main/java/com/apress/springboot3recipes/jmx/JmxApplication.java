package com.apress.springboot3recipes.jmx;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class JmxApplication {

	public static void main(String[] args) throws IOException {
		SpringApplication.run(JmxApplication.class, args);

		System.out.println("Press [ENTER] to quit:");
		System.in.read();
	}
}
