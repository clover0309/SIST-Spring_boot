package com.sist.emp_0220.mapper;

import java.util.List;
import java.util.Map;

import com.sist.emp_0220.vo.EmpVO;

public interface EmpMapper {
    // 연결하는 mapper문서의 아이디와 동일한 이름으로 
    // 추상메서드 정의
    List<EmpVO> list(String searchType, String searchValue, 
            String begin, String end); // list에는 parameterType이 Map이므로 
                        // 인자를 Map으로 정한다.
}
