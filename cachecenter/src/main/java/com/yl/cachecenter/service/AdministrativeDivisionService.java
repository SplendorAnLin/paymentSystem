package com.yl.cachecenter.service;

import java.util.List;

import com.yl.cachecenter.model.AdministrativeDivision;
import com.yl.cachecenter.model.LefuCodeToUnionPayCode;

/**
 * 行政规划码业务接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月24日
 * @version V1.0.0
 */
public interface AdministrativeDivisionService {
	/**
	 * 批量新增行政规划码信息
	 * @param ad 行政规划码列表
	 */
	void add(List<AdministrativeDivision> ad);

	/**
	 * 模糊搜索联行号银行名称
	 * @param adCode 地区编码
	 * @return 行政规划码
	 */
	AdministrativeDivision search(String adCode);
	
	/**
	 * 批量新增乐富地区码匹配银联地区码
	 * @param lp 行政规划码列表
	 */
	void addLefuCodeToUnionPayCode(List<LefuCodeToUnionPayCode> lp);
	
	/**
	 * 根据乐富地区码查询
	 * @param lefuCode 乐富地区编码
	 * @return 乐富地区码匹配银联地区码
	 */
	LefuCodeToUnionPayCode searchLefuCodeToUnionPayCode(String lefuCode);

}
