package com.apress.springboot3recipes.library.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

@Component
public final class AccessChecker {

	public boolean hasLocalAccess(Authentication auth) {
		boolean access = false;
		if (auth.getDetails() instanceof WebAuthenticationDetails details) {
			String address = details.getRemoteAddress();
			access = address.equals("127.0.0.1") || address.equals("0:0:0:0:0:0:0:1");
		}
		return access;
	}
}
