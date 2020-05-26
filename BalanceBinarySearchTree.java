package com.ssm.controller.tree;

import java.util.Comparator;

/**
 * 平衡二叉搜索树
 */
public class BalanceBinarySearchTree<E> extends BinarySearchTree<E>{
    /**
     *
     */
    public BalanceBinarySearchTree() {
        super(null);
    }
    /**
     * 比较器
     * @param comparator
     */
    public BalanceBinarySearchTree(Comparator<E> comparator) {
        super(comparator);
    }

    /**
     *
     */
    public void simpleRotate(
            Node<E> r, //子树的根节点
            Node<E> a, Node<E> b, Node<E> c,
            Node<E> d,
            Node<E> e, Node<E> f, Node<E> g
    ){
        //d设置为子树的根节点，并且更新引用
        d.parent = r.parent;
        if(r.isLeftChild()){
            r.parent.left = d;

        }else if(r.isRightChild()){
            r.parent.right = d;
        }else{
            rootNode = d;
        }

        //a-b-c
        b.left = a;
        if(a != null){
            a.parent = b;
        }
        b.right = c;
        if(c != null){
            c.parent = b;
        }


        //e-f-g
        f.left = e;
        if(e != null){
            e.parent = f;
        }
        f.right = g;
        if(g != null){
            g.parent = f;
        }


        //b-d-f
        d.left = b;
        d.right = f;
        b.parent = d;
        f.parent = d;

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
        //旋转操作
        afterRotate( grand,  parent,  child);
    }

    /**
     * LL-右旋转
     */
    public void rotateRight(Node<E> grand){
        //
        Node<E> parent = grand.left;
        Node<E> child = parent.right;
        //
        grand.left = child;
        parent.right = grand;
        //旋转操作
        afterRotate( grand,  parent,  child);
    }

    /**
     *  旋转操作
     */
    public void afterRotate(Node<E> grand, Node<E> parent, Node<E> child){
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

    }

}
