package com.yl.payinterface.core.handle.impl.remit.klt100004.single;

/**
 * 开联通
 *
 * @author AnLin
 * @version V1.0.0
 * @since 2018/4/3
 */
public class Env {
	
    private final String SHA1WithRSA="SHA1WithRSA";
    
    public static String ENCODE="UTF-8";
	/**
	 * 请求服务器地址
	 */
	private String serverUrl;

	/**
	 * 支付通证书路径
	 */
	private String smartpayCertificatePath;

	/**
	 * 商户公钥路径 
	 */
	private String merchantPublicCertificatePath;
	
	/**
	 * 商户私钥路径
	 */
	private String merchantPrivateCertificatePath;
	
	private String merchantPrivateCertificatePassword;

	private boolean debug = false; 

	public boolean isDebug() {
		return debug;
	}

	public void setDebug(boolean debug) {
		this.debug = debug;
	}

	/**
	 * @return the serverUrl
	 */
	public String getServerUrl() {
		return serverUrl;
	}

	/**
	 * @param serverUrl
	 *            the serverUrl to set
	 */
	public void setServerUrl(String serverUrl) {
		this.serverUrl = serverUrl;
	}

	public String getSmartpayCertificatePath() {
		return smartpayCertificatePath;
	}

	public void setSmartpayCertificatePath(String smartpayCertificatePath) {
		this.smartpayCertificatePath = smartpayCertificatePath;
	}

	public String getMerchantPublicCertificatePath() {
		return merchantPublicCertificatePath;
	}

	public void setMerchantPublicCertificatePath(
			String merchantPublicCertificatePath) {
		this.merchantPublicCertificatePath = merchantPublicCertificatePath;
	}

	public String getMerchantPrivateCertificatePath() {
		return merchantPrivateCertificatePath;
	}

	public void setMerchantPrivateCertificatePath(
			String merchantPrivateCertificatePath) {
		this.merchantPrivateCertificatePath = merchantPrivateCertificatePath;
	}

	public String getSingType(){
		return this.SHA1WithRSA;
	}

	public String getMerchantPrivateCertificatePassword() {
		return merchantPrivateCertificatePassword;
	}

	public void setMerchantPrivateCertificatePassword(
			String merchantPrivateCertificatePassword) {
		this.merchantPrivateCertificatePassword = merchantPrivateCertificatePassword;
	}

	public String toString(){
		StringBuffer sb = new StringBuffer();
		sb.append("serverUrl=").append(serverUrl);
		sb.append(" , smartpayCertificatePath=").append(smartpayCertificatePath);
		sb.append(" , merchantPublicCertificatePath=").append(merchantPublicCertificatePath);
		sb.append(" , merchantPrivateCertificatePath=").append(merchantPrivateCertificatePath);
		sb.append(" , merchantPrivateCertificatePassword=").append(merchantPrivateCertificatePassword);
		sb.append(" , debug=").append(debug);
		return sb.toString();
	}
	
}
