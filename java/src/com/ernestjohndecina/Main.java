package com.ernestjohndecina;

public class Main {
    public static void main(String args[]) {
        Queue q1, q2;
        q1 = new QueueLL();
        // q2 = new QueueCB();
        
        for(int i = 1; i<6; ++i)
        try { 
            q1.enQueue(i);            
        } catch (QueueException e) {
            System.out.println("Exception thrown: " + e); 
        }
        
        q1.display();
        // more test code
    } // End void main
} // End class Main
