package com.ssm.controller.tree;

import java.util.Comparator;

/**
 * 红黑树
 */
public class RedBlackTree<E> extends BalanceBinarySearchTree<E>{
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
        //得到父节点
        Node<E> parent = node.parent;

        //添加的是根节点
        if(parent == null){
            blackColor(node);
            return ;
        }

        //父节点是黑色
        if(isBlackColor(parent)){
            return ;
        }

        //叔父节点
        Node<E> uncle = parent.sibling();
        //祖父节点
        Node<E> grand = redColor(parent.parent);

        //叔父节点是红色
        if(isRedColor(uncle)){
            //把父节点和叔父节点染成黑色
            blackColor(parent);
            blackColor(uncle);
            //把祖父节点当做是新添加的节点
            addAfterBalance(grand);
            return ;
        }

        //叔父节点不是红色
        if(parent.isLeftChild()){   // L

            if(node.isLeftChild()){ // LL
                //父节点染成黑色
                blackColor(parent);

            }else{  // LR
                //当前节点染成黑色
                blackColor(node);
                //父节点左旋转
                rotateLeft(parent);
            }
            //右旋转
            rotateRight(grand);

        }else{  // R

            if(node.isLeftChild()){ // RL
                //当前节点染成黑色
                blackColor(node);
                //父节点右旋转
                rotateRight(parent);

            }else{  // RR
                //父节点染成黑色
                blackColor(parent);
            }
            //祖父节点左旋转
            rotateLeft(grand);
        }
    }

    /**
     * 删除之后进行平衡
     */
    @Override
    void removeAfterBalance(Node<E> node) {
        //删除的节点是红色就直接删除
//        if(isRedColor(node)){
//            return ;
//        }

        //删除的节点是黑色
        //取代node节点是红色
        if(isRedColor(node)){
            //把替代节点染成黑色
            blackColor(node);
            return;
        }

        Node<E> parent = node.parent;
        //删除的是根节点
        if(parent == null){
           return ;
        }

        //删除的是黑色叶子节点
        //判断被删除的node是左还是右
        boolean left = parent.left == null || node.isLeftChild();
        Node<E> sibling = left ? parent.right : parent.left;

        if(left){ //被删除的节点在左边==>>兄弟在右边

            if(isRedColor(sibling)){ //兄弟节点是红色
                //兄弟染成黑色
                blackColor(sibling);
                //父节点染成红色
                redColor(parent);
                //父节点进行左旋转
                rotateLeft(parent);
                //变换兄弟
                sibling = parent.right;
            }

            //兄弟节点必定是黑色
            //判断兄弟节点的左右子节点都是黑色
            if(isBlackColor(sibling.left) && isBlackColor(sibling.right)){
                //判断父节点是否是黑色
                boolean parentColor = isBlackColor(parent);
                //父节点向下和兄弟节点合并
                blackColor(parent);
                redColor(sibling);
                //父节点是黑色向下合并会造成下溢，把父节点当做是被删除的节点继续递归
                if(parentColor){
                    removeAfterBalance(parent);
                }

            }else{ //兄弟节点至少有一个红色子节点
                //兄弟节点的左子节点是黑色==>>需要将兄弟节点进行左旋转
                if(isBlackColor(sibling.right)){ //RL
                    //兄弟节点进行右旋转
                    rotateRight(sibling);
                    //旋转后重新赋值兄弟节点
                    sibling = parent.right;
                }

                //继承原先父节点的颜色
                color(sibling , colorOf(parent));
                //兄弟节点的右子节点染成黑色
                blackColor(sibling.right);
                //父节点染成黑色
                blackColor(parent);

                //统一进行左旋转 RR
                rotateLeft(parent);
            }

        }else{ //被删除的节点在右边==>>兄弟在左边

            if(isRedColor(sibling)){ //兄弟节点是红色
                //兄弟染成黑色
                blackColor(sibling);
                //父节点染成红色
                redColor(parent);
                //父节点进行右旋转
                rotateRight(parent);
                //变换兄弟
                sibling = parent.left;
            }

            //兄弟节点必定是黑色
            //判断兄弟节点的左右子节点都是黑色
            if(isBlackColor(sibling.left) && isBlackColor(sibling.right)){
                //判断父节点是否是黑色
                boolean parentColor = isBlackColor(parent);
                //父节点向下和兄弟节点合并
                blackColor(parent);
                redColor(sibling);
                //父节点是黑色向下合并会造成下溢，把父节点当做是被删除的节点继续递归
                if(parentColor){
                    removeAfterBalance(parent);
                }

            }else{ //兄弟节点至少有一个红色子节点
                //兄弟节点的左子节点是黑色==>>需要将兄弟节点进行左旋转
                if(isBlackColor(sibling.left)){ //LR
                    //兄弟节点进行左旋转
                    rotateLeft(sibling);
                    //旋转后重新赋值兄弟节点
                    sibling = parent.left;
                }

                //继承原先父节点的颜色
                color(sibling , colorOf(parent));
                //兄弟节点的左子节点染成黑色
                blackColor(sibling.left);
                //父节点染成黑色
                blackColor(parent);

                //统一进行右旋转 LL
                rotateRight(parent);

            }
        }
    }

    @Override
    public Node<E> createNode(E element, Node<E> parent) {
        return new RBNode<E>(element, parent);
    }

    /**
     * toString
     */
    @Override
    public String toString() {
        StringBuffer sf = new StringBuffer();
        toString( (RBNode)getRootNode() , sf , "");
        return sf.toString();
    }
    private void toString(RBNode<E> node , StringBuffer sf , String prefix){
        if(node == null){
            return;
        }
        toString( (RBNode)node.left , sf , prefix+"L---");
        sf.append(prefix).append(node.toString()).append("\n");
        toString( (RBNode)node.right , sf , prefix+"R---");
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
        //default RED
        boolean color = RED;
        /**
         * 构造方法
         */
        public RBNode(E element, Node<E> parent) { super(element, parent); }

        @Override
        public String toString() {
            String str = "";
            if(color == RED){
                str = "red_";
            }
            return str + element.toString();
        }
    }
}
