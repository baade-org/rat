package org.baade.rat.core.structure.tree;

public class TreeTest {

    public static void main(String[] args) {
        Tree<TestClassModel> tree = new Tree<>();

        String packageName = "com.sss.ddd";
        TestClassModel cm = new TestClassModel("MyTest", packageName);
        String[] strs = packageName.split("\\.");
        System.out.println(strs[0]);
        tree.put(cm, strs);


        TestClassModel cm3 = new TestClassModel("MyTest3", "");
        tree.put(cm3, cm3.getName());
        TestClassModel cm4 = new TestClassModel("MyTest4", "");
        tree.put(cm4, cm4.getName());

        TestClassModel cm2 = new TestClassModel("MyTest2", packageName);
        tree.put(cm, "com", "sss" , "ddd", cm.getName());
        tree.put(cm2, "com", "sss1" , "dd1", cm2.getName());

        System.out.println(tree);

        System.out.println("-------------------------------");
        TreeNode<TestClassModel> comTreeNode = tree.get("com", "sss1");

        System.out.println(tree.toDetailString(comTreeNode));

    }
}
