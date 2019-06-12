package com.yl.pay.pos.service;

import com.yl.pay.pos.entity.UnionPayBCN32Config;

/**
 * @author haitao.liu
 */
public interface UnionPayBCN32ConfigService {
	UnionPayBCN32Config getEffectiveBy(String bankCustomerNo);
}
