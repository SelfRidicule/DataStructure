package com.ssm.controller.tree;

import java.util.Comparator;

/**
 * AVL Tree
 */
public class AVLTree<E> extends BalanceBinarySearchTree<E>{
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
//                reBalance(node);
                simpleReBalance(node);
                break;
            }
        }
    }

    @Override
    void removeAfterBalance(Node<E> node) {
        while ((node = node.parent) != null){
            //平衡-更新高度
            if(isBalanced(node)){
                //更新父节点高度
                updateNodeHeight(node);

            //不平衡-恢复平衡
            }else{
                //恢复平衡
//                reBalance(node);
                simpleReBalance(node);
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
    public void updateNodeHeight(Node<E> node){
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
    private void simpleReBalance(Node<E> grand){
        Node<E> parent = ((AVLNode<E>)grand).tallerChild();
        Node<E> node = ((AVLNode<E>)parent).tallerChild();
        //L
        if(parent.isLeftChild()){
            if(node.isLeftChild()){ //LL-右旋转
                simpleRotate(grand, node.left, node, node.right, parent, parent.right, grand, grand.right);
            }else{  //LR-左旋转再右旋转
                simpleRotate(grand, parent.left, parent, node.left, node, node.right, grand, grand.right);
            }
        //R
        }else{
            if(node.isLeftChild()){ //RL-右旋转再左旋转
                simpleRotate(grand, grand.left, grand, node.left, node, node.right, parent, parent.right);
            }else{  //RR-左旋转
                simpleRotate(grand, grand.left, grand, parent.left, parent, node.left, node, node.right);
            }
        }
    }

    /**
     *  旋转操作
     */
    @Override
    public void afterRotate(Node<E> grand, Node<E> parent, Node<E> child) {
        super.afterRotate(grand, parent, child);

        //更新高度,从低向高更新
        updateNodeHeight(grand);
        updateNodeHeight(parent);
    }

    @Override
    public void simpleRotate(Node<E> r, Node<E> a, Node<E> b, Node<E> c, Node<E> d, Node<E> e, Node<E> f, Node<E> g) {
        super.simpleRotate(r, a, b, c, d, e, f, g);

        //更新高度
        updateNodeHeight(b);
        //更新高度
        updateNodeHeight(f);
        //更新高度
        updateNodeHeight(d);
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
     * toString
     */
    @Override
    public String toString() {
        StringBuffer sf = new StringBuffer();
        toString(getRootNode() , sf , "");
        return sf.toString();
    }
    private void toString(Node<E> node , StringBuffer sf , String prefix){
        if(node == null){
            return;
        }
        toString(node.left , sf , prefix+"L---");
        sf.append(prefix).append(node.element.toString()+"_h("+ ((AVLNode<E>)node).height+")").append("\n");
        toString(node.right , sf , prefix+"R---");
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
