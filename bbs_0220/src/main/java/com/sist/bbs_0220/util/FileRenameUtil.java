package com.sist.bbs_0220.util;

import java.io.File;

public class FileRenameUtil {
  public static String checkSameFilename(String fileName, String path) {
    // 인자인 filename이 확장자를 뺀 파일명을 가려내자.(그냥 파일명만 가져오기 위함.)
    // 우선 "."의 위치를 알아내야한다.
    int period = fileName.lastIndexOf("."); // test123.txt -> 7

    // 파일명과 확장자를 분리한다.
    String fname = fileName.substring(0, period); // test123
    String suffix = fileName.substring(period); // .txt

    // 전체경로(절대경로+파일명)
    // 현재 서버 시스템에 따라서 파일 세퍼레이터를 넣어줘야함.
    // String saveFilePath = path + "/" + fileName; 해당코드도 작동은 하긴함.
    String saveFilePath = path + System.getProperty("file.separator") + fileName;

    // 위의 전체경로를 가지고 파일객체 생성.
    File file = new File(saveFilePath);

    // 파일이 이미 존재하면 파일명 뒤에 숫자를 붙이기 위해 변수를 하나 선언.
    int idx = 1;

    while (file.exists()) {
      // 파일이 이미 존재하고 있는 파일일 때
      StringBuffer sb = new StringBuffer();
      sb.append(fname); // test123
      sb.append(idx++); // test1231
      sb.append(suffix); // test1231.txt

      // 파일명으로 설정
      // toString을 사용해서 문자열로 만들어 줘야함.
      fileName = sb.toString();

      saveFilePath = path + System.getProperty("file.separator") + fileName;

      // 수정된 파일명을 가지고 다시 File객체를 생성.
      file = new File(saveFilePath);
    }
    return fileName;
  }
}
