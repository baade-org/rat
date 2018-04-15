package org.baade.rat.cp;

import org.baade.rat.cp.anno.Entity;
import org.baade.rat.cp.crud.ICrud;
import org.baade.rat.cp.table.Table;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.net.URL;
import java.util.Map;

public class DatabaseManager {
    private static final Logger log = LoggerFactory.getLogger(DatabaseManager.class);

    private static final String DEFAULT_FILE_IN_CLASSPATH = "rat.xml";

    private static RATSession session;

    private static Map<Class<? extends Entity>, Table> allTables;

    public static void init(String configXml) throws Exception {
//        File file = new File(configXml);
//        if (file.isAbsolute()) {
//            URL url = file.toURI().toURL();
//            init(url);
//            return;
//        }
//        // 不是绝对路径，就去classpath下找
//        URL url = Thread.currentThread().getContextClassLoader().getResource(configXml);
//        if (url == null) {
//            throw new RuntimeException("RAT not found config file[" + configXml + "] when Database init.");
//        }
//        init(url);
    }

    public static void init() throws Exception {
        init(DEFAULT_FILE_IN_CLASSPATH);
    }


    public static void init(URL configXmlURL) throws Exception {
//        JAXBContext context = JAXBContext.newInstance(DataSource.class);
//        Unmarshaller unmarshaller = context.createUnmarshaller();
//        DataSource dataSource = (DataSource) unmarshaller.unmarshal(configXmlURL);
//        log.debug("{}", dataSource);
//
//        DatabaseInfo databaseInfo = dataSource.getDatabaseInfo();
//        if (databaseInfo.getType() == null) {
//            throw new RuntimeException("RAT not support current db Driver Class[" + databaseInfo.getDriverClass() + "] when Database init.");
//        }
//        Tree<ClassModel> classModelTree = ClassScanner.doScan(dataSource.getDatabaseInfo().getScanPackageName());
//        List<ClassModel> instances = classModelTree.getInstances(classModelTree.getRootNode());
//
//        allTables = DBUtils.parse(instances, databaseInfo.getSchema());
//
//        session = new RATSession(databaseInfo);
//        checkTableAndAlert(session, dataSource);
    }

    private static void checkTableAndAlert(RATSession session, DataSource dataSource) throws Exception {
//        Map<String, Table> allTablesFromDB = session.getAllTables();
//        DatabaseInfo databaseInfo = dataSource.getDatabaseInfo();
//
//        DriverClassNameDefault type = DriverClassNameDefault.getType(databaseInfo.getDriverClass());
//        if (type == null) {
//            throw new RuntimeException("RAT not support current driver class=" + databaseInfo.getDriverClass());
//        }
//        ICrud crud = type.newCrud();
//        for (Map.Entry<Class<? extends Entity>, Table> tableEntryFromClazz : allTables.entrySet()) {
//            Table tableFromClazz = tableEntryFromClazz.getValue();
//            String tableNameFromClazz = tableFromClazz.getName();
//            Table tableFromDB = allTablesFromDB.get(tableNameFromClazz);
//            if (tableFromDB == null) {
//                // 说明没有创建，就创建table
//                String createTableSql = crud.create(tableFromClazz);
//                session.addUpdateSql(createTableSql);
//            } else {
//                // 更新table结构
//                alertColumns(crud, tableFromClazz, tableFromDB);
//
//            }
//        }
    }

    private static void alertColumns(ICrud crud, Table tableFromClazz, Table tableFromDB) {
//        List<ColumnOfTable> columnsFromClazzList = Arrays.asList(tableFromClazz.getColumns().values().toArray(new ColumnOfTable[0]));
//        Map<String, ColumnOfTable> columnsFromDB = tableFromDB.getColumns();
//
//        List<ColumnOfTable> columnsFromDBList = Arrays.asList(tableFromDB.getColumns().values().toArray(new ColumnOfTable[0]));
//        String lastColumnName = columnsFromDBList.get(columnsFromDBList.size() - 1).getName();
//        List<ColumnOfTable> needAlertColumns = new ArrayList<>();
//        for (int i = 0; i < columnsFromClazzList.size(); i++) {
//            ColumnOfTable columnFromClazz = columnsFromClazzList.get(i);
//            String columnNameFromClazz = columnFromClazz.getName();
//            ColumnOfTable columnFromDB = columnsFromDB.get(columnNameFromClazz);
//            if (columnFromDB == null) {
//                // 说明table没有该字段,alert add
//                columnFromClazz.setModify(false);
//                // AFTER `name`
//                columnFromClazz.setAlertAfter("AFTER `" + lastColumnName + "`");
//                needAlertColumns.add(columnFromClazz);
//                lastColumnName = columnNameFromClazz;
//
//            } else {
//                if (columnFromDB.getSize() < columnFromClazz.getSize() || // 数据库字段长度小于类中定义的长度
//                        !columnFromClazz.getNullable().equals(columnFromDB.getNullable()) || // 是否为空不一致
//                        !columnFromClazz.getDefaultValue().equals(columnFromDB.getDefaultValue()) // 默认值不一致
//                        ) {
//                    if (i == 0){
//                        columnFromClazz.setAlertAfter("FIRST");
//                    } else {
//                        ColumnOfTable preColumn = columnsFromClazzList.get(i - 1);
//                        columnFromClazz.setAlertAfter("AFTER `" + preColumn.getName() + "`");
//                    }
//                    needAlertColumns.add(columnFromClazz);
//                }
//            }
//        }
//        if (!needAlertColumns.isEmpty()) {
//            String alterSql = crud.alter(tableFromClazz, needAlertColumns);
//            session.addUpdateSql(alterSql);
//        }
    }

    public static RATSession getSession() {
        return session;
    }

    public static void setSession(RATSession session) {
        DatabaseManager.session = session;
    }

    public static Map<Class<? extends Entity>, Table> getAllTables() {
        return allTables;
    }

    public static void setAllTables(Map<Class<? extends Entity>, Table> allTables) {
        DatabaseManager.allTables = allTables;
    }
}
