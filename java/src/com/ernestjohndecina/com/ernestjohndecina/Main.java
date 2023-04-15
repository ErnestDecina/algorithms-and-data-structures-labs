package com.ernestjohndecina;

import java.beans.VetoableChangeListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

import javax.lang.model.util.ElementScanner14;

class Kruskals {
    // Private Variable
    // V = number of vertices
    // E = number of edges
    // adj[] is the adjacency lists array
    private int V, E;
    private Node[] adj;
    private Node z;
    private ArrayList<Edge> mst;
    private ArrayList<Edge> arrayEdges;


    // default constructor
    public Kruskals(File graphFile) throws IOException {
        int u, v;
        int e, wgt;
        Node t;

        FileReader fr = new FileReader(graphFile);
        BufferedReader reader = new BufferedReader(fr);

        String splits = " +"; // multiple whitespace as delimiter
        String line = reader.readLine();
        String[] parts = line.split(splits);
        System.out.println("Parts[] = " + parts[0] + " " + parts[1]);

        V = Integer.parseInt(parts[0]);
        E = Integer.parseInt(parts[1]);

        // create sentinel node
        z = new Node();
        z.next = z;

        // create adjacency lists, initialised to sentinel node z
        adj = new Node[V + 1];
        for (v = 1; v <= V; ++v)
            adj[v] = z;

        // read the edges
        System.out.println("Reading edges from text file");

        // Create an Array of Edges
        arrayEdges = new ArrayList<Edge>();          
        arrayEdges.add(new Edge());
        arrayEdges.get(0).wgt = 0;

        // Add edges from txt file to Array of Edges
        for (e = 1; e <= E; ++e) {
            line = reader.readLine();
            parts = line.split(splits);
            u = Integer.parseInt(parts[0]);
            v = Integer.parseInt(parts[1]);
            wgt = Integer.parseInt(parts[2]);

            System.out.println("Edge " + toChar(u) + "--(" + wgt + ")--" + toChar(v));


            Edge newEdge = new Edge();
            newEdge.u = u;
            newEdge.v = v;
            newEdge.wgt = wgt;

            arrayEdges.add(newEdge);
        } // End for
    } // End Graph Constructor

    public void MST_Kruskals() {
        int i = 0;
        UnionFindSets partition = new UnionFindSets(V);


        HeapSort.heapSort(arrayEdges, E + 1);                       // Sort the Array of Edges

        // Displaying Sorted Edges
        for(i = 0; i < E + 1; i++)
            System.out.println(toChar(arrayEdges.get(i).u)  + "-(" + arrayEdges.get(i).wgt + ")-" + toChar(arrayEdges.get(i).v));
        System.out.println();

        mst = new ArrayList<Edge>();

        for (int edgeIndex = 1; edgeIndex < E + 1; edgeIndex++) 
        {   
            int setV = partition.findSet(arrayEdges.get(edgeIndex).v);
            int setU = partition.findSet(arrayEdges.get(edgeIndex).u);

            if( setU != setV ) {
                partition.union(setU, setV);
                mst.add(arrayEdges.get(edgeIndex));
            } // End if

            partition.showTrees();

        } // End for

        // Show Trees
        partition.showSets();
    } // End void MST_Kruskals

    public void showMST()
    {
        System.out.print("\nMinimum spanning tree build from following edges:\n");
        for(int e = 0; e < V-1; ++e) {
            mst.get(e).show(); 
        }
        System.out.println(); 
    } // End void showMST()

    //
    // Function Dependencies
    //
    //


    
    /**
     * convert vertex into char for pretty printing
     * 
     * @param u the number to be converted into a letter
     * @return the number as a charcacrer
     */
    private char toChar(int u) {
        return (char) (u + 64);
    } // End char toChar(u)

    /**
     * method to display the graph representation
     */
    public void display() {
        int v;
        Node n;

        for (v = 1; v <= V; ++v) {
            System.out.print("\nadj[" + toChar(v) + "] ->");
            for (n = adj[v]; n != z; n = n.next)
                System.out.print(" |" + toChar(n.vert) + " | " + n.wgt + "| ->");
        }
        System.out.println("");
    } // End void display()

    //
    // Class Dependencies
    //
    //

    private static class HeapSort {
        public static void heapSort(ArrayList<Edge> array, int size)
        {
            
            for(int k = (size / 2) - 1; k > 0; k--) {
                siftDown(k, array, size);
            } // End for

            for(int k = size - 1; k > 1; k--) {
                Edge v = array.get(0);
                array.set(0, array.get(k));
                siftDown(0, array, k);
                array.set(k, v);
            } // End for

        } // End void heapSort()

        private static void siftDown(int k, ArrayList<Edge> array, int size)
        {
            Edge edge = array.get(k);
            int j = (2 * k) + 1;

            while(j <= size - 1) {
                if(j < size - 1 && array.get(j).wgt < array.get(j + 1).wgt ) ++j;
                if( edge.wgt >= array.get(j).wgt) break;

                array.set(k, array.get(j));
                k = j;
                j = (2 * k) + 1;
            } // End while  

            array.set(k, edge);
        } // End void siftDown()

    } // End class HeapSort

    class UnionFindSets {
        private int[] treeParent;
        private int[] rank;
        private int N;

        public UnionFindSets(int V) {
            N = V;
            treeParent = new int[V + 1];
            rank = new int[V + 1];

            for(int i = 0; i < V; i++)
            {
                treeParent[i] = i;
                rank[i] = 0;
            } // End for
            // missing lines
        }

        public void makeSet(int x)
        {
            treeParent[x] = x;
            rank[x] = 0;
        } // End void makeSet(int x)

        public void union(int set1, int set2) {
            if( rank[set1] < rank[set2] ) 
            {
                treeParent[set1] = set2;
            } // End if
            else if ( rank[set1] > rank[set2])
            {
                treeParent[set2] = set1;
            } // End else if 
            else 
            {
                treeParent[set2] = set1;
                rank[set1] = rank[set1] + 1;
            } // End else 
        }

        public int findSet(int vertex) {
            if (treeParent[vertex] != vertex) 
                treeParent[vertex] = findSet(treeParent[vertex]);
        
            return treeParent[vertex];
        } // End findSet

        public void showTrees() {
            int i;
            for (i = 1; i < N; ++i)
                System.out.print(toChar(i) + "->" + toChar(treeParent[i]) + "  ");
            System.out.print("\n");
        }

        public void showSets() {
            int u, root;
            int[] shown = new int[N + 1];
            for (u = 1; u < N; ++u) {
                root = findSet(u);
                if (shown[root] != 1) {
                    showSet(root);
                    shown[root] = 1;
                }
                else {
                    return;
                }
            }
            System.out.print("\n");
        }

        private void showSet(int root) {
            int v;
            System.out.print("Set{");
            for (v = 1; v <= N; ++v)
                if (findSet(v) == root)
                    System.out.print(toChar(v) + " ");
            System.out.print("}  ");

        }

        private char toChar(int u) {
            return (char) (u + 64);
        }
    }

    private class Edge {
        public int u, v, wgt;

        // Constructor
        public Edge() {
            u = 0;
            v = 0;
            wgt = 0;
        } // End Edge Constructor

        // Constructor
        public Edge(int x, int y, int w) {
            this.u = x;
            this.v = y;
            this.wgt = w;
        } // End Edge Constructor

        public void show() {
            System.out.print("Edge " + toChar(u) + "--" + wgt + "--" + toChar(v) + "\n");
        }

        // convert vertex into char for pretty printing
        private char toChar(int u) {
            return (char) (u + 64);
        }
    }

    private class Node {
        public int vert;
        public int wgt;
        public Node next;
    }
} // End class Kruskals

public class Main {
    static String graphFileName;
    static File graphFile = new File("");
    static int startingVertex;

    public static void main(String args[]) throws IOException {
        displayStudentDetails();
        getUserInput();

        Kruskals kruskals = new Kruskals(graphFile);
        kruskals.MST_Kruskals();
        kruskals.showMST();
    } // End void main


    /**
     * 
     */
    private static void getUserInput() {
        Scanner userInputScanner = new Scanner(System.in);

        // Prompt user to input file name
        while (!graphFile.exists()) {
            System.out.println("Enter file name: ");
            graphFileName = userInputScanner.nextLine();
            graphFile = new File("data/" + graphFileName);

            if (!graphFile.exists()) {
                System.out.println("Please enter valid file name. Make sure to enter file extension");
            } // End if
        } // End while

        System.out.println("Enter starting vertex as number: ");
        while (!userInputScanner.hasNextInt()) {
            System.out.println("Invalid input. Vertex must be a number");
            System.out.println("Enter starting vertex as number: ");
            userInputScanner.nextLine();
        }

        startingVertex = userInputScanner.nextInt();

        userInputScanner.close();
    } // End void getUserInput

    private static void displayStudentDetails() {
        System.out.println("===================================================");
        System.out.println("Algorithms & Data Structures Assignment");
        System.out.println("Student Number: C21394933");
        System.out.println("===================================================");
        System.out.println("\n");
    } // End void displayStudentDetails()
} // End class GraphKruskals
