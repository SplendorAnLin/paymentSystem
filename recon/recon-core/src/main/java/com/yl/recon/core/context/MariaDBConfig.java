package com.yl.recon.core.context;

import com.alibaba.druid.pool.DruidDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * MariaDB数据源配置
 *
 * @author 聚合支付有限公司
 * @version V1.0.0
 * @since 2016年2月23日
 */
@Configuration
@PropertySource("classpath:/mariadb.properties")
public class MariaDBConfig {
	private static Logger logger = LoggerFactory.getLogger(MariaDBConfig.class);

	@Value("${recon.url}")
	private String reconUrl;
	@Value("${account.url}")
	private String accountUrl;
	@Value("${dpay.url}")
	private String dpayUrl;
	@Value("${payinterface.url}")
	private String payinterfaceUrl;
	@Value("${realAuth.url}")
	private String realAuthUrl;
	@Value("${onlineTrade.url}")
	private String onlineTradeUrl;

	@Value("${dataSource.driverClassName}")
	private String driverClassName;
	@Value("${dataSource.username}")
	private String username;
	@Value("${dataSource.password}")
	private String password;
	@Value("${dataSource.initialSize}")
	private int initialSize;
	@Value("${dataSource.maxActive}")
	private int maxActive;
	@Value("${dataSource.minIdle}")
	private int minIdle;
	@Value("${dataSource.maxWait}")
	private int maxWait;
	@Value("${dataSource.timeBetweenEvictionRunsMillis}")
	private int timeBetweenEvictionRunsMillis;
	@Value("${dataSource.minEvictableIdleTimeMillis}")
	private int minEvictableIdleTimeMillis;
	@Value("${dataSource.validationQuery}")
	private String validationQuery;
	@Value("${dataSource.testWhileIdle}")
	private Boolean testWhileIdle;
	@Value("${dataSource.testOnBorrow}")
	private Boolean testOnBorrow;
	@Value("${dataSource.testOnReturn}")
	private Boolean testOnReturn;
	@Value("${dataSource.poolPreparedStatements}")
	private Boolean poolPreparedStatements;
	@Value("${dataSource.maxPoolPreparedStatementPerConnectionSize}")
	private int maxPoolPreparedStatementPerConnectionSize;


	/**
	 * Date 2017/5/27
	 * Author 邱健
	 * <p>
	 * 方法说明:对账
	 *
	 * @return BasicDataSource
	 * @throws Exception 构建BasicDataSource异常
	 */
	@Bean(name = "reconDateSource")
	@Qualifier("reconDateSource")
	@Primary
	public DataSource reconDateSource() throws Exception {
		return getDataSource(reconUrl, username, password, driverClassName, initialSize, minIdle, maxActive, maxWait, timeBetweenEvictionRunsMillis, minEvictableIdleTimeMillis, validationQuery, testWhileIdle, testOnBorrow, testOnReturn, poolPreparedStatements, maxPoolPreparedStatementPerConnectionSize);
	}

	/**
	 * Date 2017/5/27
	 * Author 邱健
	 * <p>
	 * 方法说明:账户
	 *
	 * @return BasicDataSource
	 * @throws Exception 构建BasicDataSource异常
	 */
	@Bean(name = "accountDateSource")
	@Qualifier("accountDateSource")
	public DataSource accountDateSource() throws Exception {
		return getDataSource(accountUrl, username, password, driverClassName, initialSize, minIdle, maxActive, maxWait, timeBetweenEvictionRunsMillis, minEvictableIdleTimeMillis, validationQuery, testWhileIdle, testOnBorrow, testOnReturn, poolPreparedStatements, maxPoolPreparedStatementPerConnectionSize);
	}

	/**
	 * Date 2017/5/27
	 * Author 邱健
	 * <p>
	 * 方法说明:接口
	 *
	 * @return BasicDataSource
	 * @throws Exception 构建BasicDataSource异常
	 */
	@Bean(name = "payinterfaceDateSource")
	@Qualifier("payinterfaceDateSource")
	public DataSource payinterfaceDateSource() throws Exception {
		return getDataSource(payinterfaceUrl, username, password, driverClassName, initialSize, minIdle, maxActive, maxWait, timeBetweenEvictionRunsMillis, minEvictableIdleTimeMillis, validationQuery, testWhileIdle, testOnBorrow, testOnReturn, poolPreparedStatements, maxPoolPreparedStatementPerConnectionSize);
	}

	/**
	 * Date 2017/5/27
	 * Author 邱健
	 * <p>
	 * 方法说明:接口
	 *
	 * @return BasicDataSource
	 * @throws Exception 构建BasicDataSource异常
	 */
	@Bean(name = "realAuthDateSource")
	@Qualifier("realAuthDateSource")
	public DataSource realAuthDateSource() throws Exception {
		return getDataSource(realAuthUrl, username, password, driverClassName, initialSize, minIdle, maxActive, maxWait, timeBetweenEvictionRunsMillis, minEvictableIdleTimeMillis, validationQuery, testWhileIdle, testOnBorrow, testOnReturn, poolPreparedStatements, maxPoolPreparedStatementPerConnectionSize);
	}

	/**
	 * Date 2017/5/27
	 * Author 邱健
	 * <p>
	 * 方法说明:接口
	 *
	 * @return BasicDataSource
	 * @throws Exception 构建BasicDataSource异常
	 */
	@Bean(name = "onlineTradeDateSource")
	@Qualifier("onlineTradeDateSource")
	public DataSource onlineTradeDateSource() throws Exception {
		return getDataSource(onlineTradeUrl, username, password, driverClassName, initialSize, minIdle, maxActive, maxWait, timeBetweenEvictionRunsMillis, minEvictableIdleTimeMillis, validationQuery, testWhileIdle, testOnBorrow, testOnReturn, poolPreparedStatements, maxPoolPreparedStatementPerConnectionSize);
	}


	/**
	 * Date 2017/5/27
	 * Author 邱健
	 * <p>
	 * 方法说明:代付
	 *
	 * @return BasicDataSource
	 * @throws Exception 构建BasicDataSource异常
	 */
	@Bean(name = "remitDateSource")
	@Qualifier("remitDateSource")
	public DataSource remitDateSource() throws Exception {
		return getDataSource(dpayUrl, username, password, driverClassName, initialSize, minIdle, maxActive, maxWait, timeBetweenEvictionRunsMillis, minEvictableIdleTimeMillis, validationQuery, testWhileIdle, testOnBorrow, testOnReturn, poolPreparedStatements, maxPoolPreparedStatementPerConnectionSize);

	}


	private DataSource getDataSource(String url, String username, String password, String driverClassName, int initialSize, int minIdle, int maxActive, int maxWait, int timeBetweenEvictionRunsMillis, int minEvictableIdleTimeMillis, String validationQuery, boolean testWhileIdle, boolean testOnBorrow, boolean testOnReturn, boolean poolPreparedStatements, int maxPoolPreparedStatementPerConnectionSize) {
		logger.info("[DATASOURCE]-[USERNAME:{}],[URL:{}]",
				username,
				url);
		DruidDataSource datasource = new DruidDataSource();
		datasource.setUrl(url);
		datasource.setUsername(username);
		datasource.setPassword(password);
		datasource.setDriverClassName(driverClassName);
		datasource.setInitialSize(initialSize);
		datasource.setMinIdle(minIdle);
		datasource.setMaxActive(maxActive);
		datasource.setMaxWait(maxWait);
		datasource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
		datasource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
		datasource.setValidationQuery(validationQuery);
		datasource.setTestWhileIdle(testWhileIdle);
		datasource.setTestOnBorrow(testOnBorrow);
		datasource.setTestOnReturn(testOnReturn);
		datasource.setPoolPreparedStatements(poolPreparedStatements);
		datasource.setMaxPoolPreparedStatementPerConnectionSize(maxPoolPreparedStatementPerConnectionSize);
		return datasource;
	}


	@Bean
	public DynamicDataSource dataSource(
			@Qualifier("reconDateSource") DataSource reconDateSource,
			@Qualifier("remitDateSource") DataSource remitDateSource,
			@Qualifier("payinterfaceDateSource") DataSource payinterfaceDateSource,
			@Qualifier("realAuthDateSource") DataSource realAuthDateSource,
			@Qualifier("onlineTradeDateSource") DataSource onlineTradeDateSource,
			@Qualifier("accountDateSource") DataSource accountDateSource) {
		Map <Object, Object> targetDataSources = new HashMap <>();
		targetDataSources.put(DataBaseSourceType.RECON, reconDateSource);
		targetDataSources.put(DataBaseSourceType.DPAY, remitDateSource);
		targetDataSources.put(DataBaseSourceType.PAYINTERFACE, payinterfaceDateSource);
		targetDataSources.put(DataBaseSourceType.REAL_AUTH, realAuthDateSource);
		targetDataSources.put(DataBaseSourceType.ONLINE_TRADE, onlineTradeDateSource);
		targetDataSources.put(DataBaseSourceType.ACCOUNT, accountDateSource);
		DynamicDataSource dataSource = new DynamicDataSource();
		dataSource.setTargetDataSources(targetDataSources);
		dataSource.setDefaultTargetDataSource(reconDateSource);
		return dataSource;
	}
}
