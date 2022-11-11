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

}