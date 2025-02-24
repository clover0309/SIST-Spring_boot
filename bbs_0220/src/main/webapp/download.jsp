<%@ page import="java.io.File" %>
<%@ page import="java.io.BufferedInputStream" %>
<%@ page import="java.io.FileInputStream" %>
<%@ page import="java.io.BufferedOutputStream" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    //요청시 한글처리
    request.setCharacterEncoding("utf-8");

    //현재 페이지로 전달되는 파라미터들 받기.
    String fname = request.getParameter("f_name"); //파일명

    //위의 값들을 연결하여 절대경로를 만든다.
    String realPath = application.getRealPath("/bbs_upload/"+fname);

    File f = new File(realPath);

    if(f.exists()){ // 실제로 파일이 경로안에 존재하는 경우.
        // 다운로드에 필요한 스트림들 준비
        // 응답객체를 통해 아웃풋스트림을 사용해야함.
        BufferedInputStream bis = null;
        FileInputStream fis = null;

        BufferedOutputStream bos = null;
        //response로 얻을 수 있는 OutputStream은 ServletOutputStream 밖에 없다.
        //요청자에게 응답으로 스트림을 줘야 다운로드가 된다.
        ServletOutputStream sos = null;

        byte[] buf = new byte[2048];
        int size = -1;

        try{
            //접속자 화면에 다운로드 창을 보여준다.
            response.setContentType("application/x-msdownload");
            //키값과 값을 집어넣는다.
            response.setHeader("Content-Disposition",
                    "attachment;filename="+new String(fname.getBytes(), "8859_1"));
            //------------------------------- 탐색기창을 띄워주는 과정

            //다운로드할 File과 연결되는 스트림 생성
            fis = new FileInputStream(f);
            bis = new BufferedInputStream(fis);

            //response를 통해 이미 out이라는 스트림이 존재하므로
            // 오류가 발생한다. 이것을 잠시 없앤다.(충돌을 피하기 위해서)
            out.clear();
            out = pageContext.pushBody();

            //응답을 위한 스트림 생성.
            sos = response.getOutputStream();
            bos = new BufferedOutputStream(sos);

            //스트림들이 모두 준비완료 되었으니 읽기한 후 바로 쓰기를 하여
            //요청한 곳으로 다운이 되도록 하자!
            while((size = bis.read(buf)) != -1){
                //읽은 자원은 buf가 가지고 있으며, 읽은 바이트 수는 size가 기억한다.
                bos.write(buf, 0, size);
                bos.flush();
            }//while문의 끝
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
            }catch(Exception e){}
        }
    }
%>
<html>
<head>
    <title>Title</title>
</head>
<body>

</body>
</html>
