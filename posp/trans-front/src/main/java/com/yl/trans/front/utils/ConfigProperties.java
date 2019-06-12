package com.yl.trans.front.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.io.InputStream;
import java.util.Properties;

@Configuration
public class ConfigProperties {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	public static Properties props = new Properties();
	
	/**
	 * 获取配置文件key值
	 * @param key
	 * @return
	 */
	public static String getProperty(String key) {
		return props.getProperty(key);
	}
	
	/**
	 * 获取配置文件Key值，int类型
	 * @param key
	 * @return
	 */
	public static int getIntProperty(String key) {
		String valueStr =  props.getProperty(key);
		int value = 0;
		if (valueStr != null) {
			value = Integer.parseInt(valueStr);
		}
		return value;
	}
	
	/**
	 * 修改配置文件中属性值
	 * @param key
	 * @return
	 */
	public static void setProperty(String key,String vaule){
		props.setProperty(key, vaule);
	}

	/**
	 *  加载配置文件
	 * @throws Exception 
	 */
	@Bean
	public Properties init(){
		InputStream inStream = this.getClass().getClassLoader().getResourceAsStream("system.properties");
		try {
			props.load(inStream);
			inStream.close();
		} catch (Exception e){
			logger.error("",e);
		}
		return props;
	}

}
