package com.apress.springboot3recipes.calculator;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootApplication
public class DockerApplication {

    public static void main(String[] args) {
        SpringApplication.run(DockerApplication.class, args);
    }

    @Bean
    public ApplicationRunner lister(JdbcTemplate jdbc) {
        return (args) -> {
            jdbc.query("select * from pg_catalog.pg_tables", rs -> {
                System.out.printf("Table: %s.%s%n", rs.getString(1), rs.getString(2));
            });
        };
    }
}
