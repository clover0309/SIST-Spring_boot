package com.sist.bbs_0220.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

//단순 경로이동을 위해서 Controller를 하나 더 만들었음.
//RestController는 데이터를 전송할때 사용하는 어노테이션 인듯 함.
@Controller
public class bController {
    @RequestMapping("/")
    public String index() {
        return "index";
    }
}
