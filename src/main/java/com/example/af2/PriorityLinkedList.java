package com.example.af2;

import java.util.Objects;

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

    public Node getHead() {
        if(priority0.localHead != null) {
            return priority0.localHead;
        } else {
            return priority1.localHead;
        }
    }
    public int getCount() {
        return priority0.count + priority1.count;
    }

    public Node remove(String name) {

        Node removedNode = priority0.remove(name);

        if(removedNode != null) {
            return removedNode;
        } else {
            return priority1.remove(name);
        }
    }
}
