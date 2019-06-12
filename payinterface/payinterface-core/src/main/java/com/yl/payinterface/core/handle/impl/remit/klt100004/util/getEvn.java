package com.yl.payinterface.core.handle.impl.remit.klt100004.util;

import com.yl.payinterface.core.handle.impl.remit.klt100004.single.Env;

/**
 * 设置证书
 *
 * @author AnLin
 * @version V1.0.0
 * @since 2018/6/1
 */
public class getEvn {

    public static Env getEnv(String serverUrl, String cer, String pfx, String passWord){
        Env env = new Env();
        env.setDebug(true);
        env.setServerUrl(serverUrl);
        env.setSmartpayCertificatePath(cer);//支付通证书
        env.setMerchantPrivateCertificatePath(pfx);//商户私钥
        env.setMerchantPrivateCertificatePassword(passWord);//商户私钥密码
        return env;
    }
}