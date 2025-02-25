package com.example.jpa2_0225.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.jpa2_0225.service.EmpService;
import com.example.jpa2_0225.store.Emp;

@RestController
public class EmpController {

  @Autowired
  private EmpService empService;

  @GetMapping("all")
  public String getAll() {
    StringBuffer sb = new StringBuffer();

    Emp[] ar = empService.getAll();

    for (Emp emp : ar) {
      sb.append(emp.getEmpno());
      sb.append("/");
      sb.append(emp.getEname());
      sb.append("<br/>");
    }
    // StringBuffer로 불려진 것을 문자화 시키고 리턴.
    return sb.toString();
  }

  @PostMapping("searchName")
  // 필수적으로 ename을 받기위한 어노테이션 선언.
  public String searchName(@RequestParam String ename) {
    StringBuffer sb = new StringBuffer();

    // Emp배열 형태에 ar에 empService의 searchName 함수를 ename 인자값을 포함하여 호출.
    Emp[] ar = empService.searchName(ename);

    if (ar != null)
      for (Emp emp : ar) {
        sb.append(emp.getEmpno());
        sb.append("/");
        sb.append(emp.getEname());
        sb.append("<br/>");
      }
    // StringBuffer로 불려진 것을 문자화 시키고 리턴.
    return sb.toString();
  }

  @PostMapping("searchName2")
  // 필수적으로 ename을 받기위한 어노테이션 선언.
  public String searchName2(@RequestParam String ename) {
    StringBuffer sb = new StringBuffer();

    // Emp배열 형태에 ar에 empService의 searchName2 함수를 ename 인자값을 포함하여 호출.
    Emp[] ar = empService.searchName2(ename);

    if (ar != null)
      for (Emp emp : ar) {
        sb.append(emp.getEmpno());
        sb.append("/");
        sb.append(emp.getEname());
        sb.append("<br/>");
      }
    // StringBuffer로 불려진 것을 문자화 시키고 리턴.
    return sb.toString();
  }

  @PostMapping("searchNameLike")
  // 필수적으로 ename을 받기위한 어노테이션 선언.
  public String searchNameLike(@RequestParam String ename) {
    StringBuffer sb = new StringBuffer();

    // Emp배열 형태에 ar에 empService의 searchNameLike 함수를 ename 인자값을 포함하여 호출.
    Emp[] ar = empService.searchNameLike(ename);

    if (ar != null)
      for (Emp emp : ar) {
        sb.append(emp.getEmpno());
        sb.append("/");
        sb.append(emp.getEname());
        sb.append("<br/>");
      }
    // StringBuffer로 불려진 것을 문자화 시키고 리턴.
    return sb.toString();
  }

  @GetMapping("job_dept")
  // 필수적으로 ename을 받기위한 어노테이션 선언.
  public String searchJobAndDept(@RequestParam String job, @RequestParam String deptno) {
    StringBuffer sb = new StringBuffer();

    // Emp배열 형태에 ar에 empService의 searchJobAndDept 함수를 job, deptno 인자값을 포함하여 호출.
    Emp[] ar = empService.searchJobAndDept(job, deptno);

    if (ar != null)
      for (Emp emp : ar) {
        sb.append(emp.getEmpno());
        sb.append("/");
        sb.append(emp.getEname());
        sb.append("/");
        sb.append(emp.getJob());
        sb.append("/");
        sb.append(emp.getDeptno());
        sb.append("<br/>");

      }
    // StringBuffer로 불려진 것을 문자화 시키고 리턴.
    return sb.toString();
  }

  @GetMapping("salLessThan")
  // 필수적으로 ename을 받기위한 어노테이션 선언.
  public String salLessThan(@RequestParam long sal) {
    StringBuffer sb = new StringBuffer();

    // Emp배열 형태에 ar에 empService의 salLessThan 함수를 sal 인자값을 포함하여 호출.
    Emp[] ar = empService.salLessThan(sal);

    if (ar != null)
      for (Emp emp : ar) {
        sb.append(emp.getEmpno());
        sb.append("/");
        sb.append(emp.getEname());
        sb.append("/");
        sb.append(emp.getJob());
        sb.append("/");
        sb.append(emp.getSal());
        sb.append("/");
        sb.append(emp.getDeptno());
        sb.append("<br/>");

      }
    // StringBuffer로 불려진 것을 문자화 시키고 리턴.
    return sb.toString();
  }
}
