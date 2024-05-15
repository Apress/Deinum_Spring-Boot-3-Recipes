package com.apress.springboot3recipes.demo;

import java.util.Arrays;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Simple application which will print the number of beans in the context and
 * a sorted list of bean names.
 *
 * @author Marten Deinum
 */
@SpringBootApplication
public class DemoApplication {

  public static void main(String[] args) {
    try (var ctx = SpringApplication.run(DemoApplication.class, args)) {

      System.out.println("# Beans: " + ctx.getBeanDefinitionCount());

      var names = ctx.getBeanDefinitionNames();
      Arrays.sort(names);
      Arrays.asList(names).forEach(System.out::println);
    }
  }
}
