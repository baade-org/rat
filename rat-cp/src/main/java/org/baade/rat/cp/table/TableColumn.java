package org.baade.rat.cp.table;

public class TableColumn {

    private String name;

    private int type;
    private int size;
    private String nullable;
    private boolean isPrimaryKey;
    private String defaultValue;
    private String comment;

    private boolean isModify;

    private String typeStr;
    private String sizeStr;

    private Table table;
    private String fieldName;

    private String alertAfter;

    private int decimalsLength;


    public TableColumn(String name, int type, String typeStr, int size, int decimalsLength, String nullable,
                       boolean isPrimaryKey, String comment, String defaultValue, String fieldName){

        this.name = name;
        this.type = type;
        this.typeStr = typeStr;
        this.size = size;
        this.decimalsLength = decimalsLength;
        this.nullable = nullable;
        this.isPrimaryKey = isPrimaryKey;
        this.comment = comment;
        this.defaultValue = defaultValue;
        this.fieldName = fieldName;
    }


    public TableColumn(String name, int type, int size, String nullable, boolean isPrimaryKey, String defaultValue) {
        this.name = name;
        this.type = type;
        this.size = size;
        this.nullable = nullable;
        this.isPrimaryKey = isPrimaryKey;
        this.defaultValue = defaultValue;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getNullable() {
        return nullable;
    }

    public void setNullable(String nullable) {
        this.nullable = nullable;
    }

    public boolean isPrimaryKey() {
        return isPrimaryKey;
    }

    public void setPrimaryKey(boolean primaryKey) {
        isPrimaryKey = primaryKey;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public boolean isModify() {
        return isModify;
    }

    public void setModify(boolean modify) {
        isModify = modify;
    }

    public String getTypeStr() {
        return typeStr;
    }

    public void setTypeStr(String typeStr) {
        this.typeStr = typeStr;
    }

    public String getSizeStr() {
        return sizeStr;
    }

    public void setSizeStr(String sizeStr) {
        this.sizeStr = sizeStr;
    }

    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getAlertAfter() {
        return alertAfter;
    }

    public void setAlertAfter(String alertAfter) {
        this.alertAfter = alertAfter;
    }

    public int getDecimalsLength() {
        return decimalsLength;
    }

    public void setDecimalsLength(int decimalsLength) {
        this.decimalsLength = decimalsLength;
    }
}
