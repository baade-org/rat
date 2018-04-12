package org.baade.rat.cp;

import org.baade.rat.RATSession;
import org.baade.rat.core.scan.ClassScanner;
import org.baade.rat.core.scan.model.ClassModel;
import org.baade.rat.core.structure.tree.Tree;
import org.baade.rat.cp.anno.Entity;
import org.baade.rat.cp.crud.ICrud;
import org.baade.rat.cp.crud.mysql.MysqlCrud;
import org.baade.rat.cp.table.Table;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.List;
import java.util.Map;

public class DatabaseManager {
    private static final Logger log = LoggerFactory.getLogger(DatabaseManager.class);

    private static final String DEFAULT_FILE_IN_CLASSPATH = "rat.xml";

    private static RATSession session;

    public static void init(String configXml) throws Exception {
        File file = new File(configXml);
        if (file.isAbsolute()) {
            URL url = file.toURI().toURL();
            init(url);
            return;
        }
        // 不是绝对路径，就去classpath下找
        URL url = Thread.currentThread().getContextClassLoader().getResource(configXml);
        if (url == null){
            throw new RuntimeException("RAT not found config file[" + configXml + "] when Database init." );
        }
        init(url);
    }

    public static void init() throws Exception {
        init(DEFAULT_FILE_IN_CLASSPATH);

//        Tree<ClassModel> classModelTree = ClassScanner.doScan("org.baade");
//
//        List<ClassModel> instances = classModelTree.getInstances(classModelTree.getRootNode());
//
//        ICrud crud = new MysqlCrud();
//        for (ClassModel inst : instances) {
//            Class<?> clazz = Class.forName(inst.getClassFullName());
//            if (!clazz.isAnnotationPresent(Entity.class)) {
//                continue;
//            }
//            String createTableSql = crud.create(clazz);
//            log.debug(createTableSql);
//        }
    }


    public static void init(URL configXmlURL) throws Exception {
        JAXBContext context = JAXBContext.newInstance(DataSource.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        DataSource dataSource = (DataSource) unmarshaller.unmarshal(configXmlURL);
        log.debug("{}", dataSource);

        DatabaseInfo databaseInfo = dataSource.getDatabaseInfo();
        if (databaseInfo.getType() == null){
            throw new RuntimeException("RAT not support current db Driver Class[" + databaseInfo.getDriverClass() + "] when Database init." );
        }

        session = new RATSession(databaseInfo);

        Map<String, Table> allTables = session.getAllTables();

        Tree<ClassModel> classModelTree = ClassScanner.doScan(dataSource.getDatabaseInfo().getScanPackageName());
        List<ClassModel> instances = classModelTree.getInstances(classModelTree.getRootNode());

        ICrud crud = new MysqlCrud();
        for (ClassModel instance : instances) {
            Class<?> clazz = Class.forName(instance.getClassFullName());
            if (!clazz.isAnnotationPresent(Entity.class)) {
                continue;
            }
            String createTableSql = crud.create(clazz);
            log.debug(createTableSql);
            session.addUpdateSql(createTableSql);
        }

    }

    public static RATSession getSession() {
        return session;
    }
}
