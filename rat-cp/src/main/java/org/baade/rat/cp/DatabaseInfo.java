package org.baade.rat.cp;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import java.util.List;

/**
 * 数据库连接的基本信息
 * Created by zz@baade.org on 2017/9/27.
 */
public class DatabaseInfo {
//<scan packageName="org.baade" />
//    <url>jdbc:mysql://127.0.0.1:3306/xxx</url>
//        <driver_class>com.mysql.jdbc.Driver</driver_class>
//        <schema>xxx</schema>
//        <properties_list>
//            <username>root</username>
//            <passowrd>password</passowrd>
//            <useUnicode>true</useUnicode>
//            <characterEncoding>utf8</characterEncoding>
//            <createDatabaseIfNotExist>true</createDatabaseIfNotExist>
//        </properties_list>

    private String scanPackageName;
    private String url;
    private String driverClass;
    private String schema;
    private int poolSize;
    private boolean showSql;

    private List<DBProperty> dbProperties;

    private DriverClassNameDefault type;

    @XmlElement(name="scan_packageName")
    public String getScanPackageName() {
        return scanPackageName;
    }

    public void setScanPackageName(String scanPackageName) {
        this.scanPackageName = scanPackageName;
    }

    @XmlElement(name="url")
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    @XmlElement(name="driver_class")
    public String getDriverClass() {
        return driverClass;
    }

    public void setDriverClass(String driverClass) {
        this.driverClass = driverClass;
        this.type = DriverClassNameDefault.getType(driverClass);
    }
    @XmlElement(name="schema")
    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    @XmlElement(name="pool_size")
    public int getPoolSize() {
        return poolSize;
    }

    public void setPoolSize(int poolSize) {
        this.poolSize = poolSize;
    }

    @XmlElement(name="show_sql")
    public boolean isShowSql() {
        return showSql;
    }

    public void setShowSql(boolean showSql) {
        this.showSql = showSql;
    }

    @XmlElementWrapper(name="properties")
    @XmlElement(name = "p")
    public List<DBProperty> getDbProperties() {
        return dbProperties;
    }

    public void setDbProperties(List<DBProperty> dbProperties) {
        this.dbProperties = dbProperties;
    }

    public DriverClassNameDefault getType() {
        return type;
    }

    public void setType(DriverClassNameDefault type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "DatabaseInfo{" +
                "scanPackageName='" + scanPackageName + '\'' +
                ", url='" + url + '\'' +
                ", driverClass='" + driverClass + '\'' +
                ", schema='" + schema + '\'' +
                ", poolSize=" + poolSize +
                ", showSql=" + showSql +
                ", dbProperties=" + dbProperties +
                ", type=" + type +
                '}';
    }

    static class DBProperty{
        private String key;
        private String value;

        @XmlAttribute(name="key")
        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }
        @XmlAttribute(name="value")
        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return "DBProperty{" +
                    "key='" + key + '\'' +
                    ", value='" + value + '\'' +
                    '}';
        }
    }

}
