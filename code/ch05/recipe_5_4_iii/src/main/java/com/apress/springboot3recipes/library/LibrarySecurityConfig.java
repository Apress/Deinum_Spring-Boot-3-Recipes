package com.apress.springboot3recipes.library;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.sql.DataSource;

@Configuration
@EnableMethodSecurity
public class LibrarySecurityConfig implements WebMvcConfigurer {

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/login.html").setViewName("login");
	}

	@Bean
  public UserDetailsService jdbcUserDetailsService(DataSource datasource) {
		var usd = new JdbcDaoImpl();
		usd.setDataSource(datasource);
		return usd;
  }
}
