package org.baade.rat.cp;

import java.sql.Types;
import java.util.Arrays;
import java.util.Optional;

public enum ColumnType {

    DEFAULT(new Class[0], 0),

    INT(new Class[]{Integer.class, int.class}, 11, "INT", Types.INTEGER),

    BOOLEAN(new Class[]{Boolean.class, boolean.class}, 1, "BIT", Types.BOOLEAN),

    DOUBLE(new Class[]{Double.class, double.class}, 20, 4, "DOUBLE", Types.DOUBLE),

    FLOAT(new Class[]{Float.class, float.class}, 11, 4,"FLOAT", Types.FLOAT),

    BYTES(new Class[]{Byte[].class, byte[].class}, -1, "BLOB", Types.BINARY),

    BYTE(new Class[]{Byte.class, byte.class}, 11, "INT", Types.INTEGER),

    CHAR(new Class[]{char.class}, 1, "CHAR", Types.VARCHAR),

    LONG(new Class[]{Long.class, long.class}, 20, "BIGINT", Types.BIGINT),

    SHORT(new Class[]{Short.class, short.class}, 11, "INT", Types.INTEGER),

    STRING(new Class[]{String.class}, 255, "VARCHAR", Types.VARCHAR),

    TEXT(null, -1, "TEXT", Types.VARCHAR),

    TINYTEXT(null, -1, "TINYTEXT", Types.VARCHAR),

    MEDIUMTEXT(null, -1, "MEDIUMTEXT", Types.VARCHAR),


    ;
    private Class<?>[] types;

    private int length;
    private int decimalsLength;//小数的位数
    private String mysqlColumnType;
    private int typeValue;

    ColumnType(Class<?>[] types, int length) {
        this(types, length,  null, -9999);
    }

    ColumnType(Class<?>[] types, int length, String mysqlColumnType, int typeValue) {
        this(types, length,  -1,mysqlColumnType, typeValue);
    }

    ColumnType(Class<?>[] types, int length, int decimalsLength, String mysqlColumnType, int typeValue) {
        this.types = types;
        this.length = length;
        this.decimalsLength = decimalsLength;
        this.mysqlColumnType = mysqlColumnType;
        this.typeValue = typeValue;
    }

    public Class<?>[] getTypes() {
        return types;
    }

    public int getLength() {
        return length;
    }

    public int getDecimalsLength() {
        return decimalsLength;
    }
    public boolean isNeedDecimalsLength(){
        return this.getDecimalsLength() != -1;
    }

    public boolean isNeedFindByClass(){
        return this.getTypes() != null;
    }

    public String getLengthStr(){
        String s = String.valueOf(this.getLength());
        if (this.getDecimalsLength() != -1){
            s += "," + this.getDecimalsLength();
        }
        return s;
    }

    public String getMysqlColumnType() {
        return mysqlColumnType;
    }

    public int getTypeValue() {
        return typeValue;
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

    public static ColumnType find(final Class<?> clazz){
        Optional<ColumnType> any = Arrays.asList(ColumnType.values()).stream().filter(ct -> ct.contains(clazz)).findAny();
        if (!any.isPresent()){
            return null;
        }
        return any.get();
    }

    public static String getMysqlType(final Class<?> clazz){
        Optional<ColumnType> any = Arrays.asList(ColumnType.values()).stream().filter(ct -> ct.contains(clazz)).findAny();
        if (!any.isPresent()){
            return null;
        }
        return any.get().getMysqlColumnType();
    }
}
