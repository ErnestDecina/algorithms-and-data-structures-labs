package com.ernestjohndecina;

public class Node {
    // Priavte Variables
    int value;
    Node next_node = null;
    Node prev_node = null;

    Node(int value) {
        this.value = value;
    } // End Node Constructor 

    public void setNextNode(Node next_node) {
        this.next_node = next_node;
    } // End void setNextNode(Node next_node)

    public void setPrevNode(Node prev_node) {
        this.prev_node = prev_node;
    } // End void setNextNode(Node next_node)
}
