package com.yl.realAuth.core.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.lefu.commons.utils.Page;
import com.yl.realAuth.core.dao.BindCardInfoDao;
import com.yl.realAuth.core.service.BindCardInfoService;
import com.yl.realAuth.model.BindCardInfo;
@Service("bindCardInfoService")
public class BindCardInfoServiceImpl implements BindCardInfoService {
	@Resource
	private BindCardInfoDao bindCardInfoDao;
	/**
	 * 动态查询绑定卡信息
	 * @param params
	 * @return
	 */
	public Page findAlldynamicBindCardInfoBean(Map<String, Object> params,Page page){
		List<BindCardInfo> list = bindCardInfoDao.findAlldynamicBindCardInfoBean(params,page);
		page.setObject(list);
		return page;
	}
}
