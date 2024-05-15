package com.apress.springboot3recipes.order;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import reactor.core.publisher.Mono;

@Configuration
@EnableWebFluxSecurity
public class OrdersSecurityConfiguration {

	@Bean
	public SecurityWebFilterChain springWebFilterChain(ServerHttpSecurity http) {
		http
			.httpBasic(Customizer.withDefaults())
			.formLogin(Customizer.withDefaults())
			.headers(Customizer.withDefaults())
			.logout(Customizer.withDefaults())
			.csrf(Customizer.withDefaults());

		http
			.authorizeExchange((auth) -> auth
					.pathMatchers("/").permitAll()
					.pathMatchers("/orders*").access(this::ordersAllowed)
					.anyExchange().authenticated());

		return http.build();
	}

	private Mono<AuthorizationDecision> ordersAllowed(
		Mono<Authentication> authentication, AuthorizationContext context) {
		return authentication
			.map(a -> a.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN")))
			.map(AuthorizationDecision::new);
	}

	@Bean
	public MapReactiveUserDetailsService userDetailsService() {
		var marten = User.withDefaultPasswordEncoder()
			.username("marten").password("secret").roles("USER").build();
		var admin = User.withDefaultPasswordEncoder()
			.username("admin").password("admin").roles("USER", "ADMIN").build();
		return new MapReactiveUserDetailsService(marten, admin);
	}
}
