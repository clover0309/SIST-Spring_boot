package com.example.bbs_0220.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.example.bbs_0220.service.BbsService;
import com.example.bbs_0220.util.Paging;
import com.example.bbs_0220.vo.BbsVO;

import jakarta.servlet.ServletContext;

@RestController
public class BbsController {

  @Autowired
  private BbsService bservice;

  // realpath를 얻기위해 ServletContext를 불러냄.
  @Autowired
  private ServletContext application;

  @RequestMapping("list")
  public ModelAndView list(@RequestParam String bname, String searchType,
      String searchValue, String cPage) {
    ModelAndView mv = new ModelAndView("list");
    System.out.println("Controller Entry..");
    int nowPage = 1;

    if (cPage != null) {
      nowPage = Integer.parseInt(cPage);
    }

    // bname이 무조건 인자로 넘어와야 한다. 그런데, 공백이 들어가 있으면 안된다.
    if (bname.trim().length() == 0)
      bname = "BBS";
    System.out.println("bname: " + bname);
    // 전체 게시물의 수를 구함.
    // type과 value가 안넘어오면 null, null, bname:BBS가 지정된다.
    // String searchType, String searchValue은 @RequestParam이 지정되어 있는게 아니라 파라미터가
    // 필수가아님.
    int totalRecord = bservice.getTotalCount(searchType, searchValue, bname);

    // 페이징 객체 생성. (내일 수정될 확률이 있음.)
    Paging page = new Paging(7, 5);
    page.setTotalRecord(totalRecord);
    page.setNowPage(nowPage);

    // 뷰페이지에서 표현할 목록을 가져올 때 사용하는 값(begin, end)
    int begin = page.getBegin();
    int end = page.getEnd();

    // 목록을 가져오는 메소드 호출
    BbsVO[] ar = bservice.getList(searchType, searchValue, bname, begin, end);

    // 목록을 뷰페이지로 보내기 위해 request(mv)에 저장
    mv.addObject("ar", ar);
    mv.addObject("page", page);
    mv.addObject("totalRecord", totalRecord);
    mv.addObject("bname", bname);
    mv.addObject("nowPage", nowPage);

    // 경로변수를 사용하여서 게시판을 구분한다.
    mv.setViewName(bname + "/list");
    return mv;
  }

  @RequestMapping("write")
  public String write() {
    return "/write";
  }
}
