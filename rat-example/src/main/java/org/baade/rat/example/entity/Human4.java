package org.baade.rat.example.entity;

import org.baade.rat.cp.anno.Column;
import org.baade.rat.cp.anno.Entity;
import org.baade.rat.cp.anno.ID;

@Entity
public class Human4 {

    @ID(name = "iddddd" , length = 15)
    private long id;

    @Column(isNull = false, comment = "名字", defaultValue = "ddd")
    private String name;

    @Column
    private String sex;
    @Column(defaultValue = "3")
    private int sexXS;

    @Column(defaultValue = "3")
    private int sexXSsss;

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

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getSexXS() {
        return sexXS;
    }

    public void setSexXS(int sexXS) {
        this.sexXS = sexXS;
    }

    public int getSexXSsss() {
        return sexXSsss;
    }

    public void setSexXSsss(int sexXSsss) {
        this.sexXSsss = sexXSsss;
    }
}
