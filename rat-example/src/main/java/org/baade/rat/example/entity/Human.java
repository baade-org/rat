package org.baade.rat.example.entity;

import org.baade.rat.cp.anno.Column;
import org.baade.rat.cp.anno.Entity;
import org.baade.rat.cp.anno.ID;

@Entity(tableName = "human_xxx")
public class Human {

    @ID(name = "iddddd")
    private long id;

    @Column(isNull = false, comment = "名字", defaultValue = "haha")
    private String name;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
