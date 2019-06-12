package com.yl.realAuth.core.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.lefu.commons.utils.Page;
import com.yl.realAuth.model.BindCardInfo;

public interface BindCardInfoDao {
	/**
	 * 动态查询绑定卡信息
	 * @param params
	 * @return
	 */
	public List<BindCardInfo> findAlldynamicBindCardInfoBean(@Param("params")Map<String, Object> params,@Param("page")Page page);
}
