package com.ernestjohndecina;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

class Kruskals {
    // Private Variable
    // V = number of vertices
    // E = number of edges
    // adj[] is the adjacency lists array
    private int V, E;
    private ArrayList<Edge> mst;
    private Edge[] arrayEdges;


    // default constructor
    public Kruskals(File graphFile) throws IOException {
        int u, v;
        int e, wgt;

        FileReader fr = new FileReader(graphFile);
        BufferedReader reader = new BufferedReader(fr);

        String splits = " +"; // multiple whitespace as delimiter
        String line = reader.readLine();
        String[] parts = line.split(splits);
        System.out.println("Parts[] = " + parts[0] + " " + parts[1]);

        V = Integer.parseInt(parts[0]);
        E = Integer.parseInt(parts[1]);

        // read the edges
        System.out.println("Reading edges from text file");

        // Create an Array of Edges
        arrayEdges = new Edge[E + 1];                               // Create an array of Edges
        arrayEdges[0] = new Edge(0, 0, 0);                    // Add a 0 Edge at the start

        // Add edges from txt file to Array of Edges
        for (e = 1; e <= E; ++e) {
            line = reader.readLine();
            parts = line.split(splits);
            u = Integer.parseInt(parts[0]);
            v = Integer.parseInt(parts[1]);
            wgt = Integer.parseInt(parts[2]);

            System.out.println("Edge " + toChar(u) + "--(" + wgt + ")--" + toChar(v));

            
            Edge newEdge = new Edge(u, v, wgt);                     // Create new Edge
            arrayEdges[e] = newEdge;                                // Add new Edge to array of edges
        } // End for    

        reader.close();
        System.out.println();
    } // End Graph Constructor

    public void MST_Kruskals() {
        int mstWgt = 0;
        UnionFindSets partition = new UnionFindSets(V);

        System.out.println("Edge array before sorting:");
        displayEdgesArray();                                        // Displaying unsorted Edges

        HeapSort.heapSort(arrayEdges, E + 1);                       // Sort the Array of Edges
        
        System.out.println("Edge array after sorting:");
        displayEdgesArray();                                        // Displaying Sorted Edges

        mst = new ArrayList<Edge>();                                // Create Array List of Edges

        // Go through each edge in the array
        System.out.println("Started Kruskals Minimal Spanning Tree: ");
        for (int edgeIndex = 0; edgeIndex < E; edgeIndex++) 
        {   
            int setV = partition.findSet(arrayEdges[edgeIndex].v);  // Find the set that vertex v is part of
            int setU = partition.findSet(arrayEdges[edgeIndex].u);  // Find the set that vertex u is part of

            System.out.print("  ");
            partition.showSets();
            System.out.print("  ");
            partition.showTrees();
            System.out.println();

            if( setU != setV ) {                                    // If setU is not  part of the same set at setV
                partition.union(setU, setV);                        // Make setU and setV part of the same set
                mst.add(arrayEdges[edgeIndex]);                     // Add to the Minimal Spanning Tree
            } // End if
        } // End for
        System.out.println("Finished Kruskals Minimal Spanning Tree.");

        // Show Sets
        partition.showSets();
        partition.showTrees();

        showMST();

        System.out.println("\n");
        // Show total weight of MST
        for (Edge edge: mst) 
            mstWgt += edge.wgt;
        
        System.out.println("Weight of Kruskals Minimal Spanning Tree is: " + mstWgt);
    } // End void MST_Kruskals

    private void showMST() {
        System.out.print("\nMinimum spanning tree build from following edges:\n");
        for(int e = 0; e < V-1; ++e) {
            System.out.print("Edge ");
            mst.get(e).show(); 
            System.out.println();
        }
        System.out.println(); 
    } // End void showMST()

    public void printMST() {
        System.out.print("  MST: ");
        System.out.print("[");
        for(int e = 0; e < mst.size(); ++e) {
            mst.get(e).show();
            System.out.print(", ");
        }
        System.out.print("]");
        System.out.println(); 
    } // End void showMST()
    

    //
    // Function Dependencies
    //
    //

    /**
     * This method is used to display the contents of arrayEdges
     */
    public void displayEdgesArray()
    {
        for(int i = 0; i < E + 1; i++)
            System.out.println(toChar(arrayEdges[i].u)  + "-(" + arrayEdges[i].wgt + ")-" + toChar(arrayEdges[i].v));
        System.out.println();
    } // End void displayEdgesArray

    
    /**
     * convert vertex into char for pretty printing
     * 
     * @param u the number to be converted into a letter
     * @return the number as a charcacrer
     */
    private char toChar(int u) {
        return (char) (u + 64);
    } // End char toChar(u)


    //
    // Class Dependencies
    //
    //

    private static class HeapSort {
        public static void heapSort(Edge[] array, int size)
        {
            for(int k = (size / 2) - 1; k > 0; k--) {
                siftDown(k, array, size);
            } // End for

            for(int k = size - 1; k > 1; k--) {
                Edge v = array[0];                  // Largest value on heap
                array[0] = array[k];                // a[k] is last value on heap
                siftDown(0, array, k);            // k is now heap size
                array[k] = v;                       // a[k] is no longer in heap
            } // End for

        } // End void heapSort()

        private static void siftDown(int k, Edge[] array, int size)
        {
            Edge edge = array[k];
            int j = (2 * k) + 1;                    // Left child of k since heap begins in a[0]

            while(j <= size - 1) {                  // While left child is within heap
                if(j < size - 1 && array[j].wgt < array[j + 1].wgt ) ++j;
                if( edge.wgt >= array[j].wgt) break;

                array[k] = array[j];
                k = j;
                j = (2 * k) + 1;
            } // End while  

            array[k] = edge;
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
            // set1 and set2 are not already in same set. Merge them
            if( rank[set1] < rank[set2] ) 
                treeParent[set1] = set2;
            else if ( rank[set1] > rank[set2])
                treeParent[set2] = set1;
            else {
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
            for (u = 0; u < N; ++u) {
                root = findSet(u);
                if (findSet(root) != u && shown[root] != 1) {
                    showSet(root);
                    shown[root] = 1;
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
            System.out.print(toChar(u) + "--" + wgt + "--" + toChar(v));
        }

        // convert vertex into char for pretty printing
        private char toChar(int u) {
            return (char) (u + 64);
        }
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
    } // End void main


    /**
     *  Method used to get user input
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
