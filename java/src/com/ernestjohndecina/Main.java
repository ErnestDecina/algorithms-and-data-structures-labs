package com.ernestjohndecina;

public class Main {
    public static void main(String args[]) {
        Queue q = new Queue();

        System.out.println("Inserting ints from 9 to 1 into queue gives:\n");
        for (int i = 9; i > 0; --i) {
           q.enQueue( i);
        }
    
        q.display();
    
        System.out.println("\n is 12 in queue? " + q.isMember(12));
        System.out.println("\n is 6 in queue? " + q.isMember(6));
        
        if( ! q.isEmpty())
            System.out.println("Deleting value from queue " + q.deQueue() + "\n");
    
        System.out.println("Adding value to queue " + 27 + "\n");
    
        q.enQueue(27);
        q.display();
    
        System.out.println("Dequed nodes");
        for (int i = 0; i < 4; i++) {
            q.deQueue();
        }
            
        q.display();    
    } // End void main
} // End class Main
