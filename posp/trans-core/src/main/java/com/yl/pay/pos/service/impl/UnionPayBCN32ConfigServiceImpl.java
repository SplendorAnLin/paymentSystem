package com.yl.pay.pos.service.impl;

import com.pay.common.util.StringUtil;
import com.yl.pay.pos.dao.UnionPayBCN32ConfigDao;
import com.yl.pay.pos.entity.UnionPayBCN32Config;
import com.yl.pay.pos.service.UnionPayBCN32ConfigService;

/**
 * @author haitao.liu
 */
public class UnionPayBCN32ConfigServiceImpl implements UnionPayBCN32ConfigService {
	private UnionPayBCN32ConfigDao unionPayBCN32ConfigDao;

	public void setUnionPayBCN32ConfigDao(UnionPayBCN32ConfigDao unionPayBCN32ConfigDao) {
		this.unionPayBCN32ConfigDao = unionPayBCN32ConfigDao;
	}

	/*
	 * (non-Javadoc)
	 * @see com.com.yl.pay.pos.service.UnionPayBCN32ConfigService#getEffectiveBy(java.lang.String)
	 */
	@Override
	public UnionPayBCN32Config getEffectiveBy(String bankCustomerNo) {
		if (StringUtil.isNull(bankCustomerNo)) {
			return null;
		}
		return unionPayBCN32ConfigDao.findEffectiveBy(bankCustomerNo);
	}

}
