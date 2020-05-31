package com.ssm.controller.tree;

import java.util.Comparator;

public class Demo {

    public static void main(String[] args) {

        RedBlackTree<Person> binarySearchTree = new RedBlackTree<>(new Comparator<Person>() {
            @Override
            public int compare(Person o1, Person o2) {
                return o1.getAge() - o2.getAge();
            }
        });

        Person person1 = new Person(55);
        Person person2 = new Person(87);
        Person person3 = new Person(56);
        Person person4 = new Person(74);
        Person person5 = new Person(96);
        Person person6 = new Person(22);
        Person person7 = new Person(62);
        Person person8 = new Person(20);
        Person person9 = new Person(70);
        Person person10 = new Person(68);
        Person person11 = new Person(90);
        Person person12 = new Person(50);

        binarySearchTree.add(person1);
        binarySearchTree.add(person2);
        binarySearchTree.add(person3);
        binarySearchTree.add(person4);
        binarySearchTree.add(person5);
        binarySearchTree.add(person6);
        binarySearchTree.add(person7);
        binarySearchTree.add(person8);
        binarySearchTree.add(person9);
        binarySearchTree.add(person10);
        binarySearchTree.add(person11);
        binarySearchTree.add(person12);

        binarySearchTree.remove(person1);
        binarySearchTree.remove(person2);
//        binarySearchTree.printTree();
//        binarySearchTree.preorderTraversal();
//        binarySearchTree.inorderTraversal();
//        binarySearchTree.postorderTraversal();
//        binarySearchTree.postorderTraversal(new BinarySearchTree.Visitor<Person>() {
//            @Override
//            boolean visit(Person element) {
//                System.out.println(element.toString());
//
//                if(element.getAge() == 10){
//                    return true;
//                }
//                return false;
//            }
//        });

//        System.out.println( binarySearchTree.toString());
//        System.out.println(binarySearchTree.height());
//        System.out.println(binarySearchTree.queueHeight());
//        System.out.println(binarySearchTree.isCompleteBinaryTree());
//        System.out.println(binarySearchTree.isCompleteBinaryTreeOptimize());

//        System.out.println(binarySearchTree.toString());
//        binarySearchTree.levelTurnTree();
//        binarySearchTree.preorderTurnTree();
//        System.out.println();
//        System.out.println(binarySearchTree.toString());

//        binarySearchTree.predecessorNode(binarySearchTree.getRootNode().right);
//        binarySearchTree.remove(binarySearchTree.getRootNode().right);
        System.out.println(binarySearchTree.toString());

    }

}
