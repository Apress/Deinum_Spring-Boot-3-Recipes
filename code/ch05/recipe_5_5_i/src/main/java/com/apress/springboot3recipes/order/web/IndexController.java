package com.apress.springboot3recipes.order.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
class IndexController {

  @GetMapping
  public String index() {
    return "index";
  }

}
