package com.yl.dpay.core.Utils;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Bankab工具类
 * 
 * @author 聚合支付有限公司
 * @since 2016年5月15日
 * @version V1.0.0
 */
public class BankabUtil {
	private static final String BANK_CODE_OTHER = "OTHER";
	private static final Map<String, String> BANK_CODE_FULL_NAME_MAP = new HashMap<String, String>();
	private static final Map<String, String> BANK_CODE_SHORT_NAME_MAP = new HashMap<String, String>();
	private static final List<String> BANK_SHORT_NAME_EXCLUDED_SUFFIX = new LinkedList<String>();
	static {
		BANK_CODE_FULL_NAME_MAP.put("工商银行", "ICBC");
		BANK_CODE_FULL_NAME_MAP.put("农业银行", "ABC");
		BANK_CODE_FULL_NAME_MAP.put("建设银行", "CCB");
		BANK_CODE_FULL_NAME_MAP.put("中国银行", "BOC");
		BANK_CODE_FULL_NAME_MAP.put("兴业银行", "CIB");
		BANK_CODE_FULL_NAME_MAP.put("光大银行", "CEB");
		BANK_CODE_FULL_NAME_MAP.put("交通银行", "BCM");
		BANK_CODE_FULL_NAME_MAP.put("招商银行", "CMB");
		BANK_CODE_FULL_NAME_MAP.put("中信银行", "CITIC");
		BANK_CODE_FULL_NAME_MAP.put("民生银行", "CMBC");
		BANK_CODE_FULL_NAME_MAP.put("邮政储蓄", "PSBC");
		BANK_CODE_FULL_NAME_MAP.put("平安银行", "PAB");
		BANK_CODE_FULL_NAME_MAP.put("华夏银行", "HXB");
		BANK_CODE_FULL_NAME_MAP.put("广东发展", "GDB");
		BANK_CODE_FULL_NAME_MAP.put("深圳发展", "SDB");
		BANK_CODE_FULL_NAME_MAP.put("宁波银行", "NBCB");
		BANK_CODE_FULL_NAME_MAP.put("甘肃银行", "GSB");
		
		BANK_CODE_SHORT_NAME_MAP.put("工行", "ICBC");
		BANK_CODE_SHORT_NAME_MAP.put("农行", "ABC");
		BANK_CODE_SHORT_NAME_MAP.put("建行", "CCB");
		BANK_CODE_SHORT_NAME_MAP.put("中行", "BOC");
		BANK_CODE_SHORT_NAME_MAP.put("交行", "BCM");
		BANK_CODE_SHORT_NAME_MAP.put("招行", "CMB");
		BANK_CODE_SHORT_NAME_MAP.put("邮政", "PSBC");
		BANK_CODE_SHORT_NAME_MAP.put("广发", "GDB");
		BANK_CODE_SHORT_NAME_MAP.put("深发", "SDB");
		
		BANK_SHORT_NAME_EXCLUDED_SUFFIX.add("路");
		BANK_SHORT_NAME_EXCLUDED_SUFFIX.add("街");
		BANK_SHORT_NAME_EXCLUDED_SUFFIX.add("巷");
	}
	
	/**
	 * 根据开户行名称匹配银行编码(ICBC)
	 * @param openBankName	开户行名称
	 * @return
	 */
	public static String matchBankCodeByOpenBankName(String openBankName){
		String bankab=BANK_CODE_OTHER;
		for (String bankName : BANK_CODE_FULL_NAME_MAP.keySet()) {
			if (openBankName.contains(bankName)) {
				bankab = BANK_CODE_FULL_NAME_MAP.get(bankName);
				break;
			}
		}
		if(BANK_CODE_OTHER.equals(bankab)){
			for (String bankName : BANK_CODE_SHORT_NAME_MAP.keySet()) {
				if (openBankName.contains(bankName)) {
					int length=openBankName.lastIndexOf(bankName) + bankName.length();
					if ((length < openBankName.length() && !BANK_SHORT_NAME_EXCLUDED_SUFFIX.contains("" + openBankName.charAt(length)))
							|| openBankName.endsWith(bankName)) {
						bankab = BANK_CODE_SHORT_NAME_MAP.get(bankName);
						break;
					}
				}
			}
		}
		return bankab;
	}
}
