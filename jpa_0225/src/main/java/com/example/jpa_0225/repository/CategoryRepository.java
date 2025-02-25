package com.example.jpa_0225.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.jpa_0225.store.Category1JPO;

@Repository
// 여기서는 JpaRepository를 상속할때, JPO를 지정하고, 객체자료형을 필히 지정해줘야한다.
// Category1JPO는 Category1_t 테이블을 의미함.
public interface CategoryRepository extends JpaRepository<Category1JPO, Integer> {

}
