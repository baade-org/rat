package org.baade.rat.cp.utils;

import org.baade.rat.core.scan.model.ClassModel;
import org.baade.rat.cp.ColumnType;
import org.baade.rat.cp.Key;
import org.baade.rat.cp.anno.Column;
import org.baade.rat.cp.anno.Entity;
import org.baade.rat.cp.anno.ID;
import org.baade.rat.cp.exception.NoGetterOrSetterMethod;
import org.baade.rat.cp.exception.NoSupportDBColumnType;
import org.baade.rat.cp.table.ColumnOfTable;
import org.baade.rat.cp.table.Table;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class DBUtils {

    public static Map<Class<? extends Entity>, Table> parse(List<ClassModel> classModels, String schema) throws Exception {
        Map<Class<? extends Entity>, Table> tables = new HashMap<>();
        for (ClassModel instance : classModels) {
            Class<?> clazz0 = Class.forName(instance.getClassFullName());
            if (!clazz0.isAnnotationPresent(Entity.class)) {
                continue;
            }
            Class<? extends Entity> clazz = (Class<? extends Entity>) clazz0;
            Table table = parse(clazz, schema);
            if (table == null) {
                continue;
            }
            tables.put(clazz, table);
        }
        return tables;

    }

    public static Table parse(Class<? extends Entity> clazz, String schema) {
        if (!clazz.isAnnotationPresent(Entity.class)) {
            return null;
        }

        Entity entity = clazz.getAnnotation(Entity.class);
        String tableName = entity.tableName().equals("") ? clazz.getSimpleName().toLowerCase() : entity.tableName();
        String schemaName = entity.schema().equals("") ? schema : entity.schema();
        String charsetName = entity.charset();

        Table table = new Table(schemaName, tableName, charsetName);

        Map<String, ColumnOfTable> columns = parseColumn(clazz, table);
        if (columns == null || columns.isEmpty()) {
            return null;
        }
        table.setColumns(columns);
        return table;

    }

    public static Map<String, ColumnOfTable> parseColumn(Class<? extends Entity> clazz, Table table) {
        Field[] declaredFields = clazz.getDeclaredFields();
        if (declaredFields == null || declaredFields.length == 0) {
            return null;
        }
        Map<String, ColumnOfTable> columns = new LinkedHashMap<>();


        for (Field declaredField : declaredFields) {
            ColumnOfTable column = parseField(clazz, declaredField, table);
            if (column == null) {
                continue;
            }
            columns.put(column.getName(), column);
        }
        return columns;
    }

    public static ColumnOfTable parseField(Class<? extends Entity> clazz, Field field, Table table) {
        if (!field.isAnnotationPresent(Column.class) && !field.isAnnotationPresent(ID.class)) {
            return null;
        }
        String fieldName = field.getName();
        if (!ClassUtils.checkSetterAndGetterMethod(clazz, fieldName)) {
            throw new NoGetterOrSetterMethod(clazz, fieldName);
        }
        ColumnType columnType = getDBColumnType(field);
        if (columnType == null) {
            throw new NoSupportDBColumnType(clazz, fieldName);
        }


        if (field.isAnnotationPresent(Column.class)) {
            Column column = field.getAnnotation(Column.class);
            String name = column.name().equals("") ? fieldName : column.name();
            int length = column.length() == -1 ? columnType.getLength() : column.length();

            String nullable = column.isNull() ? "YES" : "NO";
            String comment = column.comment().equals("") ? null : column.comment();
            String defaultValue = column.defaultValue().equals("") ? "null" : column.defaultValue();
            boolean isPrimaryKey = (column.key() == Key.PRIMARY);

            String sizeStr = null;
            int decimalsLength = column.decimalsLength();
            if (length != -1){
                sizeStr = String.valueOf(length);
                if (columnType.isNeedDecimalsLength()) {
                    sizeStr += "," + decimalsLength;
                }
            }


            int typeValue = columnType.getTypeValue();
            String typeStr = columnType.getMysqlColumnType();

            ColumnOfTable columnOfTable = new ColumnOfTable(name, typeValue, typeStr, length, decimalsLength, nullable, isPrimaryKey, comment, defaultValue, fieldName);
            columnOfTable.setTable(table);
            columnOfTable.setSizeStr(sizeStr);
            return columnOfTable;
        }

        if (field.isAnnotationPresent(ID.class)) {
            ID column = field.getAnnotation(ID.class);
            String name = column.name().equals("") ? field.getName() : column.name();
            int length = column.length() == -1 ? columnType.getLength() : column.length();

            String nullable = column.isNull() ? "YES" : "NO";
            String comment = column.comment().equals("") ? null : column.comment();
            String defaultValue = column.defaultValue().equals("") ? "null" : column.defaultValue();

            String sizeStr = null;
            int decimalsLength = column.decimalsLength();
            if (length != -1){
                sizeStr = String.valueOf(length);
                if (columnType.isNeedDecimalsLength()) {
                    sizeStr += "," + decimalsLength;
                }
            }

            int typeValue = columnType.getTypeValue();
            String typeStr = columnType.getMysqlColumnType();

            ColumnOfTable columnOfTable = new ColumnOfTable(name, typeValue, typeStr, length, decimalsLength, nullable, true, comment, defaultValue, fieldName);
            columnOfTable.setTable(table);
            columnOfTable.setSizeStr(sizeStr);
            return columnOfTable;
        }
        return null;

    }

    private static ColumnType getDBColumnType(Field field) {
        if (field.isAnnotationPresent(Column.class)) {
            Column column = field.getAnnotation(Column.class);
            ColumnType columnType = column.type();
            if (columnType.isNeedFindByClass()) {
                Class<?> type = column.type() == ColumnType.DEFAULT ? field.getType() : column.type().getTypes()[0];
                return ColumnType.find(type);
            }
            return columnType;
        }
        if (field.isAnnotationPresent(ID.class)) {
            ID column = field.getAnnotation(ID.class);
            ColumnType columnType = column.type();
            if (columnType.isNeedFindByClass()) {
                Class<?> type = column.type() == ColumnType.DEFAULT ? field.getType() : column.type().getTypes()[0];
                return ColumnType.find(type);
            }
            return columnType;

        }
        return null;
    }
}
