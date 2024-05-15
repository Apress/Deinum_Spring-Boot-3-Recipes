package com.apress.springboot3recipes.calculator;

import org.springframework.boot.SpringApplication;

public class TestDockerApplication {

    public static void main(String[] args) {
        SpringApplication.from(DockerApplication::main)
                .with(TestContainersConfig.class).run(args);
    }

}
