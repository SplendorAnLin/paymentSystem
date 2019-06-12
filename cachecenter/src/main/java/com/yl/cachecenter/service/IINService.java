package com.yl.cachecenter.service;

import java.util.List;

import com.yl.cachecenter.model.IIN;

/**
 * 发行者识别号码业务接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月24日
 * @version V1.0.0
 */
public interface IINService {
	/**
	 * 银行卡识别
	 * @param panNo 联行号列表
	 * @param fields 返回参数列表
	 * @return IIN
	 */
	IIN recognition(String panNo,String [] fields);
	/**
	 * 批量新增银行卡识别信息
	 * @param cnapses 银行卡识别列表
	 */
	void add(List<IIN> iins);

}
