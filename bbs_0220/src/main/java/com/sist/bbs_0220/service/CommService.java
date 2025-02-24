package com.sist.bbs_0220.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sist.bbs_0220.mapper.CommMapper;
import com.sist.bbs_0220.vo.CommVO;

@Service
public class CommService {

    @Autowired
    private CommMapper cMapper;

    public List<CommVO> commList(String b_idx){
        return cMapper.commList(b_idx);
    }
}
