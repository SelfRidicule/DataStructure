package com.ssm.controller.tree;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 二叉树
 */
public class BinaryTree<E> {

    //总数
    protected int size;
    //根节点
    protected Node<E> rootNode;


    /**
     * 创建node节点
     */
    public Node<E> createNode(E element, Node<E> parent){
       return  new Node<E>(element,  parent);
    }

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
        rootNode = null;
        size = 0;
    }

    /**
     * 得到跟节点
     * @return
     */
    public Node<E> getRootNode(){
        return this.rootNode;
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
    public void postorderTraversal(){
        postorderTraversal(getRootNode());
    }
    private void postorderTraversal(Node<E> node){
        //如果当前节点为null说明父节点是叶子节点，不用再进行遍历
        if(node == null){
            return ;
        }

        //循环查找当前节点的左子节点
        postorderTraversal(node.left);
        //循环查找当前节点的右子节点
        postorderTraversal(node.right);
        //输出当前节点信息
        System.out.println(node.element.toString());
    }

    /**
     * 后序遍历 Visitor
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
     * 二叉树的高度
     */
    public int height(){
        return height(getRootNode() , 0);
    }
    private int height(Node<E> node , int level){
        if(node == null){
            return level;
        }
        return Math.max(height(node.left, level+1) , height(node.right,level+1));
    }

    /**
     * 队列-高度
     */
    public int queueHeight(){
        //根节点
        Node<E> rootNode = getRootNode();

        //如果当前节点为null说明父节点是叶子节点，不用再进行遍历
        if(rootNode == null){
            return 0;
        }

        //高度
        int height = 0;
        //当前层级的节点总数
        int levelSize = 1;
        //创建队列
        Queue<Node<E>> queue = new LinkedList<>();
        //向队列添加节点
        queue.offer(rootNode);

        //判断队列是否有数据
        while (!queue.isEmpty()){
            //拿取队列第一个 并移除
            Node<E> node = queue.poll();
            levelSize--;
            //向队列添加左子节点
            if(node.left != null){
                queue.offer(node.left);
            }
            //向队列添加右子节点
            if(node.right != null){
                queue.offer(node.right);
            }
            //当前层级节点遍历结束==>>就遍历下一层节点
            if(levelSize == 0){
                levelSize = queue.size();
                height++;
            }
        }

        return height;
    }

    /**
     * 是否是-完全二叉树
     *
     * 如果树为null返回false
     * 如果左子节点和右子节点都不为null就添加到队列
     * 如果左子节点为null，并且右子节点不为null就不是完全二叉树
     * 如果左子节点不为null，并且右子节点为null 。或者。 左子节点和右子节点都是null。 那么后面遍历的都应该是叶子节点
     */
    public boolean isCompleteBinaryTree(){
        if(getRootNode() == null){
            return false;
        }

        //创建队列
        Queue<Node<E>> queue = new LinkedList<>();
        //向队列添加节点
        queue.offer(getRootNode());

        //是否是叶子节点
        boolean leaf = false;

        //判断队列是否有数据
        while (!queue.isEmpty()){
            //获取队列第一个，并且移除
            Node<E> node = queue.poll();

            //判断是否必须是叶子节点,如果不是就判定不是完全二叉树
            if(leaf && !node.isLeaf()){
                return false;
            }

            //左右子节点不等于null
            if(node.ownTwoChildren()){
                queue.offer(node.left);
                queue.offer(node.right);

                //左子节点为null并且右子节点不为null就判定不是完全二叉树
            }else if(node.left == null && node.right != null){
                return false;

                //后面遍历的节点都是叶子节点
            }else{
                //设置判断状态为叶子节点
                leaf = true;
                //如果左子节点不是null就添加到队列
                if(node.left != null){
                    queue.offer(node.left);
                }
            }
        }

        //判定是完全二叉树
        return true;
    }

    /**
     * 优化判断完全二叉树
     */
    public boolean isCompleteBinaryTreeOptimize(){
        if(getRootNode() == null){
            return false;
        }

        //创建队列
        Queue<Node<E>> queue = new LinkedList<>();
        //向队列添加节点
        queue.offer(getRootNode());

        //是否是叶子节点
        boolean leaf = false;

        //判断队列是否有数据
        while (!queue.isEmpty()){
            //获取队列第一个，并且移除
            Node<E> node = queue.poll();

            //判断是否必须是叶子节点,如果不是就判定不是完全二叉树
            if(leaf && !node.isLeaf()){
                return false;
            }

            //有左子节点
            if(node.left != null){
                queue.offer(node.left);

                //没有左子节点但是有右子节点
            }else if(node.right != null){
                return false;
            }

            //有右子节点
            if(node.right != null){
                queue.offer(node.right);

                //没有左子节点 或者 没有右子节点
            }else{
                leaf = true;
            }
        }

        //判定是完全二叉树
        return true;
    }

    /**
     * 前序递归-翻转二叉树
     */
    public void preorderTurnTree(){
        preorderTurnTree(getRootNode());
    }
    private void preorderTurnTree(Node<E> node){
        //当前节点如果为null就退出
        if(node == null){
            return ;
        }

        //左子节点
        Node<E> leftNode = node.left;
        //右子节点
        Node<E> rightNode = node.right;

        //左右子节点对调
        node.left = rightNode;
        node.right = leftNode;

        //递归对调节点
        preorderTurnTree(node.left);
        preorderTurnTree(node.right);
    }

    /**
     * 层序-翻转二叉树
     */
    public void levelTurnTree(){
        //如果没有根节点直接退出
        if(getRootNode() == null){
            return ;
        }
        //创建队列
        Queue<Node<E>> queue = new LinkedList<>();
        //向队列添加节点
        queue.offer(getRootNode());

        //如果队列不为null
        while (!queue.isEmpty()){
            //拿取第一个，并且移除
            Node<E> node = queue.poll();

            //左子节点
            Node<E> leftNode = node.left;
            //右子节点
            Node<E> rightNode = node.right;

            //左右子节点对调
            node.left = rightNode;
            node.right = leftNode;

            //如果左子节点不为null就添加到队列
            if(node.left != null){
                queue.offer(node.left);
            }
            //如果右子节点不为null就添加到队列
            if(node.right != null){
                queue.offer(node.right);
            }
        }
    }

    /**
     * 前驱节点
     * 中序遍历时的前一个节点
     */
    public Node<E> predecessorNode(Node<E> node){
        //当前节点是null，或者是根节点
        if(node == null){
            System.out.println("传递节点是null");
            return null;
        }

        //输出传递节点的信息
        System.out.println("传递的节点是 : " + node.element.toString());

        //左子节点为null
        if(node.left == null){
            //左子节点为null，那么前驱节点是父节点，但父节点的右子树必须是当前节点，如果不是就继续向上找
            //父节点
            Node<E> parentNode = node.parent;

            //一直循环
            while (true){
                //如果父节点是null就没有前驱节点
                if(parentNode == null){
                    System.out.println("没有前驱节点!");
                    return null;
                }

                //如果当前节点是父节点的右子树，那么前驱节点就是父节点
                if(parentNode.right == node){
                    System.out.println("传递节点的前驱节点是 : " + parentNode.element.toString());
                    return parentNode;
                }

                //当前节点赋值当前父节点
                node = parentNode;
                //父节点赋值父节点的父节点
                parentNode = parentNode.parent;
            }

        }else{
            //父节点
            Node<E> parentNode = node.left;

            //默认从传递节点==>>左子节点==>>右子节点...
            node = node.left.right;

            //一直循环
            while (true){
                //节点为null直接退出循环
                if(node == null){
                    System.out.println("传递节点的前驱节点是 : " + parentNode.element.toString());
                    return parentNode;
                }
                //把当前节点，赋值给父节点
                parentNode = node;
                //把当前节点的右子节点，赋值给当前节点
                node = node.right;
            }
        }

    }

    /**
     * 后继节点
     * 中序遍历时的后一个节点
     */
    public Node<E> successorNode(Node<E> node){
        //传递节点是null ，或者根节点
        if(node == null){
            System.out.println("传递节点是null");
            return null;
        }

        //输出传递节点的信息
        System.out.println("传递的节点是 : " + node.element.toString());

        //右子节点为null
        if(node.right == null){

            //右子节点为null，后继节点就是父节点，但父节点的左子节点必须是当前节点,不然就继续向上找
            //父节点
            Node<E> parentNode = node.parent;

            //一直循环
            while (true){
                //父节点为null就没有后继节点
                if(parentNode == null){
                    System.out.println("没有后继节点");
                    return null;
                }
                //父节点的左子节点是当前节点，那么父节点就是后继节点
                if(parentNode.left == node){
                    System.out.println("传递节点的后继节点是 : " + parentNode.element.toString());
                    return parentNode;
                }

                //当前节点赋值父节点
                node = parentNode;
                //父节点赋值父节点的父节点
                parentNode = parentNode.parent;
            }

        }else{
            //父节点
            Node<E> parentNode = node.right;

            //默认从传递节点==>>右子节点==>>左子节点...
            node = node.right.left;

            //一直循环
            while (true){
                //当前节点为null就直接退出
                if(node == null){
                    System.out.println("传递节点的后继节点是 : " + parentNode.element.toString());
                    return parentNode;
                }

                //把当前节点赋值给父节点
                parentNode = node;
                //把当前节点的左子节点，赋给当前节点
                node = node.left;
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

        //元素
        E element;
        //父节点
        Node<E> parent;
        //左子节点
        Node<E> left;
        //右子节点
        Node<E> right;


        /**
         * 得到当前节点元素
         */
        public E getElement(){
            return element;
        }

        /**
         *  是否是叶子节点
         */
        public boolean isLeaf(){
            return left == null && right == null ;
        }

        /**
         *  拥有左右子节点
         */
        public boolean ownTwoChildren(){
            return left != null && right != null ;
        }

        /**
         *  是父节点的左子树
         */
        public boolean isLeftChild(){
            return parent != null && parent.left == this;
        }

        /**
         * 是父节点的右子树
         */
        public boolean isRightChild(){
            return parent != null && parent.right == this;
        }

        /**
         * 得到兄弟节点
         */
        public Node<E> sibling(){
            //是父节点的左子节点就返回右子节点
            if(isLeftChild()){
                return parent.right;
            }
            //是父节点的右子节点就返回左子节点
            if(isRightChild()){
                return parent.left;
            }
            //没有父节点
            return null;
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
