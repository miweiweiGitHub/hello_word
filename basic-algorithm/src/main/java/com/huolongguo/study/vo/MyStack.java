package com.huolongguo.study.vo;

/**
 * Created by lw on 2019/8/30 10:37
 */
public class MyStack {

    //使用数组作为存储
    int[] elements;

    public MyStack(){
        elements = new int[0];
    }


    //压入元素,将压入元素放到最后
    public void push(int element){

        int[] temp = new int[elements.length+1];

        for (int i = 0; i < elements.length; i++) {
            temp[i] = elements[i];
        }
        temp[elements.length] = element;
         elements = temp;
    }

    //取出栈顶元素 取出最后的一个元素
    public int pop(){

        if (elements.length==0){
            throw new RuntimeException("my stack is empty");
        }
        //取出最后的元素
        int last = elements[elements.length-1];
        //
        int[] temp = new int[elements.length-1];
        for (int i = 0; i < temp.length; i++) {
           temp[i] = elements[i] ;
        }
        elements = temp;
        return last;
    }

    //查看栈顶元素
    public int peek(){

        if (elements.length==0){
            throw new RuntimeException("my stack is empty");
        }
        return elements[elements.length-1];
    }

    //查看栈是否为空
    public boolean isEmpty(){
        return elements.length==0;
    }


}
