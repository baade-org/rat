package org.baade.rat.core.scan.model;

public class ClassModel {

    private String packageName;
    private String className;
    private String classFullName;

    public ClassModel(String classFullName) {
        if(classFullName.endsWith(".class")){
            classFullName = classFullName.substring(0, classFullName.lastIndexOf(".class"));
        }

        int i = classFullName.lastIndexOf(".");
        if (i == -1){// 说明没有包名
            this.packageName = null;
            this.className = classFullName;
        } else {
            this.className = classFullName.substring(classFullName.lastIndexOf(".") + 1, classFullName.length());
            this.packageName = classFullName.substring(0, classFullName.lastIndexOf("."));
        }

        this.classFullName = classFullName;
    }

    public ClassModel(String packageName, String className) {
        this.packageName = packageName;
        this.className = className;
        this.classFullName = (packageName == null ? "" : packageName + ".")  + className;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String[] getPath(){
        String fullName = this.packageName + "." + this.className;
        if (this.packageName == null){
            fullName = this.className;
        }
        return fullName.split("\\.");
    }

    public String getClassFullName() {
        return classFullName;
    }

    public void setClassFullName(String classFullName) {
        this.classFullName = classFullName;
    }

    @Override
    public String toString() {
        return "ClassModel{" +
                "packageName='" + (packageName == null ? "" : packageName) + '\'' +
                ", className='" + className + '\'' +
                '}';
    }
}
