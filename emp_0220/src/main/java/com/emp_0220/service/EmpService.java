package com.emp_0220.service;

import com.emp_0220.mapper.EmpMapper;
import com.emp_0220.vo.EmpVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmpService {
  @Autowired
  private EmpMapper e_mapper;

  public EmpVO[] getList(String searchType, String searchValue,
                         String begin, String end) {
      EmpVO[] ar = null;

    List<EmpVO> list = e_mapper.list(searchType, searchValue, begin, end);
    if(list != null && list.size() > 0) {
      ar = new EmpVO[list.size()];
      list.toArray(ar);
    }
    return ar;
  }
}
