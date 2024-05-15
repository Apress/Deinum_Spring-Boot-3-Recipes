package com.apress.springboot3recipes.order.rest;

import com.apress.springboot3recipes.order.Order;
import com.apress.springboot3recipes.order.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.List;

import static java.math.BigDecimal.TEN;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.TEXT_EVENT_STREAM_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderController.class)
public class OrderControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private OrderService orderService;

  @Test
  public void ordersEventStream() throws Exception {

    var orders = List.of(new Order("1234", TEN));
    when(orderService.findAll()).thenReturn(orders);

    var mvcResult = mockMvc.perform(get("/orders"))
      .andExpect(request().asyncStarted())
      .andDo(MockMvcResultHandlers.log())
      .andReturn();

    mockMvc.perform(asyncDispatch(mvcResult))
      .andDo(MockMvcResultHandlers.log())
      .andExpect(status().isOk())
      .andExpect(content().contentTypeCompatibleWith(TEXT_EVENT_STREAM_VALUE))
      .andExpect(content().string(
        allOf(
          containsString("data:{\"id\":\"1234\",\"amount\":10}"),
          containsString("event:order-created"),
          containsString("id:"))));
  }
}
