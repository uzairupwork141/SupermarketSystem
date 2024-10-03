/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package supermarketsystem;

import java.util.*;
import codeFiles.ConnectDB;
import codeFiles.FieldSetting;
import codeFiles.GetStoreInfo;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
/**
 *
 * @author Gouhar Ali
 */
public class AddProduct extends javax.swing.JFrame {

    /**
     * Creates new form AddProduct
     */
    
    Connection con;
    PreparedStatement str;
    ResultSet rs;
    public AddProduct() {
        initComponents();
        setBackground(new Color(0,0,0,0));
        con= new ConnectDB().AccessDBConnection();
        getCategory();
        getALLDATA();
        DATEtxt.setText(currentdate());
    }
    
    
    
    
    public boolean checkProductSecton(){
        return !( PNAMEtxt.getText().equals("") || DEStxt.getText().equals("") || category.getSelectedIndex()==0 ||PRICEtxt.getText().equals("") || STOCKtxt.getText().equals("") ||Double.parseDouble(PRICEtxt.getText())<=0 || Integer.parseInt(STOCKtxt.getText())<=0);
    }
    
    
    
    
    public void getCategory(){
        try{
            str=con.prepareStatement("select* from category");
            rs=str.executeQuery();
            
            while(rs.next()){
                category.addItem(rs.getString("NAME"));
            }
            
            
        }catch(Exception ex ){
            JOptionPane.showMessageDialog(this, ex.getMessage(),"error",2);
        }
    }
    
    
    
    public boolean checkBcode(String bcode ,String id){
        try{
            str=con.prepareStatement("select* from product where Product_barcode='"+bcode+"'");
            rs=str.executeQuery();
            
            if(rs.next()){
                if(rs.getString("ID").equals(id)){
                     return false;
                }
              return true;
            }
            
            
        }catch(Exception ex ){
            JOptionPane.showMessageDialog(this, ex.getMessage(),"error in barcode chacking",2);
        }
        return false;
    }
    
    
    
    
    public boolean delete (String id){
        try{
            int p = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete product data?","Delete",JOptionPane.YES_NO_OPTION);
            if (p==0){
                String Str="DELETE FROM `product` WHERE ID = "+ id ;
                str=con.prepareStatement(Str);
                str.execute();
                JOptionPane.showMessageDialog(this,"Sucessfully Deleted!");
                getALLDATA();
                clear1();
                return true;
            }else{
                JOptionPane.showMessageDialog(this,"Request Cancelled!");
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(this, e);
        }
        return false;
    }
     
    
    
    
    
    
    
    
    
       
    public String currentdate(){
        Calendar cal=new GregorianCalendar();
        int month=cal.get(Calendar.MONTH);
        int year =cal.get(Calendar.YEAR);
        int day=cal.get(Calendar.DAY_OF_MONTH);
        if(month==12){
            month=1;
        }else{
            month++;
        }
        return String.valueOf(day+"/"+month+"/"+year);
    }
    

    
    
    
    public void clear1()
    {
        SEARCHtxt.setText("");
        PNAMEtxt.setText("");
        IDtxt.setText("");
        DEStxt.setText("");
        PRICEtxt.setText("0");
        STOCKtxt.setText("0");
        category.setSelectedIndex(0);
        BARCODEtxt.setText("");
        savebtn.setText("SAVE");
    }
    
    
    
    public boolean submit(){
         
        String name=PNAMEtxt.getText();
        String des=DEStxt.getText();
        String Category=this.category.getSelectedItem().toString();
        String price=PRICEtxt.getText();
        String stock=STOCKtxt.getText();
        String barcode=BARCODEtxt.getText();
        String addDate=DATEtxt.getText();
        String updateDate=DATEtxt.getText();
        
        try{
                    
                    str=con.prepareStatement("INSERT INTO `product`(`product_name`, `description`, `category`, `price`, `stock`, `added_date`, `updated_date`, `product_barcode` ) VALUES (?,?,?,?,?,?,?,?)");
                    str.setString(1, name);
                    str.setString(2, des);
                    str.setString(3, Category);
                    str.setString(4, price);
                    str.setString(5, stock);
                    str.setString(6, addDate);
                    str.setString(7, updateDate);
                    str.setString(8, barcode);
                    str.executeUpdate();
                    JOptionPane.showMessageDialog(this,"DATA SAVED","SUCCESS",1);
                    clear1();
                    getALLDATA();
                    return true;
        }catch(Exception ex){
            JOptionPane.showMessageDialog(this,"ERROR :- "+ex.getMessage(),"error",2);
        }
        return false;
    }
    
    
    public boolean update(String id){
         
         
        String name=PNAMEtxt.getText();
        String des=DEStxt.getText();
        String Category=category.getSelectedItem().toString();
        String price=PRICEtxt.getText();
        String stock=STOCKtxt.getText();
        String barcode=BARCODEtxt.getText();
        String updateDate=DATEtxt.getText();
        
        try{
                    str=con.prepareStatement("update `product` set `product_name`=?, `description`=?, `category`=?, `price`=?, `stock`=?, `updated_date`=?, `product_barcode`=?  where ID ='"+id+"'");
                    str.setString(1, name);
                    str.setString(2, des);
                    str.setString(3, Category);
                    str.setString(4, price);
                    str.setString(5, stock);
                    str.setString(6, updateDate);
                    str.setString(7, barcode);
                    str.executeUpdate();
                    JOptionPane.showMessageDialog(this,"DATA UPDATED","SUCCESS",1);
                    clear1();
                    getALLDATA();
                    return true;
        }catch(SQLException ex)
        {
          
            JOptionPane.showMessageDialog(this,ex.getMessage(),"error",2);
        }
        return false;
    }
     
    
    
    
     public void getDataFromMainTbl(String sid) throws SQLException
    {
        str=con.prepareStatement("select* from product where id="+sid);
        rs=str.executeQuery();
        if(rs.next()){
            IDtxt.setText(rs.getString("ID"));
            PNAMEtxt.setText(rs.getString("product_name"));
            category.setSelectedItem(rs.getString("category"));
            DEStxt.setText(rs.getString("description"));
            PRICEtxt.setText(rs.getString("price"));
            STOCKtxt.setText(rs.getString("stock"));
            BARCODEtxt.setText(rs.getString("Product_barcode"));
            savebtn.setText("UPDATE");
        }else{
            JOptionPane.showMessageDialog(this, "No Data avalible");
        }
    } 
    
    
    
     
    public void getDatabyCode(String code) throws SQLException
    {
        
        str=con.prepareStatement("select* from product where product_barcode='"+code+"'");
        rs=str.executeQuery();
        if(rs.next()){
            IDtxt.setText(rs.getString("ID"));
            PNAMEtxt.setText(rs.getString("product_name"));
            category.setSelectedItem(rs.getString("category"));
            DEStxt.setText(rs.getString("description"));
            PRICEtxt.setText(rs.getString("price"));
            STOCKtxt.setText(rs.getString("stock"));
            BARCODEtxt.setText(rs.getString("Product_barcode"));
            savebtn.setText("UPDATE");
        }else{
            JOptionPane.showMessageDialog(this, "No Data avalible");
        }
        
    } 
     
     
      
      
    public void getALLDATA()
    {
        int c;
        try {
           
          
            str=con.prepareStatement ("SELECT* from product");
            rs=str.executeQuery();
            DefaultTableModel df=(DefaultTableModel)datatable.getModel();
            df.setRowCount(0);
                
            while (rs.next()){
                    
                String id =rs.getString("ID");
                String name =rs.getString("product_name");
                String des =rs.getString("description");
                String cate =rs.getString("category");
                String price =rs.getString("price");
                String stock =rs.getString("stock");
                String bcode =rs.getString("Product_barcode");
                String []row={id,name,des,cate,price,stock,bcode};
                df.addRow(row);
                
            }
            
        } catch (Exception ex) {
           JOptionPane.showMessageDialog(this,ex,"CONNECTION ",3);
        }
    }
    
       
    public void tableclick(){
        DefaultTableModel df=(DefaultTableModel)datatable.getModel();
        int srow = datatable.getSelectedRow();
        String id=df.getValueAt(srow, 0).toString();
        

        try {
            
            str=con.prepareStatement ("SELECT * FROM `product` WHERE ID='"+id+"'");
            rs=str.executeQuery();
                    if (rs.next()){
                        IDtxt.setText(rs.getString("ID"));
                        PNAMEtxt.setText(rs.getString("product_name"));
                        DEStxt.setText(rs.getString("description"));
                        category.setSelectedItem(rs.getString("category"));
                        PRICEtxt.setText(rs.getString("price"));
                        STOCKtxt.setText(rs.getString("stock"));
                        BARCODEtxt.setText(rs.getString("Product_barcode"));
                        savebtn.setText("UPDATE");
                        datatable.clearSelection();
                    }else 
                    {
                        JOptionPane.showMessageDialog(this,"NO DATA AVALIBLE","NO DATA",2);
                    }
        } catch (SQLException ex) {
           JOptionPane.showMessageDialog(this,ex,"CONNECTION ",3);
        }
        
        
        
    }
    

    
    public static String getRandomNumberString() {
        // It will generate 6 digit random Number.
        // from 0 to 999999
        Random rnd = new Random();
        int number = rnd.nextInt(999999);

        // this will convert any number sequence into 6 character.
        return String.format("%06d", number);
    }
    
    
    public String firstTwoChar(String str) {
        return str.length() < 2 ? str : str.substring(0, 2);
    }
    
    public void genBarcode(){
        if(PNAMEtxt.getText().equals("")){
            return;
        }
        
        String N= firstTwoChar(PNAMEtxt.getText());
        String C=getRandomNumberString();
        String code = N+C;
        BARCODEtxt.setText(code);
        System.out.println("done");
    }
    
    
    
    
    
    
    
   
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane3 = new javax.swing.JScrollPane();
        tableDark2 = new tabledark.TableDark();
        bgpanal1 = new panal.bgpanal();
        jLabel1 = new javax.swing.JLabel();
        DATEtxt = new ALL_UI_1.TextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        SEARCHtxt = new ALL_UI_1.TextField();
        button6 = new ALL_UI_1.Button();
        button5 = new ALL_UI_1.Button();
        pane21 = new panal.pane2();
        button3 = new ALL_UI_1.Button();
        pane31 = new panal.pane3();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        IDtxt = new ALL_UI_1.TextField();
        button1 = new ALL_UI_1.Button();
        button2 = new ALL_UI_1.Button();
        button4 = new ALL_UI_1.Button();
        DEStxt = new ALL_UI_1.TextField();
        jLabel5 = new javax.swing.JLabel();
        category = new ALL.Combobox();
        PRICEtxt = new ALL_UI_1.TextField();
        STOCKtxt = new ALL_UI_1.TextField();
        jLabel8 = new javax.swing.JLabel();
        BARCODEtxt = new ALL_UI_1.TextField();
        savebtn = new ALL_UI_1.Button();
        button7 = new ALL_UI_1.Button();
        PNAMEtxt = new ALL_UI_1.TextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        datatable = new tabledark.TableDark();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();

        tableDark2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane3.setViewportView(tableDark2);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        bgpanal1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 48)); // NOI18N
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ASSETS_files/icons8-products-100.png"))); // NOI18N
        jLabel1.setText("PRODUCTS");
        bgpanal1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 50, 370, 110));

        DATEtxt.setBackground(new java.awt.Color(232, 231, 231));
        DATEtxt.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        DATEtxt.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        DATEtxt.setShadowColor(new java.awt.Color(0, 0, 0));
        bgpanal1.add(DATEtxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(940, 90, 160, 50));

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel9.setLabelFor(PNAMEtxt);
        jLabel9.setText("Current date");
        bgpanal1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 100, 90, 30));

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel10.setLabelFor(PNAMEtxt);
        jLabel10.setText("Search by barcode / ID");
        bgpanal1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 100, 170, 30));

        SEARCHtxt.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        SEARCHtxt.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        SEARCHtxt.setShadowColor(new java.awt.Color(0, 0, 0));
        SEARCHtxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SEARCHtxtActionPerformed(evt);
            }
        });
        bgpanal1.add(SEARCHtxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 90, 160, 50));

        button6.setBackground(new java.awt.Color(204, 255, 204));
        button6.setText("GET");
        button6.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        button6.setRippleColor(new java.awt.Color(0, 153, 153));
        button6.setShadowColor(new java.awt.Color(0, 0, 0));
        button6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button6ActionPerformed(evt);
            }
        });
        bgpanal1.add(button6, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 90, 70, 50));

        button5.setBackground(new java.awt.Color(204, 255, 204));
        button5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ASSETS_files/icons8-home-40 (1).png"))); // NOI18N
        button5.setText("BACK");
        button5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        button5.setShadowColor(new java.awt.Color(0, 0, 0));
        button5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button5ActionPerformed(evt);
            }
        });
        bgpanal1.add(button5, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 660, 210, 60));

        button3.setText("X");
        button3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        button3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button3ActionPerformed(evt);
            }
        });
        pane21.add(button3, new org.netbeans.lib.awtextra.AbsoluteConstraints(1080, 0, 40, -1));

        bgpanal1.add(pane21, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1130, 40));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setLabelFor(IDtxt);
        jLabel2.setText("ID");
        pane31.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 10, 110, 30));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setLabelFor(PNAMEtxt);
        jLabel3.setText("NAME");
        pane31.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 10, 110, 30));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setLabelFor(PNAMEtxt);
        jLabel4.setText("DESCRIPTION");
        pane31.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 10, 110, 30));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setLabelFor(PNAMEtxt);
        jLabel6.setText("PRICE");
        pane31.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 10, 110, 30));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setLabelFor(PNAMEtxt);
        jLabel7.setText("STOCK");
        pane31.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 10, 110, 30));

        IDtxt.setEditable(false);
        IDtxt.setBackground(new java.awt.Color(255, 204, 204));
        IDtxt.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        IDtxt.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        IDtxt.setShadowColor(new java.awt.Color(0, 0, 0));
        pane31.add(IDtxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, 190, -1));

        button1.setBackground(new java.awt.Color(255, 102, 102));
        button1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ASSETS_files/icons8-delete-36.png"))); // NOI18N
        button1.setText("DELETE");
        button1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        button1.setRippleColor(new java.awt.Color(0, 153, 153));
        button1.setShadowColor(new java.awt.Color(0, 0, 0));
        button1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button1ActionPerformed(evt);
            }
        });
        pane31.add(button1, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 140, 200, 50));

        button2.setBackground(new java.awt.Color(204, 255, 204));
        button2.setText("AUTO GET CODE");
        button2.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        button2.setRippleColor(new java.awt.Color(0, 153, 153));
        button2.setShadowColor(new java.awt.Color(0, 0, 0));
        button2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button2ActionPerformed(evt);
            }
        });
        pane31.add(button2, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 90, 200, 50));

        button4.setBackground(new java.awt.Color(255, 204, 204));
        button4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ASSETS_files/icons8-refresh-36.png"))); // NOI18N
        button4.setText("NEW");
        button4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        button4.setRippleColor(new java.awt.Color(0, 153, 153));
        button4.setShadowColor(new java.awt.Color(0, 0, 0));
        button4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button4ActionPerformed(evt);
            }
        });
        pane31.add(button4, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 90, 200, 50));

        DEStxt.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        DEStxt.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        DEStxt.setShadowColor(new java.awt.Color(0, 0, 0));
        pane31.add(DEStxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 40, 200, 50));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setLabelFor(PNAMEtxt);
        jLabel5.setText("CATEGORY");
        pane31.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 90, 90, -1));

        category.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "NONE" }));
        category.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        category.setLabeText("");
        pane31.add(category, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 110, 190, 40));

        PRICEtxt.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        PRICEtxt.setText("0");
        PRICEtxt.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        PRICEtxt.setShadowColor(new java.awt.Color(0, 0, 0));
        PRICEtxt.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                PRICEtxtFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                PRICEtxtFocusLost(evt);
            }
        });
        PRICEtxt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                PRICEtxtKeyTyped(evt);
            }
        });
        pane31.add(PRICEtxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 40, 200, 50));

        STOCKtxt.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        STOCKtxt.setText("0");
        STOCKtxt.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        STOCKtxt.setShadowColor(new java.awt.Color(0, 0, 0));
        STOCKtxt.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                STOCKtxtFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                STOCKtxtFocusLost(evt);
            }
        });
        STOCKtxt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                STOCKtxtKeyTyped(evt);
            }
        });
        pane31.add(STOCKtxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 40, 200, 50));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setLabelFor(PNAMEtxt);
        jLabel8.setText("BARCODE");
        pane31.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 90, 110, -1));

        BARCODEtxt.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        BARCODEtxt.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        BARCODEtxt.setShadowColor(new java.awt.Color(0, 0, 0));
        BARCODEtxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BARCODEtxtActionPerformed(evt);
            }
        });
        pane31.add(BARCODEtxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 110, 200, 50));

        savebtn.setBackground(new java.awt.Color(204, 255, 204));
        savebtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ASSETS_files/icons8-submit-48.png"))); // NOI18N
        savebtn.setText("SAVE");
        savebtn.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        savebtn.setRippleColor(new java.awt.Color(0, 153, 153));
        savebtn.setShadowColor(new java.awt.Color(0, 0, 0));
        savebtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                savebtnActionPerformed(evt);
            }
        });
        pane31.add(savebtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 90, 200, 100));

        button7.setText("PRINT CODE");
        button7.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        button7.setShadowColor(new java.awt.Color(0, 0, 0));
        button7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button7ActionPerformed(evt);
            }
        });
        pane31.add(button7, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 140, 200, 50));

        PNAMEtxt.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        PNAMEtxt.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        PNAMEtxt.setShadowColor(new java.awt.Color(0, 0, 0));
        pane31.add(PNAMEtxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 40, 200, 50));

        jScrollPane1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jScrollPane1MouseClicked(evt);
            }
        });
        jScrollPane1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jScrollPane1KeyPressed(evt);
            }
        });

        datatable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "id", "product", "description", "category", "price", "stock", "barcode"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        datatable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                datatableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(datatable);

        pane31.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 210, 1080, 250));

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("the barcode field is empty");
        pane31.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 180, 160, -1));

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("To create your own barcode make sure");
        pane31.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 160, 230, -1));

        bgpanal1.add(pane31, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 160, 1100, 490));

        getContentPane().add(bgpanal1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1130, 730));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void button6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button6ActionPerformed
        // TODO add your handling code here:
        String id = SEARCHtxt.getText();
        try {
            getDataFromMainTbl(id);
        } catch (SQLException ex) {
            Logger.getLogger(AddProduct.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_button6ActionPerformed

    private void SEARCHtxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SEARCHtxtActionPerformed
        try {
            // TODO add your handling code here:
            getDatabyCode(SEARCHtxt.getText());
            SEARCHtxt.setText("");
            SEARCHtxt.requestFocus();
        } catch (SQLException ex) {
            Logger.getLogger(AddProduct.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_SEARCHtxtActionPerformed

    private void datatableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_datatableMouseClicked
        // TODO add your handling code here:
        tableclick();
        
    }//GEN-LAST:event_datatableMouseClicked

    private void button5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button5ActionPerformed
        // TODO add your handling code here:
        
        dispose();
        new ADMIN_MAIN().setVisible(true);
    }//GEN-LAST:event_button5ActionPerformed

    private void button7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button7ActionPerformed
        // TODO add your handling code here:
        if(checkProductSecton()==false){
            JOptionPane.showMessageDialog(this, "incomplete entry");
            return;
        }

        try{

            File currentDir = new File(".");
            String basePath = currentDir.getCanonicalPath();
            // Define file path
            String filePath = basePath + "/src/Reports/Create_barcode.jrxml";
            InputStream in = new FileInputStream(filePath);
            JasperDesign jd = JRXmlLoader.load(in);
            JasperReport jr = JasperCompileManager.compileReport(jd);
            HashMap para = new HashMap();
            
            GetStoreInfo store  =new GetStoreInfo();
            String []store_data =store.getData();
            
            para.put("code", BARCODEtxt.getText());
            para.put("Storename", store_data[1]);
            para.put("Price", PRICEtxt.getText());

            JasperPrint j = JasperFillManager.fillReport(jr, para,con);

            JasperViewer.viewReport(j, false);

        }catch(Exception ex){
            System.out.println(ex);
        }

    }//GEN-LAST:event_button7ActionPerformed

    private void savebtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_savebtnActionPerformed
        // TODO add your handling code here:

        if(checkProductSecton()==false){
            JOptionPane.showMessageDialog(this,"Invalid Entry","error",2);
            return;
        }

        if(savebtn.getText().equals("SAVE")){
            if(checkBcode(BARCODEtxt.getText(),IDtxt.getText())==true){
                JOptionPane.showMessageDialog(this,"Barcode is Already Available for another Product","Duplicate Barcode",2);
                return;
            }
            submit();
            return;
        }

        if(savebtn.getText().equals("UPDATE")){
            String id = IDtxt.getText();
            update(id);
            return;
        }
    }//GEN-LAST:event_savebtnActionPerformed

    private void BARCODEtxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BARCODEtxtActionPerformed
        // TODO add your handling code here:

        if(checkProductSecton()==false){
            JOptionPane.showMessageDialog(this,"Invalid Entry","error",2);
            return;
        }

        if(checkBcode(BARCODEtxt.getText(),IDtxt.getText())){
            JOptionPane.showMessageDialog(this,"Barcode is Already Available for another Product","Duplicate Barcode",2);
            return;
        }
        //
        if(savebtn.getText().equals("SAVE")){
            submit();
            return;
        }

        if(savebtn.getText().equals("UPDATE")){
            String id = IDtxt.getText();
            update(id);
            return;
        }

    }//GEN-LAST:event_BARCODEtxtActionPerformed

    private void STOCKtxtKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_STOCKtxtKeyTyped
        // TODO add your handling code here:
        new FieldSetting().only_numbers(evt);
    }//GEN-LAST:event_STOCKtxtKeyTyped

    private void STOCKtxtFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_STOCKtxtFocusLost
        // TODO add your handling code here:
        new FieldSetting().fieldFoucasLost(STOCKtxt,"", "0");

    }//GEN-LAST:event_STOCKtxtFocusLost

    private void STOCKtxtFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_STOCKtxtFocusGained
        // TODO add your handling code here:
        new FieldSetting().fieldFoucasGain(STOCKtxt,0, "");
    }//GEN-LAST:event_STOCKtxtFocusGained

    private void PRICEtxtKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PRICEtxtKeyTyped
        // TODO add your handling code here:
        new FieldSetting().only_numbers_with_point(evt);
    }//GEN-LAST:event_PRICEtxtKeyTyped

    private void PRICEtxtFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_PRICEtxtFocusLost
        // TODO add your handling code here:
        new FieldSetting().fieldFoucasLost(PRICEtxt,"","0");

    }//GEN-LAST:event_PRICEtxtFocusLost

    private void PRICEtxtFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_PRICEtxtFocusGained
        // TODO add your handling code here:
        new FieldSetting().fieldFoucasGain(PRICEtxt,0, "");
    }//GEN-LAST:event_PRICEtxtFocusGained

    private void button4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button4ActionPerformed
        // TODO add your handling code here:
        clear1();
    }//GEN-LAST:event_button4ActionPerformed

    private void button2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button2ActionPerformed
        // TODO add your handling code here:
        if(!BARCODEtxt.getText().equals("")){
            return;
        }
        genBarcode();
    }//GEN-LAST:event_button2ActionPerformed

    private void button1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button1ActionPerformed
        // TODO add your handling code here:
        if(IDtxt.getText().equals("")){
            return;
        }
        delete(IDtxt.getText());
    }//GEN-LAST:event_button1ActionPerformed

    private void button3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button3ActionPerformed
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_button3ActionPerformed

    private void jScrollPane1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jScrollPane1KeyPressed
        // TODO add your handling code here:
        tableclick();
    }//GEN-LAST:event_jScrollPane1KeyPressed

    private void jScrollPane1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jScrollPane1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jScrollPane1MouseClicked

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
            java.util.logging.Logger.getLogger(AddProduct.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AddProduct.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AddProduct.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AddProduct.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AddProduct().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private ALL_UI_1.TextField BARCODEtxt;
    private ALL_UI_1.TextField DATEtxt;
    private ALL_UI_1.TextField DEStxt;
    private ALL_UI_1.TextField IDtxt;
    private ALL_UI_1.TextField PNAMEtxt;
    private ALL_UI_1.TextField PRICEtxt;
    private ALL_UI_1.TextField SEARCHtxt;
    private ALL_UI_1.TextField STOCKtxt;
    private panal.bgpanal bgpanal1;
    private ALL_UI_1.Button button1;
    private ALL_UI_1.Button button2;
    private ALL_UI_1.Button button3;
    private ALL_UI_1.Button button4;
    private ALL_UI_1.Button button5;
    private ALL_UI_1.Button button6;
    private ALL_UI_1.Button button7;
    private ALL.Combobox category;
    private tabledark.TableDark datatable;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private panal.pane2 pane21;
    private panal.pane3 pane31;
    private ALL_UI_1.Button savebtn;
    private tabledark.TableDark tableDark2;
    // End of variables declaration//GEN-END:variables
}
