package com.yl.pay.pos.service;

import com.yl.pay.pos.entity.PosMessage;

public interface PosMessageService {
	public PosMessage findByPosCati(String posCati);
	public PosMessage create(PosMessage posMessage);
	public PosMessage update(PosMessage posMessage);
	public void delete(PosMessage posMessage);
}
