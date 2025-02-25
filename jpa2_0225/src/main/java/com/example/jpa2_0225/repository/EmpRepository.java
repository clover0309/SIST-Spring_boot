package com.example.jpa2_0225.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.jpa2_0225.store.Emp;
import java.util.List;


@Repository
public interface EmpRepository extends JpaRepository<Emp, String> {
  List<Emp> findByEname(String ename);
  //Like와 같은 형태가 된다.
  List<Emp> findByEnameContaining(String ename);

  List<Emp> findByEnameLike(String ename);

  List<Emp> findByJobContainsAndDeptno(String job, String Deptno);

  // LessThan = 미만, 급여가 sal 미만만
  List<Emp> findBySalLessThan(long sal);
  // LessThanEqual = 이하, 급여가 sal 이하
  List<Emp> findBySalLessThanEqual(long sal);
  // GreaterThan = 초과, 급여가 sal 초과
  List<Emp> findBySalGreaterThan(long sal);
  // GreaterThan = 이상, 급여가 sal 이상
  List<Emp> findBySalGreaterThanEqual(long sal);

}
