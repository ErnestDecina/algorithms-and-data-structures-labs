package com.ernestjohndecina;

import java.util.Scanner;

public class Main {
    public static void main(String args[]) {
        SortedLL list = new SortedLL();
        //list.display();
        
        double x;
        int i, r;
        
        
        for(i = 1; i <= 5; ++i)  {
            x =  (Math.random()*100.0);
            r = (int) x; 
            System.out.println("Inserting " + r);
            list.insert(r);
            list.display();            
        } // End for
        

        while(!list.isEmpty())  {
            System.out.println("\nInput a value to remove: ");
            Scanner in = new Scanner(System.in);
            r = in.nextInt();
            if(list.remove(r)) {
                System.out.println("\nSuccessfully removed: " + r);
            list.display(); }
            else System.out.println("\nValue not in list");                        
        }
    } // End void main
} // End class Main
