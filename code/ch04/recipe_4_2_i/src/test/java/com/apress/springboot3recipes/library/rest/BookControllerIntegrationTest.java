package com.apress.springboot3recipes.library.rest;

import com.apress.springboot3recipes.library.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureWebTestClient
@DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
class BookControllerIntegrationTest {

  @Autowired
  private WebTestClient webTestClient;

  @Test
  void listBooks() {

    webTestClient.get().uri("/books")
            .exchange()
              .expectStatus().isOk()
              .expectBodyList(Book.class).hasSize(24);
  }

  @Test
  void addAndGetBook() {

    var book = new Book("B0CBC71PHR", "Testing Spring Boot Applications Demystified", List.of("Philip Riecks"));
    webTestClient.post().uri("/books").bodyValue(book)
            .exchange()
              .expectStatus().isCreated()
              .expectBody(Book.class).isEqualTo(book);

    webTestClient.get().uri("/books/{isbn}", book.isbn())
            .exchange()
            .expectStatus().isOk()
            .expectBody(Book.class).isEqualTo(book);
  }
}
