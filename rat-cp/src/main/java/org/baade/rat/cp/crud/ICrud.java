package org.baade.rat.cp.crud;

import org.baade.rat.cp.table.ColumnOfTable;
import org.baade.rat.cp.table.Table;

import java.util.Collection;

public interface ICrud {

    public String create(Table table);

    public String create(ColumnOfTable column);

    public String create(Collection<ColumnOfTable> columns);

    public String createPrimaryKey(Collection<ColumnOfTable> columns);

    public String alter(Table table, ColumnOfTable column);
    public String alter(Table table, Collection<ColumnOfTable> columns);

    public String insert(Table table, Object entity) throws Exception;



}
