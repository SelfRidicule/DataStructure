package com.ssm.controller.tree;

import java.util.Comparator;

/**
 * 红黑树
 */
public class RedBlackTree<E> extends BinarySearchTree<E>{
    //
    private static final boolean RED = false;
    //
    private static final boolean BLACK = true;

    /**
     *
     */
    public RedBlackTree() {
        super(null);
    }

    /**
     * 比较器
     * @param comparator
     */
    public RedBlackTree(Comparator<E> comparator) {
        super(comparator);
    }

    /**
     * 添加之后进行平衡
     */
    @Override
    void addAfterBalance(Node<E> node) {

    }

    /**
     * 删除之后进行平衡
     */
    @Override
    void removeAfterBalance(Node<E> node) {

    }

    /**
     * 给节点染色
     */
    Node<E> color(Node<E> node , boolean color){
        if(node == null){
            return node;
        }
        //进行染色
        ((RBNode<E>)node).color = color;
        return node;
    }
    /**
     * 染成红色
     */
    Node<E> redColor(Node<E> node){
        return color(node , RED);
    }
    /**
     * 染成黑色
     */
    Node<E> blackColor(Node<E> node){
        return color(node , BLACK);
    }

    /**
     * 查看节点什么颜色
     */
    boolean colorOf(Node<E> node){
        return node == null ? BLACK : ((RBNode<E>)node).color;
    }
    /**
     * 节点是否是红色
     */
    boolean isRedColor(Node<E> node){
        return colorOf(node) == RED;
    }
    /**
     * 节点是否是黑色
     */
    boolean isBlackColor(Node<E> node){
        return colorOf(node) == BLACK;
    }

    /**
     * RedBlackTreeNode
     */
    class RBNode<E> extends Node<E>{
        //
        boolean color;
        /**
         * 构造方法
         */
        public RBNode(E element, Node<E> parent) { super(element, parent); }

    }
}
