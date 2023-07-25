/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Assignment;

/**
 *
 * @author Khairul<Khairul Amirin UM>
 */
import java.io.*;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Arrays;

public class TestAssignmentKA {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        Assignment analyzer= new Assignment("D:\\TXT\\extracted_log.txt");
        
        //analyzer.jobCausingError();
        //analyzer.jobDone();
        
//        String inputTime_Before= "2022-06-01T01:02:35.148";
//        String inputTime_After= "2022-06-01T09:16:23.309";
//        analyzer.jobCreated(inputTime_Before, inputTime_After);

        analyzer.jobCreated();
        
        //analyzer.exeTime(42808);
        
        analyzer.displayExeTime();
       
       
        
    }
    

    
    
}



