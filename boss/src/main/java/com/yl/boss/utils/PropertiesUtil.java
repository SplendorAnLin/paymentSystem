package com.yl.boss.utils;

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
}
