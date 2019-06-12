package com.yl.payinterface.core.handle.impl.remit.klt100004.single;

import com.yl.payinterface.core.handle.impl.remit.klt100004.resolve.*;
import com.yl.payinterface.core.handle.impl.remit.klt100004.single.dto.ISingle;
import com.yl.payinterface.core.handle.impl.remit.klt100004.util.Base64Utils;
import com.yl.payinterface.core.handle.impl.remit.klt100004.util.Md5Util;
import com.yl.payinterface.core.handle.impl.remit.klt100004.util.MySSLProtocolSocketFactory;
import com.yl.payinterface.core.handle.impl.remit.klt100004.util.Rsa;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.protocol.Protocol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;

/**
 * 开联通
 *
 * @author AnLin
 * @version V1.0.0
 * @since 2018/4/3
 */
public class SingleAgentpayService {

    private static final Logger logger = LoggerFactory.getLogger(SingleAgentpayService.class);

    public static ResolverAdapter resolverAdapter = new ResolverAdapter();

    static {
        // 初始化对象渲染器
        List<ICustomizeResolver> customizeResolvers = new ArrayList<ICustomizeResolver>();
        SingleAgentpayRequestCustomizeResolver agentpayRequestCustomizeResolver = new SingleAgentpayRequestCustomizeResolver();
        SingleAgentpayResponseCustomizeResolver agentpayResponseCustomizeResolver = new SingleAgentpayResponseCustomizeResolver();
        SingleAgentpayQueryRequestCustomizeResolver singleAgentpayQueryRequestCustomizeResolver = new SingleAgentpayQueryRequestCustomizeResolver();
        SingleAgentpayQueryResponseCustomizeResolver singleAgentpayQueryResponseCustomizeResolver = new SingleAgentpayQueryResponseCustomizeResolver();
        customizeResolvers.add(agentpayRequestCustomizeResolver);
        customizeResolvers.add(agentpayResponseCustomizeResolver);
        customizeResolvers.add(singleAgentpayQueryRequestCustomizeResolver);
        customizeResolvers.add(singleAgentpayQueryResponseCustomizeResolver);
        resolverAdapter.addCustomizeResolvers(customizeResolvers);

    }

    /**
     * 代付请求发送
     *
     * @param env
     * @return
     * @throws SinglepayResolveException
     */
    public static ISingle request(ISingle single, Env env) throws SinglepayResolveException {
        if (env.isDebug()) {
            logger.info("1. Env：" + env.toString());
        }
        String version = single.getEnvelope().getHead().getVersion();
        // 设置商户证书
        single.getSign().setCertificate("");
        if (!Env.ENCODE.equals(single.getEnvelope().getHead().getCharset())) {
            throw new SinglepayResolveException("不支持的字符集" + single.getEnvelope().getHead().getCharset());
        }
        try {
            String requestEnvelope = resolverAdapter.doRenderRequest(single.getEnvelope(), version);
            if (env.isDebug()) {
                logger.info("2. requestEnvelope ：" + requestEnvelope);
            }
            byte[] md5hash = Md5Util.md5(requestEnvelope.getBytes(Env.ENCODE));
            PrivateKey privateKey = Rsa.loadPrivateKey(env.getMerchantPrivateCertificatePath(), env.getMerchantPrivateCertificatePassword());
            String signMsg = Base64Utils.encode(Rsa.signSha1WithRsa(md5hash, privateKey));
            if (env.isDebug()) {
                logger.info("3. signMsg ：" + signMsg);
            }
            single.getSign().setSignContent(signMsg);
        } catch (UnsupportedEncodingException e) {
            throw new SinglepayResolveException("不支持的字符集", e);
        } catch (Exception e) {
            throw new SinglepayResolveException("签名失败", e);
        }
        String requestXml = resolverAdapter.doRenderRequest(single, version);
        if (env.isDebug()) {
            logger.info("4. requestXml ：" + requestXml);
        }
        String requestXmlBase64;
        try {
            byte[] data = requestXml.getBytes(Env.ENCODE);
            requestXmlBase64 = Base64Utils.encode(data);
            if (env.isDebug()) {
                logger.info("5. requestXml after base64 ：" + requestXmlBase64);
            }
        } catch (Exception e) {
            throw new SinglepayResolveException("编码报文失败", e);
        }
        // 发送
        String response;
        String responseXmlBase64;
        try {
            responseXmlBase64 = send(env.getServerUrl(), requestXmlBase64, Env.ENCODE);
            if (env.isDebug()) {
                logger.info("6. response   ：" + responseXmlBase64);
            }
            // Base64 decode
            byte[] data = Base64Utils.decode(responseXmlBase64);
            response = new String(data, Env.ENCODE);
            if (env.isDebug()) {
                logger.info("7. response (plain text)：" + response);
            }
        } catch (Exception e) {
            logger.info("实时代付异常：" + e.getMessage());
            throw new SinglepayResolveException("解码失败", e);
        }
        ISingle responseBatch = (ISingle) resolverAdapter.doParseResponse(response, version);
        // HexDecode，然后验证签名
        try {
            String envelope = resolverAdapter.doRenderResponse(responseBatch.getEnvelope(), version);
            String signContent = responseBatch.getSign().getSignContent();
            PublicKey PublicKey = Rsa.loadPublicKey(env.getSmartpayCertificatePath());
            byte[] md5hash = Md5Util.md5(envelope.getBytes(Env.ENCODE));
            boolean result = Rsa.verfySha1WithRsa(Base64Utils.decode(signContent), md5hash, PublicKey);
            logger.info("验签[" + version + "]：" + result);
            if (!result) {
                throw new SinglepayResolveException("验签失败");
            }
        } catch (UnsupportedEncodingException e) {
            throw new SinglepayResolveException("不支持的字符集", e);
        } catch (SinglepayResolveException e) {
            throw e;
        } catch (Exception e) {
            throw new SinglepayResolveException("验签失败", e);
        }
        return responseBatch;
    }

    /**
     * 发送请求，取得响应
     *
     * @return
     * @throws SinglepayResolveException
     */
    public static String send(String serverUrl, String params, String encoding) throws Exception {
        String strResponse = null;
        HttpClient httpclient = null;
        try {
            httpclient = new HttpClient();
            PostMethod postmethod = new PostMethod(serverUrl);
            NameValuePair[] data = {new NameValuePair("reqMsg", params)};
            postmethod.setRequestBody(data);
            int responseCode = httpclient.executeMethod(postmethod);
            if (serverUrl.startsWith("https")) {
                Protocol myhttps = new Protocol("https", new MySSLProtocolSocketFactory(), 443);
                Protocol.registerProtocol("https", myhttps);

            }
            if (responseCode == HttpURLConnection.HTTP_OK) {
                strResponse = postmethod.getResponseBodyAsString();
            } else {
                if (null == strResponse || "".equals(strResponse)) {
                    throw new SinglepayResolveException(serverUrl + "服务器响应为空");
                }
            }
        } catch (Exception e) {
            throw new SinglepayResolveException(serverUrl + "服务器响应为空");
        } finally {

        }
        return strResponse;
    }
}