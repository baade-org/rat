package org.baade.rat.example.entity;

import org.baade.rat.cp.anno.Column;
import org.baade.rat.cp.anno.Entity;
import org.baade.rat.cp.anno.ID;

@Entity
public class Human6 {

    @ID(name = "id" , length = 15)
    private long id;

    @Column(isNull = false, comment = "名字", defaultValue = "ddd")
    private String name;

    @Column
    private String sex;
    @Column(defaultValue = "3")
    private int sexXS;

    @Column(defaultValue = "3")
    private int sexXSsss;

    @Column(defaultValue = "3")
    private int sexXSsssddd;

    @Column(defaultValue = "3")
    private int sexXSsssdddd;

    @Column(defaultValue = "3")
    private int sexXSsssddddd;

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

    public int getSexXSsssddd() {
        return sexXSsssddd;
    }

    public void setSexXSsssddd(int sexXSsssddd) {
        this.sexXSsssddd = sexXSsssddd;
    }

    public int getSexXSsssdddd() {
        return sexXSsssdddd;
    }

    public void setSexXSsssdddd(int sexXSsssdddd) {
        this.sexXSsssdddd = sexXSsssdddd;
    }

    public int getSexXSsssddddd() {
        return sexXSsssddddd;
    }

    public void setSexXSsssddddd(int sexXSsssddddd) {
        this.sexXSsssddddd = sexXSsssddddd;
    }
}
