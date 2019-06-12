package com.yl.payinterface.core.handler;

import java.util.Map;

/**
 * @ClassName QuickPayFilingHandler
 * @Description TODO(这里用一句话描述这个类的作用)
 * @author Administrator
 * @date 2017年11月20日 下午3:08:48
 */
public interface QuickPayFilingHandler {

	/**
	 * 
	 * @Description 快捷报备
	 * @param map
	 * @return
	 * @date 2017年11月20日
	 */
	public Map<String, String> filing(Map<String, String> map);
}
