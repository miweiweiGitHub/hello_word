package com.huolongguo.study.vo;

/**
 * Created by lw on 2019/8/30 11:06
 */
public class MyNode {
    int data;
    MyNode next;

    public MyNode (int data) {
        this.data = data;
    }

    public MyNode() {

    }

    public int getData() {
        return this.data;
    }

    public MyNode append(MyNode node){
        MyNode currentNode = this;

        while (true){
            MyNode node1 = currentNode.next;
            if (node1==null){
                break;
            }
            currentNode = node1;
        }
        currentNode.next = node;

        return this;
    }

    public MyNode next() {
        return this.next;
    }


    public boolean isLast(){
        return this.next==null;
    }


    //往当前的节点插入数据
    public void insert(MyNode node){

        if (this.next == null){
            this.append(node);
            return;
        }

        MyNode temp = this.next;
        node.next = temp;
        this.next = node;

    }


    public void show(){
        MyNode temp = this;
        while (true){
            if (temp ==null){
                break;
            }
            System.out.println("data:"+temp.data);
           temp  =  temp.next;
        }
    }
}
