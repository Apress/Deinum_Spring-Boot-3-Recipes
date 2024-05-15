package com.apress.springboot3recipes.library;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.sql.DataSource;

@Configuration
public class LibrarySecurityConfig implements WebMvcConfigurer {

	private static final String USERS_BY_USERNAME_QUERY =
		"""
			SELECT username, password, 'true' as enabled
			FROM member WHERE username = ?
			""";

	private static final String AUTHORITIES_BY_USERNAME_QUERY =
		"""
			 SELECT member.username, member_role.role as authorities
			 FROM member, member_role
			 WHERE  member.username = ? AND member.id = member_role.member_id
			""";

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/login.html").setViewName("login");
	}

	@Bean
	public UserDetailsService jdbcUserDetailsService(DataSource datasource) {
		var usd = new JdbcDaoImpl();
		usd.setDataSource(datasource);
		usd.setUsersByUsernameQuery(USERS_BY_USERNAME_QUERY);
		usd.setAuthoritiesByUsernameQuery(AUTHORITIES_BY_USERNAME_QUERY);
		return usd;
	}
}
