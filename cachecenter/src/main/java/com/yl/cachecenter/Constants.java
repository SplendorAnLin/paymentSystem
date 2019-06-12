package com.yl.cachecenter;

import java.util.ArrayList;
import java.util.List;

/**
 * 缓存中心全局常量
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月24日
 * @version V1.0.0
 */
public class Constants {
	public static final String APP_NAME = "cachecenter";

	/** 联行号key前缀 */
	public static final String CNAPS_KEY_PREFIX = "CNAPS:";
	/** 联行号分词匹配key前缀 */
	public static final String CNAPS_SUGGEST_KEY_PREFIX = "CNAPS.SUGGEST:";
	/** 联行号分词score默认值 */
	public static final double CNAPS_SUGGEST_DEFAULT_SCORE = 1;
	/** 联行号提供方匹配key前缀 */
	public static final String CNAPS_PROVIDER_KEY_PREFIX = "CNAPS.PROVIDER:";
	/** 联行号提供方score默认值 */
	public static final double CNAPS_PROVIDER_SCORE = 1;
	/** 清分行匹配key前缀 */
	public static final String CNAPS_CLEARINGBANK_KEY_PREFIX = "CNAPS.CLEARINGBANK:";
	/** 清分行score默认值 */
	public static final double CNAPS_CLEARINGBANK_SCORE = 1;
	/** 联行号地区匹配key前缀 */
	public static final String CNAPS_ADCODE_KEY_PREFIX = "CNAPS.ADCODE:";
	/** 联行号地区score默认值 */
	public static final double CNAPS_ADCODE_KEY_SCORE = 1;
	/** 联行号分词匹配临时key */
	public static final String CNAPS_SUGGEST_TEMP_KEY = "CNAPS.TEMP";
	/** 银行名称简称匹配key */
	public static final String CNAPS_NICKNAME_KEY = "CNAPS.NICKNAME";
	/** 行政区划匹配key前缀 */
	public static final String ADCODE_KEY_PREFIX = "ADCODE:";
	/** 行政区划score默认值 */
	public static final double ADCODE_KEY_SCORE = 1;
	
	/** 乐富地区码匹配银联地区码匹配key前缀 */
	public static final String LEFUCODETOUNIONPAYCODE_KEY_PREFIX = "LEFECODETOUNIONPAYCODE:";
	/** 乐富地区码匹配银联地区码score默认值 */
	public static final double LEFUCODETOUNIONPAYCODE_KEY_SCORE = 1;
	
	/** 联行号地区匹配key前缀 */
	public static final String CNAPS_CLEARBANKLEVEL_KEY_PREFIX = "CNAPS.CLEARBANKLEVEL:";
	/** 联行号地区score默认值 */
	public static final double CNAPS_CLEARBANKLEVEL_KEY_SCORE = 1;

	/** 发行者识别号码key前缀 */
	public static final String IIN_KEY_PREFIX = "IIN:";

	/** 商户信息key前缀 */
	public static final String  PARTNER_KEY_PREFIX = "PARTNER:";
	
	/** 站点key前缀 */
	public static final String STATION_KEY_PREFIX = "STATION:";
	/** 站点分词匹配key前缀 */
	public static final String STATION_SUGGEST_KEY_PREFIX = "STATION.SUGGEST:";
	/** 联行号分词匹配临时key */
	public static final String STATION_SUGGEST_TEMP_KEY = "STATION.TEMP";
	/** 站点score默认值 */
	public static final double STATION_KEY_SCORE = 1;
	
	/** 发行者识别号码按6位长度判断需排除的列表 */
	public static final List<String> IIN_EXCLUDE_LENGTH6 = new ArrayList<String>();
	static {
		IIN_EXCLUDE_LENGTH6.add("685800");
		IIN_EXCLUDE_LENGTH6.add("622498");
		IIN_EXCLUDE_LENGTH6.add("940046");
		IIN_EXCLUDE_LENGTH6.add("900105");
		IIN_EXCLUDE_LENGTH6.add("900205");
		IIN_EXCLUDE_LENGTH6.add("622319");
		IIN_EXCLUDE_LENGTH6.add("620108");
	}
}
