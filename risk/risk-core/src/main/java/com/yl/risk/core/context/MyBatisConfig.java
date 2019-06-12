package com.yl.risk.core.context;

import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 * MyBatis配置
 *
 * @author 聚合支付有限公司
 * @since 2017年11月10日
 * @version V1.0.0
 */
@Configuration
@Import(MariaDBConfig.class)
@MapperScan("com.yl.risk.core.dao")
@EnableTransactionManagement(order = 0)
public class MyBatisConfig {
    @Resource
    private DataSource dataSource;

    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        com.lefu.commons.web.mybatis.PagePlugin pagePlugin = new com.lefu.commons.web.mybatis.PagePlugin();
//        bean.setPlugins(new Interceptor[]{pagePlugin});
        return bean.getObject();
    }

    @Bean
    public DataSourceTransactionManager dataSourceTransactionManager() {
        return new DataSourceTransactionManager(dataSource);
    }

}
