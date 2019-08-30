package com.huolongguo.study.service;

import com.huolongguo.study.vo.MyNode;

/**
 * Created by lw on 2019/8/30 11:33
 */
public class NodeTest {

    public static void main(String[] args) {
        MyNode node1 = new MyNode(1);
        MyNode node2 = new MyNode(2);
        MyNode node3 = new MyNode(3);
        MyNode node4 = new MyNode(4);


        node1.append(node2).append(node3);
        node1.insert(node4);
        node1.show();
    }



}
