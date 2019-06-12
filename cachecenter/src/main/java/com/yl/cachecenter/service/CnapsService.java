package com.yl.cachecenter.service;

import java.util.List;
import java.util.Map;

import com.yl.cachecenter.model.Cnaps;

/**
 * 联行号业务接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月24日
 * @version V1.0.0
 */
public interface CnapsService {
	/**
	 * 批量新增联行号信息
	 * @param cnapses 联行号列表
	 */
	void add(List<Cnaps> cnapses);

	/**
	 * 模糊搜索联行号银行名称
	 * @param limit 查询条数（<=0时为所有）
	 * @param fields 返回参数列表（不包含code）
	 * @param name 银行名称关键字
	 * @param providerCode 提供方编号
	 * @param areaCode 地区编码
	 * @param clearBankLevel 清分行级别
	 * @return 联行号信息列表
	 */
	List<Cnaps> suggestSearch(String name, int limit, String[] fields , String providerCode , String areaCode,int clearBankLevel);

	/**
	 * 查询所有银行简称匹配信息
	 * @return <全称，简称>
	 */
	Map<String, String> queryNickname();

	/**
	 * 更新所有银行简称匹配信息
	 * @param nicknames <全称，简称>
	 */
	void modifyNickname(Map<String, String> nicknames);

}
