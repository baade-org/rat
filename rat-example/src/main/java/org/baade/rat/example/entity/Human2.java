package org.baade.rat.example.entity;

import org.baade.rat.cp.ColumnType;
import org.baade.rat.cp.anno.Column;
import org.baade.rat.cp.anno.Entity;
import org.baade.rat.cp.anno.ID;

@Entity
public class Human2 {

    @ID(name = "id")
    private long id;

    @Column(isNull = false, comment = "名字", defaultValue = "hahas")
    private String name;

    @Column
    private String sex;
    @Column(defaultValue = "3")
    private int sexXS;

    @Column
    private char sexXS1;
    @Column(type = ColumnType.DEFAULT)
    private short sexXS2;
    @Column
    private float sexXS3;
    @Column(decimalsLength=10)
    private double sexXS4;
    @Column
    private boolean sexXS5;
    @Column
    private byte sexXS6;
    @Column
    private byte[] sexXS7;

    @Column(type = ColumnType.TEXT)
    private String sexXS8;
    @Column(type = ColumnType.TINYTEXT)
    private String sexXS9;
    @Column(type = ColumnType.MEDIUMTEXT)
    private String sexXS10;

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

    public char getSexXS1() {
        return sexXS1;
    }

    public void setSexXS1(char sexXS1) {
        this.sexXS1 = sexXS1;
    }

    public short getSexXS2() {
        return sexXS2;
    }

    public void setSexXS2(short sexXS2) {
        this.sexXS2 = sexXS2;
    }

    public float getSexXS3() {
        return sexXS3;
    }

    public void setSexXS3(float sexXS3) {
        this.sexXS3 = sexXS3;
    }

    public double getSexXS4() {
        return sexXS4;
    }

    public void setSexXS4(double sexXS4) {
        this.sexXS4 = sexXS4;
    }

    public boolean isSexXS5() {
        return sexXS5;
    }

    public void setSexXS5(boolean sexXS5) {
        this.sexXS5 = sexXS5;
    }

    public byte getSexXS6() {
        return sexXS6;
    }

    public void setSexXS6(byte sexXS6) {
        this.sexXS6 = sexXS6;
    }

    public byte[] getSexXS7() {
        return sexXS7;
    }

    public void setSexXS7(byte[] sexXS7) {
        this.sexXS7 = sexXS7;
    }

    public String getSexXS8() {
        return sexXS8;
    }

    public void setSexXS8(String sexXS8) {
        this.sexXS8 = sexXS8;
    }

    public String getSexXS9() {
        return sexXS9;
    }

    public void setSexXS9(String sexXS9) {
        this.sexXS9 = sexXS9;
    }

    public String getSexXS10() {
        return sexXS10;
    }

    public void setSexXS10(String sexXS10) {
        this.sexXS10 = sexXS10;
    }
}
