package org.baade.rat.cp.anno.parse;

import org.baade.rat.cp.anno.Entity;
import org.baade.rat.cp.table.Table;
import org.baade.rat.cp.table.TableColumn;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public class EntityParser extends AbstractParser<Class<?>, Table> implements IParser<Class<?>, Table> {


    @Override
    public Table parse(Class<?> clazzOfModel, Class<? extends Table> clazz) throws ClassNotFoundException {
        if (!clazzOfModel.isAnnotationPresent(Entity.class)) {
            return null;
        }

        Entity entity = clazzOfModel.getAnnotation(Entity.class);
        String tableName = entity.tableName().equals("") ? clazzOfModel.getSimpleName().toLowerCase() : entity.tableName();
        String schemaName = entity.schema();
        String charsetName = entity.charset();

        Field[] declaredFields = clazzOfModel.getDeclaredFields();
        if (declaredFields == null || declaredFields.length == 0) {
            return null;
        }

        Collection<TableColumn> allColumnList = ParseType.ID.getParser().parse(Arrays.asList(declaredFields), TableColumn.class);
        Collection<TableColumn> columnList = ParseType.COLUMN.getParser().parse(Arrays.asList(declaredFields), TableColumn.class);
        allColumnList.addAll(columnList);

        if (allColumnList == null || allColumnList.isEmpty()) {
            return null;
        }

        Map<String, TableColumn> columns = new LinkedHashMap<>();
        Table table = new Table(schemaName, tableName, charsetName);
        allColumnList.stream().forEach(c -> {
            c.setTable(table);
            columns.put(c.getName(), c);
        });

        table.setColumns(columns);
        return table;
    }
}
