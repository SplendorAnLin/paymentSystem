package com.yl.chat;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * 常量
 *
 * @author 聚合支付有限公司
 * @version V1.0.0
 * @since 2016年9月24日
 */
public class Constant {

    /**
     * 应用名称，用于各服务间通信
     */
    public static final String APP_NAME = "ylzf-chat";

    /**
     * MQ分隔符
     */
    public static final String DELIMITER = ":";

    /**
     * NODE分隔符
     */
    public static final String NODE_SEPATOR = ".";

    /**
     * 参数名-字符集
     */
    public static final String PARAM_NAME_INPUT_CHARSET = "inputCharset";

    /**
     * 参数名-字符集
     */
    public static final String PARAM_NAME_CHARSET = "charSet";

    public static final int OPERATOR_RESOURCE_CACHE_DB = 14;

    public static final String OPERATOR_RESOUSE = "customer.operator.resource.login";

    public static final String SYSTEM_VERSION = "app.version";

    public static final int IP_DB = 12;

    //字典
    public static final String DICTIONARY = "DICTIONARY.";

    //字典类型
    public static final String DICTIONARY_TYPE = "DICTIONARY_TYPE.";

    public static final String SMS_MESSAGE_TYPE3 = "【聚合支付】您的验证码是：[%s]，您正在进行微信登陆操作，请勿泄露。";
    /**
     * 用户认证的模版
     */
    public static String CHAT_ACCESS_TOKEN = "chat.test.tokens";
    /**
     * 异常消息模版
     */
    public static String templeEX = "4wmx1e_Zb5TWdA8lustK7tgty5_rYaC9HtKvDCdocHk";

    public static String templeFunds = "PvEpCPEji31K8cIk32s1P6uBuVFqbXl3F4jdy_ZkM54";

    public static String CLICK_URL = "http://m.bank-pay.com:8888/boss";

    private static Properties prop = new Properties();

    static {
        try {
            prop.load(new InputStreamReader(Constant.class.getClassLoader().getResourceAsStream("serverHost.properties")));
            CHAT_ACCESS_TOKEN = prop.getProperty("chatAccessToken");
            templeEX = prop.getProperty("templeEX");
            templeFunds = prop.getProperty("templeFunds");
            CLICK_URL = prop.getProperty("clickUrl");
        } catch (IOException e) {

        }
    }
}