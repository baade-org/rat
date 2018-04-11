package org.baade.rat.core.scan;

import org.baade.rat.core.scan.model.ClassModel;
import org.baade.rat.core.structure.tree.Tree;
import org.baade.rat.core.structure.tree.TreeNode;

public class TestClassScanner {

    public static void main(String[] args) {
        Tree<ClassModel> tree = ClassScanner.doScan("org.baade");
        System.out.println(tree.toString());


        System.out.println("------------\"org\", \"baade\"-------------------");
//        TreeNode<ClassModel> org = tree.get("org", "baade");

//        System.out.println(tree.toDetailString(org));

        System.out.println("--------------FormPreviewFrame-----------------");
        TreeNode<ClassModel> orgddd = tree.get("FormPreviewFrame");

        System.out.println(tree.toDetailString(orgddd));

        System.out.println("--------------java lang-----------------");
        TreeNode<ClassModel> java_lang = tree.get("java", "lang");

        System.out.println(tree.toDetailString(java_lang));
    }
}
