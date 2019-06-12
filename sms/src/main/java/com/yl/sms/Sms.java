package com.yl.sms;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

/**
 * 短信http接口
 *
 * @author AnLin
 * @version V1.0.0
 * @since 2018/8/2
 */
public class Sms {

    public static void main(String[] args) throws IOException, URISyntaxException {
        System.out.println(URLEncoder.encode("PHJlc3BvbnNlPjxlbnZlbG9wZT48aGVhZD48dmVyc2lvbj52MS4wLjcuNjwvdmVyc2lvbj48Y2hhcnNldD5VVEYtODwvY2hhcnNldD48L2hlYWQ+PGJvZHk+PHJlc3BvbnNlQ29kZT5FMDAwMDwvcmVzcG9uc2VDb2RlPjxvcmdPcmRlck5vPjIwMTgwODA4MDkxMDA0OTU3OTwvb3JnT3JkZXJObz48b3JkZXJObz5URDIwMTgwODA4MTAxNTkyNTU5NzkxPC9vcmRlck5vPjxzdGF0dXM+VFhfU1VDQ0VTUzwvc3RhdHVzPjxvcmRlckFtb3VudD4yMTUwMDAwPC9vcmRlckFtb3VudD48YWNjb3VudFR5cGU+UEVSU09OQUw8L2FjY291bnRUeXBlPjxtZXJjaGFudElkPjEwNTg0MDE4MDUxMDAwMzwvbWVyY2hhbnRJZD48b3JkZXJEYXRldGltZT4yMDE4MDgwODA5MTAwNDwvb3JkZXJEYXRldGltZT48c3RhdHVzRGVzYz7kuqTmmJPmiJDlip88L3N0YXR1c0Rlc2M+PC9ib2R5PjwvZW52ZWxvcGU+PHNpZ24+PHNpZ25UeXBlPjE8L3NpZ25UeXBlPjxjZXJ0aWZpY2F0ZT48L2NlcnRpZmljYXRlPjxzaWduQ29udGVudD5RVXF4SlBBSGJqWlBwaVVhK2VMMTBxcVhIZVpNazRRZjZEdmNJS0VDV2pYRm1yNXZueW5XNlR1aTJsNHk1MVZPbzB5SmxKWnUwVmx2aGFSTERPY0xwbHhMMG1lTkZpc0JDOFFyTXZYTjMzbVR6N1d1VVhDMGJVVXNVU042OS9LQWFTelprNkV3NHdsNWpsbVhVNnN2U3pUeG1yRUpyQnBJeU1tdHlQUkpkYWwySmcwVlJwRktjZVgwNDJZTnI3aEt3dTVxS3pzVVBTSGN2dHJYb3hTMVlTUUplTVVCM2FiQUpYdzE1LzlqekhsUldsNnJJczJ1RWx4S3M2Y3Q0V3Rwakhta3c2N1MzaU9WeER5bzNJUDBYaEJhbUMrYWJaaTNTLytZLzdsc2gzSGlwaVd4aU5Pa3c3RHAxLzdkUU1XSm16MTlLQ1FPNUNKcW1iNDZyemdPN1E9PTwvc2lnbkNvbnRlbnQ+PC9zaWduPjwvcmVzcG9uc2U+", "UTF-8"));
//        System.out.println(qdSendSas("【秒付网络】尊敬的：[测试用户]您好，您的服务商已开通，请登录pay.feiyijj.com/agent进行自助服务，您的登录帐号是：[17607114151]，密码是：[6489dasd3213]，提现密码是：[6489dasd3213]。", "17607114151"));
    }


    /**
     * 起点短信发送
     * @param text
     * @param mobile
     * @return
     */
    public static String qdSendSas(String text, String mobile){
        Map<String, String> params = new HashMap<>();
        params.put("Uname", "bf23456");
        params.put("Upass", "44ab2afde1db696687709d9546baec02");
        params.put("Mobile", mobile);
        params.put("Content", text);
        System.out.println(params.toString());
        return post("http://open.96xun.com/Api/SendSms", params);
    }

    /**
     * 基于HttpClient 4.3的通用POST方法
     *
     * @param url       提交的URL
     * @param paramsMap 提交<参数，值>Map
     * @return 提交响应
     */

    public static String post(String url, Map<String, String> paramsMap) {
        CloseableHttpClient client = HttpClients.createDefault();
        String responseText = "";
        CloseableHttpResponse response = null;
        try {
            HttpPost method = new HttpPost(url);
            if (paramsMap != null) {
                List<NameValuePair> paramList = new ArrayList<NameValuePair>();
                for (Map.Entry<String, String> param : paramsMap.entrySet()) {
                    NameValuePair pair = new BasicNameValuePair(param.getKey(), param.getValue());
                    paramList.add(pair);
                }
                method.setEntity(new UrlEncodedFormEntity(paramList, "UTF-8"));
            }
            response = client.execute(method);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                responseText = EntityUtils.toString(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                response.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return responseText;
    }
}

