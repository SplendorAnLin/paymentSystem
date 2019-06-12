package com.yl.payinterface.core.handle.impl.quick.unionPay.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.SortedMap;
import java.util.TreeMap;

import org.apache.commons.codec.binary.Base64;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lefu.commons.utils.lang.JsonUtils;
import com.yl.payinterface.core.exception.BusinessRuntimeException;
import com.yl.payinterface.core.handle.impl.quick.unionPay.bean.UnionPay120001BindCardBean;
import com.yl.payinterface.core.handle.impl.quick.unionPay.bean.UnionPay120001CommonBean;
import com.yl.payinterface.core.handle.impl.quick.unionPay.bean.UnionPay120001PayBean;
import com.yl.payinterface.core.handle.impl.quick.unionPay.bean.UnionPay120001SendVerifyCodeBean;

public class UnionPay120001Utils {

	private static final Logger logger = LoggerFactory.getLogger(UnionPay120001Utils.class);

	public static Map<String, String> getRequestMessage(String code, Map<String, String> map) {
		Properties properties = JsonUtils.toObject(String.valueOf(map.get("tradeConfigs")), new TypeReference<Properties>() {});
		UnionPay120001CommonBean commonBean = new UnionPay120001CommonBean();
		String host = properties.getProperty("tradeHost");
		String port = properties.getProperty("tradePort");

		commonBean.setDmnum(properties.getProperty("dmnum"));
		commonBean.setMsgtype(code);
		commonBean.setTrano(map.get("trano"));
		commonBean.setStan(map.get("interfaceRequestID"));
		SortedMap<String, String> signMap = null;
		if ("H199".equals(code)) {
			// 发送开卡短信
			UnionPay120001BindCardBean bean = new UnionPay120001BindCardBean(commonBean);
			bean.setName(map.get("realName"));
			bean.setPan(map.get("cardNo"));
			bean.setPhone(map.get("phone"));
			signMap = getValue(bean);
		} else if ("H198".equals(code)) {
			// 开卡
			UnionPay120001BindCardBean bean = new UnionPay120001BindCardBean(commonBean);
			bean.setName(map.get("realName"));
			bean.setPan(map.get("cardNo"));
			bean.setPhone(map.get("phone"));
			bean.setExpireDate(map.get("expireDate"));
			bean.setCvn(map.get("cvn"));
			bean.setSmsCode(map.get("smsCode"));
			signMap = getValue(bean);
		} else if ("H197".equals(code)) {
			// 发送验证码流程
			UnionPay120001SendVerifyCodeBean bean = new UnionPay120001SendVerifyCodeBean(commonBean);
			bean.setName(map.get("realName"));
			bean.setPan(map.get("cardNo"));
			bean.setPhone(map.get("phone"));
			bean.setSettletype(properties.getProperty("settletype"));
			bean.setAmount(map.get("amount"));
			signMap = getValue(bean);
		} else if ("H196".equals(code)) {
			// 支付流程
			UnionPay120001PayBean bean = new UnionPay120001PayBean(commonBean);
			bean.setAmount(map.get("amount"));
			bean.setSettletype(properties.getProperty("settletype"));
			bean.setAsynurl(properties.getProperty("noticeUrl"));
			bean.setName(map.get("realName"));
			bean.setPan(map.get("cardNo"));
			bean.setPreSerial(map.get("bankSerial"));
			bean.setSmsCode(map.get("smsCode"));
			signMap = getValue(bean);
		}
		try {
			String sign = getSignature(signMap, properties.getProperty("md5Key"));
			signMap.put("signature", sign);
			String xml = JsonUtils.toJsonString(signMap);
			logger.info("{}银联快捷请求报文：{}", code, xml);
			xml = encryptBase64(xml, "UTF-8");
			String returnMsg = connServer(xml, host, port);
			returnMsg = decipherBase64(returnMsg, "UTF-8");
			logger.info("{}银联快捷返回参数：{}", code, returnMsg);
			Map<String, String> retMap = JsonUtils.toObject(returnMsg, new TypeReference<Map<String, String>>() {});
			return retMap;
		} catch (Exception e) {
			logger.info("银联快捷报错：{}", e);
			throw new BusinessRuntimeException(e);
		}

	}

	@SuppressWarnings("rawtypes")
	public static SortedMap<String, String> getValue(Object thisObj) {
		SortedMap<String, String> map = new TreeMap<String, String>();
		Class c;
		try {
			c = Class.forName(thisObj.getClass().getName());
			Method[] m = c.getMethods();
			for (int i = 0; i < m.length; i++) {
				String method = m[i].getName();
				if (method.equals("getClass")) {
					continue;
				}
				if (method.startsWith("get")) {
					try {
						Object value = m[i].invoke(thisObj);
						if (value != null) {
							String key = method.substring(3);
							key = key.substring(0, 1).toLowerCase() + key.substring(1);
							map.put(key, value != null ? value.toString() : "");
						}
					} catch (Exception e) {
						throw new RuntimeException(e.getMessage());
					}
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
		return map;
	}

	public static String getSignature(Map<String, String> dataMap, String tmk) throws Exception {
		Map<String, String> map = new TreeMap<String, String>();
		map.putAll(dataMap);
		StringBuffer mabStr = new StringBuffer();
		for (String key : map.keySet()) { // 拼接计算签名的字符串
			if (!"signature".equals(key)) {
				mabStr.append(key);
				mabStr.append("=");
				mabStr.append(map.get(key));
				mabStr.append("&");
			}
		}
		String mab = mabStr.toString();
		mab = mab.substring(0, mab.length() - 1);
		logger.info("验签原文：{}", mab);
		return encryptMD5(mab, tmk, "UTF-8"); // MD5加密得到签名字符串
	}

	/**
	 ********************************************************* .<br>
	 * [方法] encryptMD5 <br>
	 * [描述] MD5加密-得到MD5加密字符串 <br>
	 * [参数] 要加密的字符串、密钥、编码格式 <br>
	 * [返回] String <br>
	 * [时间] 2014-11-30 下午4:07:57 <br>
	 ********************************************************* .<br>
	 */
	public static String encryptMD5(String dataStr, String tmk, String encoded) throws Exception {
		MessageDigest md5 = MessageDigest.getInstance("MD5");
		md5.update(dataStr.getBytes(encoded));
		StringBuffer result = new StringBuffer();
		byte[] temp = md5.digest(tmk.getBytes(encoded));
		for (int i = 0; i < temp.length; i++) {
			result.append(Integer.toHexString((0x000000ff & temp[i]) | 0xffffff00).substring(6));
		}
		return result.toString();
	}

	public static String decipherBase64(String dataStr, String encoded) throws Exception {
		try {
			byte[] b = null;
			b = Base64.decodeBase64(dataStr);
			return new String(b, encoded);
		} catch (Exception e) {
			throw new Exception();
		}
	}

	public static String encryptBase64(String dataStr, String encoded) throws Exception {
		try {
			byte[] b = null;
			String s = null;
			b = dataStr.getBytes(encoded);
			if (b != null) {
				s = new String(Base64.encodeBase64(b));
			}
			s = s.replace("\r", "");
			s = s.replace("\n", "");
			return s;
		} catch (Exception e) {
			throw new Exception();
		}
	}

	public static String connServer(String data, String host, String port) {
		OutputStream os = null;
		InputStream is = null;
		Socket socket = null;
		try {
			socket = new Socket(host, Integer.valueOf(port));
			socket.setSoTimeout(20000);
			os = socket.getOutputStream();
			is = socket.getInputStream();
			byte[] by = hexStr2Bytes(str2HexStr(data)); // 传入数据
			byte[] dateh = shortToBytes((short) (by.length));
			byte[] true_body = assemble(dateh, by);
			logger.info("请求原包：{}", byte2HexStr(true_body));
			os.write(true_body);
			os.flush();
			byte[] lenbtyte = new byte[4];
			is.read(lenbtyte, 0, 4);
			int resplen = Integer.parseInt(new String(lenbtyte, "UTF-8"));
			byte[] pack = new byte[resplen];
			int trueLen = is.read(pack, 0, resplen);
			while (trueLen < resplen) {
				trueLen += is.read(pack, trueLen, resplen - trueLen);
			}
			String dataStr = new String(pack, "UTF-8");
			return dataStr;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeIO(socket, is, os);
		}
		return null;
	}

	public static String str2HexStr(String str) throws UnsupportedEncodingException {
		char[] chars = "0123456789ABCDEF".toCharArray();
		StringBuilder sb = new StringBuilder("");
		byte[] bs = str.getBytes("GBK");
		int bit;
		for (int i = 0; i < bs.length; i++) {
			bit = (bs[i] & 0x0f0) >> 4;
			sb.append(chars[bit]);
			bit = bs[i] & 0x0f;
			sb.append(chars[bit]);
		}
		return sb.toString();
	}

	public static byte[] hexStr2Bytes(String src) throws Exception {
		int m = 0, n = 0;
		int l = src.length() / 2;
		byte[] ret = new byte[l];
		for (int i = 0; i < l; i++) {
			m = i * 2 + 1;
			n = m + 1;
			ret[i] = uniteBytes(src.substring(i * 2, m), src.substring(m, n));
		}
		return ret;
	}

	private static byte uniteBytes(String src0, String src1) throws Exception {
		byte b0 = Byte.decode("0x" + src0).byteValue();
		b0 = (byte) (b0 << 4);
		byte b1 = Byte.decode("0x" + src1).byteValue();
		byte ret = (byte) (b0 | b1);
		return ret;
	}

	public static byte[] shortToBytes(short val) {
		byte[] b = new byte[2];
		for (int i = 0; i < 2; i++) {
			b[i] = (byte) (val >>> (8 - i * 8));
		}
		return b;
	}

	public static byte[] assemble(byte[]... b) throws Exception {
		int length = 0;
		for (byte[] bl : b) {
			if (bl != null)
				length += bl.length;
		}
		byte[] data = new byte[length];
		int count = 0;
		for (int i = 0; i < b.length; i++) {
			if (b[i] != null) {
				System.arraycopy(b[i], 0, data, count, b[i].length);
				count += b[i].length;
			}
		}
		return data;
	}

	public static String byte2HexStr(byte[] b) throws Exception {
		String hs = "";
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = (Integer.toHexString(b[n] & 0XFF));
			hs = appendFields(hs, (stmp.length() == 1) ? appendFields("0", stmp) : stmp);
		}
		return hs.toUpperCase();
	}

	protected static void closeIO(Socket socket, InputStream input, OutputStream output) {
		try {
			if (input != null) {
				input.close();
			}
			if (output != null) {
				output.close();
			}
			if (socket != null) {
				socket.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String appendFields(Object... params) {
		StringBuffer sbf = new StringBuffer();
		for (Object str : params) {
			sbf.append(str);
		}
		return sbf.toString();
	}
	
	public static void main(String[] args) {
		Map<String, String> map = new HashMap<>();
		Properties properties = new Properties();
		properties.put("dmnum", "100004");
		properties.put("md5Key", "6E0DF145E63E3E4675F275942AD97C83");
		map.put("trano", "HF1000000002064");
		map.put("interfaceRequestID", System.currentTimeMillis() + "");
		map.put("name","孙勃洋");
		map.put("pan","6217230200003271469");
		map.put("phone","18510412233");
		map.put("tradeConfigs", JsonUtils.toJsonString(properties));
		map = getRequestMessage("H199", map);
		System.out.println(map);
	}
}
