package org.baade.rat.cp;

import java.util.Arrays;
import java.util.Optional;

public enum ColumnType {

    DEFAULT(null, 0),

    INT(new Class[]{Integer.class, int.class}, 11, "INT"),

    BOOLEAN(new Class[]{Boolean.class, boolean.class}, 1, "BIT"),

    DOUBLE(new Class[]{Double.class, double.class}, 20, "DOUBLE"),

    FLOAT(new Class[]{Float.class, float.class}, 11, "FLOAT"),

    BYTES(new Class[]{Byte[].class}, 20, "BLOB"),

    BYTE(new Class[]{Byte.class, byte.class}, 11, "INT"),

    CHAR(new Class[]{char.class}, 1, "CHAR"),

    LONG(new Class[]{Long.class, long.class}, 20, "BIGINT"),

    SHORT(new Class[]{Short.class, short.class}, 11, "INT"),

    STRING(new Class[]{String.class}, 255, "VARCHAR"),


    ;
    private Class<?>[] types;

    private int length;
    private String mysqlColumnType;

    ColumnType(Class<?>[] types, int length) {
        this(types, length, null);
    }

    ColumnType(Class<?>[] types, int length, String mysqlColumnType) {
        this.types = types;
        this.length = length;
        this.mysqlColumnType = mysqlColumnType;
    }

    public Class<?>[] getTypes() {
        return types;
    }

    public int getLength() {
        return length;
    }

    public String getMysqlColumnType() {
        return mysqlColumnType;
    }

    public boolean contains(Class<?> clazz) {
        if (getTypes() == null){
            return false;
        }
        for (Class<?> c : getTypes()){
            if (c == clazz){
                return true;
            }
        }
        return false;
    }

    public static int getLengthByType(final Class<?> clazz){
        Optional<ColumnType> any = Arrays.asList(ColumnType.values()).stream().filter(ct -> ct.contains(clazz)).findAny();
        if (!any.isPresent()){
            return -1;
        }
        return any.get().getLength();
    }

    public static String getMysqlType(final Class<?> clazz){
        Optional<ColumnType> any = Arrays.asList(ColumnType.values()).stream().filter(ct -> ct.contains(clazz)).findAny();
        if (!any.isPresent()){
            return null;
        }
        return any.get().getMysqlColumnType();
    }
}
