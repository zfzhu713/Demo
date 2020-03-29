/**
 * 
 */
package com.zfzhu.xxx.user.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

/**
 * @author Think
 *
 */
@Configuration
@MapperScan(basePackages="com.zfzhu.xxx.user.mapper.db1", sqlSessionFactoryRef="sqlSessionFactory1")
public class DataSourceConfig1 {

	@Bean(name="dataSource1")
	@Primary
	@ConfigurationProperties(prefix="spring.datasource.db1")
	public DataSource dataSource1() {
		return DataSourceBuilder.create().build();
	}
	
	@Primary
	@Bean(name="sqlSessionFactory1")
	public SqlSessionFactory sqlSessionFactory1(@Qualifier("dataSource1") DataSource dataSource) throws Exception {
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(dataSource);
		sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/db1/*.xml"));
		sqlSessionFactoryBean.setTypeAliasesPackage("com.zfzhu.xxx.user.entity");
		
		return sqlSessionFactoryBean.getObject();
	}
	
	@Primary
	@Bean(name="dataSourceTransactionManager1")
	public DataSourceTransactionManager dataSourceTransactionManager1(@Qualifier("dataSource1") DataSource dataSource) {
		return new DataSourceTransactionManager(dataSource);
	}
	
	@Primary
	@Bean(name="sqlSessionTemplate1")
	public SqlSessionTemplate sqlSessionTemplate1(@Qualifier("sqlSessionFactory1") SqlSessionFactory sqlSessionFactory) throws Exception {
		return new SqlSessionTemplate(sqlSessionFactory);
	}
}
