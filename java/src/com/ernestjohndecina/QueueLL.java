package com.ernestjohndecina;

public class QueueLL implements Queue {
    // Priavte Variables
    Node head;
    Node tail;
    Node z;

    // assume the queue is non-empty when this method is called, otherwise thro
    // exception
    @Override
    public int deQueue() throws QueueException {
        int value = head.data;
        this.head = head.next;
        return value;
    }

    @Override
    public void enQueue(int x) throws QueueException {
        Node t;

        t = new Node();
        t.data = x;
        t.next = z;

        if (head == z) // case of empty list
            head = t;
        else // case of list not empty
            tail.next = t;

        tail = t; // new node is now at the tail
    }

    @Override
    public boolean isEmpty() {
        return head == head.next;
    }

    @Override
    public void display() {
        System.out.println("\nThe queue values are:\n");

        Node t = head;
        while (t != t.next) {
            System.out.print(t.data + "  ");
            t = t.next;
        }
        System.out.println("\n");
    }

}
