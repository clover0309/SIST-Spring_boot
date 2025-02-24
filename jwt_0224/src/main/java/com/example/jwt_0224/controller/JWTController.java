package com.example.jwt_0224.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JWTController {

  @GetMapping("/")
  public String getMethodName() {
    return "JWT Practice";
  }

}
