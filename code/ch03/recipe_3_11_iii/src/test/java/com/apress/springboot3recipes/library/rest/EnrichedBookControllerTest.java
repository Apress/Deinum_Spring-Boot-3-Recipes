package com.apress.springboot3recipes.library.rest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@RestClientTest(EnrichedBookController.class)
class EnrichedBookControllerTest {

	private static final String BOOK_JSON = """
			{
		    "authors": [
		        "T. Author"
		    ],
		    "isbn": "123456789",
		    "title": "The Client Test"
		}
		""";

	private static final String OL_JSON = """
		{ "publish_date" : "2024" }
		""";

	@Autowired
	private MockRestServiceServer mockServer;

	@Autowired
	private EnrichedBookController controller;

	@Test
	void enrichBook() {

		mockServer
			.expect(requestTo("http://localhost:8080/books/123456789"))
			.andRespond(withSuccess(BOOK_JSON, MediaType.APPLICATION_JSON));

		mockServer
			.expect(requestTo("https://openlibrary.org/isbn/123456789.json"))
			.andRespond(withSuccess(OL_JSON, MediaType.APPLICATION_JSON));

		var response = controller.get("123456789");
		var enrichedBook = response.getBody();
		Assertions.assertTrue(response.getStatusCode().is2xxSuccessful(), () -> "Expected HTTP 200, got " + response.getStatusCode());
		Assertions.assertNotNull(enrichedBook);
		Assertions.assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
		Assertions.assertEquals(enrichedBook.published(), "2024");

		mockServer.verify();
	}
}
