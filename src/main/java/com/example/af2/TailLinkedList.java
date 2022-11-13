package com.example.af2;

import java.util.Objects;

public class TailLinkedList {
    int count;
    public Node localHead;
    public Node localTail;

    TailLinkedList nextList;
    TailLinkedList previousList;

    public void insertTail(Node node) {
        if (count == 0) {
            if(nextList != null && nextList.count > 0)  {
                node.nextNode = nextList.localHead;
                node.nextNode.previousNode = node;
            }
            if(previousList != null && previousList.count > 0)  {
                node.previousNode = previousList.localTail;
                node.previousNode.nextNode = node;
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

    public Node remove(String name) {
        // Start going through all the nodes with the head node
        Node currentNode = localHead;
        for (int i = 0; i < count; i++) {

            if(Objects.equals(currentNode.name, name)) {

                Node currentNodesPreviousNode = currentNode.previousNode;
                Node currentNodesNextNode = currentNode.nextNode;

                if(currentNodesPreviousNode != null) {
                    currentNodesPreviousNode.nextNode = currentNodesNextNode;
                }
                if(currentNodesNextNode != null) {
                    currentNodesNextNode.previousNode = currentNodesPreviousNode;
                }

                if(currentNode == localHead) {
                    localHead = currentNodesNextNode;
                }
                if(currentNode == localTail) {
                    localTail = currentNodesPreviousNode;
                }

                count--;

                return currentNode;
            }

            currentNode = currentNode.nextNode;
        }

        return null;
    }
}
