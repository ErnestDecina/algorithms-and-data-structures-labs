package com.ernestjohndecina;

public class Main {
    public static void main(String args[]) {
        Heap h = new Heap();
        int r;
        double x;

        // insert random numbers between 0 and 99 into heap
        for (int i = 1; i <= 10; ++i) {
            x = (Math.random() * 100.0);
            r = (int) x;
            System.out.println("Inserting " + r);
            h.insert(r);
            h.display();
        } // End for
    } // End void main
} // End class Main
