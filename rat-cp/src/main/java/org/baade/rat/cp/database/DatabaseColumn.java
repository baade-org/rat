package org.baade.rat.cp.database;

public class DatabaseColumn {

    private String name;
    private Class<?> type;
    private int length;
    private boolean isNull;
    private String comment;
    private String defaultValue;
    private boolean isPrimaryKey;


    public DatabaseColumn(String name, Class<?> type, int length, boolean isNull, String comment, String defaultValue, boolean isPrimaryKey) {
        this.name = name;
        this.type = type;
        this.length = length;
        this.isNull = isNull;
        this.comment = comment;
        this.defaultValue = defaultValue;
        this.isPrimaryKey = isPrimaryKey;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Class<?> getType() {
        return type;
    }

    public void setType(Class<?> type) {
        this.type = type;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public boolean isNull() {
        return isNull;
    }

    public void setNull(boolean aNull) {
        isNull = aNull;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public boolean isPrimaryKey() {
        return isPrimaryKey;
    }

    public void setPrimaryKey(boolean primaryKey) {
        isPrimaryKey = primaryKey;
    }
}
