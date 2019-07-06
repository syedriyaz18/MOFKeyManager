/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mofkeymanager;

import java.awt.Font;
import java.awt.print.PrinterException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import org.h2.tools.Server;


/**
 *
 * @author Syed Mazloom Shah
 */

class Users5{
    
    private String building_Name;
   private String key_Name;
    private String emp_Name;
    private int Issued;
    private int Returned;
    private int Remaining;
    public Users5(String building_name, String key_name,String emp_name, int issued, int returned, int remaining){
        //this.issue_Id = issue_id;
        this.building_Name = building_name;
        this.key_Name = key_name;
        this.emp_Name = emp_name;
        this.Issued = issued;
        this.Returned= returned;
        this.Remaining= remaining;
    }
    
    public String getbuilding_Name(){
        return this.building_Name;
    }
  public String getkey_Name(){
        return this.key_Name;
    }
    
    public String getemp_Name(){
        return this.emp_Name;
    }
    
    public int getIssued(){
        return this.Issued;
    } 
    public int getReturned(){
        return this.Returned;
    } 
    public int getRemaining(){
        return this.Remaining;
    } 
}


public class Keys_withEmp extends javax.swing.JFrame {

    /**
     * Creates new form keys_remaining
     */
    
    
    public Keys_withEmp() {
        initComponents();
        show_Table();
        cmd_print.setVisible(false);
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
    
    static ArrayList<Users5> getUsers5(){
        
        ArrayList<Users5> users5 = new ArrayList<Users5>();
        
        Connection con = getConnection();
        
        Statement st;
        
        ResultSet rs;
        
        Users5 u;
        
        try {
            
            st = con.createStatement();
            rs = st.executeQuery("SELECT sum(tbl_keydetails.keys_issued) as issued,sum(tbl_keydetails.keys_returned) as returned, tbl_keys.building_name, tbl_keys.key_name, tbl_employee.emp_name, (SUM(tbl_keydetails.keys_issued) - SUM(tbl_keydetails.keys_returned)) as remaining \n" +
"FROM tbl_keydetails \n" +
"INNER JOIN tbl_keys ON tbl_keydetails.key_Details=tbl_keys.key_id INNER JOIN tbl_employee ON tbl_keydetails.emp_details=tbl_employee.e_id   group by tbl_keydetails.key_details, tbl_employee.emp_name, tbl_keys.key_name;");
            
            while(rs.next()){
                
                u = new Users5(
                       
                         rs.getString("building_name"),
                        rs.getString("key_name"),
                        rs.getString("emp_name"),
                         rs.getInt("issued"),
                        rs.getInt("returned"),
                        rs.getInt("remaining") 
                );
                
                users5.add(u);
            }
             
        } catch (SQLException ex) {
            Logger.getLogger(issued_report.class.getName()).log(Level.SEVERE, null, ex);
        }

        return users5;
    }
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        cmd_print = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        jtbl_keys_issued = new javax.swing.JTable();
        btn_export2excel = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("مفاتيح صدر التقرير");

        jPanel1.setMaximumSize(new java.awt.Dimension(1000, 1000));
        jPanel1.setPreferredSize(new java.awt.Dimension(900, 900));

        cmd_print.setText("Print");
        cmd_print.setEnabled(false);
        cmd_print.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmd_printActionPerformed(evt);
            }
        });
        jScrollPane2.setViewportView(cmd_print);

        jtbl_keys_issued.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "الرقم التسلسلي", "اسم المبنى", "اسم المفتاح", "اسم الموظف", "مفاتيح صدر", "مفاتيح العودة", "مفاتيح المتبقية"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(jtbl_keys_issued);

        btn_export2excel.setText("Export To Excel");
        btn_export2excel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_export2excelActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(56, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 850, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(45, 45, 45))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 822, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_export2excel, javax.swing.GroupLayout.PREFERRED_SIZE, 820, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(56, 56, 56))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 700, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btn_export2excel, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(42, 42, 42))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 951, Short.MAX_VALUE)
                .addGap(0, 35, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 6, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 797, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cmd_printActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmd_printActionPerformed
        // TODO add your handling code here:
        try
        {
            MessageFormat headerFormat = new MessageFormat("Keys Issued Page {0}");
          MessageFormat footerFormat = new MessageFormat("- {0} -");
         // jtbl_keys_remaining.setFont(new Font("Courier", Font.BOLD, 12));
        
          jtbl_keys_issued.print(JTable.PrintMode.FIT_WIDTH, headerFormat, footerFormat);
        }
        catch (PrinterException pe) {
          System.err.println("Error printing: " + pe.getMessage());
        }
        
        
    }//GEN-LAST:event_cmd_printActionPerformed

    private void btn_export2excelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_export2excelActionPerformed
        // TODO add your handling code here:
        try
        {
            String username = System.getProperty("user.name");
        exportTable(jtbl_keys_issued, new File("C:\\Users\\"+ username +"\\Desktop\\keyswithEmployees.xls"));
        } catch (IOException ex) {
            Logger.getLogger(Keys_withEmp.class.getName()).log(Level.SEVERE, null, ex);
        }
        JOptionPane.showMessageDialog(this, "البيانات المصدرة إلى سطح المكتب في ملف إكسيل"
            ,"اكتمل التصدير", JOptionPane.INFORMATION_MESSAGE, tick);
    }//GEN-LAST:event_btn_export2excelActionPerformed
    
    public void exportTable(JTable table, File file) throws IOException {
            TableModel model = table.getModel();
            FileWriter out = new FileWriter(file);
            for(int i=0; i <= model.getColumnCount(); i++) {
        out.write(model.getColumnName(i) + "\t");
            }
            out.write("\n");

            for(int i=0; i< model.getRowCount(); i++) {
        for(int j=0; j < model.getColumnCount(); j++) {
            out.write(model.getValueAt(i,j).toString()+"\t");
            }
            out.write("\n");
        }

        out.close();
        System.out.println("write out to: " + file);
}

    
    public void show_Table()
    {
         DefaultTableModel model = (DefaultTableModel)jtbl_keys_issued.getModel();
        
    // AbstractTableModel model = (AbstractTableModel)jtbl_keys_remaining.getModel();
        
        Object[] rowData = new Object[8];
        
        for(int i = 0; i < getUsers5().size(); i++){
            
            rowData[0]  =   i+1;
            rowData[1] = getUsers5().get(i).getbuilding_Name();
             rowData[2] = getUsers5().get(i).getkey_Name();
              rowData[3] = getUsers5().get(i).getemp_Name();
               rowData[4] = getUsers5().get(i).getIssued();
               rowData[5] = getUsers5().get(i).getReturned();
               rowData[6] = getUsers5().get(i).getRemaining();
               //model.addTableModelListener(jtbl_keys_remaining);
               model.addRow(rowData);
        }
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Keys_withEmp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Keys_withEmp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Keys_withEmp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Keys_withEmp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
               
                new Keys_withEmp().setVisible(true);
                
            }
        });
        
        
       
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_export2excel;
    private javax.swing.JButton cmd_print;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jtbl_keys_issued;
    // End of variables declaration//GEN-END:variables
     ImageIcon tick = new ImageIcon("tick3.png");

}

