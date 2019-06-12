package com.yl.pay.pos.service;

public interface IIcKeyService {

	public String findKeysByPosCati(String posCati); 
	
	public String findByRid(String rid, String keyIndex);
}
