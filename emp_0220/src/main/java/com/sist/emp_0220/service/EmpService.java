package com.sist.emp_0220.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sist.emp_0220.mapper.EmpMapper;
import com.sist.emp_0220.vo.EmpVO;

@Service
public class EmpService {

    @Autowired
    private EmpMapper e_Mapper;

    public EmpVO[] getList(String searchType, String searchValue, 
                            String begin, String end){
        EmpVO[] ar = null;

        List<EmpVO> list = e_Mapper.list(searchType,searchValue,begin,end);
        if(list != null && list.size() > 0){
            ar = new EmpVO[list.size()];
            list.toArray(ar);
        }
        return ar;
    }
}
