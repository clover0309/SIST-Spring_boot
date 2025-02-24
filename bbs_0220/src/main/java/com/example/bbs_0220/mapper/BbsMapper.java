package com.example.bbs_0220.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.bbs_0220.vo.BbsVO;

@Mapper
public interface BbsMapper {
  int totalCount(String searchType, String searchValue, String bname);

  // 자료형이 달라도 SpringBoot가 Object로 Map으로 처리해서 보내줌.
  List<BbsVO> list(String searchType, String searchValue, String bname,
      int begin, int end);

  int add(BbsVO vo);
}
