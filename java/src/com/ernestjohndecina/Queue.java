package com.ernestjohndecina;

public interface Queue {
    public void enQueue(int x) throws QueueException;
    public int deQueue() throws QueueException;
    public boolean isEmpty();  
    public void display();
} // End interface Queue
