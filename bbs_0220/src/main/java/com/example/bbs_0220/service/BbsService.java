package com.example.bbs_0220.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.bbs_0220.mapper.BbsMapper;
import com.example.bbs_0220.vo.BbsVO;

@Service
public class BbsService {
  @Autowired
  private BbsMapper b_mapper;

  // 검색 또는 목록기능에서 사용되는 전체게시물 수
  public int getTotalCount(String searchType, String searchValue, String bname) {
    System.out.println("Service Entry..");
    return b_mapper.totalCount(searchType, searchValue, bname);
  }

  // 검색 또는 목록 기능
  public BbsVO[] getList(String searchType, String searchValue, String bname,
      int begin, int end) {

    BbsVO[] ar = null;

    List<BbsVO> list = b_mapper.list(searchType, searchValue, bname, begin, end);
    if (list != null && list.size() > 0) {
      ar = new BbsVO[list.size()];
      list.toArray(ar);
    }

    return ar;

  }

  // 원글 저장기능
  public int addBbs(BbsVO bvo) {
    return b_mapper.add(bvo);
  }
}
