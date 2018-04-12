package org.baade.rat.cp.table;

import java.util.HashMap;
import java.util.Map;

public class Table {

    private String name;
    private Map<String, Column> columns;

    public Table(String name) {
        this.name = name;
        columns = new HashMap<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, Column> getColumns() {
        return columns;
    }

    public void setColumns(Map<String, Column> columns) {
        this.columns = columns;
    }

    public void put(Column column){
        this.columns.put(column.getName(), column);
    }
}
