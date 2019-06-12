package com.pay.sign.dbservice;

import com.pay.sign.dbentity.SignPic;

/**
 * Title:保存图片和订单的关系 
 * Description:   
 * Copyright: 2015年6月12日下午2:51:42
 * Company: SDJ
 * @author zhongxiang.ren
 */
public interface ISignDBService {
 
	public void createSignPic(SignPic signPic);
	
}
