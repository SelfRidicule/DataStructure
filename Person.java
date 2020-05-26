package com.ssm.controller.tree;

public class Person{

    public Person(int age) {
        this.age = age;
    }

    private int age;


    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return  age +"";
    }
}
