package com.yl.payinterface.core.handle.impl.remit.zhongmao100001;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.yl.payinterface.core.handle.impl.remit.zhongmao100001.utils.SSLClient;
import com.yl.payinterface.core.handle.impl.remit.zhongmao100001.utils.SignUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

/**
 * 代付
 * @author yqh @date 2017年4月10日
 */
public class AgentPayment {
	public static void main(String[] args) throws Exception {
        DefaultHttpClient httpClient = new SSLClient();
        HttpPost postMethod = new HttpPost("https://cqlsytest.cmbc.com.cn/payment-gate-web/gateway/api/backTransReq");
        List<BasicNameValuePair> nvps = new ArrayList<BasicNameValuePair>();
        nvps.add(new BasicNameValuePair("requestNo", new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date())));
        nvps.add(new BasicNameValuePair("version", "V1.0"));
        nvps.add(new BasicNameValuePair("productId", "0201"));//0201-普通代付T1，0203-额度代付D0
        nvps.add(new BasicNameValuePair("transId", "07"));
        nvps.add(new BasicNameValuePair("merNo", "305440058131040"));
        nvps.add(new BasicNameValuePair("orderDate", new SimpleDateFormat("yyyyMMdd").format(new Date())));
        nvps.add(new BasicNameValuePair("orderNo", "AP" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())));
        nvps.add(new BasicNameValuePair("notifyUrl", "http://www.baidu.com"));
        nvps.add(new BasicNameValuePair("transAmt", "1"));
        nvps.add(new BasicNameValuePair("isCompay", "0"));//对公对私标识 0-对私,1-对公
        nvps.add(new BasicNameValuePair("phoneNo", "13312345678"));
        nvps.add(new BasicNameValuePair("customerName", "张三"));//代付账户名
        nvps.add(new BasicNameValuePair("cerdType", "01"));//代付证件类型
        nvps.add(new BasicNameValuePair("cerdId", "110000197609260652"));//代付证件号
        nvps.add(new BasicNameValuePair("accBankNo", "105100000025"));//代付开户行号
        nvps.add(new BasicNameValuePair("accBankName", "建设银行"));//代付开户行名称
        nvps.add(new BasicNameValuePair("acctNo", "6217007200007448098"));//代付账号 6221558812340000 
        nvps.add(new BasicNameValuePair("signature", SignUtils.signData(nvps, "C:\\Users\\Administrator\\Desktop\\upload\\305440058131040\\305440058131040_prv.pem")));
        System.out.println(nvps);
        try {
            postMethod.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));
            HttpResponse resp = httpClient.execute(postMethod);
            String str = EntityUtils.toString(resp.getEntity(), "UTF-8");
            System.out.println(str);
            int statusCode = resp.getStatusLine().getStatusCode();
            if (200 == statusCode) {
                boolean signFlag = SignUtils.verferSignData(str,"C:\\Users\\Administrator\\Desktop\\upload\\305440058131040\\305440058131040_pub.pem");
                if (!signFlag) {
                    System.out.println("验签失败");
                    return;
                }
                System.out.println("验签成功");
                return;
            }
            System.out.println("返回错误码:" + statusCode);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
