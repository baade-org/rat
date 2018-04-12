package org.baade.rat.cp.crud.mysql;

import org.baade.rat.core.SystemProperty;
import org.baade.rat.cp.ColumnType;
import org.baade.rat.cp.crud.ICrud;
import org.baade.rat.cp.database.DatabaseColumn;
import org.baade.rat.cp.database.DatabaseTable;
import org.baade.rat.cp.database.DatabaseUtils;

import java.util.List;

public class MysqlCrud implements ICrud {

    public String create(Class<?> clazz) {
        DatabaseTable databaseTable = DatabaseUtils.parse(clazz);
        if (databaseTable == null) {
            return null;
        }
        StringBuilder createTableSql = new StringBuilder();
        createTableSql.append(SystemProperty.LINE_SEPARATOR.getValue() + "CREATE TABLE ");
        if (databaseTable.getDatabaseName() != null) {
            createTableSql.append("`" + databaseTable.getDatabaseName() + "`.");
        }
        createTableSql.append("`" + databaseTable.getTableName() + "` ( " + SystemProperty.LINE_SEPARATOR.getValue());

        List<DatabaseColumn> primaryKeyColumns = databaseTable.getPrimaryKeyDatabaseColumns();
        List<DatabaseColumn> commonColumns = databaseTable.getCommonDatabaseColumns();

        for (DatabaseColumn column : primaryKeyColumns) {
            createTableSql.append(createColumnSql(column));
        }
        for (DatabaseColumn column : commonColumns) {
            createTableSql.append(createColumnSql(column));
        }

        if (primaryKeyColumns != null && primaryKeyColumns.size() != 0){
            createTableSql.append("\tPRIMARY KEY (");
            String pk = "";
            for (DatabaseColumn column : primaryKeyColumns) {
                pk += "`" + column.getName() + "`,";
            }
            pk = pk.substring(0, pk.length() - 1); // 去掉最后一个逗号
            createTableSql.append(pk);
            createTableSql.append(")" + SystemProperty.LINE_SEPARATOR.getValue());
        }
        createTableSql.append(") ENGINE=InnoDB DEFAULT CHARSET=" + databaseTable.getCharset() + ";" + SystemProperty.LINE_SEPARATOR.getValue());
        return createTableSql.toString();
    }

    private String createColumnSql(DatabaseColumn column) {
        //`id` bigint(20) NOT NULL,
        //`humanId` bigint(20) NOT NULL COMMENT '玩家ID'
        //`content` varchar(5120) NOT NULL DEFAULT '{}' COMMENT 'content',
        String sql = "\t`" + column.getName() + "` ";

        sql += ColumnType.getMysqlType(column.getType()) + "(" + column.getLength() + ") ";
        if (column.isNull() && !column.isPrimaryKey()) {
            sql += "NULL ";
        } else {
            sql += "NOT NULL ";
        }

        String comment = column.getComment();
        if (comment != null && !"".equals(comment)) {
            sql += "COMMENT '" + comment + "' ";
        }
        sql +=  "," + SystemProperty.LINE_SEPARATOR.getValue();
        return sql;
    }
}
