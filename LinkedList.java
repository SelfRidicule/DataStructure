package com.ssm.controller.link;

/**
 * 链表
 */
public class LinkedList<E> {
    /**
     * 数量
     */
    private int size;
    /**
     * 第一个节点
     */
    private Node<E> firstNode;
    /**
     * 不存在
     */
    private static final int NOT_FOUND = -1;

    /**
     * 判断是否越界
     */
    public void overstepBorderException(int index) throws IndexOutOfBoundsException {
        //判断传递下标是否正确
        if(index < 0 || index >= size){
            throw new IndexOutOfBoundsException("传递下标不正确");
        }
    }

    /**
     * 得到指定下标的元素
     * @param index
     * @return
     */
    public E get(int index){
       return node(index).element;
    }

    /**
     * 设置指定下标的元素
     * @param index
     * @param element
     * @return
     */
    public E set(int index , E element){
        //得到指定下标的节点
        Node<E> node = node(index);
        //得到下标节点的元素
        E oldElement = node.element;
        //设置下标节点的元素
        node.element = element;
        //返回原本的下标节点元素
        return oldElement;
    }

    /**
     * 添加
     * @param element
     */
    public void add(E element){
        add(size , element);
    }

    /**
     * 添加指定下标
     * @param index
     * @param element
     */
    public void add(int index , E element){
        //传递0的情况
        if(index == 0){
            //在首部添加节点
            firstNode = new Node<E>(element , firstNode);
        }else{  //不是0的情况
            //上一个节点
            Node<E> prevNode = node(index - 1);
            //prevNode.nextNode --> newNode ，newNode.nextNode --> preNode.oldNode
            prevNode.nextNode = new Node<E>(element, prevNode.nextNode);
        }
        //总数+1
        size++;
    }

    /**
     * 删除指定下标节点
     */
    public E remove(int index){
        //被删除的节点
        Node<E> oldNode = null;
        if(index == 0){ //删除第一个节点
            oldNode = firstNode;
            firstNode = firstNode.nextNode;
        }else{
            Node<E> prevNode = node(index - 1);
            oldNode = prevNode.nextNode;
            prevNode.nextNode = prevNode.nextNode.nextNode;
        }
        //总数-1
        size--;
        //返回被删除节点的元素
        return oldNode.element;
    }

    /**
     * 删除传递的节点
     */
    public E remove(Node<E> node){
        if(indexOf(node.element) == NOT_FOUND){
            throw new IndexOutOfBoundsException("传递节点不存在!");
        }
        return remove(indexOf(node.element));
    }

    /**
     * 返回指定下标的节点
     * @param index
     * @return
     */
    public Node<E> node(int index){
        //判断传递下标是否正确
        overstepBorderException(index);
        //第一个节点
        Node<E> node = firstNode;
        //得到指定下标的节点
        for (int i = 0; i < index; i++) {
            //下一个节点
            node = node.nextNode;
        }
        //返回指定的节点
        return node;
    }

    /**
     * 返回元素的下标
     */
    public int indexOf(E element){
        //开始节点
        Node<E> node = firstNode;
        //传递的元素是null
        if(element == null){
            //开始循环
            for (int i = 0; i < size; i++) {
                //节点元素等于 null 直接返回下标
                if(node.element == null){
                    return i;
                }
                //赋值下一个节点
                node = node.nextNode;
            }

        }else{
            //开始循环
            for (int i = 0; i < size; i++) {
                //元素相同直接返回下标
                if(element.equals(node.element)){
                    return i;
                }
                //赋值下一个节点
                node = node.nextNode;
            }
        }
        //不存在
        return NOT_FOUND;
    }

    /**
     * 清空
     */
    public void clear(){
        size = 0;
        firstNode = null;
    }

    /**
     * 反转节点
     */
    public Node<E> reversalNode(){
        Node<E> originNode = null;
        Node<E> originNextNode = null;
        Node<E> nextNode = firstNode;
        boolean flag = true;

        return reversalNode( originNode , originNextNode , nextNode , flag);
    }
    public Node<E> reversalNode(Node<E> originNode , Node<E> originNextNode , Node<E> nextNode , boolean flag){
        //没有下一个节点
        if(nextNode == null){
            return originNextNode;
        }
        //第一次添加
        if(flag){
            originNode = new Node<E>(nextNode.element, null);
            originNextNode = originNode.nextNode;
            flag = false;
        }else{
            originNextNode = new Node<E>(nextNode.element, null);
            originNextNode = originNextNode.nextNode;
        }

        nextNode = nextNode.nextNode;

        return reversalNode( originNode , originNextNode , nextNode , flag);
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("size : " + size);
        sb.append("  [ ");
        Node<E> node = firstNode;
        for (int i = 0; i < size; i++) {
            if(i != 0){
                sb.append(", ");
            }
            sb.append( node.element );
            node = node.nextNode;
        }
        sb.append(" ]");

        return sb.toString();
    }

    /**
     * 节点
     */
    static class Node<E>{
        /**
         * 元素
         */
        E element;
        /**
         * 节点
         */
        Node<E> nextNode;

        /**
         * 构造方法
         * @param element
         * @param nextNode
         */
        public Node(E element, Node<E> nextNode) {
            this.element = element;
            this.nextNode = nextNode;
        }

        /**
         * 清空内存
         * @throws Throwable
         */
        @Override
        protected void finalize() throws Throwable {
            super.finalize();
            System.out.println("LinkList Node GC");
        }
    }

}
