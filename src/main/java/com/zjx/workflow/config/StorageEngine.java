package com.zjx.workflow.config;

import org.hibernate.dialect.MySQLDialect;

/**
 * 存储引擎 设置字节集
 *
 * @author cgn
 * @date 2021/06/17
 */
public class StorageEngine extends MySQLDialect {
    @Override
    public String getTableTypeString() {
        return "ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE utf8mb4_unicode_ci";
    }
}
