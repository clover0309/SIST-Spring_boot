package com.sist.bbs_0220.mapper;

import java.util.List;

import com.sist.bbs_0220.vo.CommVO;

public interface CommMapper {

    List<CommVO> commList(String b_idx);
}
