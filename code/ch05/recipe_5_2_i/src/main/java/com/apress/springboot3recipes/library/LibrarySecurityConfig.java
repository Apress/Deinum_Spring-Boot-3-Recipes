package com.apress.springboot3recipes.library;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class LibrarySecurityConfig {

	@Bean
  public SecurityFilterChain security(HttpSecurity http) throws Exception {
    http.securityContext(Customizer.withDefaults());
		http.exceptionHandling(Customizer.withDefaults());
		http.servletApi(Customizer.withDefaults());
		http.httpBasic(Customizer.withDefaults());
		http.logout(Customizer.withDefaults());
		http.headers(Customizer.withDefaults());
		http.csrf(Customizer.withDefaults());
		http.anonymous( (anon) ->
		    anon.principal("guest").authorities("ROLE_GUEST"));
		http.rememberMe(Customizer.withDefaults());
		http.formLogin( (login) ->
		        login.loginPage("/login.html")
			        .defaultSuccessUrl("/books")
			        .failureUrl("/login.html?error=true").permitAll());
		http.authorizeHttpRequests( (auth) ->
		    auth.requestMatchers("/").permitAll()
			      .anyRequest().authenticated());

		return http.build();
  }
}
