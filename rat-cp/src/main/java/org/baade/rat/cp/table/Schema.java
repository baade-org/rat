package org.baade.rat.cp.table;

import java.util.HashMap;
import java.util.Map;

public class Schema {
    private String name;
    private String charsetName;
    private Map<String, Table> tables;

    public Schema(String name, String charsetName) {
        this.name = name;
        this.charsetName = charsetName;
        this.tables = new HashMap<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, Table> getTables() {
        return tables;
    }

    public void setTables(Map<String, Table> tables) {
        this.tables = tables;
    }

    public String getCharsetName() {
        return charsetName;
    }

    public void setCharsetName(String charsetName) {
        this.charsetName = charsetName;
    }

    @Override
    public String toString() {
        return "Schema{" +
                "name='" + name + '\'' +
                ", charsetName='" + charsetName + '\'' +
                ", tables=" + tables +
                '}';
    }
}
