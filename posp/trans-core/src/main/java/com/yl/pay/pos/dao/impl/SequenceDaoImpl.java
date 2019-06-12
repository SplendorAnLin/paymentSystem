package com.yl.pay.pos.dao.impl;

import com.yl.pay.pos.dao.SequenceDao;
import com.yl.pay.pos.dao.impl.helper.EntityDao;
import com.yl.pay.pos.util.CodeBuilder;

/**
 * Title: SequenceDaoImpl
 * Description: 
 * Copyright: Copyright (c)2011
 * Company: com.yl.pay
 * @author haitao.liu
 */

public class SequenceDaoImpl implements SequenceDao {

	private EntityDao entityDao;

	public String generateSequence(String sequenceName,int width) {
		Long sequence = CodeBuilder.getSequnce();
		int tempWidth = (int) Math.pow(10, width);

		Long s = sequence % tempWidth;
		String temp = String.valueOf(s);
		if(temp.length() < width){
			for (int i = temp.length(); i < width; i++) {
				temp = "0" + temp;
			}
		}else{
			temp = temp.substring(width - temp.length(), width);
		}
		String tempStr = "";
		for(int i =0; i<width; i++){
			tempStr += "0";
		}
		if(tempStr.equals(temp)){
			sequence = CodeBuilder.getSequnce();
			sequence = 1L;
			s = sequence % tempWidth;
			temp = String.valueOf(s);
			for (int i = temp.length(); i < width; i++) {
				temp = "0" + temp;
			}
		}
		return temp;
	}
	
	public String generateSequence(String sequenceName) {
		return generateSequence(sequenceName, 6);
	}
	
	public EntityDao getEntityDao() {
		return entityDao;
	}	
	public void setEntityDao(EntityDao entityDao) {
		this.entityDao = entityDao;
	}
}
