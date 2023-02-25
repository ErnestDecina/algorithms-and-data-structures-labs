package com.ernestjohndecina;

public class Queue {
    Node z, head, tail;

    public Queue() {
        z = new Node(); z.next = z;
        head = z;  tail = null;
    } // End Queue Constructor
   

    public void enQueue( int x) {
        Node t;

        t = new Node();
        t.data = x;
        t.next = z;

        // case of empty list
        if(head == z) {
            head = t;
        } // End if
        // case of list not empty
        else {
            tail.next = t;
        } // End else          
            
        tail = t;           // new node is now at the tail
    } // End void enQueue

  // assume the queue is non-empty when this method is called
    public int deQueue() {

        int value = head.data;
        this.head = head.next;
        return value;
    } // End int deQueue
    

    public boolean isEmpty() {
        return head == head.next;
    } // End boolean isEmpty
  
    public boolean isMember(int x) {
        Node node = head;
        while (node != node.next) {
            if (node.data == x) {
                return true;
            }
            node = node.next;
        }

        return false;
    } // End boolean isMember
    
    public void display() {
        System.out.println("\nThe queue values are:\n");

        Node t = head;
        while( t != t.next) {
            System.out.print( t.data + "  ");
            t = t.next;
        }
        System.out.println("\n");
    } // End void display 
}
