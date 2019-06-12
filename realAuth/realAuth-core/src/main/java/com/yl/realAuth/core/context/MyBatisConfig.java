package com.yl.realAuth.core.context;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * MyBatis配置
 * 
 * @author AnLin
 * @since 2017年4月10日
 */
@Configuration
@Import(MariaDBConfig.class)
@MapperScan("com.yl.realAuth.core.dao.mapper")
@EnableTransactionManagement(proxyTargetClass = true)
public class MyBatisConfig {
	@Resource
	private DataSource dataSource;

	@Bean
	public SqlSessionFactory sqlSessionFactory() throws Exception {
		SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
		bean.setDataSource(dataSource);
		
		com.lefu.commons.web.mybatis.PagePlugin pagePlugin = new com.lefu.commons.web.mybatis.PagePlugin();
		bean.setPlugins(new Interceptor[]{pagePlugin});
		
		return bean.getObject();
	}

	@Bean
	public DataSourceTransactionManager dataSourceTransactionManager() {
		return new DataSourceTransactionManager(dataSource);
	}

}
