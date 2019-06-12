package com.yl.pay.pos.dao;

import com.yl.pay.pos.entity.IcUpdateMark;

public interface IcUpdateMarkDao {

	public IcUpdateMark findByPosCati(String posCati, String updateKey);
	
	public void updateIcMark(IcUpdateMark icUpdateMark);
	
	public IcUpdateMark createIcMark(IcUpdateMark icUpdateMark);
	
	public IcUpdateMark findByType(String updateType);
}
