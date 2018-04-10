package org.baade.common.scan;

import org.baade.common.scan.model.ClassModel;
import org.baade.common.structure.tree.Tree;

import java.net.URL;

public class ClassScaner {

    private String basePackageName;

    private Tree<ClassModel> tree;

    private ClassLoader classLoader;

    public ClassScaner(String basePackageName) {
        this.basePackageName = basePackageName;
        this.tree = new Tree<>();
        this.classLoader = getClass().getClassLoader();
    }


    public void doScan(){
        URL resource = this.classLoader.getResource("");
        System.out.println(resource);
    }


}
