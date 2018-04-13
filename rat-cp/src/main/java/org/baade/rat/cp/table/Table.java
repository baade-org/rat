package org.baade.rat.cp.table;

import java.util.LinkedHashMap;
import java.util.Map;

public class Table {

    private String name;
    private String schema;
    private String charsetName = "utf8";
    private Map<String, TableColumn> columns;

    public Table(String name) {
        this(null, name, "utf8");
    }

    public Table(String schema, String name, String charsetName) {
        this.schema = schema;
        this.name = name;
        this.charsetName= charsetName;
        columns = new LinkedHashMap<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, TableColumn> getColumns() {
        return columns;
    }

    public void setColumns(Map<String, TableColumn> columns) {
        this.columns = columns;
    }

    public void put(TableColumn column){
        this.columns.put(column.getName(), column);
    }

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    public String getCharsetName() {
        return charsetName;
    }

    public void setCharsetName(String charsetName) {
        this.charsetName = charsetName;
    }

    @Override
    public String toString() {
        return "Table{" +
                "name='" + name + '\'' +
                ", schema='" + schema + '\'' +
                ", charsetName='" + charsetName + '\'' +
                ", columns=" + columns +
                '}';
    }
}
