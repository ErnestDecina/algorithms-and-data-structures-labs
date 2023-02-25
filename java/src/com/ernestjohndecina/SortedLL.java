package com.ernestjohndecina;

public class SortedLL {
    // internal data structure and
    // constructor code to be added here
    // Private Variables
    Node head;
    Node z;


    SortedLL() {
        this.head = new Node(Integer.MAX_VALUE); // Head node
        this.z = new Node(Integer.MIN_VALUE); // Sentile node
        this.head.next_node = this.z;
    } // End 

    // this is the crucial method
    public void insert(int x) {
        Node prev_node; 
        Node current_node; 
        Node new_node = new Node(x);

        prev_node = head;
        current_node = head.next_node;

        while(current_node != z && current_node.value < new_node.value) {
            prev_node = current_node;
            current_node = current_node.next_node;
        } // End while
        new_node.next_node = current_node;
        prev_node.next_node = new_node;
    }

    
    public boolean remove(int x) {
        Node prev_node; 

        Node current_node; 

        prev_node = head;
        current_node = head;
        

        while(current_node != z) {
            prev_node = current_node;
            current_node = current_node.next_node;

            if(current_node.value == x) {
                prev_node.next_node = current_node.next_node;
                return true;
            } // End if
        } // End while

        return false;
    } // End boolean remove(int x)
    

    public boolean isEmpty() {
        return head.next_node == z;
    } // End boolean isEmpty()
    

    public void display() {
        Node t = head;
        System.out.print("\nHead -> ");
        // Print till tail is reached
        while (t.next_node != null) {
            System.out.print(t.value + " -> ");
            t = t.next_node;
        } // End while
        System.out.println("Z\n");
    }
}  

