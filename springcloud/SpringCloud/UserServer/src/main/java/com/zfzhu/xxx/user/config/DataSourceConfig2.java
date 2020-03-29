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
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

/**
 * @author Think
 *
 */
@Configuration
@MapperScan(basePackages="com.zfzhu.xxx.user.mapper.db2", sqlSessionFactoryRef="sqlSessionFactory2")
public class DataSourceConfig2 {

	@Bean(name="dataSource2")
	@ConfigurationProperties(prefix="spring.datasource.db2")
	public DataSource dataSource2() {
		return DataSourceBuilder.create().build();
	}
	
	@Bean(name="sqlSessionFactory2")
	public SqlSessionFactory sqlSessionFactory2(@Qualifier("dataSource2") DataSource dataSource) throws Exception {
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(dataSource);
		sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/db2/*.xml"));
		
		return sqlSessionFactoryBean.getObject();
	}
	
	@Bean(name="dataSourceTransactionManager2")
	public DataSourceTransactionManager dataSourceTransactionManager2(@Qualifier("dataSource2") DataSource dataSource) {
		return new DataSourceTransactionManager(dataSource);
	}
	
	@Bean(name="sqlSessionTemplate2")
	public SqlSessionTemplate sqlSessionTemplate2(@Qualifier("sqlSessionFactory2") SqlSessionFactory sqlSessionFactory) throws Exception {
		return new SqlSessionTemplate(sqlSessionFactory);
	}
}
