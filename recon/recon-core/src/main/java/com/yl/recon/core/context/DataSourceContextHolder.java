package com.yl.recon.core.context;

/**
 * 数据源容器
 */
public class DataSourceContextHolder {

	private static final ThreadLocal <DataBaseSourceType> contextHolder = new ThreadLocal <>();

	public static void setDataSourceType(DataBaseSourceType dataSourceType) {
		contextHolder.set(dataSourceType);
	}

	public static DataBaseSourceType getDataSourceType() {
		return contextHolder.get();
	}

	public static void clearDataSourceTypeType() {
		contextHolder.remove();
	}

}