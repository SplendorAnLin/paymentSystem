package com.yl.pay.pos.service.impl;

import com.pay.common.util.StringUtil;
import com.yl.pay.pos.dao.LefuOrg2UnionPayOrgDao;
import com.yl.pay.pos.service.LefuOrg2UnionPayOrgService;

/**
 * @author haitao.liu
 */
public class LefuOrg2UnionPayOrgServiceImpl implements LefuOrg2UnionPayOrgService {
	private LefuOrg2UnionPayOrgDao lefuOrg2UnionPayOrgDao;

	public void setLefuOrg2UnionPayOrgDao(LefuOrg2UnionPayOrgDao lefuOrg2UnionPayOrgDao) {
		this.lefuOrg2UnionPayOrgDao = lefuOrg2UnionPayOrgDao;
	}

	/*
	 * (non-Javadoc)
	 * @see com.com.yl.pay.pos.service.LefuOrg2UnionPayOrgService#findUnionPayCodeBy(java .lang.String)
	 */
	@Override
	public String findEffectiveUnionPayCodeBy(String lefuCode) {
		if (StringUtil.isNull(lefuCode)) {
			return null;
		}
		return lefuOrg2UnionPayOrgDao.findEffectiveUnionPayCodeBy(lefuCode);
	}

}
