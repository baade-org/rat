package org.baade.rat.cp.table;

public class Column {
    private String name;
    private int type;
    private int size;
    private int nullable;

    public Column(String name, int type, int size, int nullable) {
        this.name = name;
        this.type = type;
        this.size = size;
        this.nullable = nullable;
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

    public int getNullable() {
        return nullable;
    }

    public void setNullable(int nullable) {
        this.nullable = nullable;
    }

    @Override
    public String toString() {
        return "Column{" +
                "name='" + name + '\'' +
                ", type=" + type +
                ", size=" + size +
                ", nullable=" + nullable +
                '}';
    }
}
