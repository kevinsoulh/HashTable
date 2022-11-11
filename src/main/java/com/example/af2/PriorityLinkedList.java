package com.example.af2;

public class PriorityLinkedList {
    TailLinkedList priority0 = new TailLinkedList();
    TailLinkedList priority1 = new TailLinkedList();

    public PriorityLinkedList() {
        priority0.nextList = priority1;
    }

    public void insert(Node node) {
        if(node.priority == 0) {
            priority0.insertTail(node);
        } else {
            priority1.insertTail(node);
        }
    }
}
