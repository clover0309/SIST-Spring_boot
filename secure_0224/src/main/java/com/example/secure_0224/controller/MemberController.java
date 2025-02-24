package com.example.secure_0224.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.example.secure_0224.service.MemberSerivce;
import com.example.secure_0224.vo.MemVO;

import jakarta.servlet.http.HttpSession;

@RestController
public class MemberController {
  // DB활용을 위한 service 객체
  @Autowired
  private MemberSerivce m_service;

  // 로그인 처리를 위한 세션
  @Autowired
  private HttpSession session;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @RequestMapping
  public ModelAndView index() {
    ModelAndView mv = new ModelAndView();
    mv.setViewName("index");
    return mv;
  }

  @RequestMapping("reg")
  public ModelAndView reg() {
    ModelAndView mv = new ModelAndView();
    mv.setViewName("reg");
    return mv;
  }

  // 여기서는 암호화가 된 비밀번호가 db에 저장되기 때문에, 컬럼의 용량을 바꿔줘야함.
  // not null이 지정되어 있는 컬럼들이 존재하는지 확인을 해줘야한다.
  // 만약에 not null이 지정되어 있다면, 기본값을 지정을 해주든, not null을 해지하든 해야한다.
  @PostMapping("reg")
  public ModelAndView reg(MemVO vo) {
    // 서비스를 이용하여 회원가입.
    int cnt = m_service.regMember(vo);
    ModelAndView mv = new ModelAndView();
    mv.setViewName("index");
    return mv;
  }

  @GetMapping("login")
  public ModelAndView login() {
    ModelAndView mv = new ModelAndView();
    mv.setViewName("login");
    return mv;
  }

  @PostMapping("reqLogin")
  public ModelAndView login(MemVO vo) {
    ModelAndView mv = new ModelAndView();
    MemVO mvo = m_service.login(vo);
    System.out.println("mvo : " + mvo);

    // 여기서 mvo가 null이면 아이디 또는 비밀번호가 불일치하다.
    if (mvo != null)
      session.setAttribute("mvo", mvo);
    else
      mv.addObject("status", "1");

    mv.setViewName("redirect:/");
    return mv;
  }

}
