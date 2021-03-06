package org.baade.rat.core.structure.tree;

import java.util.Collection;
import java.util.TreeMap;

/**
 * The type Tree node.
 * 树结构的节点
 *
 * @param <T> the type parameter
 */
public class TreeNode<T> {

    private String key;
    private T inst;

    private TreeNode<T> parentNode;

    private TreeMap<String, TreeNode<T>> childNodes;

    private int instChildCount = 0;

    /**
     * Instantiates a new Tree node.
     *
     * @param key        the key
     * @param parentNode the parent node
     */
    public TreeNode(String key, TreeNode<T> parentNode, T inst) {
        this.key = key;
        this.inst = inst;
        this.parentNode = parentNode;
        this.childNodes = new TreeMap<>();
    }

    /**
     * Get child node tree node.
     *
     * @param childKey the child key
     * @return the tree node
     */
    public TreeNode<T> getChildNode(String childKey){
        return childNodes.get(childKey);
    }

    /**
     * Put.
     *
     * @param childKey the child key
     * @param node     the node
     */
    public void put(String childKey, TreeNode<T> node){
        childNodes.put(childKey, node);
        if (node != null){
            instChildCount++;
        }
    }

    public Collection<TreeNode<T>> getAllChildNodes(){
        return childNodes.values();
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public T getInst() {
        return inst;
    }

    public void setInst(T inst) {
        this.inst = inst;
    }

    public TreeNode<T> getParentNode() {
        return parentNode;
    }

    public void setParentNode(TreeNode<T> parentNode) {
        this.parentNode = parentNode;
    }

    public TreeMap<String, TreeNode<T>> getChildNodes() {
        return childNodes;
    }

    public void setChildNodes(TreeMap<String, TreeNode<T>> childNodes) {
        this.childNodes = childNodes;
    }

    public int getInstChildCount() {
        return instChildCount;
    }

    public void setInstChildCount(int instChildCount) {
        this.instChildCount = instChildCount;
    }

    @Override
    public String toString() {
        return "TreeNode{" +
                "key='" + key + '\'' +
                ", instChildCount=" + instChildCount +
                (inst == null ? "" : ", inst=" + inst.toString()) +
                '}';
    }
}
