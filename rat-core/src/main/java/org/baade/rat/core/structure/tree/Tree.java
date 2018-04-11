package org.baade.rat.core.structure.tree;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;

/**
 * The type Tree.
 *
 * @param <T> the type parameter
 */
public class Tree<T> {

    private static final Logger log = LoggerFactory.getLogger(Tree.class);

    private TreeNode<T> rootNode;

    private int totalCount = 0;


    /**
     * Instantiates a new Tree.
     */
    public Tree() {
        this.rootNode = new TreeNode<>("root", null, null);
    }

    /**
     * Get root node tree node.
     *
     * @return the tree node
     */
    public TreeNode<T> getRootNode() {

        return rootNode;
    }

    /**
     * Get tree node.
     *
     * @param keys the keys
     * @return the tree node
     */
    public TreeNode<T> get(String... keys) {
        if (keys == null || keys.length == 0) {
            return null;
        }
        TreeNode<T> parentNode = rootNode;
        TreeNode<T> childNode = null;
        for (int i = 0; i < keys.length; i++) {
            String key = keys[i];
            childNode = parentNode.getChildNode(key);
            if (childNode == null) {
                return null;
            }
            parentNode = childNode;
        }
        return childNode;
    }


    /**
     * Put.
     *
     * @param t    the t
     * @param keys the keys
     */
    public void put(T t, String... keys) {
        totalCount++;
        TreeNode<T> childNode = null;
        TreeNode<T> parentNode = rootNode;
        for (int i = 0; i < keys.length; i++) {
            String key = keys[i];
            if (i == keys.length - 1) {
                childNode = new TreeNode<>(key, parentNode, t);
                parentNode.put(key, childNode);
                log.debug("Tree add node[{}] at parent node [{}]", childNode, parentNode);
                return;
            }
            childNode = parentNode.getChildNode(key);
            if (childNode == null) {
                childNode = new TreeNode<>(key, parentNode, null);
                parentNode.put(key, childNode);
                log.debug("Tree add node[{}] at parent node [{}]", childNode, parentNode);
            }
            parentNode = childNode;
        }
    }

    @Override
    public String toString() {
        return "Tree{" +
                "INST　totalCount=" + totalCount +
                "}\n" +
                toDetailString(rootNode);
    }

    public String toDetailString(TreeNode parentNode){
        if (parentNode == null){
            return "";
        }
        StringBuilder sb = new StringBuilder();
        sb.append(parentNode.toString() + "\n");
        toDetailString(parentNode, 0, sb);
        return sb.toString();
    }

    private void toDetailString(TreeNode<T> parentNode, int index, StringBuilder sb){
        Collection<TreeNode<T>> allChildNodes = parentNode.getAllChildNodes();
        index++;
        for (TreeNode<T> childNode : allChildNodes) {
            for (int i = 0; i < index; i++) {
                sb.append("\t");
            }
            sb.append(childNode.toString() + "\n");
            toDetailString(childNode, index, sb);
        }
    }
}
