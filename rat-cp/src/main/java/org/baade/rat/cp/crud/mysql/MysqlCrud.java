package org.baade.rat.cp.crud.mysql;

import org.baade.rat.cp.ColumnType;
import org.baade.rat.cp.ConnectionPool;
import org.baade.rat.cp.RSql;
import org.baade.rat.cp.crud.AbstractCrud;
import org.baade.rat.cp.crud.ICrud;
import org.baade.rat.cp.table.Table;
import org.baade.rat.cp.table.TableColumn;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

public class MysqlCrud extends AbstractCrud implements ICrud {

    private static final Logger log = LoggerFactory.getLogger(MysqlCrud.class);

    @Override
    public String create(Table table) {
        StringBuilder createTableSql = new StringBuilder();
        createTableSql.append("CREATE TABLE ");
        String schema = table.getSchema();
        if (schema != null && !"".equals(schema)) {
            createTableSql.append("`" + schema + "`.");
        }
        createTableSql.append("`" + table.getName() + "` ( ");
        Map<String, TableColumn> columns = table.getColumns();
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
    public String create(TableColumn column) {
        //`content` varchar(5120) NOT NULL DEFAULT '{}' COMMENT 'content',
        return getColumnSql(column);
    }

    @Override
    public String create(Collection<TableColumn> columns) {
        StringBuilder createColumnSql = new StringBuilder();
        TableColumn[] columnOfTables = columns.toArray(new TableColumn[0]);
        for (int i = 0; i < columnOfTables.length; i++) {
            TableColumn column = columnOfTables[i];
            if (i == columns.size() - 1) {
                createColumnSql.append(create(column));
            } else {
                createColumnSql.append(create(column) + ",");
            }
        }
        return createColumnSql.toString();
    }

    @Override
    public String alter(Table table, TableColumn column) {
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
    public String alter(Table table, Collection<TableColumn> columns) {
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
        TableColumn[] columnOfTables = columns.toArray(new TableColumn[0]);
        for (int i = 0; i < columnOfTables.length; i++) {
            TableColumn column = columnOfTables[i];
            if (i == columns.size() - 1) {
                alterColumnSql.append(alter(null, column) + ";");
            } else {
                alterColumnSql.append(alter(null, column) + ", ");
            }
        }
        return alterColumnSql.toString();
    }

    @Override
    public String createPrimaryKey(Collection<TableColumn> columns) {
        StringBuilder createPrimaryKeySql = new StringBuilder();
        boolean isHavePrimaryKey = false;
        createPrimaryKeySql.append("PRIMARY KEY (");
        String pk = "";
        for (TableColumn column : columns) {
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

    private String getColumnSql(TableColumn column) {
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
        Map<String, TableColumn> columns = table.getColumns();
        StringBuilder insertSql = new StringBuilder();
        insertSql.append("INSERT INTO `" + table.getName() + "` ");
        String columnNames = "(";
        String values = "(";
        for (TableColumn columnOfTable : columns.values()) {
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

//    @Override
//    public Runnable update(RSql rSql) {
//        return () -> {
//            String sql = null;
//            try {
//                sql = rSql.getSql();
//                PreparedStatement preparedStatement = this.getPreparedStatement(rSql);
//                int r = preparedStatement.executeUpdate();
//                if (rSql.getReturnMethod() != null){
//                    System.out.println("update Thread: " + Thread.currentThread());
//                    rSql.getReturnMethod().accept(r);
//                }
//                if (rSql.isShowSql()){
//                    log.info("Run SQL Success: [{}]", sql);
//                }
//            } catch (SQLException e) {
//                e.printStackTrace();
//                log.error("Update No Return Error [{}]", sql, e);
//            }
//        };
//    }

    @Override
    public Callable update(RSql rSql) {
        return () -> {
            String sql = null;
            try {
                sql = rSql.getSql();
                PreparedStatement preparedStatement = this.getPreparedStatement(rSql);
                int r = preparedStatement.executeUpdate();

                if (!rSql.isSync()) {
                    if (rSql.getReturnMethod() != null) {
                        System.out.println("update Thread: " + Thread.currentThread());
                        rSql.getReturnMethod().accept(r);
                    }

                    return null;
                }

                if (rSql.isShowSql()) {
                    log.info("Run SQL Success: [{}]", sql);
                }
                return r;
            } catch (SQLException e) {
                e.printStackTrace();
                log.error("Update No Return Error [{}]", sql, e);
            }
            return null;
        };
    }
}
