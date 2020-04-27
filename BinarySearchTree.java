package com.ssm.controller.tree;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;

/**
 * 二叉搜索树
 */
public class BinarySearchTree<E>{
    /**
     * 构造方法
     */
    public BinarySearchTree() {
    }

    public BinarySearchTree(Comparator<E> comparator) {
        this.comparator = comparator;
    }

    //总数
    private int size;
    //根节点
    private Node<E> rootNode;
    //比较器
    private Comparator<E> comparator;

    /**
     * 总数
     */
    int size(){
        return size;
    }

    /**
     * 是否为null
     */
    boolean isEmpty(){
        if(size == 0){
            return true;
        }
        return false;
    }

    /**
     * 清空
     */
    void clear(){

    }

    /**
     * 添加
     */
    void add(E element){
        //检查元素是否为null
        elementNotNullCheck(element);

        //如果添加的是根节点
        if(rootNode == null){
            rootNode = new Node<>(element , null);
            size++;
            return;
        }

        //如果添加的不是根节点
        Node<E> node = rootNode;
        //遍历后的父节点
        Node<E> parentNode = null;
        //判断的大小
        int compareValue = 0;

        //循环遍历
        while (node != null){
            //赋值父节点
            parentNode = node ;
            //判断节点大小
            compareValue = compareElement(element , node.element);
            //传递的元素大于节点元素
            if(compareValue > 0){
                //左小又大==>>赋值右子节点
                node = node.right;

            //传递的元素小于节点元素
            }else if(compareValue < 0){
                //做左小右大==>>赋值左子节点
                node = node.left;

            //相等
            }else{
                node.element = element;
                return ;
            }
        }

        //创建添加的元素节点
        Node<E> addNode = new Node<>(element, parentNode);
        //根据判断的大小决定添加元素的左右
        //大于0==>>右子节点
        if(compareValue > 0){
            parentNode.right = addNode;

        //小于0==>>左子节点
        }else if(compareValue < 0){
            parentNode.left = addNode;
        }

        //总数+1
        size++;
    }

    /**
     * 删除指定元素
     */
    void remove(E element){

    }

    /**
     * 删除指定下标的元素
     */
    void remove(int index){

    }

    /**
     * 得到跟节点
     * @return
     */
    public Node<E> getRootNode(){
        return this.rootNode;
    }

    /**
     * 是否包含该元素
     */
    boolean contains(){
        return false;
    }

    /**
     * 检查元素是否为null
     */
    void elementNotNullCheck(E element){
        if(element == null){
            throw new IllegalArgumentException(" element must not null! ");
        }
    }

    /**
     * 返回值
     *  0 相等
     *  1 e1 > e2
     * -1 e1 < e2
     */
    int compareElement(E e1 , E e2){
        if(this.comparator != null){
            return this.comparator.compare(e1 , e2);
        }else{
            return ((Comparable<E>) e1).compareTo(e2);
        }
    }

    /**
     * 前序遍历
     * 根节点==>>左子节点==>>右子节点
     */
    public void preorderTraversal(){
        preorderTraversal(getRootNode());
    }
    private void preorderTraversal(Node<E> node){
        //如果当前节点为null说明父节点是叶子节点，不用再进行遍历
        if(node == null){
            return ;
        }
        //输出当前节点信息
        System.out.println(node.element.toString());

        //循环当前节点的左子节点
        preorderTraversal(node.left);
        //循环当前节点的右子节点
        preorderTraversal(node.right);
    }

    /**
     * 中序遍历
     * 左子节点==>>根节点==>>右子节点
     */
    public void inorderTraversal(){
        inorderTraversal(getRootNode());
    }
    private void inorderTraversal(Node<E> node){
        //如果当前节点为null说明父节点是叶子节点，不用再进行遍历
        if(node == null){
            return ;
        }
        //循环查找当前节点的左子节点
        inorderTraversal(node.left);
        //输出当前节点信息
        System.out.println(node.element.toString());
        //循环查找当前节点的右子节点
        inorderTraversal(node.right);
    }

    /**
     * 后序遍历
     * 左子节点==>>右子节点==>>根节点
     */
    public void postorderTraversal(Visitor<E> visitor){
        postorderTraversal(getRootNode(),visitor);
    }
    private void postorderTraversal(Node<E> node, Visitor<E> visitor){
        //如果当前节点为null说明父节点是叶子节点，不用再进行遍历
        if(node == null || visitor == null || visitor.stop){
            return ;
        }

        //循环查找当前节点的左子节点
        postorderTraversal(node.left , visitor);
        //循环查找当前节点的右子节点
        postorderTraversal(node.right , visitor);
        //判断是否停止
        if(visitor.stop){ return; }
        //输出当前节点信息
        visitor.stop = visitor.visit(node.element);
    }


    /**
     * 层序遍历
     * 从上到下，从左到右依次访问每个节点
     */
    public void levelOrderTraversal(){
        //根节点
        Node<E> rootNode = getRootNode();

        //如果当前节点为null说明父节点是叶子节点，不用再进行遍历
        if(rootNode == null){
            return ;
        }

        //创建队列
        Queue<Node<E>> queue = new LinkedList<>();
        //向队列添加根节点
        queue.offer(rootNode);

        //判断队列是否为null
        while(!queue.isEmpty()){
            //得到队列第一个节点，并且删除队列第一个节点
            Node<E> node = queue.poll();
            //输出当前节点信息
            System.out.println(node.element.toString());

            //如果左子节点不为null则添加到队列
            if(node.left != null){
                queue.offer(node.left);
            }
            //如果右子节点不为null则添加到队列
            if(node.right != null){
                queue.offer(node.right);
            }
        }
    }


    /**
     * 输出二叉树所有的值
     */
    public void printTree(){

        Node<E> node = getRootNode();

        while (node != null){
            //输出当前节点元素
            System.out.print(node.getElement().toString());

            //父节点
            Node<E> parentNode = node.parent;

            //根节点
            if(parentNode == null){
                if(node.left != null){
                    node = node.left;
                }else{
                    node = node.right;
                }
                System.out.println();
                continue;
            }

            //当前节点是叶子节点
            if(node.left == null && node.right == null){

                //当前节点是父节点的==>>左子节点
                int compareValue = compareElement(node.element , parentNode.element);
                if(compareValue < 0){
                    node = parentNode.right;
                    System.out.println();
                    continue;

                //当前节点是父节点的==>>右子节点
                }else if(compareValue > 0){
                    node = getRightParentNode(node);
                    System.out.println();
                    continue;

                //不会存在相同的值
                }else{
                    return ;
                }

            //有左子节点
            }else if(node.left != null){
                node = node.left;
                System.out.println();
                continue;

            //右子节点
            }else if(node.right != null){
                node = node.right;
                System.out.println();
                continue;
            }
        }
    }

    /**
     * 递归查找
     */
    Node<E> getRightParentNode(Node<E> node){
        //父节点
        Node<E> parentNode = node.parent;
        //如果没有父节点判定所有节点遍历结束
        if(parentNode == null){
            return null;
        }

        //如果是左子树就返回右子树
        int compareValue = compareElement(node.element , parentNode.element);
        if(compareValue < 0){
            return parentNode.right;
        }else{
            return getRightParentNode(parentNode);
        }
    }

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
        sf.append(prefix).append(node.element.toString()).append("\n");
        toString(node.right , sf , prefix+"R---");
    }

    /**
     * 节点类
     */
    public static class Node<E>{

        public Node(E element, Node<E> parent) {
            this.element = element;
            this.parent = parent;
        }

        E element;
        Node<E> parent;
        Node<E> left;
        Node<E> right;

        public E getElement(){
            return element;
        }
    }

    /**
     *  输出抽象类
     */
    public abstract static class Visitor <E>{
        /**
         *  是否停止
         */
        boolean stop = false;

        /**
         * 输出
         */
        abstract boolean visit(E element);
    }

}
