package com.apress.springboot3recipes.library.i18n;

import org.springframework.context.i18n.LocaleContext;
import org.springframework.context.i18n.SimpleLocaleContext;
import org.springframework.http.ResponseCookie;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.i18n.AcceptHeaderLocaleContextResolver;

import java.util.Locale;

public class CookieLocaleContextResolver extends AcceptHeaderLocaleContextResolver {

	public static final String DEFAULT_NAME =
		CookieLocaleContextResolver.class.getName() + ".LOCALE";

	@Override
	public LocaleContext resolveLocaleContext(ServerWebExchange exchange) {
		parseCookieIfNecessary(exchange);
		return exchange.getAttribute(DEFAULT_NAME);
	}

	private void parseCookieIfNecessary(ServerWebExchange exchange) {
		if (exchange.getAttribute(DEFAULT_NAME) == null) {
			var cookies = exchange.getRequest().getCookies();
			var cookie = cookies.getFirst(DEFAULT_NAME);
			if (cookie != null) {
				var locale = Locale.forLanguageTag(cookie.getValue());
				var context = new SimpleLocaleContext(locale);
				exchange.getAttributes().put(DEFAULT_NAME, context);
			} else
				exchange.getAttributes().put(DEFAULT_NAME, super.resolveLocaleContext(exchange));
			}
	}

	@Override
	public void setLocaleContext(ServerWebExchange exchange, LocaleContext lc) {
		var cookies = exchange.getResponse().getCookies();
		if (lc != null && lc.getLocale() != null) {
			exchange.getAttributes().put(DEFAULT_NAME, lc);
			var langTag = lc.getLocale().toLanguageTag();
			var cookie = ResponseCookie.from(DEFAULT_NAME, langTag).build();
			cookies.set(DEFAULT_NAME, cookie);
		} else {
			exchange.getAttributes().remove(DEFAULT_NAME, lc);
			cookies.remove(DEFAULT_NAME);
		}
	}
}
