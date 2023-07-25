/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Assignment;

/**
 *
 * @author Khairul<Khairul Amirin UM>
 */
//import LearnJava.*;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

public class Pie extends ApplicationFrame {

   public Pie( String title ) {
      super( title ); 
      setContentPane(createDemoPanel( ));
   }
   
   private static PieDataset createDataset( ) {
      
      DefaultPieDataset dataset = new DefaultPieDataset( );
      
      dataset.setValue( "Epyc(%)" ,28.02);  
      dataset.setValue( "OPTERON(%)" ,49.90);   
      dataset.setValue( "GPU Titan(%)" ,7.24);    
      dataset.setValue( "NGPU v100s(%)" ,6.65);
      dataset.setValue( "NGPU k40c(%)" ,3.33);
      dataset.setValue( "NGPU k10(%)" ,4.86);
      return dataset;         
   }
   
   private static JFreeChart createChart( PieDataset dataset ) {
      JFreeChart chart = ChartFactory.createPieChart(      
         "Job by Partition",   // chart title 
         dataset,          // data    
         true,             // include legend   
         true, 
         false);

      return chart;
   }
   
   public static JPanel createDemoPanel( ) {
      JFreeChart chart = createChart(createDataset( ) );  
      return new ChartPanel( chart ); 
   }
}
class Pie2 extends ApplicationFrame{
    public Pie2 (String title ) {
      super( title ); 
      setContentPane(createDemoPanel( ));
   }
   
   private static PieDataset createDataset( ) {
      
      DefaultPieDataset dataset = new DefaultPieDataset( );
      
      dataset.setValue( "June" ,2423);
      dataset.setValue( "July" ,1448);
      dataset.setValue( "August" ,1520);
      dataset.setValue( "September" ,1377);
      dataset.setValue( "October" ,1866);
      dataset.setValue( "November" ,1212);
      dataset.setValue( "December" ,706);
      
      
      return dataset;         
   }
   
   private static JFreeChart createChart( PieDataset dataset ) {
      JFreeChart chart = ChartFactory.createPieChart(      
         "Job Created",   // chart title 
         dataset,          // data    
         true,             // include legend   
         true, 
         false);

      return chart;
   }
   
   public static JPanel createDemoPanel( ) {
      JFreeChart chart = createChart(createDataset( ) );  
      return new ChartPanel( chart ); 
   }
}