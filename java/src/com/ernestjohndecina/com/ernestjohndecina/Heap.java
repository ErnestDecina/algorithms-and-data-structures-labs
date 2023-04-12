package com.ernestjohndecina;

public class Heap {
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
        if(hPos[checkMember] == 0) return false;
        return true;
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
