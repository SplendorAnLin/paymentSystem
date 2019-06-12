package com.yl.payinterface.core.handle.impl.remit.zhongmao100001.utils;

import org.apache.commons.lang.StringUtils;
import org.apache.http.message.BasicNameValuePair;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.TreeMap;

public class SignUtils {

    public static String signData(List<BasicNameValuePair> nvps, String privateKeyPath) throws Exception {
        TreeMap<String, String> tempMap = new TreeMap<String, String>();
        for (BasicNameValuePair pair : nvps) {
            if (StringUtils.isNotBlank(pair.getValue())) {
                tempMap.put(pair.getName(), pair.getValue());
            }
        }
        StringBuffer buf = new StringBuffer();
        for (String key : tempMap.keySet()) {
            buf.append(key).append("=").append((String) tempMap.get(key)).append("&");
        }
        String signatureStr = buf.substring(0, buf.length() - 1);
        String signData = RSAUtil.signByPrivate(signatureStr, RSAUtil.readFile(privateKeyPath, "UTF-8"), "UTF-8");
        return signData;
    }

    public static boolean verferSignData(String str, String publicKeyPath) throws UnsupportedEncodingException {
        String data[] = str.split("&");
        StringBuffer buf = new StringBuffer();
        String signature = "";
        for (int i = 0; i < data.length; i++) {
            String tmp[] = data[i].split("=", 2);
            if ("signature".equals(tmp[0])) {
                signature = tmp[1];
            } else {
                buf.append(tmp[0]).append("=").append(tmp[1]).append("&");
            }
        }
        String signatureStr = buf.substring(0, buf.length() - 1);
        return RSAUtil.verifyByKeyPath(signatureStr, signature, publicKeyPath, "UTF-8");
    }

}
