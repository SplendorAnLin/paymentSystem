package com.yl.dpay.core.mybatis.interceptor;

import java.util.Properties;


import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;

import com.yl.dpay.core.mybatis.Exception.OptimisticLockException;

/**
 * 乐观锁拦截器
 * 
 * @author 聚合支付有限公司
 * @since 2017年4月28日
 * @version V1.0.0
 */
@Intercepts({  
    @Signature(type = Executor.class, method = "update", args = {  
        MappedStatement.class, Object.class })
     })  
public class OptimisticLockInterceptor implements Interceptor {

	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		Object o = invocation.proceed();
		if((Integer)o < 1){
			throw new OptimisticLockException();
		}
		return o;
	}

	@Override
	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	@Override
	public void setProperties(Properties properties) {
		// TODO Auto-generated method stub
	}

}
