package com.emp_0220.mapper;

import com.emp_0220.vo.EmpVO;

import java.util.List;
import java.util.Map;

public interface EmpMapper {
  // 연결하는 mapper문서의 아이디의 동일한 이름으로 추상메서드 정의
  // list에는 parameterType Map이므로 인자를 Map으로 정해야함.
  // mapper파일에서 ParameterType을 보고 Parameter를 넘겨줘야하는지 판단하면된다.
  List<EmpVO> list(String searchType, String searchValue,
                   String begin, String end);
}
