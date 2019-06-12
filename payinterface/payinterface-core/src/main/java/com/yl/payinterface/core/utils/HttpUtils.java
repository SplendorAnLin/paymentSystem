package com.yl.payinterface.core.utils;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * http工具类
 * 
 * @author 聚合支付有限公司
 * @since 2016年10月22日
 * @version V1.0.0
 */
public class HttpUtils {

	public static String sendReq(String url, String data, String menthod)
			throws Exception {

		java.net.HttpURLConnection urlConnection = null;
		BufferedOutputStream out;
		StringBuffer respContent = new StringBuffer();

		java.net.URL aURL = new java.net.URL(url);
		urlConnection = (java.net.HttpURLConnection) aURL.openConnection();

		urlConnection.setRequestMethod(menthod);

		urlConnection.setDoInput(true);
		urlConnection.setDoOutput(true);
		urlConnection.setUseCaches(false);

		urlConnection.setRequestProperty("Connection", "close");
		urlConnection.setRequestProperty("Content-Length",
				String.valueOf(data.getBytes("UTF-8").length));
		urlConnection.setRequestProperty("Content-Type", "text/html");

		urlConnection.setConnectTimeout(60000);
		urlConnection.setReadTimeout(60000);

		out = new BufferedOutputStream(urlConnection.getOutputStream());

		out.write(data.getBytes("UTF-8"));
		out.flush();
		out.close();
		int responseCode = urlConnection.getResponseCode();

		if (responseCode != 200) {
			throw new Exception("请求失败");
		}

		BufferedReader reader = new BufferedReader(new InputStreamReader(
				urlConnection.getInputStream(), "utf-8"));
		String line;

		while ((line = reader.readLine()) != null) {
			respContent.append(line);
		}

		urlConnection.disconnect();
		return respContent.toString();
	}

	public static String getWebIp() {
		try {
			URL url = new URL("http://iframe.ip138.com/ip2city.asp");
			BufferedReader br = new BufferedReader(new InputStreamReader(
					url.openStream()));
			String s = "";
			StringBuffer sb = new StringBuffer("");
			String webContent = "";
			while ((s = br.readLine()) != null) {
				sb.append(s + "\r\n");
			}
			br.close();
			webContent = sb.toString();
			int start = webContent.indexOf("[") + 1;
			int end = webContent.indexOf("]");
			webContent = webContent.substring(start, end);
			return webContent;
		} catch (Exception e) {
			e.printStackTrace();
			return "error";

		}
	}

	public static String sendReq2(String url, String xml, String menthod) throws Exception {

		java.net.HttpURLConnection urlConnection = null;
		BufferedOutputStream out;
		StringBuffer respContent = new StringBuffer();

		java.net.URL aURL = new java.net.URL(url);
		urlConnection = (java.net.HttpURLConnection) aURL.openConnection();

		urlConnection.setRequestMethod(menthod);

		urlConnection.setDoInput(true);
		urlConnection.setDoOutput(true);
		urlConnection.setUseCaches(false);

		urlConnection.setRequestProperty("Connection", "close");
		urlConnection.setRequestProperty("Content-Length", String.valueOf(xml.getBytes("UTF-8").length));
		// urlConnection.setRequestProperty("Content-Type", "text/html");
		

		urlConnection.setConnectTimeout(60000);
		urlConnection.setReadTimeout(60000);

		out = new BufferedOutputStream(urlConnection.getOutputStream());

		out.write(xml.getBytes("UTF-8"));
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
			respContent.append("\r\n");
		}

		urlConnection.disconnect();
		return respContent.toString();
	}
	
	public static String sendReq3(String url, String xml, String menthod, String charsetName) throws Exception {

		java.net.HttpURLConnection urlConnection = null;
		BufferedOutputStream out;
		StringBuffer respContent = new StringBuffer();

		java.net.URL aURL = new java.net.URL(url);
		urlConnection = (java.net.HttpURLConnection) aURL.openConnection();

		urlConnection.setRequestMethod(menthod);

		urlConnection.setDoInput(true);
		urlConnection.setDoOutput(true);
		urlConnection.setUseCaches(false);

		urlConnection.setRequestProperty("Connection", "close");
		urlConnection.setRequestProperty("Content-Length", String.valueOf(xml.getBytes(charsetName).length));
		// urlConnection.setRequestProperty("Content-Type", "text/html");
		

		urlConnection.setConnectTimeout(60000);
		urlConnection.setReadTimeout(60000);

		out = new BufferedOutputStream(urlConnection.getOutputStream());

		out.write(xml.getBytes(charsetName));
		out.flush();
		out.close();
		int responseCode = urlConnection.getResponseCode();
		if (responseCode == 302) {
			String locationUrl = urlConnection.getHeaderField("Location"); 
			return sendReq3(locationUrl, xml, menthod, charsetName);
		} else if (responseCode != 200) {
			throw new Exception("请求失败");
		}

		BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), charsetName));
		String line;

		while ((line = reader.readLine()) != null) {
			respContent.append(line);
			respContent.append("\r\n");
		}

		urlConnection.disconnect();
		return respContent.toString();
	}
	
	public static void main(String[] args) {
		try {
			sendReq("http://127.0.0.1:8080/payinterface-front/kingPassB2cFrtNotify/completePay",
					"signType=RSA256&charset=00&serverSign=821CCC5E973E0DE6A9CC1766E4B2D85606656AE6B85451CAB5C6072A56FFAB78A23F1DACCB338936A5BB39A1548113942804F0B27E5DAA363C2BABB5BD69931D66D56B8935A343D1C5915A2887FB10167A2F51D10C1633DE89A063AF214CF80806FD70B44B53EA0FB8FC805E5856EDB19B30AF6BE8F63D54865ECB0609ACFC74&orderTime=20170809165207&orderSts=PD&payTime=20170809165407&acDate=20170809&bankAbbr=CCB&version=1.0&fee=0&amount=2&serverCert=308203653082024DA00302010202081BF6DEF10C56066B300D06092A864886F70D0101050500305B310B300906035504061302636E310B300906035504080C02626A3110300E06035504070C076265696A696E67310F300D060355040A0C064D75726F6E67310B3009060355040B0C024954310F300D06035504030C06726F6F744341301E170D3135303433303032313730335A170D3435303433303032313730335A305A310B300906035504061302636E310B300906035504080C02626A3110300E06035504070C076265696A696E67310F300D060355040A0C064D75726F6E67310B3009060355040B0C024954310E300C06035504030C05506179434130819F300D06092A864886F70D010101050003818D0030818902818100C760A3664325DBB3E7BCAAC41A9EB877A140C569762D0ECFFF388CAE1C6E1578534F3C9F6FA0B4F2BE9DBFA8AF3AAEE973DA2C9A7909FBE2790565FA9E6F1722C2F092363A40970F3B12EA35B174FAA1F1C4835DEA7DC9BDD601A34D5AF9F6A189A9E1C874F55974B33344D42B32260A364FCA759000AADF429CC09357C165790203010001A381B13081AE301D0603551D0E04160414DE7B316C2C93E2F4081D0241200578431985776230818C0603551D230481843081818014829BBF4208CC0675279B975A4D2276FBC8556467A15FA45D305B310B300906035504061302636E310B300906035504080C02626A3110300E06035504070C076265696A696E67310F300D060355040A0C064D75726F6E67310B3009060355040B0C024954310F300D06035504030C06726F6F74434182081BF6DEF10BFAB25D300D06092A864886F70D010105050003820101004BEBB7FDFC6BE8AC5DD8C3778222623801F5487C1819344870540A27D54C34951D2ED225206DCFE7097526E83DC33165381378841A9588CC2970CEC7B5D145D003322EF300DE67DF75A21C878FFA41A1AD76E2794129F4DF887B80CE1E985032CE042AEAB8F61267DDFBE06E568271B7ADBE806992BA529DE9664EB1EA16EB446BC47036E43A18F23A79B547A1963C6921DE7C38BC9D1EF15B3687EC9DDCB32AD89DAC2A15294DF8467E17C53AD5FFCC18CCDBA87EF3B23AB5DBD795CB9258E8E1258AA1F256084FB74F2969A2467F56ABD539D4DB93E65C21C354769A8DB68196BBFFECDA63BCDAEC8051F86E7825B404BB1F31901D37A65620A5111742D775&payType=B2C&merchantId=800001702700003&memberId=100161886838&orderId=TDX20170809X101600360845",
					"POST");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
