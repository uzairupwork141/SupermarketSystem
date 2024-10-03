/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package supermarketsystem;

import codeFiles.ConnectDB;
import codeFiles.FieldSetting;
import java.awt.Color;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Gouhar Ali
 */
public class AddSeller extends javax.swing.JFrame {

    Connection con;
    PreparedStatement str;
    ResultSet rs;
    public AddSeller() {
        initComponents();
        setBackground(new Color(0,0,0,0));
        con=new ConnectDB().AccessDBConnection();
        Get_Set_Id();
        getALLDATA();
    }
    
    
    public void Get_Set_Id(){
//        
         try{   
                    
                    str=con.prepareStatement("SELECT AUTO_INCREMENT FROM information_schema.TABLES WHERE TABLE_NAME = 'logs' ");
                    rs=str.executeQuery();
                    if (rs.next()){
                        int id=rs.getInt("AUTO_INCREMENT");
                        IDtxt.setText(""+id);
                    }
                    
                    
        }catch(Exception ex)
        {
            JOptionPane.showMessageDialog(this,ex,"error",2);
        }
        
    }
    
    
    
    public void clear1()
    {
        Get_Set_Id();
        NAMEtxt.setText("");
        PHONEtxt.setText("");
        CNICtxt.setText("");
        EMAILtxt.setText("");
        ADDRESStxt.setText("");
        PASSWORDtxt.setText("");
        CODEtxt.setText("");
        USERNAMEtxt.setText("");
        
        saveBtn.setText("SAVE");
        getALLDATA();
    }
    
     
     
     public boolean checkFields(){
        if(IDtxt.getText().equals("") || NAMEtxt.getText().equals("") || PHONEtxt.getText().equals("")|| CNICtxt.getText().equals("")|| ADDRESStxt.getText().equals("")|| PASSWORDtxt.getText().equals("")  ){
            return false;
        }
        return true;
    }
    
    
    
    public boolean submit(){
        String id=IDtxt.getText();
        String name=NAMEtxt.getText();
        String uname=NAMEtxt.getText();
        String phone=PHONEtxt.getText();
        String cnic=CNICtxt.getText();
        String email=EMAILtxt.getText();
        String address=ADDRESStxt.getText();
        String password=PASSWORDtxt.getText();     
        String code=CODEtxt.getText();
        
        try{
                    
                    str=con.prepareStatement("INSERT INTO `logs`(`UNAME`, `USERNAME`, `PHONE`, `CNIC`, `EMAIL`, `ADDRESS`, `PASSWORD`, `TYPE`,ID, `CODE`) VALUES (?,?,?,?,?,?,?,?,?,?)");
                    str.setString(9, id);
                    str.setString(1, name);
                    str.setString(2, uname);
                    str.setString(3, phone);
                    str.setString(4, cnic);
                    str.setString(5, email);
                    str.setString(6, address);
                    str.setString(7, password);
                    str.setString(8, "SALESMAN");
                    str.setString(10, code);
                    str.executeUpdate();
                    JOptionPane.showMessageDialog(this,"DATA SAVED","SUCCESS",1);
                    getALLDATA();
                    
                    return true;
        }catch(Exception ex){
            JOptionPane.showMessageDialog(this,"ERROR :- "+ex.getMessage(),"error",2);
        }
        return false;
    }
    
    
    
     public void getALLDATA()
    {
        int c;
        try {
           
           str=con.prepareStatement ("SELECT* from logs where TYPE='SALESMAN'");
           rs=str.executeQuery();
           DefaultTableModel df=(DefaultTableModel)tableDark1.getModel();
           df.setRowCount(0);
                
            while (rs.next()){
               String id =rs.getString("ID");
               String name =rs.getString("uname");
               String uname =rs.getString("username");
               String phone =rs.getString("phone");
               String cnic =rs.getString("cnic");
               String email =rs.getString("email");
               String address =rs.getString("address");
               String password =rs.getString("password");
               String code =rs.getString("code");
               String []row={id,name,uname,phone,cnic,email,address,password,code};
               df.addRow(row);
            }
           
        } catch (Exception ex) {
           JOptionPane.showMessageDialog(this,ex,"CONNECTION ",3);
        }
    }
    
     
     
     
     
     
     
     
     
     public void dataToFields(String id,String type){
       
        try {
            
            str=con.prepareStatement ("SELECT * FROM `logs` WHERE ID='"+id+"' AND TYPE='"+type+"'");
            rs=str.executeQuery();
                    if (rs.next()){
                        IDtxt.setText(rs.getString("ID"));
                        NAMEtxt.setText(rs.getString("uname"));
                        PHONEtxt.setText(rs.getString("phone"));
                        CNICtxt.setText(rs.getString("cnic"));
                        USERNAMEtxt.setText(rs.getString("USERNAME"));
                        EMAILtxt.setText(rs.getString("email"));
                        ADDRESStxt.setText(rs.getString("address"));
                        PASSWORDtxt.setText(rs.getString("password"));
                        CODEtxt.setText(rs.getString("code"));
                      
                        saveBtn.setText("UPDATE");
                    }else 
                    {
                       JOptionPane.showMessageDialog(this,"NO DATA AVALIBLE","NO DATA",2);
                    }
                    
        } catch (SQLException ex) {
           //JOptionPane.showMessageDialog(this,ex,"CONNECTION ",3);
        }
        ///
    }
     
     
     
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bgpanal1 = new panal.bgpanal();
        button3 = new ALL_UI_1.Button();
        pane21 = new panal.pane2();
        button1 = new ALL_UI_1.Button();
        pane31 = new panal.pane3();
        IDtxt = new ALL_UI_1.TextField();
        jLabel1 = new javax.swing.JLabel();
        NAMEtxt = new ALL_UI_1.TextField();
        jLabel2 = new javax.swing.JLabel();
        PHONEtxt = new ALL_UI_1.TextField();
        jLabel3 = new javax.swing.JLabel();
        CNICtxt = new ALL_UI_1.TextField();
        jLabel4 = new javax.swing.JLabel();
        EMAILtxt = new ALL_UI_1.TextField();
        jLabel5 = new javax.swing.JLabel();
        ADDRESStxt = new ALL_UI_1.TextField();
        jLabel6 = new javax.swing.JLabel();
        PASSWORDtxt = new ALL_UI_1.TextField();
        button2 = new ALL_UI_1.Button();
        saveBtn = new ALL_UI_1.Button();
        button5 = new ALL_UI_1.Button();
        jLabel8 = new javax.swing.JLabel();
        USERNAMEtxt = new ALL_UI_1.TextField();
        jLabel11 = new javax.swing.JLabel();
        CODEtxt = new ALL_UI_1.TextField();
        jLabel12 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tableDark1 = new tabledark.TableDark();
        jLabel13 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        bgpanal1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        button3.setBackground(new java.awt.Color(204, 255, 255));
        button3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ASSETS_files/icons8-home-40 (1).png"))); // NOI18N
        button3.setText("BACK");
        button3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        button3.setShadowColor(new java.awt.Color(0, 0, 0));
        button3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button3ActionPerformed(evt);
            }
        });
        bgpanal1.add(button3, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 660, 230, 60));

        button1.setText("X");
        button1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        button1.setShadowColor(new java.awt.Color(0, 0, 0));
        button1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button1ActionPerformed(evt);
            }
        });
        pane21.add(button1, new org.netbeans.lib.awtextra.AbsoluteConstraints(1000, 0, 40, 40));

        bgpanal1.add(pane21, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1050, 40));

        IDtxt.setEditable(false);
        IDtxt.setBackground(new java.awt.Color(204, 204, 204));
        IDtxt.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        IDtxt.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        pane31.add(IDtxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 50, 80, -1));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel1.setText("ID");
        pane31.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 50, 40));

        NAMEtxt.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        NAMEtxt.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        NAMEtxt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                NAMEtxtKeyTyped(evt);
            }
        });
        pane31.add(NAMEtxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 50, 170, -1));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setText("NAME");
        pane31.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 50, 50, 40));

        PHONEtxt.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        PHONEtxt.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        PHONEtxt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                PHONEtxtKeyTyped(evt);
            }
        });
        pane31.add(PHONEtxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 50, 160, -1));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel3.setText("PHONE");
        pane31.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 50, -1, 40));

        CNICtxt.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        CNICtxt.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        CNICtxt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                CNICtxtKeyTyped(evt);
            }
        });
        pane31.add(CNICtxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 50, 170, -1));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel4.setText("CODE");
        pane31.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 100, 40, 40));

        EMAILtxt.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        EMAILtxt.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        EMAILtxt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                EMAILtxtKeyTyped(evt);
            }
        });
        pane31.add(EMAILtxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 100, 180, -1));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel5.setText("EMAIL");
        pane31.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, 50, 40));

        ADDRESStxt.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        ADDRESStxt.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        ADDRESStxt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                ADDRESStxtKeyTyped(evt);
            }
        });
        pane31.add(ADDRESStxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 100, 250, -1));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel6.setText("ADDRESS");
        pane31.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 100, 60, 40));

        PASSWORDtxt.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        PASSWORDtxt.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        PASSWORDtxt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                PASSWORDtxtKeyTyped(evt);
            }
        });
        pane31.add(PASSWORDtxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 100, 160, -1));

        button2.setBackground(new java.awt.Color(249, 226, 226));
        button2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ASSETS_files/icons8-refresh-36.png"))); // NOI18N
        button2.setText("NEW");
        button2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        button2.setRippleColor(new java.awt.Color(255, 0, 0));
        button2.setShadowColor(new java.awt.Color(0, 0, 0));
        button2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button2ActionPerformed(evt);
            }
        });
        pane31.add(button2, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 150, 160, 60));

        saveBtn.setBackground(new java.awt.Color(204, 255, 255));
        saveBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ASSETS_files/icons8-submit-48.png"))); // NOI18N
        saveBtn.setText("SAVE");
        saveBtn.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        saveBtn.setShadowColor(new java.awt.Color(0, 0, 0));
        saveBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveBtnActionPerformed(evt);
            }
        });
        pane31.add(saveBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 150, 180, 60));

        button5.setBackground(new java.awt.Color(255, 204, 204));
        button5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ASSETS_files/icons8-delete-36.png"))); // NOI18N
        button5.setText("DELETE");
        button5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        button5.setRippleColor(new java.awt.Color(255, 0, 0));
        button5.setShadowColor(new java.awt.Color(0, 0, 0));
        pane31.add(button5, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 150, 150, 60));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel8.setText("PASSWORD");
        pane31.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 100, 70, 40));

        USERNAMEtxt.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        USERNAMEtxt.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        pane31.add(USERNAMEtxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 50, 170, -1));

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel11.setText("CNIC");
        pane31.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 50, 70, 40));

        CODEtxt.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        CODEtxt.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        pane31.add(CODEtxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 100, 150, -1));

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel12.setText("USERNAME");
        pane31.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 50, 70, 40));

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("SALESMAN LOOKUP");
        pane31.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 230, 340, 50));

        jScrollPane2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jScrollPane2MouseClicked(evt);
            }
        });

        tableDark1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "id", "name", "username", "phone", "cnic", "email", "address", "password", "security code"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableDark1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableDark1MouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tableDark1);

        pane31.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 300, 1000, 270));

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("MANAGE SALESMAN");
        pane31.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 340, 50));

        bgpanal1.add(pane31, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 1020, 590));

        getContentPane().add(bgpanal1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1050, 730));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void button3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button3ActionPerformed
        // TODO add your handling code here:
        dispose();
        new ADMIN_MAIN().setVisible(true);
    }//GEN-LAST:event_button3ActionPerformed

    private void tableDark1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableDark1MouseClicked
        // TODO add your handling code here:
        DefaultTableModel df=(DefaultTableModel)tableDark1.getModel();
        int srow = tableDark1.getSelectedRow();
        String id=df.getValueAt(srow, 0).toString();
        
        dataToFields(id, "SALESMAN");
    }//GEN-LAST:event_tableDark1MouseClicked

    private void saveBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveBtnActionPerformed
        // TODO add your handling code here:

        if(checkFields()==false){
            JOptionPane.showMessageDialog(this,"Empty fields detected\nPlease fill all fields","empty fields",2);
            return;
        }

        if(saveBtn.getText().equals("SAVE")){
            if(submit()){
                clear1();
            }
            return;
        }

    }//GEN-LAST:event_saveBtnActionPerformed

    private void button2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button2ActionPerformed
        // TODO add your handling code here:
        clear1();
    }//GEN-LAST:event_button2ActionPerformed

    private void PASSWORDtxtKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PASSWORDtxtKeyTyped
        // TODO add your handling code here:
        new FieldSetting().limit(evt, PASSWORDtxt.getText().length(), 10);

        new FieldSetting().NoSpace(evt);

    }//GEN-LAST:event_PASSWORDtxtKeyTyped

    private void ADDRESStxtKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ADDRESStxtKeyTyped
        // TODO add your handling code here:
        if(ADDRESStxt.getText().equals("")){
            new FieldSetting().NoSpace(evt);
        }
    }//GEN-LAST:event_ADDRESStxtKeyTyped

    private void EMAILtxtKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_EMAILtxtKeyTyped
        // TODO add your handling code here:
        new FieldSetting().limit(evt, EMAILtxt.getText().length(), 30);
        new FieldSetting().NoSpace(evt);

    }//GEN-LAST:event_EMAILtxtKeyTyped

    private void CNICtxtKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CNICtxtKeyTyped
        // TODO add your handling code here:

        new FieldSetting().limit(evt, CNICtxt.getText().length(), 20);
        new FieldSetting().only_numbers(evt);
    }//GEN-LAST:event_CNICtxtKeyTyped

    private void PHONEtxtKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PHONEtxtKeyTyped
        // TODO add your handling code here:
        new FieldSetting().limit(evt, PHONEtxt.getText().length(), 20);
        new FieldSetting().only_numbers(evt);
    }//GEN-LAST:event_PHONEtxtKeyTyped

    private void NAMEtxtKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NAMEtxtKeyTyped
        // TODO add your handling code here:

        if(NAMEtxt.getText().equals("")){
            new FieldSetting().NoSpace(evt);
        }
    }//GEN-LAST:event_NAMEtxtKeyTyped

    private void button1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button1ActionPerformed
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_button1ActionPerformed

    private void jScrollPane2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jScrollPane2MouseClicked
        // TODO add your handling code here:
        
    }//GEN-LAST:event_jScrollPane2MouseClicked

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
            java.util.logging.Logger.getLogger(AddSeller.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AddSeller.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AddSeller.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AddSeller.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AddSeller().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private ALL_UI_1.TextField ADDRESStxt;
    private ALL_UI_1.TextField CNICtxt;
    private ALL_UI_1.TextField CODEtxt;
    private ALL_UI_1.TextField EMAILtxt;
    private ALL_UI_1.TextField IDtxt;
    private ALL_UI_1.TextField NAMEtxt;
    private ALL_UI_1.TextField PASSWORDtxt;
    private ALL_UI_1.TextField PHONEtxt;
    private ALL_UI_1.TextField USERNAMEtxt;
    private panal.bgpanal bgpanal1;
    private ALL_UI_1.Button button1;
    private ALL_UI_1.Button button2;
    private ALL_UI_1.Button button3;
    private ALL_UI_1.Button button5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane2;
    private panal.pane2 pane21;
    private panal.pane3 pane31;
    private ALL_UI_1.Button saveBtn;
    private tabledark.TableDark tableDark1;
    // End of variables declaration//GEN-END:variables
}
