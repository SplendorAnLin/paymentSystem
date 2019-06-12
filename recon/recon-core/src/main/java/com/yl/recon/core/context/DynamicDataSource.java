package com.yl.recon.core.context;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * 使用DatabaseContextHolder获取当前线程的DatabaseType
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        return DataSourceContextHolder.getDataSourceType();
    }
}
