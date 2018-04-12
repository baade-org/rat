package org.baade.rat.cp.crud.mysql;

import org.baade.rat.cp.ColumnType;
import org.baade.rat.cp.anno.Entity;
import org.baade.rat.cp.crud.ICrud;
import org.baade.rat.cp.table.ColumnOfTable;
import org.baade.rat.cp.table.Table;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Map;

public class MysqlCrud implements ICrud {


    @Override
    public String create(Table table) {
        StringBuilder createTableSql = new StringBuilder();
        createTableSql.append("CREATE TABLE ");
        String schema = table.getSchema();
        if (schema != null && !"".equals(schema)) {
            createTableSql.append("`" + schema + "`.");
        }
        createTableSql.append("`" + table.getName() + "` ( ");
        Map<String, ColumnOfTable> columns = table.getColumns();
        String createColumnSql = create(columns.values());
        createTableSql.append(createColumnSql);
        String primaryKeySql = createPrimaryKey(columns.values());
        if (primaryKeySql != null) {
            createTableSql.append("," + primaryKeySql);
        }
        createTableSql.append(") ENGINE=InnoDB DEFAULT CHARSET=" + table.getCharsetName() + ";");
        return createTableSql.toString();
    }

    @Override
    public String create(ColumnOfTable column) {
        //`content` varchar(5120) NOT NULL DEFAULT '{}' COMMENT 'content',
        return getColumnSql(column);
    }

    @Override
    public String create(Collection<ColumnOfTable> columns) {
        StringBuilder createColumnSql = new StringBuilder();
        ColumnOfTable[] columnOfTables = columns.toArray(new ColumnOfTable[0]);
        for (int i = 0; i < columnOfTables.length; i++) {
            ColumnOfTable column = columnOfTables[i];
            if (i == columns.size() - 1) {
                createColumnSql.append(create(column));
            } else {
                createColumnSql.append(create(column) + ",");
            }
        }
        return createColumnSql.toString();
    }

    @Override
    public String alter(Table table, ColumnOfTable column) {
//      ALTER TABLE `human4`
//MODIFY COLUMN `iddddd`  bigint(11) NOT NULL FIRST ,
//MODIFY COLUMN `sex`  varchar(2552) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL AFTER `name`;
        StringBuilder alterColumnSql = new StringBuilder();
        if (table != null) {
            alterColumnSql.append("ALTER TABLE ");
            String schema = table.getSchema();
            if (schema != null && !"".equals(schema)) {
                alterColumnSql.append("`" + schema + "`.");
            }
            alterColumnSql.append("`" + table.getName() + "` ");
        }

        if (column.isModify()) {
            alterColumnSql.append("MODIFY COLUMN ");
        } else {
            alterColumnSql.append("ADD COLUMN ");
        }
        alterColumnSql.append(getColumnSql(column) + " ");

        alterColumnSql.append(column.getAlertAfter());

        return alterColumnSql.toString();
    }

    @Override
    public String alter(Table table, Collection<ColumnOfTable> columns) {
        if (columns == null && columns.isEmpty()) {
            return null;
        }
        StringBuilder alterColumnSql = new StringBuilder();
        alterColumnSql.append("ALTER TABLE ");
        String schema = table.getSchema();
        if (schema != null && !"".equals(schema)) {
            alterColumnSql.append("`" + schema + "`.");
        }
        alterColumnSql.append("`" + table.getName() + "` ");
        ColumnOfTable[] columnOfTables = columns.toArray(new ColumnOfTable[0]);
        for (int i = 0; i < columnOfTables.length; i++) {
            ColumnOfTable column = columnOfTables[i];
            if (i == columns.size() - 1) {
                alterColumnSql.append(alter(null, column) + ";");
            } else {
                alterColumnSql.append(alter(null, column) + ", ");
            }
        }
        return alterColumnSql.toString();
    }

    @Override
    public String createPrimaryKey(Collection<ColumnOfTable> columns) {
        StringBuilder createPrimaryKeySql = new StringBuilder();
        boolean isHavePrimaryKey = false;
        createPrimaryKeySql.append("PRIMARY KEY (");
        String pk = "";
        for (ColumnOfTable column : columns) {
            if (column.isPrimaryKey()) {
                isHavePrimaryKey = true;
                pk += "`" + column.getName() + "`,";
            }
        }
        if (!isHavePrimaryKey) {
            return null;
        }
        pk = pk.substring(0, pk.length() - 1); // 去掉最后一个逗号
        createPrimaryKeySql.append(pk);
        createPrimaryKeySql.append(")");
        return createPrimaryKeySql.toString();
    }

    private String getColumnSql(ColumnOfTable column) {
        StringBuilder columnSql = new StringBuilder();
        columnSql.append("`" + column.getName() + "` ");

        columnSql.append(column.getTypeStr());
        if (column.getSize() != -1) {
            columnSql.append("(" + column.getSizeStr() + ") ");
        } else {
            columnSql.append(" ");
        }

        if (column.getNullable().equals("YES") && !column.isPrimaryKey()) {
            columnSql.append("NULL");
        } else {
            columnSql.append("NOT NULL");
        }
        String defaultValue = column.getDefaultValue();
        if (defaultValue != null && !"null".equals(defaultValue)) {
            columnSql.append(" DEFAULT ");
            if (column.getType() == ColumnType.STRING.getTypeValue()) {
                columnSql.append("'");
            }
            columnSql.append(defaultValue);
            if (column.getType() == ColumnType.STRING.getTypeValue()) {
                columnSql.append("'");
            }
        }
        String comment = column.getComment();
        if (comment != null && !"".equals(comment)) {
            columnSql.append(" COMMENT '" + comment + "'");
        }
        return columnSql.toString();
    }

    @Override
    public String insert(Table table, Object entity) throws Exception {
        //INSERT INTO `demo_activity_recorder` VALUES ('7666289631452291922', '17', '17', '1504627200000', '7666289631452241921', '0', '0', '0', '{\"r\":{},\"ars\":[],\"d\":100,\"days\":0,\"isr\":0,\"m\":4}');
        Map<String, ColumnOfTable> columns = table.getColumns();
        StringBuilder insertSql = new StringBuilder();
        insertSql.append("INSERT INTO `" + table.getName() + "` ");
        String columnNames = "(";
        String values = "(";
        for (ColumnOfTable columnOfTable : columns.values()) {
            String columnName = columnOfTable.getName();
            String fieldName = columnOfTable.getFieldName();
            PropertyDescriptor pd = new PropertyDescriptor(fieldName, entity.getClass());
            Method readMethod = pd.getReadMethod();
            readMethod.setAccessible(true);
            Object value = readMethod.invoke(entity);

            if (value == null) {
                continue;
            }

            columnNames += "`" + columnName + "`, ";
            values += "'" + value + "', ";
        }
        columnNames = columnNames.substring(0, columnNames.length() - 2) + ") ";
        values = values.substring(0, values.length() - 2) + ");";

        insertSql.append(columnNames);
        insertSql.append("VALUES");
        insertSql.append(values);
        return insertSql.toString();
    }
}
