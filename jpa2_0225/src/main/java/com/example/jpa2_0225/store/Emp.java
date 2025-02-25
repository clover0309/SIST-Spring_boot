package com.example.jpa2_0225.store;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

//테이블 이름을 지정하는 어노테이션.
@Entity(name = "Emp")
@Setter
@Getter

// 기본생성자가 하나 생성된다.
public class Emp {
  // 기본키를 지정하는 어노테이션.
  @Id
  // 명확하게 컬럼을 명시하는 어노테이션. (컬럼을 전부 들리지않고, 명시된 이름으로 바로 이동한다.)
  @Column(name = "empno")
  private String empno;
  // 명확하게 컬럼을 명시하는 어노테이션. (컬럼을 전부 들리지않고, 명시된 이름으로 바로 이동한다.)
  @Column(name = "ename")
  private String ename;
  // 명확하게 컬럼을 명시하는 어노테이션. (컬럼을 전부 들리지않고, 명시된 이름으로 바로 이동한다.)
  @Column(name = "job")
  private String job;
  // 명확하게 컬럼을 명시하는 어노테이션. (컬럼을 전부 들리지않고, 명시된 이름으로 바로 이동한다.)
  @Column(name = "deptno")
  private String deptno;
  // 명확하게 컬럼을 명시하는 어노테이션. (컬럼을 전부 들리지않고, 명시된 이름으로 바로 이동한다.)
  @Column(name = "sal")
  private long sal;

}
