package com.sist.bbs_0220.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.sist.bbs_0220.service.BbsService;
import com.sist.bbs_0220.util.FileRenameUtil;
import com.sist.bbs_0220.util.Paging;
import com.sist.bbs_0220.vo.BbsVO;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@RestController
public class BbsController {

    @Autowired
    private BbsService bService;

    @Autowired
    private ServletContext application;

    @Autowired
    private HttpSession session;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private HttpServletResponse response;

    @Value("${server.upload.path}")
    private String upload_img;

    @Value("${server.bbs_upload.path}")
    private String bbs_upload;

    private BbsVO[] r_list;

    @RequestMapping("list")
    //cPage는 현재 페이지를 의미한다.
    public ModelAndView list(@RequestParam String bname, String searchType,
            String searchValue, String cPage) {
        //페이지에 표현할 데이터를 가져오기 위해서 ModelAndView를 사용한다.
        ModelAndView mv = new ModelAndView();
        
        //페이징 기법을 위해 기본적으로 nowPage를 1로 설정한다.
        //처음에 접근을 하면 nowPage가 null이라서 1로 설정해줌.
        int nowPage = 1;
        
        //만약에 cPage가 null이 아니면, nowPage를 cPage로 설정.
        if (cPage != null)
            nowPage = Integer.parseInt(cPage);

        // bname이 무조건 인자로 넘어와야 한다. 그런데 공백이면 안된다.
        if (bname.trim().length() == 0)
            bname = "BBS";

        // 전체게시물의 수를 위에서 @Autowired로 선언해둔 bService를 통해서 가져온다.
        // 이때, 검색어가 있을수도 있고 없을 수도 있으니, searchType과 searchValue를 인자로 넘겨준다.
        // bname은 게시판의 이름이다.
        int totalRecord = bService.getTotalCount(searchType, searchValue, bname);

        // 페이징 객체 생성
        // numPerPage는 한 페이지에 보여줄 게시글이 7개라는 의미고, 
        // pagePerBlock은 한 블럭에 보여줄 페이지가 5개라는 의미이다.
        Paging page = new Paging(7, 5);

        //Paging에 totalRecord와 nowPage를 넘겨준다.
        //totalRecord는 전체 게시물의 수이고, nowPage는 현재 페이지이다.
        page.setTotalRecord(totalRecord);
        page.setNowPage(nowPage);

        // 뷰페이지에서 표현할 목록을 가져올 때 사용하는 값(begin,end)

        // begin은 한 페이지에 보여줄 게시글의 시작번호이고, end는 끝번호이다.
        // 예를 들어, 현재 페이지가 1이라면, begin은 1이고, end는 7이다.
        // 현재 페이지가 2라면, begin은 8이고, end는 14이다.
        // 현재 페이지가 3이라면, begin은 15이고, end는 21이다.

        //결국에는 페이지에 표현할 게시글의 시작번호와 끝번호를 구한다.
        //paging에서 getter setter를 통해서 begin과 end를 가져온다.
        int begin = page.getBegin();
        int end = page.getEnd();

        // 목록 가져오기
        BbsVO[] ar = bService.getList(searchType, searchValue, bname, begin, end);

        // 뷰페이지에서 표현할 정보들을 mv에 저장!
        mv.addObject("ar", ar);
        mv.addObject("page", page);
        mv.addObject("totalRecord", totalRecord);
        mv.addObject("bname", bname);
        mv.addObject("nowPage", nowPage);

        mv.setViewName(bname + "/list");
        return mv;
    }

    // RestController에서 경로를 넘어갈려면 이렇게 해주면됨.
    @GetMapping("/write")
    public ModelAndView write(@RequestParam String bname, String cPage) {
        ModelAndView mv = new ModelAndView(bname + "/write");
        return mv;
    }

    @PostMapping("/saveImg")
    @ResponseBody
    // 하나하나 파라미터를 받는것 보다는 VO객체를 하나 만들어서 전달하는것이 더 효율적이다.
    public Map<String, String> saveImg(MultipartFile upload, String bname) {
        // 반환형 준비
        Map<String, String> map = new HashMap<>();

        if (upload.getSize() > 0) {
            // 파일을 저장할 위치(upload_img)를 절대경로화시키자.
            String realPath = application.getRealPath(upload_img);

            // 파일명을 얻어내자.
            String oname = upload.getOriginalFilename();

            // 동일한 파일명이 있다면, 파일명을 변경해주자.
            // 없으면 fname에 그대로 들어오고, 있으면 파일명 뒤에 숫자가 추가가 된다.
            String fname = FileRenameUtil.checkSameFilename(oname, realPath);

            try {
                upload.transferTo(new File(realPath + fname)); // 서버에 업로드가 됨.
            } catch (Exception e) {
                e.printStackTrace();
            }

            // 업로드된 파일의 경로를 반환하기 위해
            // 현재 서버의 URL을 알아내야한다.
            // 스프링 환경에서의 프로젝트와 Context는 같은 개념이라 생각해도 좋다.
            String url_path = request.getContextPath();

            // JSON으로 반환하기 위해 map에 저장
            // 여기서 jsp에 있는 ajax안의 res로 url과 fname이 map으로 넘어온다.
            map.put("url", url_path + upload_img);
            map.put("fname", fname);
        }
        return map;
    }

    @PostMapping("write")
    // Post방식의 write가 호출되면 BbsVO 인자를 알아서 빈것으로 만들어서 처리한다.
    // 모든 파라미터값들이 BbsVO에 저장되어 있는 상태이다.
    public ModelAndView write(BbsVO vo) {
        ModelAndView mv = new ModelAndView();

        // 전달되어 오는 파라미터값들은 vo에 저장되어 있는 상태이다.
        // 중요한 것은 첨부파일이 있었는지? 없었는지? 확인해야한다.
        MultipartFile mf = vo.getFile();

        // 파일이 첨부되지 않았다고 해도 mf는 null이 아니며, 용량이 0이다.
        if (mf.getSize() > 0) {
            // 파일이 첨부된 상태일 때만 서버에 업로드한다.

            // 업로드할 서버의 위치를 절대경로화 시킨다.
            String realPath = application.getRealPath(bbs_upload);
            System.out.println("realPath : " + realPath);
            String oname = mf.getOriginalFilename();
            System.out.println("oname : " + oname);

            // 같은 파일명이 있다면 파일명 변경.
            String fname = FileRenameUtil.checkSameFilename(oname, realPath);

            try {
                // 업로드 경로 지정.
                mf.transferTo(new File(realPath, fname));

            } catch (Exception e) {
                e.printStackTrace();
            }

            // DB에 저장된 파일 관련 정보(file_name, ori_name)를 vo에 저장.
            vo.setFile_name(fname);
            vo.setOri_name(oname);
        }
        // ip주소 저장
        vo.setIp(request.getRemoteAddr());

        // DB에 저장
        int cnt = bService.addBbs(vo);

        mv.setViewName("redirect:/list?bname=" + vo.getBname());

        return mv;
    }

    @GetMapping("/view")
    public ModelAndView view(@RequestParam String b_idx, @RequestParam String bname, String cPage) {
        ModelAndView mv = new ModelAndView();

        // 세션에 read_list라는 이름으로 저장된 객체를 얻어낸다.
        Object obj = session.getAttribute("read_list");
        ArrayList<BbsVO> list = null;

        // obj가 null이 아니면, obj를 형변환하여 list에 저장.
        if (obj != null) {

            list = (ArrayList<BbsVO>) obj;

        } else {

            list = new ArrayList<>();

            // 새로 생성된 경우 일때만, 세션에 저장.
            session.setAttribute("read_list", list);
        }

        // 사용자가 선택한 게시물의 기본키를 인자로 받았으니, 그것을 조건으로
        // 해당 게시물을 객체로 얻어낸다.

        BbsVO vo = bService.getBbs(b_idx);

        // 이미 읽었던 게시물인지? 아닌지? 확인하자.

        boolean chk = false;
        for (BbsVO bvo : list) {
            if (bvo.getB_idx().equals(b_idx)) {
                chk = true;
                break;
            }
        }

        // chk가 false를 유지하고 있다면 한번도 읽지 않은 게심물을 선택한 경우
        // 그러므로 hit수를 증가한 후 vo를 list에 저장.
        // ArrayList에 담겨있던 주솟값을 다른 객체에게 던져줬을 경우에
        // 던져준 객체에서 값이 바뀐다 하면, ArrayList에 있는 값이 변경된다.
        if (!chk) {
            // 화면에 즉각적으로 반영하기 위해 먼저 hit값을 받아낸다.
            int hit = Integer.parseInt(vo.getHit());
            vo.setHit(String.valueOf(hit + 1));

            bService.hit(b_idx);
            list.add(vo); // 지역변수인 list가 사실은 session에 저장된 "read_list"라는 이름으로
            // 저장된 리스트구조의 가리키고 있다.
            // session의 리스트에 추가된다.
        }

        mv.addObject("vo", vo);
        mv.addObject("bname", bname);
        mv.addObject("cPage", cPage);
        mv.setViewName(bname + "/view");
        return mv;
    }

    // SpringBoot.core에 있는 Resource를 사용한다.
    @PostMapping("download")
    // view.jsp에 있는 down 함수에 있는 f_name을 인자값으로 넘겨줘야한다.
    public ResponseEntity<Resource> download(String f_name) {
        // 파일들이 저장되어 있는 곳(bbs_upload)를 절대경로화 시키자.
        String realPath = application.getRealPath(bbs_upload + "/" + f_name);

        File f = new File(realPath);

        // 파일 존재 여부확인
        if (f.exists()) {
            // 존재할 경우에만 요청한 곳으로 파일을 보내줘야한다.
            byte[] buf = new byte[4096];
            int size = -1;

            // 파일을 다운로드에 필요한 스트림 준비.
            BufferedInputStream bis = null;
            FileInputStream fis = null;

            // 요청한 곳으로 보내기 위한 스트림 (응답)
            BufferedOutputStream bos = null;

            // 응답을 하는 것이 접속자의 컴퓨터로 다운로드를 시켜야하기 때문에
            // Response를 통해 OutputStream을 얻어내야한다. 이때 response가 주는 스트림이
            // ServletOutputStream만 줄 수 있기 때문에 우린 sos를 선언한 것이다.
            ServletOutputStream sos = null;

            try {
                // 접속자 화면에 다운로드 창을 보여준다.
                response.setContentType("application/x-msdownload");
                response.setHeader("Content-Disposition", "attachment;filename="
                        + new String(f_name.getBytes(), "8859_1"));

                // 다운로드 시킬 파일과 연결되는 스트림 생성.
                fis = new FileInputStream(f);
                bis = new BufferedInputStream(fis);

                // response를 통해 이미 out이라는 스트림이 존재한다.
                // 다운로드를 시키기 위해 스트림을 준비하자.
                sos = response.getOutputStream();
                bos = new BufferedOutputStream(sos);

                while ((size = bis.read(buf)) != -1) {
                    // 읽은 자원을 buf에 적재된 상태다. 이제는
                    // buf라는 배열에 있는 자원들을 쓰게해서 사용자에게 보낸다.
                    bos.write(buf, 0, size);
                    bos.flush();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (fis != null)
                        fis.close();
                    if (bis != null)
                        bis.close();
                    if (sos != null)
                        sos.close();
                    if (bos != null)
                        bos.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            // 화면을 표기하는 것이 아니기 때문에, null로 반환해주면 된다.
        }
        return null;

    }
    // 내가 여기서 주의해야할 점
    // 현재 View.jsp에서 edit함수를 호출할때, post방식으로 넘겨주고 있음.
    // 107번째 부터 113번 라인까지가 현재 edit함수를 호출할 때 , post방식으로 넘겨주는 곳이다.
    // 이거 떄문에 2시간을 날렸으니, 너는 이부분을 주의해야한다.
    @PostMapping("edit1")
    public ModelAndView edit(@RequestParam String b_idx, @RequestParam String bname, String cPage) {
        ModelAndView mv = new ModelAndView();
    
        BbsVO vo = bService.getBbs(b_idx);
    
        mv.addObject("vo", vo);
        mv.addObject("bname", bname);
        System.out.println("bname : " + bname);
        mv.addObject("cPage", cPage);
        mv.setViewName(bname + "/edit");
        return mv;
    }

    @PostMapping("/edit")
    public ModelAndView edit(
            @RequestParam String b_idx, @RequestParam String bname, String cPage,
            @RequestParam String subject, @RequestParam String content,
            @RequestParam String pwd, @RequestParam MultipartFile file,
            @RequestParam String file_name, @RequestParam String ori_name) {
        ModelAndView mv = new ModelAndView();

        // 파일이 저장될 위치를 절대경로화 시킨다.
        String realPath = application.getRealPath(bbs_upload);

        // 파일 처리
        String fname = null;
        String oname = null;

        if (file != null && !file.isEmpty()) {
            oname = file.getOriginalFilename();
            fname = FileRenameUtil.checkSameFilename(oname, realPath);

            try {
                file.transferTo(new File(realPath, fname));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // ip주소 저장
        String ip = request.getRemoteAddr();

        // DB에 저장된 파일 관련 정보(file_name, ori_name)를 vo에 저장.
        BbsVO vo = new BbsVO();
        vo.setB_idx(b_idx);
        vo.setSubject(subject);
        vo.setContent(content);
        vo.setPwd(pwd);
        vo.setFile_name(fname);
        vo.setOri_name(oname);
        vo.setIp(ip);

        // DB에 저장
        int cnt = bService.edit(vo);
        mv.setViewName("redirect:/view?b_idx=" + b_idx + "&bname=" + bname + "&cPage=" + cPage);
        return mv;
    }
}

