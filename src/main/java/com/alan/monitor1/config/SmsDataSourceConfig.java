//package com.alan.monitor1.config;
//
//import org.apache.ibatis.session.SqlSessionFactory;
//import org.mybatis.spring.SqlSessionFactoryBean;
//import org.mybatis.spring.SqlSessionTemplate;
//import org.mybatis.spring.annotation.MapperScan;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.boot.jdbc.DataSourceBuilder;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.transaction.annotation.EnableTransactionManagement;
//
//import javax.sql.DataSource;
//
//@Configuration
//@MapperScan(value = "com.alan.monitor1.mapper1", sqlSessionFactoryRef="db2SqlSessionFactory")
//@EnableTransactionManagement
//public class SmsDataSourceConfig {
//
//    @Bean(name = "db2DataSource")
//
//    @ConfigurationProperties(prefix = "spring.db2.datasource")
//    public DataSource db2DataSource(){
//        return DataSourceBuilder.create().build();
//    }
//
//    @Bean(name = "db2SqlSessionFactory")
//    public SqlSessionFactory sqlSessionFactory(@Qualifier("db2DataSource") DataSource dataSource, ApplicationContext applicationContext) throws Exception{
//        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
//        sqlSessionFactoryBean.setDataSource(dataSource);
////        Resource[] arrResource = new PathMatchingResourcePatternResolver()
////                .getResources("classpath:mapper/sms/**/*Mapper.xml");
//        sqlSessionFactoryBean.setMapperLocations(applicationContext.getResources("classpath:mapper/sms/**/*Mapper.xml"));
//        sqlSessionFactoryBean.getObject().getConfiguration().setMapUnderscoreToCamelCase(true);
//        return sqlSessionFactoryBean.getObject();
//    }
//
//    @Bean(name = "db2SqlSessionTemplate")
//    public SqlSessionTemplate db2SqlSessionTemplate(SqlSessionFactory db2SqlSessionFactory) throws Exception {
//        return new SqlSessionTemplate(db2SqlSessionFactory);
//    }
//
//
//}
