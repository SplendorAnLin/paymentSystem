package com.yl.online.trade.context;

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
 * @author 聚合支付有限公司
 * @since 2016年7月24日
 * @version V1.0.0
 */
@Configuration
@Import(MariaDBConfig.class)
@MapperScan("com.yl.online.trade.dao.mapper")
@EnableTransactionManagement(order = 0)
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
	
	
	
	/*@Bean
	public Plugin addPlugin(){
		Plugin plugin = new Plugin(null, null, null);
		com.lefu.commons.web.mybatis.PagePlugin pagePlugin = new com.lefu.commons.web.mybatis.PagePlugin();
		java.util.Properties properties = new java.util.Properties();
		properties.setProperty("dialect", "mysql");
		properties.setProperty("pageSqlId", "findAll*");
		pagePlugin.setProperties(properties);
		return null;
	}*/
	
}
