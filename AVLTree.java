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
        //L
        if(parent.isLeftChild()){
            //LL-右旋转
            if(node.isLeftChild()){
                //右旋转
                rotateRight(grand);

            //LR-左旋转再右旋转
            }else{
                //左旋转
                rotateLeft(parent);
                //右旋转
                rotateRight(grand);
            }
        //R
        }else{
            //RL-右旋转再左旋转
            if(node.isLeftChild()){
                //右旋转
                rotateRight(parent);
                //左旋转
                rotateLeft(grand);

            //RR-左旋转
            }else{
                //左旋转
                rotateLeft(grand);
            }
        }
    }

    /**
     * RR-左旋转
     */
    public void rotateLeft(Node<E> grand){
        //
        Node<E> parent = grand.right;
        Node<E> child = parent.left;
        //
        grand.right = child;
        parent.left = grand;
        //parent成为子树的根节点
        parent.parent = grand.parent;
        //祖先节点是左子树
        if(grand.isLeftChild()){
            grand.parent.left = parent;

        //祖先节点是右子树
        }else if(grand.isRightChild()){
            grand.parent.right = parent;
        }else{  //parent是根节点
            rootNode = parent;
        }

        //更新child的parent
        if(child != null){
            child.parent = grand;
        }
        //更新grand的parent
        grand.parent = parent;

        //更新高度,从低向高更新
        updateNodeHeight(grand);
        updateNodeHeight(parent);
    }

    /**
     * LL-右旋转
     */
    public void rotateRight(Node<E> node){

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
            //返回左子树
            if(leftHeight > rightHeight){return left;}
            //返回右子树
            if(leftHeight < rightHeight){return right;}

            //判断是父节点的左右子树，返回当前节点的左右子树
            return isLeftChild() ? left : right;
        }
    }

}
