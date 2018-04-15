package org.baade.rat.example.entity;

import org.baade.rat.cp.anno.Column;
import org.baade.rat.cp.anno.Entity;
import org.baade.rat.cp.anno.ID;

@Entity
public class Human3 {

    @ID(name = "iddddd")
    private long id;

    @Column(isNull = false, comment = "名字", defaultValue = "hahas")
    private String name;

    @Column
    private String sex;
    @Column(defaultValue = "1")
    private int sexXS;

    @Column
    private String sex1;

    @Column
    private String sex2;

    @Column
    private String sex3;

    @Column
    private String sex4;

    @Column
    private String sex5;

    @Column
    private String sex6;

    @Column
    private String sex7;


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

    public String getSex1() {
        return sex1;
    }

    public void setSex1(String sex1) {
        this.sex1 = sex1;
    }

    public String getSex2() {
        return sex2;
    }

    public void setSex2(String sex2) {
        this.sex2 = sex2;
    }

    public String getSex3() {
        return sex3;
    }

    public void setSex3(String sex3) {
        this.sex3 = sex3;
    }

    public String getSex4() {
        return sex4;
    }

    public void setSex4(String sex4) {
        this.sex4 = sex4;
    }

    public String getSex5() {
        return sex5;
    }

    public void setSex5(String sex5) {
        this.sex5 = sex5;
    }

    public String getSex6() {
        return sex6;
    }

    public void setSex6(String sex6) {
        this.sex6 = sex6;
    }

    public String getSex7() {
        return sex7;
    }

    public void setSex7(String sex7) {
        this.sex7 = sex7;
    }
}
