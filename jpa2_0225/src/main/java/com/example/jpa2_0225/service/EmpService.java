package com.example.jpa2_0225.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.jpa2_0225.repository.EmpRepository;
import com.example.jpa2_0225.store.Emp;

//서비스를 지정하기 위한 어노테이션
@Service
public class EmpService {

  @Autowired
  private EmpRepository empRepository;

  public Emp[] getAll() {
    Emp[] ar = null;

    // 존재하지 않지만, 상속을 받았기 때문에 findAll을 사용가능함.
    List<Emp> list = empRepository.findAll();

    if (list != null && list.size() > 0) {
      // ar을 Emp객체의 배열로 생성후, list의 size만큼 만든다.
      ar = new Emp[list.size()];

      // 리스트에 반환된 것을 ar로 저장.
      list.toArray(ar);
    }

    return ar;
  }

  public Emp[] searchName(String ename) {
    Emp[] ar = null;

    // interface에 생성된 findByEname을 사용, 여기서는 ename을 반드시 넘겨줘야함.
    List<Emp> list = empRepository.findByEname(ename);

    if (list != null && list.size() > 0) {
      // ar을 Emp객체의 배열로 생성후, list의 size만큼 만든다.
      ar = new Emp[list.size()];

      // 리스트에 반환된 것을 ar로 저장.
      list.toArray(ar);
    }

    return ar;
  }

  public Emp[] searchName2(String ename) {
    Emp[] ar = null;

    // interface에 생성된 findByEnameContaining 사용, 여기서는 ename을 반드시 넘겨줘야함.
    List<Emp> list = empRepository.findByEnameContaining(ename);

    if (list != null && list.size() > 0) {
      // ar을 Emp객체의 배열로 생성후, list의 size만큼 만든다.
      ar = new Emp[list.size()];

      // 리스트에 반환된 것을 ar로 저장.
      list.toArray(ar);
    }

    return ar;
  }

  public Emp[] searchNameLike(String ename) {
    Emp[] ar = null;

    // interface에 생성된 findByEnameLike 사용, 여기서는 ename을 반드시 넘겨줘야함.
    // LIKE문을 사용하기 때문에 "%"를 사용하여, 작성해줘야한다.
    List<Emp> list = empRepository.findByEnameLike("%" + ename + "%");

    if (list != null && list.size() > 0) {
      // ar을 Emp객체의 배열로 생성후, list의 size만큼 만든다.
      ar = new Emp[list.size()];

      // 리스트에 반환된 것을 ar로 저장.
      list.toArray(ar);
    }

    return ar;
  }

  public Emp[] searchJobAndDept(String job, String deptno) {
    Emp[] ar = null;

    // interface에 생성된 findByEnameLike 사용, 여기서는 ename을 반드시 넘겨줘야함.
    // LIKE문을 사용하기 때문에 "%"를 사용하여, 작성해줘야한다.
    List<Emp> list = empRepository.findByJobContainsAndDeptno(job, deptno);

    if (list != null && list.size() > 0) {
      // ar을 Emp객체의 배열로 생성후, list의 size만큼 만든다.
      ar = new Emp[list.size()];

      // 리스트에 반환된 것을 ar로 저장.
      list.toArray(ar);
    }

    return ar;
  }

  public Emp[] salLessThan(long sal) {
    Emp[] ar = null;

    // interface에 생성된 findBySalLessThan 사용, 여기서는 ename을 반드시 넘겨줘야함.
    // LIKE문을 사용하기 때문에 "%"를 사용하여, 작성해줘야한다.
    List<Emp> list = empRepository.findBySalLessThan(sal);

    if (list != null && list.size() > 0) {
      // ar을 Emp객체의 배열로 생성후, list의 size만큼 만든다.
      ar = new Emp[list.size()];

      // 리스트에 반환된 것을 ar로 저장.
      list.toArray(ar);
    }

    return ar;
  }
}
