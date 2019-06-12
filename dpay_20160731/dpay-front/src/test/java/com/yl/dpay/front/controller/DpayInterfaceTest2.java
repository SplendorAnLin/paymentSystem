package com.yl.dpay.front.controller;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import com.lefu.commons.utils.lang.JsonUtils;

/**
 * 代付接口测试
 * 
 * @author 聚合支付有限公司
 * @since 2016年5月22日
 * @version V1.0.0
 */
public class DpayInterfaceTest2 {

	public final static String password = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCEy79cRTBuL/YJo4ZZrkJbzroVqhN4RpoBliBT\r\npubFccppVol/pgf6BQbf8dbve0zreL4exfT9OpsD840xUB5nPBFc+a60U2rtqWJLbyAoWaUZTV4w\r\n57I7uKbMZtD1UdTvNhUgxQ+z6PSWkV+rSt+wrLRAyOl0oCw1mwz1+IP4NQIDAQAB";
	static String prk = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBALLdUksO0L4fgbfLjZEh89kSfydX\r\n"
			+"qDbARi24ktUAuEh7+pivK+QBdUtrUkUThQgEBbbDGmRivqiS3MtODDanuko5hJi/Tr0it8RIiT5f\r\n"
			+"/T39XPEtGLqPv8k21B7IX/IBSt0dKAXYt3Raq+3bhZdCvEueWd6D1w5vHp4nUHnDXLDvAgMBAAEC\r\n"
			+"gYEAo1zTOayJaOx73LN0VDcPcZacPgGoqIEKV935dcQXjFR3+BYd3zyZtc2KsX53hW+vDFK7+y3Y\r\n"
			+"9zxTnvhk3VJqKLC7CrAaQPX6/GwCGR4CNYiDdox34XRJe0eyCXujHmhuD6CNOkBvF2w1kNv1K5BF\r\n"
			+"GCjsHy6Z1x1x0spZAiFm2RECQQDzDSR3bO3j9EC4JuJQYZBdf9CUWQZHHIvuIYT80SSzl+X6w+p3\r\n"
			+"57yBkecvPU49Xr4tx3vBg51rZftLFCqAj2FZAkEAvGTETwvKetC6SmzmyxU3OIWjvhr6Hq+rd8wf\r\n"
			+"0gwqQ1Nglyz0EizzNacoakgf8Jk1ZW8FHTjRuL+GcslDqiTThwJBALO/wHxiHv07IrIOb8kqnm3H\r\n"
			+"nSZZQH9O6V3PhF7E/fOkHv157umGhK7+jI8vM7HHY7bPlQLkp4NKBLLd0yEI6OECQHu2/IcgKhZe\r\n"
			+"zYckXqcMRpgSgoXLt2bBW6uu21KXdIWD0lFUTu9En24jWFH7DSZ1pe/3aPdYobhd5Y+phYrM5i8C\r\n"
			+"QAQ33JiBdpVnc8EIiNufWd+Ae6ZHREKAz87hkiIsV1MtRhn0LRS2RUxW3Hih+lmyWNvoYLdZ7jIl\r\n"
			+"SKrhwOvCX4o=";
	static String puk = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCy3VJLDtC+H4G3y42RIfPZEn8nV6g2wEYtuJLV\r\nALhIe/qYryvkAXVLa1JFE4UIBAW2wxpkYr6oktzLTgw2p7pKOYSYv069IrfESIk+X/09/VzxLRi6\r\nj7/JNtQeyF/yAUrdHSgF2Ld0Wqvt24WXQrxLnlneg9cObx6eJ1B5w1yw7wIDAQAB";
	//public final static String password = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDBxAlzWzffrEfzNCSnxbpdIaUa4CsZw4vY9YJG\r\nzyRo9CZV34IGctt5J2BctfCT1nIc15zFgji14shWw8RmEk+bku5clBk6wBudXrs0yJmBku37EoPj\r\nk4f/L/N4rNgrv695yaZQ2et2ngEkbnw8Uux8o9MQAziiaJ6OrZbQdBBsCQIDAQAB";
	
	
	public static void main(String[] args) throws Exception {

		dpay();

	}

	public static void dpay() throws Exception {
		Map<String,String> map = new HashMap<>();
		String plainData = "af2afda196b524c2551cc069348b7df3647c86fd3eb5b9b994aed70308e715aa27c508ee9f8ee46cd21ce311cee99ff0bd3491f2d3cf5cb2eddf9f4801e7774a967397a412ba97e8deee5b7370ffaca91acb548d6d0e1587b0570dc1cf88ef33af48f4b7534da995f111533fa5ac9b806d54ecf1236d76fef6fef008232d1be7b9c9b8a43cf91b8d323cefb1d1edafae025b174fa04f903f062f5bd2290efd0d19c40acd4f8b369171290014f248fd6cfd26335aacc5941b8b0abeffd1cf07b9f67f9b1840fb1ffcb5d20cae7ff00c31543c15994faff25d0cf20aa515b8f5c09a6dca2e78b860543aa8ef9f0b158400c9f4798f95c79a5522a318820956d419b746aef9b08936e99ea52a44e8e3d4f5d02127e614161739e1b13ebbdeb52315012218f918d78277a1d8a8ffd6ded30fb0df64851b3543e7fe73a6b4f4c9da1f19d617319ec0d1729406e99c0f8463c2ee4993c063dfdb59228bbdef09ab78e4ecfdebe08085b595e6f359b8e90ad42ef9808abb898da5ecf1e090f9dc7e74295fc19ce51e5baf185f62de23b09250fc4d21702ac59bbdf0af788f0ce4faf69313fc275f7cd55a724325830f037f2a05f3bfc636f9429a9a963c635b7cf7c6e68326dcfff93027f89eb3e081a5dab05fa3d1c85de074ea2f7daaa4873ba71c786c20d8b9e3246d7c882056952e17db3d96ad63ca39010134cc1898c719cab4beb34a726edf919ba9ea32d00ad9d8651fcd7fa88b3b7570a09995279b25f756c7f3c1ef9ba2ec5abcde70f356f1a386f71ccd46015b43b80b4384db5b46b92402310f60cdb0d9481349852e6eb3d0d562776f9c486b81185267b8f27f37255882d2bad78dccde534053ab60439db24f9295f1dc44730ddad9356bf7379051b9d85325dd442aef698644d7d5ec4f03010a7851ff71b9d93d146530d5525f705c95ff9e0e0454701a528973281a3281d55901f538b49f4d858732e2c752b17a6314c3b4de32af2b203f8644009b757c136adb13f7c061c0a20009c40c5a80b35cbec52dc6150a44acd4f0b2a280e65fe805c1abe872711e72cf4f0a8880225117b3639b68d1d4bd8d217af7e2ffb66e1670d78c46d63501740f0d7554fe87c2c63e13e79628c85d9724fe39951f12ee1408a1592a9fd4b71c2d34289840e55c2da607bfc8e6f034c00333e7c1002a073b6a780c3cc9ca4d0fa7210922d6a8f9f2ad9e7439fc67a24f656afe8f83e69b5316d2c5eeab698b97a077395279ae8ac2cc66a67c6e62dbe0b76c2dde72bdd6894de511b3be83014290518992dd539ab85ad96d4bb74c4771e667e3ba4638c9c1f8ef0d6a312c5a01c9dd0738bc8b8d40b594bf42e71b2894dc7ac2936bc592f22233e8de4582e18f945e06f8167206076d46cfedaea29ed1fad8af34b7bc84ec1454360aee161d9960e85d430dc2615be3c78ad8011f818b7a7e43a0a122282cc735131c32cec8e819690bd115d61b1b562303bb1e23d98925c9aaf206de35e268847d537fe5422dcb7a14172c7fbbbbd68ea64964fd113327caf82511203a6eef4863708555e9de0f0ca9fd456bb01ad308fc15dad56396fca61bfcc081c18c686c15e1b62b3da3d42a01f1b46a5100bad684c9ae3fc0e1ff90999c72485ec631960a5269bc24abb97117cb57647117ff422d20ace6c315925aa6bd017c863a4d90463957475212336ea5a01da8d35d8de739568f9ba1b1dfae7d5bc54fdb0592affab40f84de6cbc8c5bdb5a96d67856a8a2ebe9085c374438706d3c32c6336e78f1faa6020a8da546dc18fe96c20723d8ddf4525060df570a03ab9b3c6172158793c1726cc70618304ba21a85f16a473d9c1a96c43d75bdce8178f06c4959feb742bbc04445adc4de0c00316cb189ff0e737971e4cc03fb9a18e776b3b61430417408d30e3920cea82bb6544209dd84bc1f2aad60c6c3f01314f245e37e10ab2ce6838154a2968b55b08994f8a1fa59f57eb86eab3e5adef7b076c1b5225f446bc1315cf755668e83c27c3d0c6ea602dc3e89820a89b5928e7c445516ff746ac36c902b535eca93eb5a39c56c609a904c24cca4cb4975781f5077198d82099f5aa517040f5d6469d90294f5cc0614cde5a2c8a67f52823feb4750a63b7f45153adfbd57f05478aa285b7c08e5e1c091c5465bb13a37c0e92d7f7a25193f1357147a4c084b75bc8bc41ea7adb90d3efc41b75f58cacf383abd7669185fc565d4b80ec9098a83a2a6e9b19a2720560ce885a326015e8ac1247cc07cc5a2c8900cd52dc0358f4024f8b6fde3d2d0bdc94a2bab07b4cbfefe1b2e4454a7cef387c66a21cc7e7a0d15c6c22da9c7d2b154a8e85d4de53b32842c51b999d2f7c6f0ceaf20e82d8b45b240";
		String key = "BD161A60C8933E7EC1D1B802376D6245";
		map.put("key", key);
		map.put("data", plainData);
		String res = sendReq(JsonUtils.toJsonString(map), "http://183.3.223.28:8011/encrypt/des3Decrypt");
		System.out.println(res);
	}

	public static String sendReq(String reqJson, String url) throws Exception {

		java.net.HttpURLConnection urlConnection = null;
		BufferedOutputStream out;
		StringBuffer respContent = new StringBuffer();

		java.net.URL aURL = new java.net.URL(url);
		urlConnection = (java.net.HttpURLConnection) aURL.openConnection();

		urlConnection.setRequestMethod("POST");

		urlConnection.setDoInput(true);
		urlConnection.setDoOutput(true);
		urlConnection.setUseCaches(false);

		urlConnection.setRequestProperty("Connection", "close");
		urlConnection.setRequestProperty("Content-Length", String.valueOf(reqJson.getBytes("UTF-8").length));
		urlConnection.setRequestProperty("Content-Type", "text/html");

		urlConnection.setConnectTimeout(60000);
		urlConnection.setReadTimeout(60000);

		out = new BufferedOutputStream(urlConnection.getOutputStream());

		out.write(reqJson.getBytes("UTF-8"));
		out.flush();
		out.close();
		int responseCode = urlConnection.getResponseCode();

		if (responseCode != 200) {
			throw new Exception("请求失败");
		}

		BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "utf-8"));
		String line;

		while ((line = reader.readLine()) != null) {
			respContent.append(line);
		}

		urlConnection.disconnect();
		return respContent.toString();
	}

}
