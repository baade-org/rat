package org.baade.rat.cp.crud;

import org.baade.rat.cp.RSql;
import org.baade.rat.cp.table.TableColumn;
import org.baade.rat.cp.table.Table;

import java.util.Collection;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

public interface ICrud {

    public String create(Table table);

    public String create(TableColumn column);

    public String create(Collection<TableColumn> columns);

    public String createPrimaryKey(Collection<TableColumn> columns);

    public String alter(Table table, TableColumn column);
    public String alter(Table table, Collection<TableColumn> columns);

    public String insert(Table table, Object entity) throws Exception;


//    public Runnable update(RSql rSql);

    public Callable update(RSql rSql);
}
