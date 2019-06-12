package com.yl.pay.pos.service;

import com.yl.pay.pos.bean.UnionPayBean;
import com.yl.pay.pos.entity.Pos;
import com.yl.pay.pos.entity.SignPic;

import java.util.Map;

public interface PosService {
	public boolean createPos(Pos pos);
	
	public boolean update(Pos pos);
	
	public Pos findByPosCati(String posCati);
	
	public Map<String, String> handleP001(String posSn, String posType);

	public UnionPayBean handleT001(String posSn);

	public UnionPayBean handleT002(UnionPayBean unionPayBean, String posSn);

	public UnionPayBean handleT003(UnionPayBean unionPayBean, String posSn);

	public SignPic handleT004(String externalId);
}
