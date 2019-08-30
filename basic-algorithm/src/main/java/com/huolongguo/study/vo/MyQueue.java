package com.huolongguo.study.vo;

/**
 * Created by lw on 2019/8/30 10:03
 * 自定义队列
 */
public class MyQueue {

    int[] elements ;

    public MyQueue(){
        elements = new int[0];
    }

    //入队
    public void add(int element ){
        //将原先数组扩充一个长度
        int[] temp = new int[elements.length+1];
        //将原始数组元素存到新数组
        for (int i = 0; i < elements.length; i++) {
            temp[i] = elements[i];

        }
        //将加入的元素添加到新的数组
        temp[elements.length] = element;
        //将队列指向新的数组
        elements = temp;

    }

    //出队
    public int  poll(){

        //取出原始数组的最前面的元素
        int element = elements[0];

        //新建一个临时数组
        int[]  temp = new int[elements.length-1];
        //赋值原始数组到新数组
        for (int i = 0; i < temp.length; i++) {
            temp[i] = elements[i+1];
        }
        //将队列指向新数组
        elements = temp;

        return element;
    }


    public boolean isEmpty(){
        return elements.length == 0;
    }



}
