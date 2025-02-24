package com.emp_0220.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

//Configuration 어노테이션의 기능은
//Bean을 정의하는 클래스이자, 어플리케이션 컨텍스트에 등록될 Bean 객체들을 생성할 때 사용함.
//이 과정에서 config관련 xml이 필요없어지고, 직접 Java에서 Bean으로 정의가능.
@Configuration

//맵퍼 관련 인터페이스가 들어있는 패키지의 모든 인터페이스를 스캔하여
//Spring 컨테이너에 Bean으로 등록한다.
//이제는 Mapper를 수동으로 등록할 필요 없이, Mybatis Mapper를 자동으로 관리함.
@MapperScan(basePackages = "com.emp_0220.mapper")

public class Dbconfig {

  // Dbconfig 클래스는 스프링환경이 알아서 호출하고,
  // 아래에 Bean어노테이션이 적용되어 있는 것들은, 강제로 함수들을 한번씩 호출하게 된다(스프링이 시작될 때)
  // 스프링환경(context)에 반환되어서 객체들을 등록한다.

  //root-context.xml에 등록하던 과정을, @Bean으로 선언해도 호출되게한다.
  // 여기서는 반드시 객체로 반환되야함, 반환되는 객체는 @Bean이다.

  @Bean
  public SqlSessionFactory getFactory(DataSource dataSource) throws Exception {
    SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();

    //현재 위에서 생성된 factoryBean은 비어있는 상태, application.properties에서 datasource안에 지정된 녀석들을
    // 인자로 받아서 dataSource객체의 dataSource를 factoryBean에게 설정한다.
    factoryBean.setDataSource(dataSource);

    //위 객체에 DB에 대한 정보가 저장되었으니, 맵퍼들의 경로를 지정.

    // main의 Resources 폴더경로를 지정하기 위한 resolver 생성(viewResolver 역할과 유사함)
    PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();

    // Resources 경로에 있는 모든 경로의 모든 xml파일을 맵핑.
    factoryBean.setMapperLocations(resolver.getResources("classpath:mybatis/mapper/*.xml"));

    //위에서 지정한 factoryBean을 객체를 반환해주기 위한 리턴.
    return factoryBean.getObject();
  }

  @Bean
  //여기로 오게되면, 위에서 지정한 factoryBean이 들어있는 sqlSessionFactory를 생성해 놓은것을 이용해
  // SqlSession을 개방한다.
  public SqlSessionTemplate getTemplate(SqlSessionFactory factory) {
    return new SqlSessionTemplate(factory);
  }
}