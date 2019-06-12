package  com.yl.pay.pos.util;

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
		//urlConnection.setRequestProperty("Content-Type", "text/html");

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
	
	public static void main(String[] args) {
		try {
			String respCode = sendReq("http://101.37.168.236:8082/D0Interface/payOther",
					"refNo=164551145394&custName=5rWL6K+V&custCardNo=123456&txnAmt=1193&cardType=BUSINESS&issno=ICBC&casOrdNo=164551145394&notifyUrl=http://111.172.254.19:8087/trans-core/proxyPay/callback.action&mercNo=896420157021880&signCode=CF4C166417818576B0B2DB2A5D7C436B",
					"POST");
			System.out.println(respCode);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
