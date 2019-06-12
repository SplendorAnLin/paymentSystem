package com.yl.payinterface.core.handle.impl.wallet.cmbc584001;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 服务参数配置文件的加载和获取
 * 
 * @author 聚合支付有限公司
 * @since 2016年12月14日
 * @version V1.0.0
 */
public class Config {

	private static final String propsPath = "/trade-config/cmbc-wallet.properties";
	private static Properties props = new Properties();
	private static boolean initFlag = false;

	/**
	 * 初始化配置文件
	 */
	private static synchronized void init() {
		if (!initFlag) {
			InputStream in = null;
			try {
				in = Config.class.getResourceAsStream(propsPath);
				props.load(in);
			} catch (IOException e) {
				throw new RuntimeException(e.getMessage(), e);
			} finally {
				if (in != null) {
					try {
						in.close();
					} catch (IOException e) {
					}
				}
			}
			initFlag = true;
		}
	}

	/**
	 * 获取初始化参数
	 * @param propName
	 * @return
	 */
	public static String getProperty(String propName) {
		init();
		return props.getProperty(propName);
	}
}
