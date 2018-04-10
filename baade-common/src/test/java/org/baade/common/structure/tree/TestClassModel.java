package org.baade.common.structure.tree;

public class TestClassModel {

    private String name;
    private String packageName;

    public TestClassModel(String name, String packageName) {
        this.name = name;
        this.packageName = packageName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    @Override
    public String toString() {
        return "TestClassModel{" +
                "name='" + name + '\'' +
                ", packageName='" + packageName + '\'' +
                '}';
    }
}
