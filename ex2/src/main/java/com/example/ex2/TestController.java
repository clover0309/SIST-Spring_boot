package com.example.ex2;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class TestController {

  // application.properties파일에 있는 변수 값을 가져온다.
  @Value("${msg}")
  private String msg;

  @RequestMapping(value = "/t1", method = RequestMethod.GET)
  public ModelAndView requestMethodName() {
    ModelAndView mv = new ModelAndView();
    mv.addObject("str", "쌍용교육센터");
    mv.addObject("msg", msg);
    mv.setViewName("t1"); // t1.jsp라고 의미를 칭하긴하나.. ViewReslover가 없어서 안됨.
    return mv;
  }

}
