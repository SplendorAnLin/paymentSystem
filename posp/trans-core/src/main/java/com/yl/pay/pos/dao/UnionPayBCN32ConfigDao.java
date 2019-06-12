package com.yl.pay.pos.dao;

import com.yl.pay.pos.entity.UnionPayBCN32Config;

/**
 * @author haitao.liu
 */
public interface UnionPayBCN32ConfigDao {
	UnionPayBCN32Config findEffectiveBy(String bankCustomerNo);

}
