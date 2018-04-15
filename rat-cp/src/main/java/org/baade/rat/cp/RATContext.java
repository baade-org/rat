package org.baade.rat.cp;

import org.baade.rat.core.scan.ClassScanner;
import org.baade.rat.core.scan.model.ClassModel;
import org.baade.rat.core.structure.tree.Tree;
import org.baade.rat.cp.anno.parse.ParseType;
import org.baade.rat.cp.crud.ICrud;
import org.baade.rat.cp.table.Table;
import org.baade.rat.cp.table.TableColumn;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.ExecutionException;

public class RATContext {

    private static final Logger log = LoggerFactory.getLogger(RATContext.class);

    private static final String DEFAULT_FILE_IN_CLASSPATH = "rat.xml";

    private String ratXML;
    private RATSession session;
    private Map<String, Table> allTables;

    public RATContext() throws ClassNotFoundException, MalformedURLException, SQLException, JAXBException, ExecutionException, InterruptedException {
        this(DEFAULT_FILE_IN_CLASSPATH);
    }

    public RATSession getSession() {
        return session;
    }

    public RATContext(String ratXML) throws ClassNotFoundException, MalformedURLException, SQLException, JAXBException, ExecutionException, InterruptedException {
        this.ratXML = ratXML;
        allTables = new HashMap<>();
        init();
    }

    public void init() throws MalformedURLException, JAXBException, ClassNotFoundException, SQLException, ExecutionException, InterruptedException {
        File file = new File(ratXML);
        if (file.isAbsolute()) {
            URL url = file.toURI().toURL();
            init(url);
            return;
        }
        // 不是绝对路径，就去classpath下找
        URL url = Thread.currentThread().getContextClassLoader().getResource(ratXML);
        if (url == null) {
            throw new RuntimeException("RAT not found config file[" + ratXML + "] when Database init.");
        }
        init(url);
    }

    public void init(URL configXmlURL) throws JAXBException, ClassNotFoundException, SQLException, ExecutionException, InterruptedException {
        JAXBContext context = JAXBContext.newInstance(DataSource.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        DataSource dataSource = (DataSource) unmarshaller.unmarshal(configXmlURL);
        log.debug("{}", dataSource);

        DatabaseInfo databaseInfo = dataSource.getDatabaseInfo();
        if (databaseInfo.getType() == null) {
            throw new RuntimeException("RAT not support current db Driver Class[" + databaseInfo.getDriverClass() + "] when Database init.");
        }
        Tree<ClassModel> classModelTree = ClassScanner.doScan(dataSource.getDatabaseInfo().getScanPackageName());
        List<ClassModel> classModels = classModelTree.getInstances(classModelTree.getRootNode());

        Collection<Table> allTablesFromClazz = ParseType.CLASS_MODEL.getParser().parse(classModels, Table.class);
        allTablesFromClazz.stream().forEach(t -> allTables.put(t.getName(), t));


        session = new RATSession(databaseInfo);
        checkTableAndAlert();
    }

    private void checkTableAndAlert() throws InterruptedException, ExecutionException, SQLException {
        Map<String, Table> allTablesFromDB = session.getAllTables();
        DatabaseInfo databaseInfo = session.getDatabaseInfo();
        ICrud crud = session.getCurd();
        for (Map.Entry<String, Table> tableEntryFromClazz : allTables.entrySet()) {
            Table tableFromClazz = tableEntryFromClazz.getValue();
            tableFromClazz.setSchema(databaseInfo.getSchema());
            String tableNameFromClazz = tableFromClazz.getName();
            Table tableFromDB = allTablesFromDB.get(tableNameFromClazz);
            if (tableFromDB == null) {
                // 说明没有创建，就创建table
                String createTableSql = crud.create(tableFromClazz);
                System.out.println("checkTableAndAlert Thread: " + Thread.currentThread());
                session.updateSync(createTableSql, this::sssssss);
                System.out.println("checkTableAndAlert aaaa Thread: " + Thread.currentThread());
            } else {
                // 更新table结构
                alertColumns(crud, tableFromClazz, tableFromDB);

            }
        }
    }

    private void sssssss(Object obj){
        System.out.println("sssssss Thread: " + Thread.currentThread());
        System.out.println("sssssss dddddddddddddddddddddddddddddddddd: " + obj);
    }

    private void alertColumns(ICrud crud, Table tableFromClazz, Table tableFromDB) throws ExecutionException, InterruptedException {
        List<TableColumn> columnsFromClazzList = Arrays.asList(tableFromClazz.getColumns().values().toArray(new TableColumn[0]));
        Map<String, TableColumn> columnsFromDB = tableFromDB.getColumns();

        List<TableColumn> columnsFromDBList = Arrays.asList(tableFromDB.getColumns().values().toArray(new TableColumn[0]));
        String lastColumnName = columnsFromDBList.get(columnsFromDBList.size() - 1).getName();
        List<TableColumn> needAlertColumns = new ArrayList<>();
        for (int i = 0; i < columnsFromClazzList.size(); i++) {
            TableColumn columnFromClazz = columnsFromClazzList.get(i);
            String columnNameFromClazz = columnFromClazz.getName();
            TableColumn columnFromDB = columnsFromDB.get(columnNameFromClazz);
            if (columnFromDB == null) {
                // 说明table没有该字段,alert add
                columnFromClazz.setModify(false);
                // AFTER `name`
                columnFromClazz.setAlertAfter("AFTER `" + lastColumnName + "`");
                needAlertColumns.add(columnFromClazz);
                lastColumnName = columnNameFromClazz;

            } else {
                if (columnFromDB.getSize() < columnFromClazz.getSize() || // 数据库字段长度小于类中定义的长度
                        !columnFromClazz.getNullable().equals(columnFromDB.getNullable()) || // 是否为空不一致
                        !columnFromClazz.getDefaultValue().equals(columnFromDB.getDefaultValue()) // 默认值不一致
                        ) {
                    if (i == 0){
                        columnFromClazz.setAlertAfter("FIRST");
                    } else {
                        TableColumn preColumn = columnsFromClazzList.get(i - 1);
                        columnFromClazz.setAlertAfter("AFTER `" + preColumn.getName() + "`");
                    }
                    needAlertColumns.add(columnFromClazz);
                }
            }
        }
        if (!needAlertColumns.isEmpty()) {
            String alterSql = crud.alter(tableFromClazz, needAlertColumns);
            session.update(alterSql, this::sssssssdd);
//            session.updateSync(alterSql, this::sssssssdd);
        }
    }

    private void sssssssdd(Object obj){
        System.out.println("sssssssdd Thread: " + Thread.currentThread());
        System.out.println("sssssss dddddddddddddddddddddddddddddddddd: " + obj);
    }
}
