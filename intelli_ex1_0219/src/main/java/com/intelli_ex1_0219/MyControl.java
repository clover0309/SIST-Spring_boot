package com.intelli_ex1_0219;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyControl {
    // @RestController = @Controller + @ResponseBody와 같다.

  @GetMapping("/t1")
  public String t1() {
    return "Test 연습입니다.";
  }
}
