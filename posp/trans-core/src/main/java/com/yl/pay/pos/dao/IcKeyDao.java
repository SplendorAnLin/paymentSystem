package com.yl.pay.pos.dao;

import com.yl.pay.pos.entity.IcKey;

import java.util.List;

public interface IcKeyDao {

	/**
	 * 查询所有IC公钥
	 * @return
	 */
	public List<IcKey> findAll();
	
	public IcKey findByRidAndIndex(String rid, String keyIndex);
}
