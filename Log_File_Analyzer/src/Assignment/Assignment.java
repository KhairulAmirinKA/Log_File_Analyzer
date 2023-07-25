/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Assignment;

/**
 *
 * @author OCC9-02
 */
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.Duration;

import java.util.regex.*;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashMap;

public class Assignment {
    
    String filePath; //location of the log file

    //create constructor
    public Assignment(String filePath) {
        this.filePath = filePath;
    }
    public Assignment(){
       
    }
    
     //store timestamp for job created
    ArrayList<String> jobCreatedTime= new ArrayList<>();
    
    //store timestamp for job causing error
    ArrayList<String> jobErrorTime= new ArrayList<>();
    
    ArrayList<String> jobID_Created= new ArrayList<>(); //store job id created
    
    ArrayList<String> jobError= new ArrayList<>(); // to store job id causing error
    ArrayList<String> jobDone= new ArrayList<>();  //to store job id done
    
    //partition
    //opteron
    ArrayList<String> opteron= new ArrayList<>(); 
    
    //epyc
    ArrayList<String> epyc= new ArrayList<>();
    
    //gpu_titan
    ArrayList<String> gpu_titan= new ArrayList<>();
    
    //gpu_v100s
    ArrayList<String> gpu_v100s= new ArrayList<>();
    
    //gpu_k40c
    ArrayList<String> gpu_k40c= new ArrayList<>();
    
    //gpu_k10
    ArrayList<String> gpu_k10= new ArrayList<>();
    
    
    //question d. average execution time
    ArrayList<Integer> usec= new ArrayList<>();
    
    
    //question e- job kiled
    ArrayList<String> jobKilledList= new ArrayList<>();
    
    
    //question a- job created within time range
    public int jobCreated(String startTime, String endTime){
       
        String regex = "\\[(\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}\\.\\d{3})\\]\\s(_slurm_rpc_submit_batch_job:)\\sJobId=(\\d+)\\sInitPrio=(\\d+)\\susec=(\\d+)";
        int jobCount = 0;
        //clear to avoid duplicate
        jobCreatedTime.clear();
        
        try{
   
        Scanner sc=  new Scanner(new FileInputStream(filePath));
        Pattern pattern = Pattern.compile(regex);
        
            //kira job
           
        while (sc.hasNextLine()){
            
            String line= sc.nextLine();
        Matcher matcher = pattern.matcher(line);
        
        if (matcher.find()) {
            
            //System.out.println("Timestamp: " + matcher.group(1));

            String timestamp= matcher.group(1);
            
            //add ke dlm list
            jobCreatedTime.add(timestamp);
            
        } //if find
    
        } //while
        
        //processing time
         DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");
         
         //startTime
        LocalDateTime startDateTime = LocalDateTime.parse(startTime, formatter);
        
        //endtime
        LocalDateTime endDateTime = LocalDateTime.parse(endTime, formatter);

     
        
        // jobCreatedTime is ArrayList. traverse the list
        for (String jobCreatedTimestamp : jobCreatedTime) {
            
            LocalDateTime jobDateTime = LocalDateTime.parse(jobCreatedTimestamp, formatter);
            
            //between timestamp. startTime<= x <= endTime
            
            if (  (jobDateTime.isAfter(startDateTime) || (jobDateTime.isEqual(startDateTime)) ) 
                    && 
                (    jobDateTime.isBefore(endDateTime) || jobDateTime.isEqual(endDateTime) )  ) {
                jobCount++;
            }
            
        }
       // System.out.println("Number of jobs completed between " + startTime +" and "+ endTime+ ": " + jobCount);
        }
        
        
        catch (FileNotFoundException e){
            System.out.println("File not found. Please run again");
        }
        return jobCount;
        } //job created 
    
    //question b. job by partition
    public int jobByPartition(int i){
        
        gpu_k10.clear();
        gpu_k40c.clear();
        gpu_titan.clear();
        gpu_v100s.clear();
        opteron.clear();
        epyc.clear();
     
        try{
            //read the file
        Scanner sc= new Scanner(new FileInputStream(filePath));
        
        //regex to match the line
        //this line indicates the job is created
        String regex = "\\[(\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}.\\d{3})\\] sched: Allocate JobId=(\\d+) NodeList=(\\w+) #CPUs=(\\d+) Partition=((\\w+)-\\w+)";
       
 
        while (sc.hasNextLine()){
    
        String line= sc.nextLine();
        
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(line);

        if (matcher.find()) {
            
            String jobId= matcher.group(2);
            
            String partition= matcher.group(5);
            
            if (partition.contains("epyc")){
                epyc.add(jobId); //add to the epyc arrayList
            }
            
            else if (partition.contains("opteron")){
                opteron.add(jobId);
            }
            
            else if (partition.contains("titan")){
                gpu_titan.add(jobId);
            }
            
            else if (partition.contains("k40c")){
                gpu_k40c.add(jobId);   
            }
            
            else if (partition.contains("v100s")){
                gpu_v100s.add(jobId);   
            }
            
            else if (partition.contains("k10")){
                gpu_k10.add(jobId);   
            }
            
       
        } //if find

        }//while
    
    //jgn buang yg ni
//            System.out.println("Total jobs of CPU EPYC= " + epyc.size());
//            System.out.println("Total jobs of CPU Opteron= " + opteron.size());
//            System.out.println("Total jobs of GPU Titan= " + gpu_titan.size());
//            System.out.println("Total jobs of GPU V100s= " + gpu_v100s.size());
//            System.out.println("Total jobs of GPU K40c= " + gpu_k40c.size());
//            System.out.println("Total jobs of GPU K10= " + gpu_k10.size());
//            
        sc.close();
        } //try
        
        catch (FileNotFoundException fe){
            System.out.println("File not found. Please run again");
        } //catch
        
        //CHoice
        if(i==1)
            return epyc.size();
        if(i==2)
            return opteron.size();
        if(i==3)
            return gpu_titan.size();
        if(i==4)
            return gpu_v100s.size();
        if(i==5)
            return gpu_k40c.size();
        else
            return gpu_k10.size();
        
}//job done
    
    //question c- job error within time range
    public String jobCausingError(String startTime, String endTime){
       
        String regex = "\\[(\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}.\\d{3})\\] error: This association (\\d+)\\(account='(\\S+)', user='(\\S+)', partition='(\\S+)'\\) does not have access to qos (\\S+)";
        int jobErrorCount = 0;
        
        //clear to avoid duplicate
        jobErrorTime.clear();
       String user="ali";
       
       //store timestamp with user
       HashMap<String,String> map= new HashMap<>();
       map.clear();
        
        try{
   
        Scanner sc=  new Scanner(new FileInputStream(filePath));
        Pattern pattern = Pattern.compile(regex);
        
        
            //kira job
           System.out.printf("%-23s %s\n", "Timestamp",   "User");
           
        while (sc.hasNextLine()){
            
            String line= sc.nextLine();
        Matcher matcher = pattern.matcher(line);
        
        if (matcher.find()) {
            
            String timestamp= matcher.group(1);
            user= matcher.group(4);
            
            //add into hashmap
            map.put(timestamp, user);
            
            //add ke dlm list
            jobErrorTime.add(timestamp);
            
        } //if find
    
        } //while
        
        //processing time
         DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");
         
         //startTime
        LocalDateTime startDateTime = LocalDateTime.parse(startTime, formatter);
        
        //endtime
        LocalDateTime endDateTime = LocalDateTime.parse(endTime, formatter);

     
        
        // jobCreatedTime is ArrayList. traverse the list
        for (String jobErrorTimestamp : jobErrorTime) {
            
            LocalDateTime jobDateTime = LocalDateTime.parse(jobErrorTimestamp, formatter);
            
            //between timestamp. startTime<= x <= endTime
            
            if (  (jobDateTime.isAfter(startDateTime) || (jobDateTime.isEqual(startDateTime)) ) 
                    && 
                (    jobDateTime.isBefore(endDateTime) || jobDateTime.isEqual(endDateTime) )  ) {
                jobErrorCount++;
               
                //display the timestamp with user in terminal
                 System.out.println(jobErrorTimestamp+ " "+map.get(jobErrorTimestamp));
            }
            
        }
            System.out.println();
        System.out.println("Number of jobs causing error between " + startTime +" and "+ endTime+ ": " + jobErrorCount);
            System.out.println();
        
        }
        
        
        catch (FileNotFoundException e){
            System.out.println("File not found. Please run again");
        }
        return "Number of jobs causing error: " + jobErrorCount;
        } //job error 
    
    
    //job causing error. question c. just to calculate the total of jobs causing error
    public String jobCausingError(){
        
        //clear first to avoid duplicate
        jobError.clear();
        
        try{
            //read the file
        Scanner sc= new Scanner(new FileInputStream(filePath));
        
        //pattern
    String regex = "\\[(\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}.\\d{3})\\] error: This association (\\d+)\\(account='(\\S+)', user='(\\S+)', partition='(\\S+)'\\) does not have access to qos (\\S+)";
       
    while (sc.hasNextLine()){
    
        String line= sc.nextLine();
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(line);

        if (matcher.find()) {
            System.out.println("Timestamp: " + matcher.group(1));
           
            System.out.println("User: " + matcher.group(4));
  
            String userCauseError= matcher.group(4);
            
            jobError.add(userCauseError); //store to the arrayList
            
        } //if
        }//while
    
    sc.close();
        }
        
        catch (FileNotFoundException fe){
            System.out.println("File not found. Please run again");
        }
       
        return "Total jobs causing error= " + jobError;
} //job error
    
     //job done
    public String jobDone(){
    
        //clear to avoid duplicate
        jobDone.clear();
        
        try{
            //read the file
        Scanner sc= new Scanner(new FileInputStream(filePath));
        
        //regex to extract the info
            String regex = "\\[(\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}.\\d{3})\\] _job_complete: JobId=(\\d+) done";
       
    while (sc.hasNextLine()){
    
        String line= sc.nextLine();
        
    Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(line);

               if (matcher.find()) {
            
            String jobIdDone= matcher.group(2);
            jobDone.add(jobIdDone); //add to the arrayList
            
             
        } //if
        }//while
   
    sc.close();
        } //try
        
        catch (FileNotFoundException fe){
            System.out.println("File not found. Please run again");
        } //catch
        return "Total jobs done: "+jobDone.size();
}//job done
    
    //question d. average execution time
    public String aveProcessingTime(){
       
        //clear to 
        usec.clear();
        
       float average=0;
        try{
            
            int totalProcessingTime=0; //in order to calc the sum
            
            //read the file
        Scanner sc= new Scanner(new FileInputStream(filePath));
        
        //pattern to match the line
    String regex = "\\[(\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}\\.\\d{3})\\]\\s(_slurm_rpc_submit_batch_job:)\\sJobId=(\\d+)\\sInitPrio=(\\d+)\\susec=(\\d+)";
       
    while (sc.hasNextLine()){
    
        String line= sc.nextLine();
        
    Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(line);

       if (matcher.find()) {
            
            String processingTimeStr= matcher.group(5); //still a String
            
            //convert processing time to int
            int processingTime= Integer.parseInt(processingTimeStr); 
            
            usec.add(processingTime); //add to the list
            
            totalProcessingTime+= processingTime; //calc the sum of processing time
            
        } //if find


        }//while
    
    //calc average
     average= totalProcessingTime/ (float) usec.size();
    
    sc.close();
        } //try
        
        catch (FileNotFoundException fe){
            System.out.println("File not found. Please run again");
        } //catch
        
        return "Average processing time= " +average+" microseconds";
   }//ave Processing time
    
    
    //question e- job killed
    public String jobKilled(){
        
        jobKilledList.clear();
        
       try{
   
            //read the file
        Scanner sc= new Scanner(new FileInputStream(filePath));
        
        //pattern to match the line
    String regex = "^\\[(\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}\\.\\d{3})\\] _slurm_rpc_kill_job: REQUEST_KILL_JOB JobId=(\\d+) uid (\\d+)$";
    while (sc.hasNextLine()){
    
        String line= sc.nextLine();
        
    Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(line);

       if (matcher.find()) {
            
           String jobKilledId= matcher.group(2);
           jobKilledList.add(jobKilledId);
        } //if find


        }//while
   }
       catch (FileNotFoundException fe){
            System.out.println("File not found. Please run again");
        } //catch
       
       return "Total job killed: "+ jobKilledList.size(); 
   } //job killed
    
     //store job created id
      public void jobCreated(){
        
        jobID_Created.clear();
     
        try{
            //read the file
        Scanner sc= new Scanner(new FileInputStream(filePath));
        
        //regex to match the line
        //this line indicates the job is created
        String regex = "\\[(\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}.\\d{3})\\] sched: Allocate JobId=(\\d+) NodeList=(\\w+) #CPUs=(\\d+) Partition=((\\w+)-\\w+)";
       
 
        while (sc.hasNextLine()){
    
        String line= sc.nextLine();
        
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(line);

        if (matcher.find()) {
            
            String jobId= matcher.group(2);
            //System.out.println(jobId);
            //add job id created into arrayList
            jobID_Created.add(jobId);
        }
        
        }//while
        
        }//try
        
        catch (IOException e){
            e.printStackTrace();
        }
    }//job created
      
    //calc execution time for each jobId
     public long exeTime(int jobId){
        
         long duration=0;
         
        try{
            
            //read the file
        Scanner sc= new Scanner(new FileInputStream(filePath));
        
       // int jobId = 42804;
       // String jobId= "42802"; //problem
        
        LocalDateTime startTime = null;
    LocalDateTime endTime = null;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");
    
        while (sc.hasNextLine()){
            String line= sc.nextLine();
            
//          startTime = null;
//  endTime = null;
    
             if (line.contains("JobId=" + jobId)) {
        String timestampString = line.substring(1, line.indexOf("]"));
        LocalDateTime timestamp = LocalDateTime.parse(timestampString, formatter);

        if (line.contains("sched: Allocate")) {
          startTime = timestamp;
        }
        else if (line.contains("_job_complete:")) {
          endTime = timestamp;
        }
      } //if line ada jobID
                
        
        }//while
        
        if (startTime != null && endTime != null) {
            
       duration = Duration.between(startTime, endTime).getSeconds();
      System.out.println("Execution time for JobId " + jobId + ": " + duration + " seconds");
  
    }
 
        }//try
        
        catch (FileNotFoundException fe){
            fe.printStackTrace();
        }
        
        return duration;
    } //exeTime
      
     //display execution time
    public String displayExeTime(){
        
        //for calc average
            long totalTime=0;
            double averageExeTime=0;
            
        //traverse the array list
        for (int i=0; i< jobID_Created.size();i++){
            
            //job in string
            String jobStr= jobID_Created.get(i);
            
            //convert to int
            int jobId= Integer.parseInt(jobStr);
            
            //calc exeTime
            exeTime(jobId);
            
            //calc total execution time
      totalTime+= exeTime(jobId); //in seconds
        }
        
        averageExeTime= totalTime/ 8479; //8479= job complete
        
        //comvert to hours
        averageExeTime /= 3600;
        
              System.out.println(totalTime);
       
              return "Average execution time= "+averageExeTime+" hours";
    } //ave execution time
} //class
