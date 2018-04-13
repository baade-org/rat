package org.baade.rat.cp.crud;

import org.baade.rat.cp.ConnectionThread;
import org.baade.rat.cp.RSql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public abstract class AbstractCrud implements ICrud{

    protected PreparedStatement getPreparedStatement(RSql rSql) throws SQLException {
        String sql = rSql.getSql();
        Connection connection = ((ConnectionThread) Thread.currentThread()).getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        System.out.println("getPreparedStatement Thread: " + Thread.currentThread());
        return preparedStatement;
    }
}
