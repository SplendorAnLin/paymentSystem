package com.yl.pay.pos.dao;

import com.yl.pay.pos.entity.PosMessage;

public interface PosMessageDao {
		//根据ID查询PosMessage
		public PosMessage findById(Long id);

		//创建PosMessage
		public PosMessage create(PosMessage posMessage);
		
		//更新PosMessage
		public PosMessage update(PosMessage posMessage);
		
		//根据终端号查询
		public PosMessage findByPosCati(String posCati);
		//删除PosMessage
		public void delete(PosMessage posMessage) ;
}
