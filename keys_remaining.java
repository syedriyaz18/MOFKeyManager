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

class Users2{
    
    private int key_Id;
    private String building_No;
    private String key_Name;
    private String building_Name;
    private int keys_Issueable;
    private int keys_Issued;
    private int keys_Returned;
    private int keys_Remaining;
    
    public Users2(int key_id, String building_no, String building_name, String key_name, int keys_issueable, int keys_issued, int keys_returned, int keys_remaining){
        //this.issue_Id = issue_id;
        this.key_Name = key_name;
        this.building_Name = building_name;
        this.key_Id = key_id;
        this.building_No = building_no;
        this.keys_Issueable = keys_issueable;
        this.keys_Issued = keys_issued;
        this.keys_Returned = keys_returned;
        this.keys_Remaining = keys_remaining;
         
         
    }
    
    public int getkey_Id(){
        return this.key_Id;
   }
    
    public String getkey_Name(){
        return this.key_Name;
    }
    
    public String getbuilding_No(){
        return this.building_No;
    }
    
    public String getbuilding_Name(){
        return this.building_Name;
    } 
    public int getkeys_Issueable(){
        return this.keys_Issueable;
    } 
    public int getkeys_Issued(){
        return this.keys_Issued;
    } 
    public int getkeys_Returned(){
        return this.keys_Returned;
    } 
    public int getkeys_Remaining(){
        return this.keys_Remaining;
    } 
}


public class keys_remaining extends javax.swing.JFrame {

    /**
     * Creates new form keys_remaining
     */
    
    
    public keys_remaining() {
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
    
    static ArrayList<Users2> getUsers2(){
        
        ArrayList<Users2> users2 = new ArrayList<Users2>();
        
        Connection con = getConnection();
        
        Statement st;
        
        ResultSet rs;
        
        Users2 u;
        
        try {
            
            st = con.createStatement();
            rs = st.executeQuery("Select key_id, building_no,building_name,key_name, keys_issueable, keys_issued, keys_returned, keys_remaining from tbl_keys" );
            
            while(rs.next()){
                
                u = new Users2(
                       
                        rs.getInt("key_id"),
                        rs.getString("building_no"),
                        rs.getString("building_name"),
                        rs.getString("key_name"),
                        rs.getInt("keys_issueable"),
                        rs.getInt("keys_issued"),
                        rs.getInt("keys_returned"),
                         rs.getInt("keys_remaining")
                );
                
                users2.add(u);
            }
             
        } catch (SQLException ex) {
            Logger.getLogger(issued_report.class.getName()).log(Level.SEVERE, null, ex);
        }

        return users2;
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
        jtbl_keys_remaining = new javax.swing.JTable();
        btn_export2excel = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("مفاتيح التقارير المتبقية");

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

        jtbl_keys_remaining.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "الرقم التسلسلي", "رقم المبنى", "اسم المبنى", "اسم المفتاح", "مجموع مفاتيح", "مفاتيح صدر", "مفاتيح العودة", "مفاتيح المتبقية"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(jtbl_keys_remaining);

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
                .addContainerGap(69, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 850, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(32, 32, 32))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(btn_export2excel, javax.swing.GroupLayout.DEFAULT_SIZE, 820, Short.MAX_VALUE)
                            .addComponent(jScrollPane2))
                        .addGap(47, 47, 47))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 700, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btn_export2excel, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 836, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cmd_printActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmd_printActionPerformed
        // TODO add your handling code here:
        try
        {
            MessageFormat headerFormat = new MessageFormat("Page {0}");
          MessageFormat footerFormat = new MessageFormat("- {0} -");
         // jtbl_keys_remaining.setFont(new Font("Courier", Font.BOLD, 12));
        
          jtbl_keys_remaining.print(JTable.PrintMode.FIT_WIDTH, headerFormat, footerFormat);
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
        exportTable(jtbl_keys_remaining, new File("C:\\Users\\"+ username +"\\Desktop\\keysremaining.xls"));
        } catch (IOException ex) {
            Logger.getLogger(keys_remaining.class.getName()).log(Level.SEVERE, null, ex);
        }
        JOptionPane.showMessageDialog(this, "البيانات المصدرة إلى سطح المكتب في ملف إكسيل"
            ,"اكتمل التصدير", JOptionPane.INFORMATION_MESSAGE, tick);
    }//GEN-LAST:event_btn_export2excelActionPerformed
    
    public void exportTable(JTable table, File file) throws IOException {
            TableModel model = table.getModel();
            FileWriter out = new FileWriter(file);
            for(int i=0; i < model.getColumnCount(); i++) {
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
         DefaultTableModel model = (DefaultTableModel)jtbl_keys_remaining.getModel();
        
    // AbstractTableModel model = (AbstractTableModel)jtbl_keys_remaining.getModel();
        
        Object[] rowData = new Object[8];
        
        for(int i = 0; i < getUsers2().size(); i++){
            
            rowData[0]  =   i+1;
             rowData[1] = getUsers2().get(i).getbuilding_No();
              rowData[2] = getUsers2().get(i).getbuilding_Name();
               rowData[3] = getUsers2().get(i).getkey_Name();
               rowData[4] = getUsers2().get(i).getkeys_Issueable();
                       rowData[5] = getUsers2().get(i).getkeys_Issued();
                               rowData[6] = getUsers2().get(i).getkeys_Returned();
                                       rowData[7] = getUsers2().get(i).getkeys_Remaining();
               
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
            java.util.logging.Logger.getLogger(keys_remaining.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(keys_remaining.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(keys_remaining.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(keys_remaining.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new keys_remaining().setVisible(true);
            }
        });
        
        
       
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_export2excel;
    private javax.swing.JButton cmd_print;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jtbl_keys_remaining;
    // End of variables declaration//GEN-END:variables
     ImageIcon tick = new ImageIcon("tick3.png");

}
