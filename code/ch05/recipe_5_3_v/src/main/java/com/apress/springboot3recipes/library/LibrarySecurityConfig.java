package com.apress.springboot3recipes.library;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class LibrarySecurityConfig implements WebMvcConfigurer {

//	@Override
//	public void addViewControllers(ViewControllerRegistry registry) {
////		registry.addViewController("/login.html").setViewName("login");
//	}
//
//	@Bean
//	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//		http.authorizeHttpRequests((auth) -> auth.anyRequest().authenticated());
//		http.oauth2Login(Customizer.withDefaults());
//		return http.build();
//	}

}
