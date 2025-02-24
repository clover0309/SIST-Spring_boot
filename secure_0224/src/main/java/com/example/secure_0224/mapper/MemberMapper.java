package com.example.secure_0224.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.example.secure_0224.vo.MemVO;

@Mapper
public interface MemberMapper {
  int reg(MemVO vo);

  // mem.xml의 login 쿼리부분에 자료형과 변수명만 맞춰주면됨.
  MemVO login(String m_id);
}
