package com.example.af2;

public class HashTable {
    //    Scanner sc = new Scanner(System.in);
    PriorityLinkedList[] linkedLists = new PriorityLinkedList[10];

    public void insert(String name, int priority) {
        Node node = new Node(name, priority);
        int key = node.hashCode();
        int hash = key % linkedLists.length;


        if (linkedLists[hash] == null) {
            linkedLists[hash] = new PriorityLinkedList();
        }

        linkedLists[hash].insert(node);
    }

    public static int key(String name) {
        return Node.hashCode(name);
    }
    public static int hash(int key, int linkedListsLength) {
        return key % linkedListsLength;
    }

    public Node remove(String name) throws Exception {
        int key = key(name);
        int hash = hash(key, linkedLists.length);

        if (linkedLists[hash] == null) {
            throw new Exception("Node not found");
        }
        Node removedNode = linkedLists[hash].remove(name);

        if(removedNode == null) {
            throw new Exception("Node not found");
        }

        return removedNode;
    }
}