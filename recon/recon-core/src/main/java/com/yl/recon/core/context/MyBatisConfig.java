package com.yl.recon.core.context;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * MyBatis配置
 *
 * @author 聚合支付有限公司
 * @version V1.0.0
 * @since 2016年2月23日
 */
@Configuration
@Import(MariaDBConfig.class)
@MapperScan("com.yl.recon.core.mybatis.mapper")
@EnableTransactionManagement(order = 0)
public class MyBatisConfig {
	@Resource
	private DataSource dataSource;

	//@Bean
	//public SqlSessionFactory sqlSessionFactory() throws Exception {
	//	SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
	//	bean.setDataSource(dataSource);
	//
	//	com.lefu.commons.web.mybatis.PagePlugin pagePlugin = new com.lefu.commons.web.mybatis.PagePlugin();
	//	bean.setPlugins(new Interceptor[]{pagePlugin});
	//	return bean.getObject();
	//}

	/**
	 * 根据数据源创建SqlSessionFactory
	 */
	@Bean
	public SqlSessionFactory sqlSessionFactory(DynamicDataSource dynamicDataSource) throws Exception {
		PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
		SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
		bean.setDataSource(dynamicDataSource);// 指定数据源(这个必须有，否则报错)
		//bean.setTypeAliasesPackage("com.yl.recon.core.mybatis.mapper");// 指定基包
		//bean.setMapperLocations(resolver.getResources("classpath:com/yl.recon/core/mybatis/mapper/**/*.xml"));
		com.lefu.commons.web.mybatis.PagePlugin pagePlugin = new com.lefu.commons.web.mybatis.PagePlugin();
		bean.setPlugins(new Interceptor[]{pagePlugin});
		return bean.getObject();
	}

	@Bean
	public DataSourceTransactionManager dataSourceTransactionManager() {
		return new DataSourceTransactionManager(dataSource);
	}
}



