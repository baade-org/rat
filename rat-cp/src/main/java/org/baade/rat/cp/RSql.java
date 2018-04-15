package org.baade.rat.cp;

import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * The type R sql.
 */
public class RSql {

    /**
     * 是否是同步 默认是异步
     * false : 异步
     * true : 同步
     */
    private boolean isSync = false;

    /**
     * 要执行的sql语句
     */
    private String sql;

    /**
     * 是否是查询语句
     */
    private boolean isSelect = false;

    private boolean isShowSql;

    private Consumer<Object> returnMethod;

    public RSql(String sql, boolean isShowSql) {
        this(sql,false, false, isShowSql, null);
    }
    public RSql(String sql, boolean isSync, boolean isShowSql) {
        this(sql, isSync, false, isShowSql, null);
    }

    public RSql(String sql, boolean isSync, boolean isSelect, boolean isShowSql, Consumer<Object> returnMethod) {
        this.isSync = isSync;
        this.sql = sql;
        this.isSelect = isSelect;
        this.isShowSql = isShowSql;
        this.returnMethod = returnMethod;
    }

    public boolean isSync() {
        return isSync;
    }

    public void setSync(boolean sync) {
        isSync = sync;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public boolean isShowSql() {
        return isShowSql;
    }

    public void setShowSql(boolean showSql) {
        isShowSql = showSql;
    }

    public Consumer<Object> getReturnMethod() {
        return returnMethod;
    }

    public void setReturnMethod(Consumer<Object> returnMethod) {
        this.returnMethod = returnMethod;
    }

    @Override
    public String toString() {
        return "RSql{" +
                "isSync=" + isSync +
                ", sql='" + sql + '\'' +
                ", isSelect=" + isSelect +
                ", isShowSql=" + isShowSql +
                ", returnMethod=" + returnMethod +
                '}';
    }
}
