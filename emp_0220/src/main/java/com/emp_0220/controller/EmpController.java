package com.emp_0220.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.emp_0220.service.EmpService;
import com.emp_0220.vo.EmpVO;

@RestController
public class EmpController {

  @Autowired
  private EmpService e_Service;

  @GetMapping("/list")
  public ModelAndView list() {

    ModelAndView mv = new ModelAndView("list");

    // Service에서 searchType, searchValue, begin, end를 String형태로 파라미터를 받아야하기 때문에,
    // 파라미터값을 넘겨주거나, 하지않아도 되지만, 페이징이 적용되어 있기에, begin과 end에는
    // 값을 넣어줘야한다.
    EmpVO[] ar = e_Service.getList(null, null, "1", "10");
    mv.addObject("ar", ar);

    return mv;
  }

  @PostMapping("/search")
  @ResponseBody
  public Map<String, Object> search(@RequestParam("type") String type, @RequestParam("value") String value, String cPage) {
    Map<String,Object> map = new HashMap<>();
    
    EmpVO[] ar = e_Service.getList(type, value, "1", "10");

    map.put("ar",ar);
    map.put("length", ar.length);
    

    return map;
  }

}
