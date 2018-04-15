package org.baade.rat.cp;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 连接池数据源
 * Created by zz@baade.org on 2017/9/27.
 */
@XmlRootElement(name="rat")
public final class DataSource {

    DatabaseInfo databaseInfo;


    private DataSource() {
    }

    @XmlElement(name = "database")
    public DatabaseInfo getDatabaseInfo() {
        return databaseInfo;
    }

    public void setDatabaseInfo(DatabaseInfo databaseInfo) {
        this.databaseInfo = databaseInfo;
    }

    @Override
    public String toString() {
        return "DataSource{" +
                "databaseInfo=" + databaseInfo +
                '}';
    }
}
