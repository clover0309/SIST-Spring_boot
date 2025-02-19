package com.example.ex1;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestControl {

    @GetMapping("/tt")
    public String getMethodName(@RequestParam String param) {
        return "VSCODE 스프링 환경구축 테스트입니다." + param;
    }

    @GetMapping("test/{var}")
    // public String test(@PathVariable("var") String v1) {
    public String test(@PathVariable String var) {
        return "경로변수활용  : " + var;
    }

    @GetMapping("/req1")
    public String getReq(String name, String email) {
        return name + "/" + email;
    }

}
