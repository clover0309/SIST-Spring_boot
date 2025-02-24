package com.sist.emp_0220.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

@Configuration
@MapperScan(basePackages = "com.sist.emp_0220.mapper")
public class DbConfig {

    @Bean
    public SqlSessionFactory getFactory(DataSource dataSource) throws Exception{
        //Factory를 만들어주는 객체를 생성하여 해당객체에 DataSource지정!
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource);

        //위 객체에 DB에 대한 정보가 적용되었으니 맵퍼들 지정
        PathMatchingResourcePatternResolver resolver = 
            new PathMatchingResourcePatternResolver();
        factoryBean.setMapperLocations(resolver.getResources(
            "classpath:mybatis/mapper/**/*.xml"));

        return factoryBean.getObject();
    }

    @Bean
    public SqlSessionTemplate getTemplate(SqlSessionFactory factory){
        return new SqlSessionTemplate(factory);
    }
}
