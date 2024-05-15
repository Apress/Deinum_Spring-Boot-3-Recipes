package com.apress.springboot3recipes.library;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class LibrarySecurityConfig implements WebMvcConfigurer {

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/login.html").setViewName("login");
	}

	@Bean
  public UserDetailsService userDetailsService() {
    var adminUser = User.withDefaultPasswordEncoder()
            .username("admin@books.io").password("secret")
            .authorities("ADMIN","USER").build();

    var normalUser = User.withDefaultPasswordEncoder()
            .username("marten@books.io").password("user")
            .authorities("USER").build();

    var disabledUser = User.withDefaultPasswordEncoder()
            .username("jdoe@books.io").password("unknown")
            .disabled(true)
            .authorities("USER").build();

		return new InMemoryUserDetailsManager(adminUser, normalUser, disabledUser);
  }
}
