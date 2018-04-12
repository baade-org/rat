package org.baade.rat.cp;

import java.util.Arrays;
import java.util.Optional;

/**
 * 数据库驱动的默认类名
 * Created by zz@baade.org on 2017/9/27.
 */
public enum DriverClassNameDefault {

    MYSQL("com.mysql.jdbc.Driver"),

//    ORACLE("oracle.jdbc.driver.OracleDriver"),
//
//    SQL_SERVER("com.inet.tds.TdsDriver"),
//
//    DB2( "com.ibm.db2.jcc.DB2Driver"),

    ;

    private String className;

    DriverClassNameDefault(String className){
        this.className = className;

    }

    public String getClassName() {
        return className;
    }

    public static DriverClassNameDefault getType(final String driverClassName){
        Optional<DriverClassNameDefault> any = Arrays.asList(DriverClassNameDefault.values()).stream().filter(ct -> ct.getClassName().equals(driverClassName)).findAny();
        if (!any.isPresent()){
            return null;
        }
        return any.get();
    }
}
