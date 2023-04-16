package com.ernestjohndecina;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

class Graph {

    // Private Variable
    // V = number of vertices
    // E = number of edges
    // adj[] is the adjacency lists array
    private int V, E;
    private Node[] adj;
    private Node z;
    private int[] mst;
    private int[] spt;
    private int[] spt_parent;
    
    // used for traversing graph
    private int[] visited;
    private int id;

    // End Private Variables

    // default constructor
    public Graph(File graphFile) throws IOException
    {
        int u, v;
        int e, wgt;

        FileReader fr = new FileReader(graphFile);
		BufferedReader reader = new BufferedReader(fr);
	    
        String splits = " +";  // multiple whitespace as delimiter
		String line = reader.readLine();        
        String[] parts = line.split(splits);
        System.out.println("Parts[] = " + parts[0] + " " + parts[1]);
        
        V = Integer.parseInt(parts[0]);
        E = Integer.parseInt(parts[1]);
        
        // create sentinel node
        z = new Node(); 
        z.next = z;
        
        // create adjacency lists, initialised to sentinel node z       
        adj = new Node[V+1];        
        for(v = 1; v <= V; ++v)
            adj[v] = z;               
        
       // read the edges
        System.out.println("Reading edges from text file");
        for(e = 1; e <= E; ++e)
        {
            line = reader.readLine();
            parts = line.split(splits);
            u = Integer.parseInt(parts[0]);
            v = Integer.parseInt(parts[1]); 
            wgt = Integer.parseInt(parts[2]);
            
            System.out.println("Edge " + toChar(u) + "--(" + wgt + ")--" + toChar(v));   

            Node vVertex = new Node();                                  // Create a vVertex Node
            Node uVertex = new Node();                                  // Create a uVertex Node
            Node adjListTraversalNode = adj[u];                         // Create a traversalNode for nodes connected to A, B, C, D ... M. 

            vVertex.vert = v;                                           // Set vert of vVertex
            vVertex.wgt = wgt;                                          // Set wgt of vVertex

            uVertex.vert = u;                                           // Set vert of uVertex
            uVertex.wgt = wgt;                                          // Set wgt of uVertex

            // Empty List               
            if(adjListTraversalNode == z) {                             // Traverse first list
                adj[u] = vVertex;                                       // Set vVertex as first item in adjacency list
                vVertex.next = z;                                       // Set vVertex.next to z
            } // End if

            // Non empty list
            else {
                while(adjListTraversalNode.next != z) {                 // Traverse List till reaching the end of LinkedList A -> B -> z
                    adjListTraversalNode = adjListTraversalNode.next;   // Move to next connected node
                } // End while

                adjListTraversalNode.next = vVertex;                    // Add new node to next  
                vVertex.next = z;                                       // set vVertex.next to z to indicate end
            } // End else


            adjListTraversalNode = adj[v];                              // Set traversalNode from adj[v]

            // First item in the list
            if(adjListTraversalNode == z) {                             // First item in the list
                adj[v] = uVertex;                                       // Set uVertex as first item in adjacency list
                uVertex.next = z;                                       // Set uVertex.next to z
            } // End if
            // Non empty list
            else {
                while(adjListTraversalNode.next != z) {                 // Traverse second list
                    adjListTraversalNode = adjListTraversalNode.next;   // Move to next connected node 
                } // End while

                adjListTraversalNode.next = uVertex;                    // Add new node to next 
                uVertex.next = z;                                       // set vVertex.next to z to indicate end
            } // End else

        } // End for

        reader.close();
    } // End Graph Constructor


    /**
     * This method is used to find the Minimal Spanning Tree using
     * Prims MST algo
     * @param s This is the anchor vertex for Minimal Spanning Tree
     */
	public void MST_Prim(int s)
	{
        int v, u;
        int wgt, wgt_sum = 0;
        Node t;
        int[]  dist = new int[V + 1];       // Set size of dist array of V + 1 for 0 at start
        int[]  parent = new int[V + 1];     // Set size of parent array of V + 1 for 0 at start
        int[]  hPos = new int[V + 1];       // Set size of hPos array of V + 1 for 0 at start

        for(v = 0; v < V + 1; v++) 
        {
            dist[v] = Integer.MAX_VALUE;    // Set all distances to MAX
            parent[v] = 0;      	        // Set all parent to 0
            hPos[v] = 0;                    // Ser all HeapPositions to 0
        } // End for
        
        dist[s] = 0;

        Heap h =  new Heap(V, dist, hPos);  // Heap initially empty
        h.insert(s);                        // s will be the root of the MST
        
        // Start of Prim's MST creation
        while(!h.isEmpty()) 
        {
            v = h.remove();                 // Add v to the MST
            wgt_sum += dist[v];             // Adds to total wgt of MST
            dist[v] = -dist[v];             // Marks v as now in the MST

            t = adj[v];                     // Make t first node connect to s vertex
            while(t != z)                   // Continue till end of linked list
            {                 
                u = t.vert;                 // Set u as vertex number
                wgt = t.wgt;                // Set wgt as weight of vertex u

                if(wgt < dist[u])              // Check if new wgt is smaller than current wgt in dist[]
                {         
                    dist[u] = wgt;          // Replace with new wgt
                    parent[u] = v;          // Replace parent[u] to the connect vertex v

                    if(!h.isMember(u)) 
                    {                       // Check if u is member of Heap
                        h.insert(u);        // Add u vertex into the Heap
                    } // End if 
                    else {              
                        h.siftUp(hPos[u]);  // SiftUp the position of u in Heap
                    } // End else
                } // End if

                t = t.next;                 // Move to the next connect vertex of vetex v
            } // End while
        } // End while

        // Print Weight of MST
        System.out.print("\n\nWeight of MST = " + wgt_sum + "\n");      
        mst = parent;            		    // Replace mst with parent array
	} // End void MST_Prim(int s)
   
    
    /**
     * This method prints out the mst generated by MST_Prim(s)
     */
    public void showMST()
    {
        System.out.print("\n\nMinimum Spanning tree parent array is:\n");
        for(int v = 1; v <= V; ++v)
            System.out.println(toChar(v) + " -> " + toChar(mst[v]));
        System.out.println("");
    } // End void showMST(int s)


    /**
     * This method is used to find the Shortest Path Tree using
     * Dijkstra's SPT algo
     * @param s This is the anchor vertex for Minimal Spanning Tree
     */
    public void SPT_Dijkstra(int s)
    {
        int v, u;
        int wgt, wgt_sum = 0;
        Node t;
        int[]  dist = new int[V + 1];                           // Set size of dist array of V + 1 for 0 at start
        int[]  parent = new int[V + 1];                         // Set size of parent array of V + 1 for 0 at start
        int[]  hPos = new int[V + 1];                           // Set size of hPos array of V + 1 for 0 at start

        for(v = 0; v < V + 1; v++) 
        {
            dist[v] = Integer.MAX_VALUE;                        // Set all distances to MAX
            parent[v] = 0;      	                            // Set all parent to 0
            hPos[v] = 0;                                        // Ser all HeapPositions to 0
        } // End for

        Heap priorityQueue =  new Heap(V, dist, hPos);          // Heap initially empty
        v = s;                                                  // Start v as anchor vertex
        priorityQueue.insert(v);                                // Insert v into priorityQueue
        dist[s] = 0;                                            // Set anchor verx distance to 0 as we already have reached it


        System.out.println("Shortest Path Tree values are:");
        while(!priorityQueue.isEmpty())
        {
            t = adj[v];       
            while(t != z)                                       // Continue till end of linked list
            {                 
                u = t.vert;                                     // Set u as vertex number
                wgt = t.wgt;                                    // Set wgt as weight of vertex u

                if(dist[v] + wgt < dist[u])                     // Check if new wgt is smaller than current wgt in dist[]
                {         
                    dist[u] = dist[v] + wgt;                    // Replace with new wgt

                    if(!priorityQueue.isMember(u)) 
                    {                                           // Check if u is member of Heap
                        priorityQueue.insert(u);                // Add u vertex into the Heap
                    } // End if 
                    else {              
                        priorityQueue.siftUp(hPos[u]);          // SiftUp the position of u in Heap
                    } // End else

                    parent[u] = v;
                } // End if
            
                t = t.next;                                     // Move to the next connect vertex of vetex v
            } // End while

            v = priorityQueue.remove();                         // Remove head of priorityQueue
        } // End while

        spt = dist;
        spt_parent = parent;
    } // End void SPT_Dijkstra(int s)


    /**
     * Thid method prints out the spt generated by SPT_Dijkstra(s)
     */
    void showSPT()
    {   
        System.out.println("[Vertex]  [Parent]  [Distance from chosen vertex]");
        for(int index = 1; index < V + 1; index++)
        {
            System.out.println(toChar(index) + "             " + toChar(spt_parent[index]) + "                   " +spt[index]);
        } // End for
    } // End void showSPT()


    /**
     * This method is used to Search using Depth First
     * @param s This is the anchor vertex for Depth First Search
     */
    public void DF(int s)
    {
        System.out.println("");
        System.out.println("Depth First Search: ");

        id = 0;
        visited = new int[V +  1];                                          // Initialize visted array
        for(int vertexIndex = 1; vertexIndex < V + 1; vertexIndex++)
            visited[vertexIndex] = 0;                                       // Set all visted valued to 0
        
        DFS(s, 0);                                           // Call Recursive Depth First Search starting at s

        System.out.println("Depth First Search Ended.");
    } // End void DF(int s)


    /**
     * This method is a recurisve function using Depth First Traversal
     * @param currentVertex This is the current Vertex 
     * @param previousVertex This is the previous Vertex
     */
    private void DFS(int currentVertex, int previousVertex)
    {
        Node t = adj[currentVertex];
        visited[currentVertex] = id++;                                      // Set the current vertex as visted
        
        System.out.println("    DFS visted vertex: " + toChar(currentVertex) + " From: " + toChar(previousVertex));


        while(t != z)
        {
            if(visited[t.vert] == 0)                                        // If t.vert has been Visted
                DFS(t.vert, currentVertex);                                 // Recursively Call DFS

            t = t.next;                                                     // Move to next connected vertex
        } // End while
    } // End void DFS(int currentVertex, int previousVertex)

    
    /**
     * This method is used to Search using Breadth First
     * @param s This id the anchor vertex for Breadth First Search
     */
    public void BF(int s)
    {
        int v;
        Node t;
        visited = new int[V + 1];
        for(int index = 0; index < V + 1; index++)
            visited[index] = 0;                          

        Queue queue = new Queue();                      // Create a queue
        queue.enQueue(s);   	                        // Enqueue the anchor vertex


        System.out.println("");
        System.out.println("Breadth First Search: ");


        while(!queue.isEmpty())
        {
            v = queue.deQueue();                        // Dequeue to get the current vertex
            if(visited[v] == 0)
            {
                visited[v] = 1;                         // Mark the current vertex as visted
                System.out.println("    BFS visted vertex: " + toChar(v));
                
                t = adj[v];                             // Assign t the current vertex
                while(t != z)                           
                {
                    if(visited[t.vert] == 0)            // Check if vertex has been visted
                        queue.enQueue(t.vert);          // Enqueue unvisted vertex
                    
                    t = t.next;                         // Move to the next vertex connected
                } // End while
            } // End if
        } // End while


        System.out.println("Breadth First Search Ended.");
    } // End void BF(int s)


    //
    // Function Dependencies
    //
    //

    /**
     * convert vertex into char for pretty printing
     * @param u the number to be converted into a letter
     * @return  the number as a charcacrer
     */
    private char toChar(int u)
    {  
        return (char)(u + 64);
    } // End char toChar(u)
    
    /**
     * method to display the graph representation
     */
    public void display() 
    {
        int v;
        Node n;
        
        for(v=1; v<=V; ++v){
            System.out.print("\nadj[" + toChar(v) + "] ->" );
            for(n = adj[v]; n != z; n = n.next) 
                System.out.print(" |" + toChar(n.vert) + " | " + n.wgt + "| ->");    
        }
        System.out.println("");
    } // End void display()

    //
    //  Class Dependencies
    //      
    //
    //
    private class Heap {
        private int[] a;	   // heap array
        private int[] hPos;	   // hPos[h[k]] == k
        private int[] dist;    // dist[v] = priority of v
    
        private int N;         // heap size
       
        // The heap constructor gets passed from the Graph:
        //    1. maximum heap size
        //    2. reference to the dist[] array
        //    3. reference to the hPos[] array
        public Heap(int maxSize, int[] _dist, int[] _hPos) 
        {
            N = 0;
            a = new int[maxSize + 1];
            dist = _dist;
            hPos = _hPos;
        }
    
    
        public boolean isEmpty() 
        {
            return N == 0;
        }
    
        public boolean isMember(int checkMember) {
            return hPos[checkMember] != 0;
        } // End boolean isMember(int checkMember)
    
    
        public void siftUp( int k) 
        {
            int v = a[k];
            
            while(k > 1 && dist[v] < dist[a[k / 2]]) {
                a[k] = a[k / 2];
                hPos[a[k]] = k;
                k = k / 2;
            }
    
            a[k] = v;
            hPos[v] = k;
        }
    
    
        public void siftDown( int k) 
        {
            int v, j;
            v = a[k]; 
    
            while( k <= N / 2 ) { 
                j = 2 * k; 
                if(j < N && dist[a[j]] > dist[a[j+1]]) ++j;
    
                if( dist[v] <= dist[a[j]]) break;
    
                a[k] = a[j]; 
                hPos[a[k]] = k; 
                k = j; 
            } 
    
            a[k] = v; 
            hPos[v] = k; 
        }
    
    
        public void insert(int x) 
        {
            a[++N] = x;
            siftUp(N);
        }
    
    
        public int remove() 
        {   
            int v = a[1];
            hPos[v] = 0; // v is no longer in heap
            a[N+1] = 0;  // put null node into empty spot
            
            a[1] = a[N--];
            siftDown(1);
            
            return v;
        }
    
        private void display() {
            System.out.print("[");
            for(int i = 0; i < N; i++) {
                System.out.print(a[i] + ", ");
            } // End for
            System.out.print("]");
            System.out.println("");
        }
    }
    
    private class Node {
        public int vert;
        public int wgt;
        public Node next;
    }

    private class Queue {
        // Priavte Variables
        Node head;
        Node tail;
        Node z;
    
        // assume the queue is non-empty when this method is called, otherwise thro
        // exception
        public int deQueue() {
            int value = head.vert;
            this.head = head.next;
            return value;
        }
    
        public void enQueue(int x) {
            Node t;
    
            t = new Node();
            t.vert = x;
            t.next = z;
    
            if (head == z) // case of empty list
                head = t;
            else // case of list not empty
                tail.next = t;
    
            tail = t; // new node is now at the tail
        }
    
        public boolean isEmpty() {
            return head == z;
        }
    
        public void display() {
            System.out.println("\nThe queue values are:\n");
    
            Node t = head;
            while (t != t.next) {
                System.out.print(t.vert + "  ");
                t = t.next;
            }
            System.out.println("\n");
        }
    }
    
} // End class Graph


public class GraphAssignment 
{
    static String graphFileName;
    static File graphFile = new File("");
    static int startingVertex;

    public static void main(String args[]) throws IOException
    {
        getUserInput();
        Graph graph = new Graph(graphFile);
        graph.display();
        graph.MST_Prim(startingVertex);
        graph.showMST();
        graph.SPT_Dijkstra(startingVertex);
        graph.showSPT();
        graph.DF(startingVertex);
        graph.BF(startingVertex);
    } // End void main


    /**
     * 
     */
    private static void getUserInput() {
        Scanner userInputScanner = new Scanner(System.in);

        // Prompt user to input file name
        while(!graphFile.exists()) {
            System.out.println("Enter file name: ");
            graphFileName = userInputScanner.nextLine();
            graphFile = new File("data/" + graphFileName);

            if(!graphFile.exists()) {
                System.out.println("Please enter valid file name. Make sure to enter file extension");
            } // End if
        } // End while

        System.out.println("Enter starting vertexas number: ");
        while(!userInputScanner.hasNextInt()) {
            System.out.println("Invalid input. Vertex must be a number");
            System.out.println("Enter starting vertex as number: ");
            userInputScanner.nextLine();
        }

        startingVertex = userInputScanner.nextInt();

        userInputScanner.close();
    } // End void getUserInput

    private static void displayStudentDetails()
    {
        System.out.println("===================================================");
        System.out.println("Algorithms & Data Structures Assignment");
        System.out.println("Student Number: C21394933");
        System.out.println("===================================================");
        System.out.println("\n");
    } // End void displayStudentDetails()
}