package com.huolongguo.study.service;

import com.huolongguo.study.vo.MyQueue;
import com.huolongguo.study.vo.MyStack;

/**
 * Created by lw on 2019/8/30 10:30
 */
public class QueueTest {




    public static void main(String[] args) {



        MyStack myStack = new MyStack();

        myStack.push(2);
        myStack.push(4);
        myStack.push(8);

        System.out.println(myStack.peek());
        System.out.println(myStack.pop());
        System.out.println(myStack.peek());

    }




}
