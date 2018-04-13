package org.baade.rat.example.entity;

import org.baade.rat.cp.RATContext;
import org.baade.rat.cp.RATSession;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

public class TestEntity {

    public static void main(String[] args) throws Exception {
//        DatabaseManager.init();
//
//        for (int i = 0; i < 10; i++) {
//            ResultSet resultSet = DatabaseManager.getSession().addSelectSql("", false);
//            ResultSetMetaData metaData = resultSet.getMetaData();
//            String ss = "";
//            while (resultSet.next()){
//                int columnCount = metaData.getColumnCount();
//
//                for (int j = 1; j <= columnCount; j++) {
//                    ss += "\t" + resultSet.getString(j);
//                }
//                ss += "\t";
//
//            }
//            System.out.println(ss);
//        }
//        Human4 h = new Human4();
//        h.setId(4);
//
//        DatabaseManager.getSession().save(h);
        System.out.println(Thread.currentThread());
        RATContext context = new RATContext();

        for (int i = 0; i < 10; i++) {
            RATSession session = context.getSession();
            ResultSet resultSet = session.testSelect();
            ResultSetMetaData metaData = resultSet.getMetaData();
            String ss = "";
            while (resultSet.next()){
                int columnCount = metaData.getColumnCount();

                for (int j = 1; j <= columnCount; j++) {
                    ss += "\t" + resultSet.getString(j);
                }
                ss += "\t";

            }
//            System.out.println(Thread.currentThread() + " : " + ss);
//            System.out.println(Thread.currentThread());
        }

        Thread.sleep(15000);
    }
}
