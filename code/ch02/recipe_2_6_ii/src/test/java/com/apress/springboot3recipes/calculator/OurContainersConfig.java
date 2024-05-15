package com.apress.springboot3recipes.calculator;

import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;

public interface OurContainersConfig {

    @ServiceConnection
    @Container
    PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15.2");
}
