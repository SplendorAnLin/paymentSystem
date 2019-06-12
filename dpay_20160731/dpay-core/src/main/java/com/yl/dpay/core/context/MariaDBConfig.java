package com.yl.dpay.core.context;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.alibaba.druid.pool.DruidDataSource;

/**
 * MariaDB数据源配置
 * 
 * @author 聚合支付有限公司
 * @since 2016年5月11日
 * @version V1.0.0
 */
@Configuration
@PropertySource("classpath:/mariadb.properties")
public class MariaDBConfig {
	@Value("${dataSource.driverClassName}")
	private String driverClassName;
	@Value("${dataSource.url}")
	private String url;
	@Value("${dataSource.username}")
	private String username;
	@Value("${dataSource.password}")
	private String password;
	@Value("${dataSource.initialSize}")
	private int initialSize;
	@Value("${dataSource.maxActive}")
	private int maxActive;

	@Bean(destroyMethod = "close")
	public DataSource dataSource() {
		DruidDataSource dataSource = new DruidDataSource();
		dataSource.setDriverClassName(driverClassName);
		dataSource.setUrl(url);
		dataSource.setUsername(username);
		dataSource.setPassword(password);
		dataSource.setInitialSize(initialSize);
		dataSource.setMaxActive(maxActive);
		dataSource.setTestWhileIdle(true);
		dataSource.setValidationQuery("select 1 from dual");
		return dataSource;
	}
}
