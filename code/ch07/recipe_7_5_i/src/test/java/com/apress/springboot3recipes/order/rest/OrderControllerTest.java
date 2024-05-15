package com.apress.springboot3recipes.order.rest;

import com.apress.springboot3recipes.order.Order;
import com.apress.springboot3recipes.order.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;

import java.math.BigDecimal;

import static org.mockito.Mockito.when;

@WebFluxTest(OrderController.class)
public class OrderControllerTest {

  @Autowired
  private WebTestClient webTestClient;

  @MockBean
  private OrderService orderService;

  @Test
  public void shouldReturnOrdres() throws Exception {

    when(orderService.findAll()).thenReturn(Flux.just((new Order("1234", BigDecimal.TEN))));

    webTestClient.get()
        .uri("/orders")
        .accept(MediaType.APPLICATION_NDJSON)
        .exchange()
            .expectStatus().isOk()
        .expectBody().json("{\"id\":\"1234\",\"amount\":10}");
  }
}
