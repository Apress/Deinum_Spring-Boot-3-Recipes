package com.apress.springboot3recipes.library;

import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.web.reactive.function.server.ServerRequest;

import java.util.Map;

public class CustomizedErrorAttributes extends DefaultErrorAttributes {

	@Override
	public Map<String, Object> getErrorAttributes(ServerRequest req,
	                                              ErrorAttributeOptions options) {
		var errorAttributes = super.getErrorAttributes(req, options);
		errorAttributes.put("parameters", req.exchange().getRequest().getQueryParams());
		return errorAttributes;
	}
}
