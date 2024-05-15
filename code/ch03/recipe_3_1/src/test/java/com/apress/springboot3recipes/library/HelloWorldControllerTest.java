package com.apress.springboot3recipes.library;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(HelloWorldController.class)
class HelloWorldControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	void testHelloWorldController() throws Exception {
		var expected = "Hello World, from Spring Boot ";
		mockMvc.perform(MockMvcRequestBuilders.get("/"))
			.andExpect(status().isOk())
			.andExpect(content().string(Matchers.containsString(expected)))
			.andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_PLAIN));
	}
}
