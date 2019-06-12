package com.yl.payinterface.core.handle.impl.remit.shand100001.utils;

import java.io.IOException;

/**
 * 证书加载
 *
 * @author AnLin
 * @version V1.0.0
 * @since 2018/6/26
 */
public class ConfigurationManager extends com.netflix.config.ConfigurationManager {

    public ConfigurationManager() {
    }

    public static void loadProperties(String[] configNames) throws IOException {
        for(int i = 0; i < configNames.length; ++i) {
            loadAppOverrideProperties(configNames[i]);
        }
    }
}