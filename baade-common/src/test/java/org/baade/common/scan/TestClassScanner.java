package org.baade.common.scan;

import org.baade.common.scan.model.ClassModel;
import org.baade.common.structure.tree.Tree;

public class TestClassScaner {

    public static void main(String[] args) {
        ClassScanner cs = new ClassScanner("");

        cs.doScan();

        Tree<ClassModel> tree = cs.getTree();
        System.out.println(tree.toString());
    }
}
