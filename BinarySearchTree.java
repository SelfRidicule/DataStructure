package com.ssm.controller.tree;

import com.alibaba.druid.sql.ast.statement.SQLUnionQuery;

import javax.validation.constraints.Max;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;

/**
 * 二叉搜索树
 */
public class BinarySearchTree<E> extends BinaryTree<E>{
    /**
     * 构造方法
     */
    public BinarySearchTree() {
    }

    public BinarySearchTree(Comparator<E> comparator) {
        this.comparator = comparator;
    }


    //比较器
    private Comparator<E> comparator;



    /**
     * 添加
     */
    void add(E element){
        //检查元素是否为null
        elementNotNullCheck(element);

        //如果添加的是根节点
        if(rootNode == null){
            rootNode = createNode(element , null);
            size++;

            //添加之后进行平衡
            addAfterBalance(rootNode);
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
        Node<E> addNode = createNode(element, parentNode);
        //根据判断的大小决定添加元素的左右
        //大于0==>>右子节点
        if(compareValue > 0){
            parentNode.right = addNode;

        //小于0==>>左子节点
        }else if(compareValue < 0){
            parentNode.left = addNode;
        }

        //添加之后进行平衡
        addAfterBalance(addNode);

        //总数+1
        size++;
    }

    /**
     * 添加之后进行平衡
     */
    void addAfterBalance(Node<E> node){}

    /**
     * 删除指定元素
     */
    public void remove(E element){
        remove(getNode(element));
    }

    /**
     * 删除指定节点
     */
    public void remove(Node<E> node){
        //传递节点为null
        if(node == null){
            System.out.println("传递的节点是null");
            return;
        }

        //删除度为2的节点
        if(node.ownTwoChildren()){
            //当前节点==>>后继节点
            Node<E> successorNode = successorNode(node);
            //用后继节点的元素覆盖度为2的节点元素
            node.element = successorNode.element;
            //被删除的节点设置为后继节点
            node = successorNode;
        }

        //replaceNode节点（node的度必然是1或0）
        Node<E> replaceNode = node.left != null ? node.left : node.right;
        //不是叶子节点
        if(replaceNode != null){
            //node.child.parent  = node.parent
            replaceNode.parent = node.parent;

            //node是度为1的节点，并且是根节点
            if(node.parent == null){
                rootNode = replaceNode;

            //被删除节点，是父节点的左子节点
            } else if(node == node.parent.left){
                node.parent.left = replaceNode;

            //被删除节点，是父节点的右子节点
            }else{
                node.parent.right = replaceNode;
            }

        //node是叶子节点，并且是根节点
        }else if(node.parent == null){
            rootNode = null;

        //node是叶子节点，并且不是父节点
        }else{
            //node是父节点的左子节点==>>删除父节点的左子节点
            if(node.parent.left == node){
                node.parent.left = null;

            //node是父节点的右子节点==>>删除父节点的右子节点
            }else{
                node.parent.right = null;
            }
        }

        //总数-1
        size--;
    }

    /**
     * 通过传递的元素得到对应的节点
     */
    private Node<E> getNode(E element){
        //从根节点开始遍历
        Node<E> node = getRootNode();

        //节点不为null
        while (node != null){
            //比较元素
            int compareValue = compareElement(element ,node.element);
            //如果相等
            if(compareValue == 0){
                return node;
            }
            //传递元素大于当前节点元素
            if(compareValue > 0){
                node = node.right;
                continue;

            //传递元素小于当前节点元素
            }else{
                node = node.left;
                continue;
            }
        }

        //没有匹配到
        return null;
    }

    /**
     * 是否包含该元素
     */
    boolean contains(E element){
        return getNode(element) != null ? true : false;
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



}
