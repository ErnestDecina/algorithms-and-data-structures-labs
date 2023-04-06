package com.ernestjohndecina;

import java.io.IOException;

public class Main {
    public static void main(String args[]) throws IOException {
        int s = 2;
        String fname = "wGraph3.txt";               

        Graph g = new Graph(fname);
       
        g.display();

       //g.DF(s);
       //g.breadthFirst(s);
       //g.MST_Prim(s);   
       //g.SPT_Dijkstra(s);  
    } // End void main
} // End class Main
