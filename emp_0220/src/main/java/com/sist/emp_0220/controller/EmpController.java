package com.sist.emp_0220.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.sist.emp_0220.service.EmpService;
import com.sist.emp_0220.vo.EmpVO;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@RestController
public class EmpController {

    @Autowired
    private EmpService e_Service;

    @GetMapping("/list")
    public ModelAndView list() {
        ModelAndView mv = new ModelAndView();

        EmpVO[] ar = e_Service.getList(
            null, null, "1", "10");
        mv.addObject("ar", ar);
        mv.setViewName("list");
        return mv;
    }

    @PostMapping("/search")
    @ResponseBody
    //public ModelAndView search(@RequestParam String type, 
    public Map<String, Object> search(@RequestParam String type, 
            @RequestParam String value, String cPage) {
        //ModelAndView mv = new ModelAndView();
        Map<String, Object> map = new HashMap<>();
                
        EmpVO[] ar = e_Service.getList(
            type, value, "1", "10");
        //mv.addObject("ar", ar);
        //mv.setViewName("list");
        //return mv;
        map.put("ar", ar);
        map.put("length", ar.length);

        return map;
    }
    
}
