package com.example.ex3_db.db;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

@Configuration
@MapperScan(basePackages = "com.example.ex3_db.mapper")
public class DbConfig {
  // 현재 클래스는 스프링환경이 알아서 호출하며, 아래의
  // @Bean이라는 어노테이션 때문에, 강제로 함수들을 한번 씩 호출하게 된다.
  // 스프링환경(Context)에 반환되어 객체들을 등록한다.

  // root-context.xml에서 사용하던 ds, factory, ss를 @Bean으로 선언해서 호출 되게 만든다.
  // 여기서는 반드시 객체로 반환을 해야하는데, 반환되는 객체가 @Bean이다.
  @Bean
  public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
    SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
    // 생성된 factoryBean은 비어있는 상태며, application.properties에 지정되어 있는 것을 인자로 받아
    // dataSource를
    // factoryBean에게 설정한다.
    factoryBean.setDataSource(dataSource);

    // main의 Resources 폴더경로를 잡기위한 resolver 생성. (viewResolver 역활과 비슷함.)
    PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();

    // Resources 경로에 있는, 모든 경로의 모든 xml파일을 맵핑.
    factoryBean.setMapperLocations(resolver.getResources(
        "classpath:mybatis/mapper/**/*.xml"));

    // 지정된것을 객체를 반환해주기 위한 리턴.
    return factoryBean.getObject();
  }

  @Bean
  public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory factory) {
    return new SqlSessionTemplate(factory);
  }
}
