package com.apress.springboot3recipes.library.rest;

import com.apress.springboot3recipes.library.Book;
import com.apress.springboot3recipes.library.BookGenerator;
import com.apress.springboot3recipes.library.BookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebFluxTest(BookController.class)
class BookControllerSliceWithMockTest {

  @Autowired
  private WebTestClient webTestClient;

  @MockBean
  private BookService bookService;

  @Test
  void listBooks() {

    Flux<Book> books = Flux.fromIterable(BookGenerator.random(10));
    when(bookService.findAll()).thenReturn(books);

    webTestClient.get().uri("/books")
            .exchange()
              .expectStatus().isOk()
              .expectBodyList(Book.class).hasSize(10);
  }

  @Test
  void addAndGetBook() {
    var book = BookGenerator.random();

    when(bookService.create(any())).thenReturn(Mono.just(book));
    when(bookService.find(book.isbn())).thenReturn(Mono.just(book));

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
