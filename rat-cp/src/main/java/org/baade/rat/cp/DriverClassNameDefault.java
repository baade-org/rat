package org.baade.rat.cp;

import org.baade.rat.cp.crud.ICrud;
import org.baade.rat.cp.crud.mysql.MysqlCrud;

import java.util.Arrays;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * 数据库驱动的默认类名
 * Created by zz@baade.org on 2017/9/27.
 */
public enum DriverClassNameDefault {

    MYSQL("com.mysql.jdbc.Driver", MysqlCrud::new),

//    ORACLE("oracle.jdbc.driver.OracleDriver"),
//
//    SQL_SERVER("com.inet.tds.TdsDriver"),
//
//    DB2( "com.ibm.db2.jcc.DB2Driver"),

    ;

    private String className;
    private Supplier<ICrud> supplier;

    DriverClassNameDefault(String className, Supplier<ICrud> supplier){
        this.className = className;
        this.supplier = supplier;

    }

    public String getClassName() {
        return className;
    }

    public Supplier<ICrud> getSupplier() {
        return supplier;
    }

    public ICrud newCrud(){
        if (supplier == null) {
            return null;
        }
        return supplier.get();
    }

    public static DriverClassNameDefault getType(final String driverClassName){
        Optional<DriverClassNameDefault> any = Arrays.asList(DriverClassNameDefault.values()).stream().filter(ct -> ct.getClassName().equals(driverClassName)).findAny();
        if (!any.isPresent()){
            return null;
        }
        return any.get();
    }
}
