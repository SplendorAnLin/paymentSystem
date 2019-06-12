/**
 * 
 */
package com.yl.payinterface.core.handle.impl.b2c.unicom100001;

import com.lefu.commons.utils.security.DigestUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 联通沃支付工具类
 * 
 * @author 聚合支付有限公司
 * @since 2016年11月16日
 * @version V1.0.0
 */
public class UnicomUtil {
	/** 
	 * 得到签名源数据
	 * @param params 要签名的参数数组
	 * @param key 签名密码
	 * @return 签名源字符串
	 */
	public static String getSignSourMsg(Map<String, Object> params, String key) {
		params = doFilterParam(params);
		String signSource = createLinkString(params);
		if (key != null && key.length() > 0) {//证书签名时签名密码为空
			signSource = signSource + "|key=" + key;
		}
		return signSource;

	}
	
	
	/** 
	 * 处理数组中的null值和除去非签名参数
	 * @param params 签名参数组
	 * @return 去掉空值与签名参数后的新签名参数组
	 */
	private static Map<String, Object> doFilterParam(Map<String, Object> params) {
		List<String> keys = new ArrayList<String>(params.keySet());
		Map<String, Object> newParam = new HashMap<String, Object>();

		for (int i = 0; i < keys.size(); i++) {
			String key = keys.get(i);
			String value = (String) params.get(key);
			
			if ("hmac".equalsIgnoreCase(key) || "signMsg".equalsIgnoreCase(key)
					|| "cert".equalsIgnoreCase(key)) {
				continue;
			}
			if (value == null||value.length()==0) {
				continue;
			}
			newParam.put(key, value);
		}

		return newParam;
	}
	
	/** 
	 * 把数组所有元素排序，并按照“参数=参数值”的模式用“|”字符拼接成字符串
	 * @param params 需要排序并参与字符拼接的参数组
	 * @return 拼接后字符串
	 */
	private static String createLinkString(Map<String, Object> params) {
		List<String> keys = new ArrayList<String>(params.keySet());
		Collections.sort(keys);

		StringBuffer prestr = new StringBuffer("");

		for (int i = 0; i < keys.size(); i++) {
			String key = keys.get(i);
			String value = (String) params.get(key);

			if (i == keys.size() - 1) {// 拼接时，不包括最后一个|字符
				prestr.append(key).append("=").append(value);
			} else {
				prestr.append(key).append("=").append(value).append("|");
			}
		}
		return prestr.toString();
	}
	
	@SuppressWarnings("rawtypes")
	public static String generateQueryMsg(Map<String,Object> map,String charset) throws UnsupportedEncodingException {
		StringBuilder sb = new StringBuilder();
		Iterator it = map.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pairs = (Map.Entry) it.next();
			sb.append(pairs.getKey()).append("=").append(
					URLEncoder.encode(pairs.getValue().toString(), charset)).append("&");
		}
		if (sb.length() > 0)// delete last & char
		{
			sb.setLength(sb.length() - 1);
		}
		return sb.toString();
	}
	
	/**
	 * 封装查询结果信息
	 * @param respondData
	 * @return
	 */
	public static Map<String, Object> queryRespondMap(String respondData) {
		//拆分时进行排序，以便于读取数据
		Map<String, Object> resMap = new LinkedHashMap<String, Object>();
		for (String str : respondData.split("\\n")) {
			resMap.put(str.substring(0, str.indexOf("=")), str.substring(str.indexOf("=") + 1));
		}
		return resMap;
	}

	public static void main(String[] args) {
		String key = "FN983TOPAPNQ7FA7H26BH2CVD922IOFR";
		Map<String, Object> params = new HashMap<>();
		params.put("interfaceVersion", "1.0.0.0");
		params.put("merNo", "307100710001186");
		params.put("goodId", "");
		params.put("goodsName", "鑫金涛");
		params.put("goodsDesc", "");
		params.put("orderNo", "TD20170804100522067223");
		params.put("amount", "903700");
		params.put("merUserId", "");
		params.put("mobileNo", "");
		params.put("orderDate", "20170804");
		params.put("merExtend", "TD-20170804-100522067223");
		params.put("charSet", "UTF-8");
		params.put("merchantTime", "20170804204253");
		params.put("transRst", "1");
		params.put("errDis", "立即支付,交易成功");
		params.put("payJnlno", "20170804020215644120");
		params.put("payTime", "20170805113928");
		params.put("acountDate", "20170805");
		params.put("payAcountDetail", "06:903700");
		params.put("respMode", "2");
		params.put("payProAmt", "B2CWY:903700");
		params.put("payBankCode", "B2C");
		params.put("bankAcountNo", "");
		params.put("bankAcountName", "");
		params.put("remark", "");
		params.put("signType", "MD5");

		System.out.println(UnicomUtil.getSignSourMsg(params, key));
		try {
			System.out.println(DigestUtils.md5DigestAsHex(UnicomUtil.getSignSourMsg(params, key).getBytes("utf-8")));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	
}
