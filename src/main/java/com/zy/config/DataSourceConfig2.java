package com.zy.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
/**
 * @Auther: zy
 * @Date: 2019/7/27 14:57
 * @Description:
 */
@Configuration
@MapperScan(basePackages = "com.zy.mapper.test2", sqlSessionFactoryRef = "test2SqlSessionFactory")
public class DataSourceConfig2 {
    @Bean(name = "test2DataSource")
    @ConfigurationProperties(prefix = "spring.datasource.test2")
    public DataSource getDateSource2() {
        return DataSourceBuilder.create().build();
    }
    @Bean(name = "test2SqlSessionFactory")
    public SqlSessionFactory test2SqlSessionFactory(@Qualifier("test2DataSource") DataSource datasource)
            throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(datasource);
        /*加载mybatis全局配置文件*/
        bean.setConfigLocation(new PathMatchingResourcePatternResolver().getResource("classpath:config/mybatis.cfg.xml"));
        /*加载所有的mapper.xml映射文件*/
//        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:com/zy/mapper/test2/*./xml"));
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:com/zy/mapper/*.xml"));
        return bean.getObject();
    }
    @Bean(name = "casTransactionManager")
    public DataSourceTransactionManager testTransactionManager(@Qualifier("test2DataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
    @Bean("test2SqlSessionTemplate")
    public SqlSessionTemplate test2sqlsessiontemplate(
            @Qualifier("test2SqlSessionFactory") SqlSessionFactory sessionfactory) {
        return new SqlSessionTemplate(sessionfactory);
    }
}