package org.baade.rat.cp.anno.parse;

import org.baade.rat.cp.ColumnType;
import org.baade.rat.cp.Key;
import org.baade.rat.cp.anno.Column;
import org.baade.rat.cp.exception.EntityFieldNoGetterOrSetterMethod;
import org.baade.rat.cp.exception.EntityNoSupportDBColumnType;
import org.baade.rat.cp.table.TableColumn;
import org.baade.rat.cp.utils.ClassUtils;

import java.lang.reflect.Field;

public class ColumnParser extends AbstractParser<Field, TableColumn> implements IParser<Field, TableColumn> {

    @Override
    public TableColumn parse(Field field, Class<? extends TableColumn> clazz) {
        if (!field.isAnnotationPresent(Column.class)) {
            return null;
        }

        if (!ClassUtils.checkSetterAndGetterMethod(field)) {
            throw new EntityFieldNoGetterOrSetterMethod(field);
        }
        ColumnType columnType = getDBColumnType(field);
        if (columnType == null) {
            throw new EntityNoSupportDBColumnType(field);
        }

        Column column = field.getAnnotation(Column.class);
        String fieldName = field.getName();
        String name = column.name().equals("") ? fieldName : column.name();
        int length = column.length() == -1 ? columnType.getLength() : column.length();

        String nullable = column.isNull() ? "YES" : "NO";
        String comment = column.comment().equals("") ? null : column.comment();
        String defaultValue = column.defaultValue().equals("") ? "null" : column.defaultValue();
        boolean isPrimaryKey = (column.key() == Key.PRIMARY);

        String sizeStr = null;
        int decimalsLength = column.decimalsLength();
        if (length != -1) {
            sizeStr = String.valueOf(length);
            if (columnType.isNeedDecimalsLength()) {
                sizeStr += "," + decimalsLength;
            }
        }
        int typeValue = columnType.getTypeValue();
        String typeStr = columnType.getMysqlColumnType();

        TableColumn tableColumn = new TableColumn(name, typeValue, typeStr, length, decimalsLength, nullable, isPrimaryKey, comment, defaultValue, fieldName);
        tableColumn.setSizeStr(sizeStr);
        return tableColumn;
    }

    private ColumnType getDBColumnType(Field field) {
        if (field.isAnnotationPresent(Column.class)) {
            Column column = field.getAnnotation(Column.class);
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
