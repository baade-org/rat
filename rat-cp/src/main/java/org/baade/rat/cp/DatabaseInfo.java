package org.baade.rat.cp;

/**
 * 数据库连接的基本信息
 * Created by zz@baade.org on 2017/9/27.
 */
public class DatabaseInfo {

    private String driverClassName;

    private String url;

    private String user;

    private String password;

    private int maxConnectCount;
    private int minConnectCount;
}
