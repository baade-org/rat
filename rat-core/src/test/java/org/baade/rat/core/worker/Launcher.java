package org.baade.rat.core.worker;

import org.baade.rat.core.scan.ClassScanner;
import org.baade.rat.core.scan.model.ClassModel;
import org.baade.rat.core.structure.tree.Tree;

import java.util.List;

public class Launcher {

    public static void main(String[] args) {
        Tree<ClassModel> classModelTree = ClassScanner.doScan("org.baade");
        List<ClassModel> classModels = classModelTree.getInstances(classModelTree.getRootNode());

    }
}
