package com.yl.payinterface.core.handle.impl.remit.kingpass100001.utils;

import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.util.Properties;

public class MerchantConfig {
//    public static final String ConfigFileName = "p2pmerchant.properties";
    private String requestUrl;
    private String offlineNotifyUrl;
    private String pageReturnUrl;
    private String merchantId;
    private String merchantName;
    private String signType;
    private String version;
    private String merchantCertPath;
    private String merchantCertPass;
    private String merchantCertPass2;
    private String rootCertPath;
    private String checkFileDir;
    private String charset;
    private String payRequestUrl;
    private String b2bRequestUrl;
    private String notifyUrl;
    private static MerchantConfig config;
    private Properties properties;

    public MerchantConfig() {
    }

    public static MerchantConfig getConfig() {
        if(config == null) {
            config = new MerchantConfig();
        }

        return config;
    }

    public void loadPropertiesFromPath(String rootPath) {
        File file = new File(rootPath + File.separator + "p2pmerchant.properties");
        FileInputStream in = null;
        if(file.exists()) {
            try {
                in = new FileInputStream(file);
                this.properties = new Properties();
                this.properties.load(in);
                this.loadProperties(this.properties);
                if(in != null) {
                    try {
                        in.close();
                    } catch (IOException var21) {
                        var21.printStackTrace();
                    }
                }
            } catch (FileNotFoundException var22) {
                var22.printStackTrace();
                if(in != null) {
                    try {
                        in.close();
                    } catch (IOException var20) {
                        var20.printStackTrace();
                    }
                }
            } catch (IOException var23) {
                var23.printStackTrace();
                if(in != null) {
                    try {
                        in.close();
                    } catch (IOException var19) {
                        var19.printStackTrace();
                    }
                }
            } finally {
                if(in != null) {
                    try {
                        in.close();
                    } catch (IOException var18) {
                        var18.printStackTrace();
                    }
                }

            }
        } else {
            System.out.println(rootPath + "p2pmerchant.properties" + "File not found");
        }

    }

    public void loadPropertiesFromSrc() {
        InputStream in = null;

        try {
            in = MerchantConfig.class.getClassLoader().getResourceAsStream("p2pmerchant.properties");
            if(in != null) {
                this.properties = new Properties();

                try {
                    this.properties.load(in);
                } catch (IOException var17) {
                    throw var17;
                }
            }

            this.loadProperties(this.properties);
            if(in != null) {
                try {
                    in.close();
                } catch (IOException var16) {
                    var16.printStackTrace();
                }
            }
        } catch (IOException var18) {
            var18.printStackTrace();
            if(in != null) {
                try {
                    in.close();
                } catch (IOException var15) {
                    var15.printStackTrace();
                }
            }
        } finally {
            if(in != null) {
                try {
                    in.close();
                } catch (IOException var14) {
                    var14.printStackTrace();
                }
            }

        }

    }

    public void loadProperties(Properties pro) {
        String value = pro.getProperty("pay.requestUrl");
        if(!StringUtils.isBlank(value)) {
            this.requestUrl = value.trim();
        }

        value = pro.getProperty("sdk.offlineNotifyUrl");
        if(!StringUtils.isBlank(value)) {
            this.offlineNotifyUrl = value.trim();
        }

        value = pro.getProperty("sdk.pageReturnUrl");
        if(!StringUtils.isBlank(value)) {
            this.pageReturnUrl = value.trim();
        }

        value = pro.getProperty("sdk.merchantId");
        if(!StringUtils.isBlank(value)) {
            this.merchantId = value.trim();
        }
        value = pro.getProperty("sdk.merchantName");
        if(!StringUtils.isBlank(value)) {
            this.merchantName = value.trim();
        }

        value = pro.getProperty("sdk.signType");
        if(!StringUtils.isBlank(value)) {
            this.signType = value.trim();
        }

        value = pro.getProperty("sdk.version");
        if(!StringUtils.isBlank(value)) {
            this.version = value.trim();
        }

        value = pro.getProperty("sdk.merchantCertPath");
        if(!StringUtils.isBlank(value)) {
            this.merchantCertPath = value.trim();
        }

        value = pro.getProperty("sdk.merchantCertPass");
        if(!StringUtils.isBlank(value)) {
            this.merchantCertPass = value.trim();
        }
        value = pro.getProperty("sdk.charset");
        if(!StringUtils.isBlank(value)) {
            this.charset = value.trim();
        }

        value = pro.getProperty("sdk.rootCertPath");
        if(!StringUtils.isBlank(value)) {
            this.rootCertPath = value.trim();
        }

        value = pro.getProperty("sdk.checkFileDir");
        if(!StringUtils.isBlank(value)) {
            this.checkFileDir = value.trim();
        }

        value = pro.getProperty("pay.requestUrl");
        if(!StringUtils.isBlank(value)) {
            this.payRequestUrl = value.trim();
        }

        value = pro.getProperty("b2b.requestUrl");
        if (!StringUtils.isBlank(value)) {
            this.b2bRequestUrl = value.trim();
        }

        value = pro.getProperty("sdk.notifyUrl");
        if (!StringUtils.isBlank(value)) {
            this.notifyUrl = value.trim();
        }
    }

    public String getB2bRequestUrl() {
        return b2bRequestUrl;
    }


    public String getRequestUrl() {
        return this.requestUrl;
    }

    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }

    public String getOfflineNotifyUrl() {
        return this.offlineNotifyUrl;
    }

    public void setOfflineNotifyUrl(String offlineNotifyUrl) {
        this.offlineNotifyUrl = offlineNotifyUrl;
    }

    public String getPageReturnUrl() {
        return this.pageReturnUrl;
    }

    public void setPageReturnUrl(String pageReturnUrl) {
        this.pageReturnUrl = pageReturnUrl;
    }

    public String getMerchantId() {
        return this.merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getMerchantName() {
        return this.merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getSignType() {
        return this.signType;
    }

    public void setSignType(String signType) {
        this.signType = signType;
    }

    public String getVersion() {
        return this.version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getMerchantCertPath() {
        return this.merchantCertPath;
    }

    public void setMerchantCertPath(String merchantCertPath) {
        this.merchantCertPath = merchantCertPath;
    }

    public String getMerchantCertPass() {
        return this.merchantCertPass;
    }

    public void setMerchantCertPass(String merchantCertPass) {
        this.merchantCertPass = merchantCertPass;
    }

    public String getRootCertPath() {
        return this.rootCertPath;
    }

    public void setRootCertPath(String rootCertPath) {
        this.rootCertPath = rootCertPath;
    }

    public String getCharset() {
        return this.charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public String getCheckFileDir() {
        return this.checkFileDir;
    }

    public void setCheckFileDir(String checkFileDir) {
        this.checkFileDir = checkFileDir;
    }

    public Properties getProperties() {
        return this.properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    public String getPayRequestUrl() {
        return payRequestUrl;
    }

    public void setPayRequestUrl(String payRequestUrl) {
        this.payRequestUrl = payRequestUrl;
    }


    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    public static void main(String[] args) {
    }
}
