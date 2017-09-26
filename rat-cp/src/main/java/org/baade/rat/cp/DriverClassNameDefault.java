package org.baade.rat.cp;

/**
 * 数据库驱动的默认类名
 */
public enum  DriverClassNameDefault {

    MYSQL("com.mysql.jdbc.Driver"),

    ORACLE("oracle.jdbc.driver.OracleDriver"),

    SQL_SERVER("com.inet.tds.TdsDriver"),

    DB2( "com.ibm.db2.jcc.DB2Driver"),

    ;

    private String className;

    DriverClassNameDefault(String className){
        this.className = className;

    }

    public String getClassName() {
        return className;
    }
}
