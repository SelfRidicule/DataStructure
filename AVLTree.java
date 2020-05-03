package com.ssm.controller.tree;

import java.util.Comparator;

/**
 * AVL Tree
 */
public class AVLTree<E> extends BinarySearchTree<E>{
    /**
     *
     */
    public AVLTree() {
        super(null);
    }
    /**
     * 比较器
     * @param comparator
     */
    public AVLTree(Comparator<E> comparator) {
        super(comparator);
    }


    /**
     * 添加之后进行平衡
     */
    @Override
    void addAfterBalance(Node<E> node) {
        while ((node = node.parent) != null){
            //判断是否平衡

        }
    }

    /**
     * 创建节点
     * 重写是为了设置高度和计算平衡因子
     */
    @Override
    public Node<E> createNode(E element, Node<E> parent) {
        return new AVLNode<E>(element, parent);
    }

    /**
     * 是否平衡
     */
    public boolean isBalanced(Node<E> node){
        return Math.abs(((AVLNode<E>)node).getBalanceFactor()) <= 1 ? true : false;
    }

    /**
     * AVLNode
     */
    class AVLNode<E> extends Node<E>{
        /**
         * 构造
         */
        public AVLNode(E element, Node<E> parent) {
            super(element, parent);
        }

        //高度
        int height;

        /**
         * 得到平衡因子
         */
        public int getBalanceFactor(){
            //得到左子树高度
            int leftHeight = left == null ? 0 : ((AVLNode<E>)left).height;
            //得到右子树高度
            int rightHeight = right == null ? 0 : ((AVLNode<E>)right).height;
            //平衡因子 = 左子树高度 - 右子树高度
            return leftHeight - rightHeight;
        }

    }

}
