package com.example.ex3_db.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ex3_db.mapper.EmpMapper;
import com.example.ex3_db.vo.EmpVO;

@Service
public class EmpService {
  // 필요한 맵퍼 지정
  @Autowired // 여기서 실제로 부를 수 있는 함수는 1개이다 (interface에 하나밖에 없음.)
  private EmpMapper mapper;

  // 컨트롤러(요청)에서 호출하는 메서드 정의.
  public EmpVO[] getList() {
    EmpVO[] ar = null;

    List<EmpVO> list = mapper.list();
    System.out.println(list.size());

    if (list != null && !list.isEmpty()) {
      // 리스트가 비어있지 않을 때만 배열생성.
      ar = new EmpVO[list.size()];

      // 비어있는 배열에 리스트의 요소들로 복사하기
      list.toArray(ar);
    }
    System.out.println(ar.length);
    return ar;
  }

  public EmpVO[] search(String type, String value) {
    //인자로 넘어온 type과 value를 Map구조화 시킨다.
    HashMap<String, String> map = new HashMap<>();
    map.put("searchType", type);
    map.put("searchValue", value);
    return mapper.search(map);
  }
}
