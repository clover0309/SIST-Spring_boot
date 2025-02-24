package com.example.secure_0224.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.secure_0224.mapper.MemberMapper;
import com.example.secure_0224.vo.MemVO;

@Service
public class MemberSerivce {
  @Autowired
  private MemberMapper mapper;

  // 비밀번호를 암호화 해주는 PasswordEncoder를 사용.
  @Autowired
  private PasswordEncoder passwordEncoder;

  // 쿼리문에서 결과값을 0아니면 1을 도출함으로 메서드 자료형을 int로 설정.
  // 파라미터는 MemVO로 설정.
  public int regMember(MemVO vo) {
    // reg.jsp에서 전달되는 m_id, m_pw, m_name이 컨트롤러에서 vo로 받은 것을
    // 그대로 인자로 받아, 현재 메서드로 전달한다.
    // 이중 m_pw(비밀번호)를 암호화하자.

    // vo.setM_pw(passwordEncoder.encode(vo.getM_pw())); <<한줄처리.
    String pw = passwordEncoder.encode(vo.getM_pw());

    // 이러면 vo에 담긴 m_pw가 암호화되어 저장된다.
    vo.setM_pw(pw);

    return mapper.reg(vo);
  }

  // 로그인 메서드
  public MemVO login(MemVO mv) {
    // DB로부터 mv에 있는 m_id를 보내어
    // 해당 MemVO를 받아서 반환 받는다.
    // 이때 비밀번호가 일치하는지는
    // passwordEncode에게 물어봐야 한다.
    // mvo안에는 암호화된 pw가 들어가 있다.
    MemVO mvo = mapper.login(mv.getM_id());

    // 사용자가 입력한 아이디가 잘 못 되었다면
    // m_id는 null이 저장된다
    if (mvo != null) {
      // 여기서는 아이디는 잘 입력되었으니 이제는
      // 비밀번호가 일치한지?를 PasswordEncode에게 물어보면 된다.
      // 순서는 원문으로 들어온 패스워드의 mv, 암호화된 패스워드의 mvo가 들어온다.
      if (passwordEncoder.matches(mv.getM_pw(), mvo.getM_pw()))
        return mvo;
    }
    // mvo가 null이거나 비밀번호가 일치하지 않을 경우에 수행하는 곳.
    return null;
  }
}
