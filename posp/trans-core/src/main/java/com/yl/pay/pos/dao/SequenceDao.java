package com.yl.pay.pos.dao;

/**
 * Title: SequenceDao
 * Description: 
 * Copyright: Copyright (c)2011
 * Company: com.yl.pay
 * @author haitao.liu
 */

public interface SequenceDao  {

	public String generateSequence(String sequenceName, int width) ;
	public String generateSequence(String sequenceName);
}
