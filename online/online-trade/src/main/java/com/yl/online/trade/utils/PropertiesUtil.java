package com.yl.online.trade.utils;

import com.yl.online.trade.exception.BusinessRuntimeException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtil {

	/**
	 * 获取Properties对象，该对象加载了classpath路径下指定文件名的文件
	 * @param fileName 文件名称
	 * @return 读取指定文件名称的Properties对象
	 */
	public static Properties getProperties(String fileName) {

		InputStream in = null;
		Properties prop = null;

		try {
			in = PropertiesUtil.class.getClassLoader().getResourceAsStream(fileName);// 没有则返回null
			prop = new Properties();
			prop.load(in);
		} catch (IOException e) {
		} finally {
			try {
				in.close();
			} catch (IOException e) {
			}
		}

		return prop;
	}

	/**
	 * @Description 获取快捷有积分通道费率（临时方案）
	 * @param ownerId
	 * @param interfaceInfoCode
	 * @return
	 * @date 2018年1月24日
	 */
	public static String[] getQuickPayFen(String ownerId, String interfaceInfoCode) {
		/** 快捷积分费率 */
		Properties prop = PropertiesUtil.getProperties("quickPayFee.properties");
		Object info = prop.get(ownerId + "_" + interfaceInfoCode);
		info = prop.get(ownerId + "_" + interfaceInfoCode);
		if (info == null) {
			throw new BusinessRuntimeException("ownerId " + ownerId + "customerFee error");
		}
		String[] feeInfo = info.toString().split(",");
		return feeInfo;
	}
}
