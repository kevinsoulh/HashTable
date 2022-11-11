package com.example.af2;

public class TailLinkedList {
    int count;
    private Node localHead;
    private Node localTail;

    TailLinkedList nextList;

    public void insertTail(Node node) {
        if (count == 0) {
            if(nextList != null && nextList.count > 0)  {
                node.nextNode = nextList.localHead;
            }

            localHead = node;
            localTail = node;
        } else {
            localTail.nextNode = node;
            node.previousNode = localTail;

            if(nextList != null && nextList.count > 0) {
                node.nextNode = nextList.localHead;
            }

            localTail = node;
        }

        count++;
    }
}
