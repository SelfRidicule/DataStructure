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
            //平衡-更新高度
            if(isBalanced(node)){
                //更新父节点高度
                updateNodeHeight(node);

            //不平衡-恢复平衡
            }else{
                //恢复平衡
                reBalance(node);
                break;
            }
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
    private boolean isBalanced(Node<E> node){
        return Math.abs(((AVLNode<E>)node).getBalanceFactor()) <= 1 ? true : false;
    }

    /**
     * 更新节点高度
     */
    private void updateNodeHeight(Node<E> node){
        ((AVLNode<E>) node).updateNodeHeight();
    }

    /**
     * 恢复平衡
     * 传递的grandNode是高度最低的不平衡节点
     *
     * LL-右旋转
     * RR-左旋转
     * LR-左旋转再右旋转
     * RL-右旋转再左旋转
     */
    private void reBalance(Node<E> grand){
        Node<E> parent = ((AVLNode<E>)grand).tallerChild();
        Node<E> node = ((AVLNode<E>)parent).tallerChild();

        
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
        int height = 1;

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

        /**
         * 更新节点高度
         */
        public void updateNodeHeight(){
            //得到左子树高度
            int leftHeight = left == null ? 0 : ((AVLNode<E>)left).height;
            //得到右子树高度
            int rightHeight = right == null ? 0 : ((AVLNode<E>)right).height;
            //更新高度
            height = 1 + Math.max(leftHeight , rightHeight);
        }

        /**
         * 最高的子节点
         */
        public Node<E> tallerChild(){
            //得到左子树高度
            int leftHeight = left == null ? 0 : ((AVLNode<E>)left).height;
            //得到右子树高度
            int rightHeight = right == null ? 0 : ((AVLNode<E>)right).height;
            //
            return isLeftChild() ? left : right;
        }
    }

}
