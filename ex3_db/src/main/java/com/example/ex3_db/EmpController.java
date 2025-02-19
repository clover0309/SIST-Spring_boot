package com.example.ex3_db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.example.ex3_db.service.EmpService;
import com.example.ex3_db.vo.EmpVO;

@RestController
public class EmpController {

  @Autowired
  private EmpService service;

  @RequestMapping("/list")
  public ModelAndView requestMethodName() {
    ModelAndView mv = new ModelAndView();

    // 뷰페이지에서 표현할 자원을 얻어낸다.
    EmpVO[] ar = service.getList();
    System.out.println("ar : " + ar);

    // jsp에서 사용하는 변수명이 ar임으로 ar으로 지정.
    mv.addObject("ar", ar);
    mv.setViewName("list");
    return mv;
  }

  @PostMapping("empSearch")
  public ModelAndView search(@RequestParam("type") String type, @RequestParam("value") String value) {
    ModelAndView mv = new ModelAndView("Search");

    EmpVO[] ar = service.search(type, value);

    mv.addObject("ar", ar);

    mv.setViewName("empSearch");

    return mv;
  }
}
