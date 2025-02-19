package com.example.ex3_db.mapper;

import java.util.HashMap;
import java.util.List;

import com.example.ex3_db.vo.EmpVO;

public interface EmpMapper {
  // SQL문을 가진 mapper파일 (emp.xml)과 연동되어 있는 상태이다.
  // 그래서 여기에 정의하는 함수들은 emp.xml에 존재하는 id명과 일치해야한다.
  List<EmpVO> list();

  EmpVO[] search(HashMap<String, String> params);

  EmpVO getEmp(String empno);
}
