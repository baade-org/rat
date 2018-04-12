package org.baade.rat.cp.database;

import org.baade.rat.cp.ColumnType;
import org.baade.rat.cp.Key;
import org.baade.rat.cp.anno.Column;
import org.baade.rat.cp.anno.Entity;
import org.baade.rat.cp.anno.ID;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DatabaseUtils {

    public static DatabaseTable parse(Class<?> clazz){
        if(!clazz.isAnnotationPresent(Entity.class)){
            return null;
        }

        Map<Integer, List<DatabaseColumn>> listMap = parseColumn(clazz);
        if (listMap == null || listMap.isEmpty()){
            return null;
        }

        Entity entity = clazz.getAnnotation(Entity.class);
        String tableName = entity.tableName().equals("") ? clazz.getSimpleName().toLowerCase() : entity.tableName();
        String databaseName = entity.schema().equals("") ? null : entity.schema();
        String charsetName = entity.charset();

        DatabaseTable databaseTable = new DatabaseTable(tableName, databaseName, charsetName);
        databaseTable.setPrimaryKeyDatabaseColumns(listMap.get(0));
        databaseTable.setCommonDatabaseColumns(listMap.get(1));
        return databaseTable;
    }

    public static Map<Integer, List<DatabaseColumn>> parseColumn(Class<?> clazz){
        Field[] declaredFields = clazz.getDeclaredFields();
        if (declaredFields == null || declaredFields.length == 0){
            return null;
        }
        List<DatabaseColumn> primaryKeyDatabaseColumns = new ArrayList<>();
        List<DatabaseColumn> commonDatabaseColumns = new ArrayList<>();


        for (Field declaredField : declaredFields) {
            DatabaseColumn databaseColumn = parseField(declaredField);
            if (databaseColumn == null){
                continue;
            }

            if (databaseColumn.isPrimaryKey()){
                primaryKeyDatabaseColumns.add(databaseColumn);
            } else {
                commonDatabaseColumns.add(databaseColumn);
            }
        }

        Map<Integer, List<DatabaseColumn>> listMap = new HashMap<>();
        if (!primaryKeyDatabaseColumns.isEmpty()){
            listMap.put(0, primaryKeyDatabaseColumns);
        }
        if (!commonDatabaseColumns.isEmpty()){
            listMap.put(1, commonDatabaseColumns);
        }
        return listMap;
    }

    public static DatabaseColumn parseField(Field field){
        if (!field.isAnnotationPresent(Column.class) && !field.isAnnotationPresent(ID.class)){
            return null;
        }
        if (field.isAnnotationPresent(Column.class)){
            Column column = field.getAnnotation(Column.class);
            String name = column.name().equals("") ? field.getName() : column.name();
            Class<?> type = column.type() == ColumnType.DEFAULT ? field.getType() : column.type().getTypes()[0];
            int length = column.length() == -1 ? ColumnType.getLengthByType(field.getType()) : column.length();

            boolean isNull = column.isNull();
            String comment = column.comment().equals("") ? null : column.comment();
            String defaultValue = column.defaultValue().equals("") ? null : column.defaultValue();
            boolean isPrimaryKey = column.key() == Key.DEFAULT ? false : true;
            return new DatabaseColumn(name, type, length, isNull, comment, defaultValue, isPrimaryKey);
        }

        if (field.isAnnotationPresent(ID.class)){
            ID column = field.getAnnotation(ID.class);
            String name = column.name().equals("") ? field.getName() : column.name();
            Class<?> type = column.type() == ColumnType.DEFAULT ? field.getType() : column.type().getTypes()[0];
            int length = column.length() == -1 ? ColumnType.getLengthByType(field.getType()) : column.length();

            boolean isNull = column.isNull();
            String comment = column.comment().equals("") ? null : column.comment();
            String defaultValue = column.defaultValue().equals("") ? null : column.defaultValue();
            return new DatabaseColumn(name, type, length, isNull, comment, defaultValue, true);
        }
        return null;

    }
}
