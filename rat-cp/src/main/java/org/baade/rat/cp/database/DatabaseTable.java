package org.baade.rat.cp.database;

import java.util.List;

public class DatabaseTable {

    private String tableName;
    private String databaseName;

    private String charset = "utf8";

    private List<DatabaseColumn> primaryKeyDatabaseColumns;
    private List<DatabaseColumn> commonDatabaseColumns;

    public DatabaseTable(String tableName, String databaseName, String charset) {
        this.tableName = tableName;
        this.databaseName = databaseName;
        this.charset = charset;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public List<DatabaseColumn> getPrimaryKeyDatabaseColumns() {
        return primaryKeyDatabaseColumns;
    }

    public void setPrimaryKeyDatabaseColumns(List<DatabaseColumn> primaryKeyDatabaseColumns) {
        this.primaryKeyDatabaseColumns = primaryKeyDatabaseColumns;
    }

    public List<DatabaseColumn> getCommonDatabaseColumns() {
        return commonDatabaseColumns;
    }

    public void setCommonDatabaseColumns(List<DatabaseColumn> commonDatabaseColumns) {
        this.commonDatabaseColumns = commonDatabaseColumns;
    }
}
