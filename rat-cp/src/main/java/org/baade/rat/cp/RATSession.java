package org.baade.rat.cp;

import org.baade.rat.cp.anno.Entity;
import org.baade.rat.cp.crud.ICrud;
import org.baade.rat.cp.table.Table;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.*;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class RATSession {

    private static final Logger log = LoggerFactory.getLogger(RATSession.class);

    private ConnectionPool pool;
    private DatabaseInfo databaseInfo;
    private ICrud curd;


    public RATSession(DatabaseInfo databaseInfo) throws SQLException, ClassNotFoundException {
        log.info("########### RAT Session init start ##########");

        this.databaseInfo = databaseInfo;
        this.curd = initCrud();

        this.pool = new ConnectionPool(databaseInfo, this.curd);
        log.info("##      SCAN PKG: [{}]", this.databaseInfo.getScanPackageName());
        log.info("##       DB TYPE: [{}]", this.databaseInfo.getType());
        log.info("##        DB URL: [{}]", this.databaseInfo.getUrl());
        log.info("##     DB Schema: [{}]", this.databaseInfo.getSchema());
        log.info("##     Pool size: [{}]", this.databaseInfo.getPoolSize());
        log.info("##  Driver Class: [{}]", this.databaseInfo.getDriverClass());
        log.info("## DB Properties: [{}]", this.databaseInfo.getDbProperties());
        log.info("########### RAT Session init Done ##########");
    }
    private ICrud initCrud() {
        DriverClassNameDefault type = DriverClassNameDefault.getType(databaseInfo.getDriverClass());
        if (type == null) {
            throw new RuntimeException("RAT not support current driver class=" + databaseInfo.getDriverClass());
        }
        return type.newCrud();
    }


    public DatabaseInfo getDatabaseInfo() {
        return databaseInfo;
    }

    public ICrud getCurd() {
        return curd;
    }

    public Map<String, Table> getAllTables() throws InterruptedException, ExecutionException, SQLException {
        return this.pool.getAllTables();
    }

    public ResultSet addSelectSql(String sql, boolean isAsync) throws ExecutionException, InterruptedException {
        return pool.testSelect();
    }

    public void addSelectSql(String sql) throws ExecutionException, InterruptedException {
        addSelectSql(sql, true);
    }

    public ResultSet testSelect() throws ExecutionException, InterruptedException {
        return pool.testSelect();
    }

    public void update(String sql){
        pool.update(sql);
    }

    public void updateSync(String sql) throws ExecutionException, InterruptedException {
        updateSync(sql, null);
    }
    public void updateSync(String sql, Consumer<Object> returnMethod) throws ExecutionException, InterruptedException {
        pool.updateSync(sql, returnMethod);
    }

    public void update(String sql, Consumer<Object> returnMethod){
        pool.update(sql, returnMethod);
    }


    public void save(Object entity) throws Exception {
        Class<?> clazz = entity.getClass();
        if (!clazz.isAnnotationPresent(Entity.class)) {
            log.error("RATSession Save is not @Entity class=[{}].", entity.getClass());
            return;
        }
//        Table table = DatabaseManager.getAllTables().get(entity.getClass());
//        if (table == null) {
//            log.error("RATSession Save not support @Entity class=[{}].", entity.getClass());
//            return;
//        }
//        String insertSql = curd.insert(table, entity);
//        addUpdateSql(insertSql);
    }
}
