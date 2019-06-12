package com.yl.rate.core.dao;

import org.apache.ibatis.annotations.Param;

import java.util.List;

import com.yl.rate.core.entity.RateRule;

public interface RateRuleMapper {
	int insert(@Param("rateRule") RateRule rateRule);

	int insertSelective(@Param("rateRule") RateRule rateRule);

	int insertList(@Param("rateRules") List <RateRule> rateRules);

	int update(@Param("rateRule") RateRule rateRule);

	/**
	 * 查询费率规则
	 * @param rateCode
	 * @return
	 */
	List<RateRule> queryRateRule(String rateCode);

	/**
	 * 费率规则新增
	 * @param rateRule
	 * @return 费率编号
	 */
	void rateRuleAdd(RateRule rateRule);

	/**
	 * 根据费率规则编号效验是否存在
	 * @param code
	 * @return
	 */
	int checkRateRuleByCode(@Param("code") String code);


	int delete(@Param("id")Long id);

	/**
	 * 根据费率编号，删除对应的所有费率规则
	 * @param rateCode
	 */
	void deleteByRateCode(@Param("rateCode") String rateCode);
}
