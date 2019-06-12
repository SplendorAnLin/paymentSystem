package com.yl.pay.pos.interfaces.common.util.ISO8583;

/**
 * Title: 编码方式
 * Description: 
 * Copyright: Copyright (c)2011
 * Company: pay
 * @author qiuzhl
 */

public enum EncodeType {
	/**
	 * BCD 编码方式，一般用于数字，靠右，左补零
	 */
	BCD_LEFT_FILL_ZERO,
	
	/**
	 * BCD 编码方式，一般用于数字，靠左，右补零
	 */
	BCD_RIGHT_FILL_ZERO,
	/**
	 * ASCII 编码，一般的字符
	 */
	ASCII, 
	/**
	 * 二进制，一般用于位图
	 */
	BINARY,
	
	/**
	 * 一般用于其他非字符类数据
	 */
	HEX
	;
}