package com.apress.springboot3recipes.library.i18n;

import org.springframework.context.i18n.SimpleLocaleContext;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import org.springframework.web.server.i18n.LocaleContextResolver;
import reactor.core.publisher.Mono;

import java.util.Locale;

public class LocaleChangeWebFilter implements WebFilter {

	public static final String DEFAULT_PARAM_NAME = "locale";

	private final LocaleContextResolver resolver;

	public LocaleChangeWebFilter(LocaleContextResolver resolver) {
		this.resolver = resolver;
	}

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
		var params = exchange.getRequest().getQueryParams();
		if (params.containsKey(DEFAULT_PARAM_NAME)) {
			var locale = params.getFirst(DEFAULT_PARAM_NAME);
			var context = StringUtils.hasText(locale) ?
				new SimpleLocaleContext(Locale.forLanguageTag(locale)) : null;
			resolver.setLocaleContext(exchange, context);
		}
		return chain.filter(exchange);
	}
}
