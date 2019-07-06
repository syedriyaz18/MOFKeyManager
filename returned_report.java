/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mofkeymanager;

/**
 *
 * @author Syed Mazloom Shah
 */
 import java.awt.BorderLayout;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import org.h2.tools.Server;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;
import java.text.MessageFormat;
import javax.swing.JButton;

// create a users class
class Users1{
    
    //private int issue_Id;
    private String key_Name;
    private String emp_Name;
    private int Count;
    
    public Users1(String key_name,String emp_name, int count){
        //this.issue_Id = issue_id;
        this.key_Name = key_name;
        this.emp_Name = emp_name;
        this.Count = count;
    }
    
   // public int getissue_Id(){
        //return this.issue_Id;
   // }
    
    public String getkey_Name(){
        return this.key_Name;
    }
    
    public String getemp_Name(){
        return this.emp_Name;
    }
    
    public int getCount(){
        return this.Count;
    } 
}

public class returned_report extends JFrame {
    
    public returned_report(){
        
        super("Total Keys Returned from Employees");
        
        setLocationRelativeTo(null);
        
        setSize(600,400);
        
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        setVisible(true);
    }

 // create a Function to get the connection
    static Connection getConnection(){
        Connection con = null;
        
        try {
             Server server = null;
            // JDBC driver name and database URL 
         String JDBC_DRIVER = "org.h2.Driver";   
         //String DB_URL = "jdbc:h2:~/test";
         String DB_URL = "jdbc:h2:tcp://localhost/~/test";
         
       //  Database credentials 
         String USER = "sa"; 
         String PASS = ""; 
        
          
          Statement stmt = null; 
           server = Server.createTcpServer("-tcpAllowOthers").start();
            // STEP 1: Register JDBC driver
            Class.forName(JDBC_DRIVER);
            
            // STEP 2: Open a connection 
            System.out.println("Connecting to database..."); 
            con = DriverManager.getConnection(DB_URL,USER,PASS);  
          
            // STEP 3: Execute a query 
            System.out.println("Connected database successfully..."); 
            
        } catch (SQLException ex) {
            Logger.getLogger(issued_report.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(issued_report.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return con;
    }
    
 // create a function to fill the an arraylist from database
    static ArrayList<Users1> getUsers(){
        
        ArrayList<Users1> users1 = new ArrayList<Users1>();
        
        Connection con = getConnection();
        
        Statement st;
        
        ResultSet rs;
        
        Users1 u;
        
        try {
            
            st = con.createStatement();
            rs = st.executeQuery("SELECT count(tbl_keysreturned.key_details) as counted, tbl_keys.key_name, tbl_employee.emp_name\n" +
"FROM tbl_keysreturned \n" +
"INNER JOIN tbl_keys ON tbl_keysreturned.key_Details=tbl_keys.key_id INNER JOIN tbl_employee ON tbl_keysreturned.emp_details=tbl_employee.e_id group by tbl_keysreturned.key_details, tbl_employee.emp_name, tbl_keys.key_name;");
            
            while(rs.next()){
                
                u = new Users1(
                       
                        rs.getString("key_name"),
                        rs.getString("emp_name"),
                         rs.getInt("counted")
                );
                
                users1.add(u);
            }
             
        } catch (SQLException ex) {
            Logger.getLogger(issued_report.class.getName()).log(Level.SEVERE, null, ex);
        }

        return users1;
    }
    
    
    public static void main(String[] args){

 /*
   now we are gonna create and populate a jtable from the arraylist who is populated from mysql database
*/
    
        JTable table = new JTable();
        //table.setFillsViewportHeight(true);
        table.setGridColor(Color.LIGHT_GRAY);
         JButton button = new JButton("Print");
        DefaultTableModel model = new DefaultTableModel();
        
        Object[] columnsName = new Object[4];
        
        columnsName[0] = "S.No";
        columnsName[1] = "Key Details";
        columnsName[2] = "Emp Details";
        columnsName[3] = "Total Keys Returned";
        
        model.setColumnIdentifiers(columnsName);
        
        Object[] rowData = new Object[4];
        
        for(int i = 0; i < getUsers().size(); i++){
            
            rowData[0]  =   i;
             rowData[1] = getUsers().get(i).getkey_Name();
              rowData[2] = getUsers().get(i).getemp_Name();
               rowData[3] = getUsers().get(i).getCount();
               
               model.addRow(rowData);
        }
        
        table.setModel(model);
        
//        System.out.println(getUsers().size());
        
        returned_report window = new returned_report();
        
        JPanel panel = new JPanel(new GridLayout(2,1,10,10));
        
        //panel.setLayout(new BorderLayout());
        
        JScrollPane pane = new JScrollPane(table);
        
       panel.add(pane);
        ActionListener printAction = new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        try {
          MessageFormat headerFormat = new MessageFormat("Page {0}");
          MessageFormat footerFormat = new MessageFormat("- {0} -");
          table.print(JTable.PrintMode.FIT_WIDTH, headerFormat, footerFormat);
        } catch (PrinterException pe) {
          System.err.println("Error printing: " + pe.getMessage());
        }
      }
    };
    button.addActionListener(printAction);
        panel.add(button);
        window.setContentPane(panel);
    }
}