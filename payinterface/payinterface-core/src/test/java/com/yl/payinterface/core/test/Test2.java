package com.yl.payinterface.core.test;

import java.util.HashMap;
import java.util.Map;

import cfca.sm2rsa.common.Mechanism;
import cfca.sm2rsa.common.PKIException;
import cfca.util.Base64;
import cfca.util.CertUtil;
import cfca.util.EnvelopeUtil;
import cfca.util.KeyUtil;
import cfca.util.SignatureUtil2;
import cfca.util.cipher.lib.JCrypto;
import cfca.util.cipher.lib.Session;
import cfca.x509.certificate.X509Cert;
import cfca.x509.certificate.X509CertHelper;

import com.cmbc.unps.util.Constants;
import com.cmbc.unps.util.Environment;
import com.cmbc.unps.util.SM2Utils;
import com.cmbc.unps.util.SecureRuleFactory;
import com.cmbc.unps.util.exception.PlatformException;
import com.cmbc.unps.util.security.ISecuRule;
import com.google.gson.Gson;

/**
 * 民生公网接口解析密文的test类：异步通知
 * 
 * @author 聚合支付有限公司
 * @since 2017年2月19日
 * @version V1.0.0
 */
public class Test2 {
	
public static void main(String[] args) {
	Map<String, Object> respMap  = new HashMap<String, Object>(); 
	Map<String, Object> respMap2  = new HashMap<String, Object>(); 
	respMap.put("merchantSeq", "1800000100000277TD-20170214-1002674059370000000000");
	respMap.put("version", "v1.0");
	respMap.put("transDate", "20170214");
	respMap.put("source", "unpsgate");
	respMap.put("securityType", "SM203");
	respMap.put("transTime", "225903");
	respMap.put("merchantNum", "1800000100000277");
	respMap.put("transCode", "PAY_T005");
	respMap2.put("custsm2path", "D:/Youxun/cmbc.sm2");
	respMap2.put("custsm2pass", "123456");
	respMap2.put("cmbccertpath", "D:/Youxun/cmbc.cer");
	respMap2.put("custcertpass", "D:/Youxun/yl.cer");

	
	
	respMap.put("businessContext", "TUlJQ21BWUtLb0VjejFVR0FRUUNBNkNDQW9nd2dnS0VBZ0VDTVlHZE1JR2FBZ0VDZ0JUWmw4SVBhSWd5VDZyVW9XQnVtYStXNklhYnpqQU5CZ2txZ1J6UFZRR0NMUU1GQUFSdzhHNm8ycGRVS3lZNFpuVG1pRGp1UzZ6by85bHBHTzFkeCt2SE12S0F1c3JTZkFReVl4dmE1eHJqT2xHZW5vd1FxbUJ3S3JkMGp3bVBpYTNyN2ZlWmdBZmhHRy9Xd0psL1dxQUxqckUzWmdLZ0tTOVFUMnBSYUoxcXB2NHdDWlFMQ2twellvd2lBZXg2R2IwcmUyLzg0VENDQWQwR0NpcUJITTlWQmdFRUFnRXdHd1lIS29FY3oxVUJhQVFReVRmVjZ2YmErYWRDR2oxWlZ3Zk1DWUNDQWJCTnhwaHZoTXVnby9wRmtlcW9COHVsbmc4eGoyc215bEcvZWh3K1RBdXVPcTJaTHNBT2xIc2kzUUg4SzNBMWRYMnpJaTFIS2xxU0JBMnFYZnYvRS9xS3k4UFRBY2tJRmlNMEhETjJaZ3VhdldwS0hVaHl6d3d3bmp0Q1RiaTR1VWlyaFpncjBGRVhCZjlGRFBuUU5TZG5CMG1nRzhmbzlkQ3l1UnE3NDU4d3d2aTRaRm5sMFYvTDJ3Y3BlOEhIWVhjYnNvTHJMU1BObHBCWUcvbVU1MEtQbG0xb251QSt0UnowRUNyWFY3Qk5iSk5rSHRvM2ZWOTVaRlJVbThFRUd5NWRUOUJyYUVOMHBHVkIwUDY4U3lVSE40UURIcVhNa2Q1dlByL2JGSnFHQ3lHcUV1UDJPV2dvTkJ4SGlUamp6eGFHRDE4MTQyRDFJSEY1TG9xaGxmVkRqTmd5aGJaN1FpcVBra0w3YWZLYUlRWkpRWUdDa09keFlYNDFxYnVuaXdFNkplcXF4ZWRHakVnQVhtbEkyRityMWM1aWJ3aHZ2M3FMblNRRjQxb28zK2V0dzNTaTV0M3JnWWZtN2c4WFNnMG9aTXpNRlJkdmIzOEtoZEV2UE0xMGovRnV1aHZ6UGlIbXhCUk5obWlTSkV6aFJ6TXhLK2FyOTZ2Z01TSUFkckRuMTNnbjdhQzZXWE5wU2w4ZDl2SUtEUlNHRkZZY1pkeHdEdFF5Wlk1VmtrK0FhTlREcmFlc1NhekR0QSt3RStNPQ==");
	ISecuRule secuRule = SecureRuleFactory.getSecureRule("1800000100000277", "SM203");
	
	Map<String, Object> decResultMap = secuRule.decMsg(respMap,respMap2);
	System.out.println(decResultMap);
}

}
