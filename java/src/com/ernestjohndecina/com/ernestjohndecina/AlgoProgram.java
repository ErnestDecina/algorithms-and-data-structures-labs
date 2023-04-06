package com.ernestjohndecina;

// Dependencies
import java.util.Scanner;
import java.io.File;

public class AlgoProgram {
    // Private Variables
    int startingVertex;
    String graphFileName;
    File graphFile = new File("");


    // Constructor
    public AlgoProgram() {

    } // End AlgoProgram Constructor

    public void getUserInput() {
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

        // TODO: Add error checking for vertex's existence
        System.out.println("Enter starting vertexas number: ");
        while(!userInputScanner.hasNextInt()) {
            System.out.println("Invalid input. Vertex must be a number");
            System.out.println("Enter starting vertex as number: ");
            userInputScanner.nextLine();
        }

        startingVertex = userInputScanner.nextInt();

        userInputScanner.close();
    } // End void getUserInput

    public void startProgram() {
        
    }

    public void endProgram() {
        System.out.println("Exiting File");
    } // End void endProgram()
}
