package com.example.jpa_0225.store;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "category1_t")
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Category1JPO {

  @Id
  @GeneratedValue
  // 기본키 값을 int로 잡아놨다면 Integer, long을 사용했다면 Long
  private int cIdx;
  private String cName;
  private String cDesc;
  private int status;

  @OneToMany // OneToMany는 현재 객체가 1이고 연결테이블에서는
             // 여러개가 있다는 뜻이므로, 현재 테이블에서 1개만 존재하는 것을 구별하는 것은 기본키이다.
             // 즉 cIdx(c_Idx)가 One이고
             // 연결되는 테이블에는 여러 개가 참조되는 컬럼이
             // category1이 되어야 한다.
  @JoinColumn(name = "category1")
  List<ProductJPO> list;
}

/*
 * Spring DataJPA는 메서드 이름을 분석해 JPQL쿼리를 실행한다.
 * 메서드명을 이용한 쿼리 실행은 조회(SELECT)에 해당되며
 * 쿼리문을 내가 직접 지정하고 싶다면 @Query 어노테이션을 통해 다양한 쿼리를 정의 할 수 있다.
 * 
 * AND조건 : findByEnameAndJob() = WHERE ename=? AND job=?
 * OR조건 : findByEnameOrJob() = WHERE ename=? OR job= ?
 * Between : findByHiredateBetween(?) = WHERE Hiredate Between ? AND ?
 * LIKE : findByHiredateLIKE() = WHERE Hiredate LIKE ?
 */