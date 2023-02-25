package com.ernestjohndecina;

public class Heap {
    private int[] a;
    int N;
    static int maxH = 100;

    // two constructors
    Heap() {
        N = 0;
        a = new int[maxH + 1];
    }

    Heap(int size) {
        N = 0;
        a = new int[size + 1];
    }

    void siftUp(int k) {
        int v = a[k];
        a[0] = Integer.MAX_VALUE;

        // Check if the inserted value if greater than current value in tree
        while(v > a[k / 2]) {
            a[k] = a[k / 2];
            k = k / 2;
        } // End while

        // Set Value to current position in tree
        a[k] = v;
    }

    void siftDown(int k) {
        int v, j;
        v = a[k];
        // complete yourself

        // Check if LEFT CHILD Exists
        while( (2 * k) <= N) {
            j = 2 * k;

            // Check which INSERT VALUE is greater than RIGHT child node
            if( j < N && a[j] < a[j + 1] ) {
                j++; // Make RIGHT CHILD NODE new path to go down to
            } // end if

            // Check if INSERT VALUE is greater than LEFT child node
            if( v >= a[j])
                // Break and set that as position for new value
                break;
            
            // Set 
            a[k] = a[j];
            k = j;
        }

        a[k] = v;
    }

    void insert(int x) {
        a[++N] = x;
        siftUp(N);
    }

    int remove() {
        a[0] = a[1]; // store highest priority value in a[0]
        a[1] = a[N--]; // and replace it with value from end og the heap
        siftDown(1); // and then sift this value down
        return a[0];
    }

    void display() {
        System.out.println("\nThe tree structure of the heaps is:");
        System.out.println(a[1]);
        for (int i = 1; i <= N / 2; i = i * 2) {
            for (int j = 2 * i; j < 4 * i && j <= N; ++j)

                System.out.print(a[j] + "  ");
            System.out.println();
        }
        System.out.println();
    }
}
