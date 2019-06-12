package com.yl.pay.pos.dao;

import com.yl.pay.pos.entity.IcParams;

import java.util.List;

public interface IcParamsDao {

	public List<IcParams> findAll();
	
	public IcParams findByRidAndIndex(String aid);
}
