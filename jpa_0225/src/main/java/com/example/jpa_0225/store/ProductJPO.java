package com.example.jpa_0225.store;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//테이블 이름을 의미함.
@Entity(name = "product_t")
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class ProductJPO {
  @Id
  // SQL에서 AI와 같음
  @GeneratedValue
  private long pNum;
  private String pName;
  private String pCompany;
  private LocalDate regDate;

  @Column(name = "category1")
  private long category1;
  private int category2;
  private int category3;

  //category
  @ManyToOne
  // 해당 컬럼을 Hibernate가 이러한 필드를 삽입하거나 업데이트 하지않도록 지정하는 방법.
  @JoinColumn(name = "category1", insertable = false, updatable = false)
  private Category1JPO cvo1;
}
