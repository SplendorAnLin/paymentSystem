package com.yl.payinterface.core.handle.impl.wallet.cmbc584001;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.HashMap;
import java.util.Map;

import cfca.sm2.signature.SM2PrivateKey;
import cfca.sm2rsa.common.Mechanism;
import cfca.sm2rsa.common.PKIException;
import cfca.util.CertUtil;
import cfca.util.EnvelopeUtil;
import cfca.util.KeyUtil;
import cfca.util.SignatureUtil2;
import cfca.util.cipher.lib.JCrypto;
import cfca.util.cipher.lib.Session;
import cfca.x509.certificate.X509Cert;
import cfca.x509.certificate.X509CertHelper;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * 签名验签加解密工具
 * 
 * @author 聚合支付有限公司
 * @since 2017年4月28日
 * @version V1.0.0
 */
public class SignEncryptDncryptSignChk {
	
	private static String context = "{\"amount\":\"1\",\"merchantNo\":\"M01002016090000001428\","
			+ "\"merchantSeq\":\"cust0001T233504494\",\"notifyUrl\":\"http://111.205.207.103/cmbcpaydemo/NoticeServlet?name=notice\",\"orderInfo\":\"统一下单API测试-API_WXQRCODE\","
			+ "\"platformId\":\"A00002016110000000091\",\"remark\":\"\",\"selectTradeType\":\"API_WXQRCODE\","
			+ "\"transDate\":\"20161109\",\"transTime\":\"20161109233504494\"}";
	private static Session session;
	
	static {
		try {
			JCrypto.getInstance().initialize(JCrypto.JSOFT_LIB, null);
			session = JCrypto.getInstance().openSession(JCrypto.JSOFT_LIB);
		} catch (PKIException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		String sign = getSign(context);
		System.out.println("--------------------------------------");
		System.out.println("签名：");
		System.out.println(sign);

		String signContext = sign(sign, context);
		System.out.println("--------------------------------------");
		System.out.println("加密前：");
		System.out.println(signContext);

		String encryptContext = encrypt(signContext);
		System.out.println("--------------------------------------");
		System.out.println("加密后：");
		System.out.println(encryptContext);

		String resStr = "MIICaAYKKoEcz1UGAQQCA6CCAlgwggJUAgECMYGdMIGaAgECgBQsSqnMRVlKZGoGXx60IGHC4xq8PzANBgkqgRzPVQGCLQMFAARwuAC7jvMXHaXNgd++d3N+jiw0jINNSdpmWRxmrQvbYk3+WxT3kAVeW3eHxgDI7EsqpTCIASxV8c6EX9YCH9R7+WCsi/vVk0ooFBMJ/YfJyaNCzANRzrQ9Avoia5ygMQW987p6CoBK8hglOGG8tO52CDCCAa0GCiqBHM9VBgEEAgEwGwYHKoEcz1UBaAQQmm8C2muWuRfJqvXBwOnaWYCCAYDeqAk7sJZYLCtaL19Sd62+xRdMdmCHlHW3pYjogFLoLeJmNTDuo6BVKyPZ1rAL93ESeNvlElq0192uiFY4OkiedgzjPQWa2llJCx+rNzgXP9RCvi7p5oS9yd5ewNAPCHllQhDbsVliwiqM7sahNrP/FnqnYw4nvTk9+92y+SHMFaTtUU69H3DXBI4jcwzXI/l+Bf610hP0AdjaUhwe8a5JgEziw93qA2TsRSf+f2z7qpbyG8Nf/BHy6RR8v+5dwceVw8cFi3BsQchzNQjnBaq4LXinx4tYvjwVRyWHyxzxIvxCKFnZbsRczMfYwCoDB3E9dA5XU9ygUA5cFmJcSqgJNcfIg3HAnByei4y7s73xzhRe7h6zbZxRB1RVvXd7EdGHK22LqAIr1hafzLshkP3aECDQdi0de65ZiPwCGMhRPa8tRWR2Q4mddoiE7lmUoQ1pYUgep7iGuFgLMZ0iphUSi25FDXm/434tW9JaBiFaRs/nukuHhttyX0WYnKkvPjg=";
		String dncryptContext = dncrypt(resStr);
		System.out.println("--------------------------------------");
		System.out.println("解密后：");
		System.out.println(dncryptContext);

		String signChkResult = signCheck(dncryptContext);
		System.out.println("--------------------------------------");
		System.out.println("验证签名结果：");
		System.out.println(signChkResult);
	}

	/**
	 * 签名
	 * 
	 * @param sign
	 * @param context
	 * @return
	 */
	public static String sign(String sign, String context) {
		GsonBuilder builder = new GsonBuilder();
		builder.disableHtmlEscaping();
		Gson gson = builder.create();
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("sign", sign);
		paramMap.put("body", context);
		String signInfo = gson.toJson(paramMap); // 待加密字符串
		return signInfo;
	}

	/**
	 * 加密
	 * 
	 * @param signContext
	 *            需要加密的报文
	 * @return
	 */
	public static String encrypt(String signContext) {
		String certAbsPath = Config.getProperty("custCer");
		X509Cert cert = null;
		try {
			cert = X509CertHelper.parse(certAbsPath);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (PKIException e) {
			e.printStackTrace();
		}
		X509Cert[] certs = { cert };
		byte[] encryptedData = null;
		try {
			encryptedData = EnvelopeUtil.envelopeMessage(signContext.getBytes("UTF8"), Mechanism.SM4_CBC, certs);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (PKIException e) {
			e.printStackTrace();
		}
		String encodeText = null;
		try {
			encodeText = new String(encryptedData, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return encodeText;
	}

	/**
	 * 解密
	 * 
	 * @param encryptContext
	 *            需要解密的报文
	 * @return
	 */
	public static String dncrypt(String encryptContext) {
		String priKeyAbsPath = Config.getProperty("custKey");
		String priKeyPWD = Config.getProperty("custKeyPwd");
		String decodeText = null;
		try {
			PrivateKey priKey = KeyUtil.getPrivateKeyFromSM2(priKeyAbsPath, priKeyPWD);
			X509Cert cert = CertUtil.getCertFromSM2(priKeyAbsPath);
			byte[] sourceData = EnvelopeUtil.openEvelopedMessage(encryptContext.getBytes("UTF8"), priKey, cert, session);
			decodeText = new String(sourceData, "UTF8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return decodeText;
	}

	/**
	 * 验证签名
	 * 
	 * @param dncryptContext
	 *            需要验证签名的明文
	 * @return
	 */
	public static String signCheck(String dncryptContext) {
		String certAbsPath = Config.getProperty("cmbc_cer");
		Gson gson = new Gson();
		@SuppressWarnings("unchecked")
		Map<String, Object> paraMap = gson.fromJson(dncryptContext, Map.class);
		String sign = paraMap.get("sign").toString();
		String body = paraMap.get("body").toString();
		boolean isSignOK = false;
		try {
			X509Cert cert = X509CertHelper.parse(certAbsPath);
			PublicKey pubKey = cert.getPublicKey();
			isSignOK = new SignatureUtil2().p1VerifyMessage(Mechanism.SM3_SM2, body.getBytes("UTF8"),
					sign.getBytes(), pubKey, session);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (isSignOK) {
			return "验签通过";
		} else {
			return "验签不通过";
		}
	}

	public static String getSign(String context) {
		String priKeyAbsPath = Config.getProperty("custKey");
		String priKeyPWD = Config.getProperty("custKeyPwd");
		String sign = "";
		try {
			JCrypto.getInstance().initialize(JCrypto.JSOFT_LIB, null);
			Session session = JCrypto.getInstance().openSession(JCrypto.JSOFT_LIB);
			SM2PrivateKey priKey = KeyUtil.getPrivateKeyFromSM2(priKeyAbsPath, priKeyPWD);
			sign = new String(
					new SignatureUtil2().p1SignMessage(Mechanism.SM3_SM2, context.getBytes("UTF8"), priKey, session));
		} catch (PKIException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return sign;
	}
}
