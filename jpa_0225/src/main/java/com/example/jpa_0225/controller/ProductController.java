package com.example.jpa_0225.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.jpa_0225.repository.CategoryRepository;
import com.example.jpa_0225.repository.ProductRepository;
import com.example.jpa_0225.store.Category1JPO;
import com.example.jpa_0225.store.ProductJPO;

@RestController
public class ProductController {

  @Autowired
  private ProductRepository productRepository;

  @Autowired
  private CategoryRepository categoryRepository;

  @GetMapping("test")
  public String test() {
    // JPO안에서 @Builder가 정의되었으므로, JPO는 생성하는것이 아니라
    // builder를 사용해야한다.
    ProductJPO p1 = ProductJPO.builder()
        .pNum(2L)
        .pName("빈센트 해바라기")
        .pCompany("Art Box").build();

    System.out.println("p1이 생성되었을까? : " + p1);
    // JpaRepository를 상속받게 되면서, productRepository에 함수가 존재한다.
    productRepository.save(p1);
    return "저장완료!";
  }

  @GetMapping("list")
  public String getList() {

    List<ProductJPO> list = productRepository.findAll(Sort.by(Sort.Direction.DESC, "pNum"));
    StringBuffer sb = new StringBuffer();

    for (ProductJPO p : list) {
      sb.append(p.getPNum());
      sb.append("/");
      sb.append(p.getPName());
      sb.append("/");
      sb.append(p.getCvo1().getCName());
      sb.append("/");
      sb.append(p.getCvo1().getCDesc());
      sb.append("<br/>");
    }
    return sb.toString();
  }

  // 미술품(cName)/미술,예술,시각예술(c_desc)
  // <br/>
  // product
  @GetMapping("list2")
  public String getList2() {
    List<Category1JPO> list = categoryRepository.findAll();
    StringBuffer sb = new StringBuffer();

    for (Category1JPO c : list) {
      sb.append(c.getCIdx());
      sb.append("/");
      sb.append(c.getCName());
      sb.append("/");
      sb.append(c.getCDesc());
      sb.append("<br/>");
      for (ProductJPO p : c.getList()) {
        sb.append("&nbsp;&nbsp;&nbsp;");
        sb.append(p.getPNum());
        sb.append("/");
        sb.append(p.getPName());
        sb.append("<br/>");
      }
    }
    return sb.toString();
  }
}
