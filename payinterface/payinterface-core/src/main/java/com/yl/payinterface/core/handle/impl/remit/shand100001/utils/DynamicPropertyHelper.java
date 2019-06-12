package com.yl.payinterface.core.handle.impl.remit.shand100001.utils;

import com.netflix.config.DynamicBooleanProperty;
import com.netflix.config.DynamicIntProperty;
import com.netflix.config.DynamicPropertyFactory;
import com.netflix.config.DynamicStringProperty;

/**
 * 加载器
 *
 * @author AnLin
 * @version V1.0.0
 * @since 2018/6/26
 */
public class DynamicPropertyHelper {

    private static final DynamicPropertyFactory dynamicPropertyFactory = DynamicPropertyFactory.getInstance();

    public DynamicPropertyHelper() {
    }

    public static DynamicPropertyFactory getDynamicPropertyFactory() {
        return dynamicPropertyFactory;
    }

    public static DynamicStringProperty getStringProperty(String propName, String defaultValue) {
        return getDynamicPropertyFactory().getStringProperty(propName, defaultValue);
    }

    public static DynamicIntProperty getIntProperty(String propName, int defaultValue) {
        return getDynamicPropertyFactory().getIntProperty(propName, defaultValue);
    }

    public static DynamicBooleanProperty getBooleanProperty(String propName, boolean defaultValue) {
        return getDynamicPropertyFactory().getBooleanProperty(propName, defaultValue);
    }
}