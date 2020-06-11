package com.ssm.controller.array;


/**
 * 动态数组
 */
public class DynamicArray <E>{

    /**
     * 元素数量
     */
    private int size;
    /**
     * 元素
     */
    private E elements[];
    /**
     * 默认容量
     */
    private static final int CAPATICY = 10;
    /**
     * 不存在
     */
    private static final int NOT_FOUND = -1;

    /**
     * 构造方法
     */
    public DynamicArray(int capaticy) {
        //如果传递容量小于默认容量，就使用默认容量
        capaticy = CAPATICY > capaticy ? CAPATICY : capaticy;
        elements = (E[]) new Object[capaticy];
    }
    public DynamicArray() {
        this(CAPATICY);
    }


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
     * 清空
     */
    public void clear(){
        size = 0;
    }

    /**
     * 总数
     */
    public int size(){
        return size;
    }

    /**
     * 是否为null
     */
    public boolean isEmpty(){
        return size == 0;
    }

    /**
     * 得到指定下标元素
     */
    public E get(int index) throws IndexOutOfBoundsException {
        //判断传递下标是否正确
        overstepBorderException(index);
        //获取下标元素
        return elements[index];
    }

    /**
     * 覆盖指定位置
     */
    public E set(int index , E element) throws IndexOutOfBoundsException {
        //判断传递下标是否正确
        overstepBorderException(index);
        //获取被覆盖的元素
        E oldElement = elements[index];
        //覆盖
        elements[index] = element;
        //返回被覆盖元素
        return oldElement;
    }

    /**
     * 添加
     */
    public void add(E element){
        elements[size] = element;
        size++;
    }

    /**
     * 指定位置添加
     */
    public void add(int index, E element){

    }

    /**
     * 扩容
     */
    private void expansion(){

    }

    /**
     * 删除指定位置
     */
    public E remove(int index){
        //判断传递下标是否正确
        overstepBorderException(index);
        //得到被删除的元素
        E removeElement = elements[index];
        //循环向前移动一位
        for (int i = index; i < elements.length -1; i++) {
            elements[i] = elements[i+1];
        }
        //总数-1
        size--;
        //返回被删除的元素
        return removeElement;
    }

    /**
     * 返回元素的下标
     */
    public int indexOf(E element){
        //循环判断
        for (int i = 0; i < elements.length; i++) {
            if(elements[i].equals(element)){
                return i;
            }
        }
        //不存在
        return NOT_FOUND;
    }

    /**
     * 判断元素是否存在
     */
    public boolean contains(E element){
        return indexOf(element) != NOT_FOUND;
    }


    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("size : " + size);
        sb.append("  [ ");
        for (int i = 0; i < elements.length; i++) {
            if(i != 0){
                sb.append(", ");
            }
            sb.append( elements[i] );
//            if(i != elements.length - 1){
//                sb.append(", ");
//            }
        }
        sb.append(" ]");

        return sb.toString();
    }
}
