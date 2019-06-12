import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.lefu.commons.utils.web.HttpClientUtils;
import com.lefu.commons.utils.web.HttpClientUtils.Method;

/**
 * Simple测试
 * 
 * @author 聚合支付有限公司
 * @since 2016年4月24日
 * @version V1.0.0
 */
public class SimpleTest {

	public static void main(String[] args) {

		// =========== 账户信息查询 ==========
		// Map<String, String> params = new HashMap<String, String>();
		// params.put("accountNo", "203631388317");
		// try {
		// String responseMsg = HttpClientUtils.send(Method.GET, "http://10.10.110.57:6062/account-front/accountFront/accountInfo.htm", params, false, "UTF-8",
		// 5000);
		// System.out.println("账务信息查询接口返回信息：{}" + responseMsg);
		// } catch (IOException e) {
		// e.printStackTrace();
		// }

		// ========== 入账 ========

		String accountTally = "{\"systemCode\":\"1001\",\"systemFlowId\":\"-ac3b-8b47879d10a6\",\"bussinessCode\":\"ONLINE_CREDIT\",\"requestTime\":1418013375599,\"tradeVoucher\":{\"tallyVouchers\":[{\"accountNo\":\"203631388317\",\"currency\":\"RMB\",\"transOrder\":\"aa-a386-bf4a1d17d2d3\",\"transDate\":1418013375780,\"amount\":110.0,\"symbol\":\"PLUS\",\"fee\":10.0,\"feeSymbol\":\"SUBTRACT\",\"waitAccountDate\":1418013375780}]},\"operator\":\"JOSE\",\"remark\":\"\"}";

		Map<String, String> params = new HashMap<String, String>();
		params.put("tallyJson", accountTally);
		try {
			String responseMsg = HttpClientUtils.send(Method.POST, "http://10.10.110.57:6062/account-front/accountFront/accountTally.htm", params, false, "UTF-8",
					5000);
			System.out.println("账务信息查询接口返回信息：{}" + responseMsg);
		} catch (IOException e) {
			e.printStackTrace();
		}
		// String uri = "http://10.10.110.57:6062/account-front/accountFront/accountInfo.htm";
		// Map<String, String> params = new HashMap<String, String>();
		// params.put("payData",
		// "NUogGO6VVhB4cUi6QK1aYdn/9+wnTcVziKQs7KhZN7eumGgUazeJb3aR/AB6OijGpsu4fpgdPG2XmDVQ+ve5iirCh0CUGsEXG9u5di9yZ6ockcrvNyrKZiujWhlgJXuG1eoVOfeXb56n2ZL07a2CTQCzmhfA52/Ihto0x6pNu5sKFn0lkP8pyAtwYfg8kMjZzyp+c4o8SmIbuRZPmPemxoroSpv1f+Oc6opHBXI2VCAjZYqAJI7xH9+2hpNMVbTsPhGyYXk3WZXN8PYzbDwDJjxSl9MnEwrx9XCqhLQe1wjPWbT8t0Ha8I4G0WrYmdODMvfiyCZdUOzqXYJ0MVJM1w==");
		// params.put("sign",
		// "DOdsuqHXHMK/D7p8FRs9fnsH03dLyfe+tGb+QXJYMK9oXi0weIDokNNBKnlXQ8uQeqs/R7UwTpa2iPznSylpXjr9gnqcdwoW1sbnsqwxKCNEdP7T7LI1NqkBzXfcHDL5SnDjTVdUgA8IdBDh/fUjHFwA1pyB/zwJkexuNYGLyHXHP4Djdh4Y/mZsg3c1i8VIZvxlalp7Kbv9cf/oXjN/Vhqni9K2oaK4ymrI0qnen2wOKPzLkNzH9Ggnkz9FOTb02H8gZ5jht7al5HXhO7i9l4ZJpB/egpkIdCCc/dFoOk8CHAUZNHUGaKp0nWpwUOhlTBLukQWi5v2/F4TVHwsktg==");
		// try {
		// HttpClientUtils.send(Method.POST, uri, params, true, "utf-8");
		// } catch (IOException e) {
		// e.printStackTrace();
		// }
	}
}
