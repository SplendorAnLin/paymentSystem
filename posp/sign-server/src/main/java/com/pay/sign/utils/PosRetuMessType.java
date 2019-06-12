package com.pay.sign.utils;

/**
 * Title: POS交易类型列表
 * Description: 
 * Copyright: Copyright (c)2015
 * Company: pay
 * @author zhongxiang.ren
 */

import java.util.ArrayList;
import java.util.List;

public class PosRetuMessType {
	
	static List MtpList=new ArrayList();
	
	static{
		MtpList.add("0900");
		MtpList.add("0910");
	}

	public static boolean getMtpResult(String mtp){
		boolean result=false;
		for(int i=0;i<MtpList.size();i++){
			if(MtpList.get(i).equals(mtp)){
				result=true;
				break;
			}
		}
		return result;
	}
}
