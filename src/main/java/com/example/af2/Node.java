package com.example.af2;

public class Node {
    String name;
    int priority;
    Node previousNode, nextNode;

    public Node(String name, int priority) {
        this.name = name;
        this.priority = priority;
    }

    @Override
    public int hashCode() {
        return Node.hashCode(name);
    }


    public static int hashCode(String name) {
        int hash = 0;
        for (int i = 0; i < name.length(); i++) {
            hash += name.charAt(i);
        }
        return hash;
    }
}
