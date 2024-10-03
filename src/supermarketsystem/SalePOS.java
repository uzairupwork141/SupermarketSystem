/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package supermarketsystem;
import codeFiles.ConnectDB;
import codeFiles.FieldSetting;
import codeFiles.GetStoreInfo;
import codeFiles.usermodel;
import java.awt.Image;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import static java.lang.String.format;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.*;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRTableModelDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
/**
 *
 * @author Gouhar Ali
 */
public class SalePOS extends javax.swing.JFrame {

    /**
     * Creates new form SalePOS
     */
    Connection con;
    PreparedStatement str;
    ResultSet rs;
    
    
    
    
    
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    
    
    
    public SalePOS() {
        initComponents();
        this.setBackground(new Color(0,0,0,0));
        con = new ConnectDB().AccessDBConnection();
        getCategory();
        getAllProductByCategory(searchCATEGORYcbox.getSelectedItem().toString());
        Get_Set_Id();
        DATEtxt.setText(currentdate());
        mainSearchtxt.requestFocus();
    }

    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    
    
    
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
    
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    
    
    
    
    
    
    
    public void Clearprodutc(){
        IDtxt.setText("");
        NAMEtxt.setText("");
        sellPricetxt.setText("");
        stocktxt.setText("");
        QTYtxt.setText("1");
        mainSearchtxt.requestFocus();
        
    }
    
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    
    
    
    
    
    
    
     public void getCategory(){
        try{
            str=con.prepareStatement("select* from category");
            rs=str.executeQuery();
            
            while(rs.next()){
                searchCATEGORYcbox.addItem(rs.getString("NAME"));
            }
            
            
        }catch(Exception ex ){
            JOptionPane.showMessageDialog(this, ex.getMessage(),"error",2);
        }
    }
    
    
    
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    
     public void Get_Set_Id(){
//        
         try{
                    str=con.prepareStatement("SELECT AUTO_INCREMENT FROM information_schema.TABLES WHERE TABLE_NAME = 'sales' ");
                    rs=str.executeQuery();
                    if (rs.next()){
                        int id=rs.getInt("AUTO_INCREMENT");
                        INVOICEtxt.setText(""+id);
                    }
                    
        }catch(Exception ex)
        {
            JOptionPane.showMessageDialog(this,ex,"error",2);
        }
        
    }
    
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    
     
    public double calProductPrice(double qty,Double price){
        Double p = qty*price;
        return p;
    } 
     
     
     //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    
    public void addRowToTable(String[]row ,JTable tbl){
        
        DefaultTableModel df = (DefaultTableModel)tbl.getModel();
        df.addRow(row);
    }
     //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    
    
    public void addItem(){
        String id =IDtxt.getText();
        String name =NAMEtxt.getText();
        String price =sellPricetxt.getText();
        String qty =QTYtxt.getText();
        
        if(checkAndAddDuplicateItem(id, Double.valueOf(qty))){
            return;
        }
        
        Double total =calProductPrice(Double.parseDouble(qty), Double.valueOf(price));
        
        
        
        
        String []arr={id,name,String.format("%.0f",Double.valueOf(price)),qty,String.format("%.0f",total)};
        addRowToTable(arr, detailTable);
        Clearprodutc();
        calulateAll();
    }
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
     
     
     
     public void ProductTofields(){
        
        DefaultTableModel df = (DefaultTableModel)productTable.getModel();
        int srow= productTable.getSelectedRow();
        
        getProductByIDOrCode("ID",df.getValueAt(srow, 0).toString() );
         
        
    }
     
    
    
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    
    
    public void calulateAll()
    {
        try{
            
            SUBTOTALtxt.setText(""+format("%.0f", calTableData(detailTable,4)));
            Double tax=Double.valueOf(TAXtxt.getText());
            Double subt=Double.valueOf(SUBTOTALtxt.getText());
            Double recv=Double.valueOf(RECIVEDtxt.getText());
            int p = (int)(subt*(tax/100.0f));
            double tol = subt +p;
            TOTALtxt.setText(""+format("%.0f",tol));
            
            double chang = recv-tol;
            if(chang<0){
                CHANGEtxt.setText("0");
                return;
            }
            CHANGEtxt.setText(""+format("%.0f",chang));
                
        }catch(Exception ex){
            System.out.println("Error="+ex);
        }
    }
    
    
    
     //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    
    
    public double calTableData(JTable tbl,int columIndex){
        
        try{
            double tol = 0.0;
            

            DefaultTableModel df = (DefaultTableModel)tbl.getModel();
            
            System.out.println(df.getRowCount());
            
            for (int i=0;i<df.getRowCount();i++){
                tol  +=Double.parseDouble(df.getValueAt(i, columIndex).toString());
                        
            }
            return tol;  
        }catch(Exception ex){
            
            return 0;
           
        }
        
    }
    
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    
    
    public boolean checkProductSecton(){
        return !( NAMEtxt.getText().equals("") || sellPricetxt.getText().equals("") || stocktxt.getText().equals("") ||Double.parseDouble(sellPricetxt.getText())<=0 || Integer.parseInt(stocktxt.getText())<=0 ||  Integer.parseInt(QTYtxt.getText())<=0  || QTYtxt.getText().equals("")  );
    }
   
    
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    
     
    public boolean getProductByIDOrCode(String Search_by,String search){
        int c;
        try {
            if(Search_by.equals("ID")){
                str = con.prepareStatement ("SELECT * FROM `product` where ID='"+search+"'");
            }else{
                str=con.prepareStatement ("SELECT * FROM `product` where "+Search_by+"='"+search+"'");
            }
            ResultSet rs=str.executeQuery();
            ResultSetMetaData rss=rs.getMetaData();
            if(rs.next()){
                String ID  =rs.getString("ID");
                String name  =rs.getString("product_name");
                String cate =rs.getString("category");
                String price=rs.getString("price");
                String stock=rs.getString("stock");
                IDtxt.setText(ID);
                NAMEtxt.setText(name);
                sellPricetxt.setText(price);
                stocktxt.setText(stock);
                return true;
                
            }
        } catch (SQLException ex) {
           JOptionPane.showMessageDialog(this,ex,"CONNECTION ",3);
           
        }
        return false;
    }
    
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    
    
    public void SearchForMainTable(String Search_by,String search){
        int c;
        try {

            if(Search_by.equals("ID")){
                str = con.prepareStatement ("SELECT * FROM `product` where ID='"+search+"'");
            }else{
                str=con.prepareStatement ("SELECT * FROM `product` where "+Search_by+" LIKE '%"+search+"%'");
            }
            
            ResultSet rs=str.executeQuery();
            ResultSetMetaData rss=rs.getMetaData();
            c=rss.getColumnCount();
            DefaultTableModel df=(DefaultTableModel)productTable.getModel();
            df.setRowCount(0);
                    
            while (rs.next()){
                
               String ID  =rs.getString("ID");
                String name  =rs.getString("product_name");
                String cate =rs.getString("category");
                String price=rs.getString("price");
                String stock=rs.getString("stock");
                String code=rs.getString("product_barcode");
                String [] row = {ID,name,cate,price,stock,code};
                df.addRow(row);
            }
        } catch (SQLException ex) {
           JOptionPane.showMessageDialog(this,ex,"CONNECTION ",3);
        }
    }
    
    
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    
    
    
     public void getAllProductByCategory(String category){
        int c;
        try {

            if(searchCATEGORYcbox.getSelectedIndex()==0){
                str=con.prepareStatement ("SELECT * FROM `product`");
            }else{
                str=con.prepareStatement ("SELECT * FROM `product` where category='"+category+"'");
            }
            ResultSet rs=str.executeQuery();
            ResultSetMetaData rss=rs.getMetaData();
            c=rss.getColumnCount();
            DefaultTableModel df=(DefaultTableModel)productTable.getModel();
            df.setRowCount(0);
                    
            while (rs.next()){
                
               String ID  =rs.getString("ID");
                String name  =rs.getString("product_name");
                String cate =rs.getString("category");
                String price=rs.getString("price");
                String stock=rs.getString("stock");
                String code=rs.getString("product_barcode");
                String [] row = {ID,name,cate,price,stock,code};
                df.addRow(row);
            }
        } catch (SQLException ex) {
           JOptionPane.showMessageDialog(this,ex,"CONNECTION ",3);
        }
    }
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    
    
    
     
    public void enterPress(KeyEvent evt, JTextField nextField){
        if(evt.getKeyCode() == KeyEvent.VK_ENTER) {
            // Enter was pressed. Your code goes here.
            nextField.requestFocus();
        }
    }
     
     
     //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    
     
    
    
    public boolean checkAndAddDuplicateItem(String id ,double QTY){
        DefaultTableModel df=(DefaultTableModel)detailTable.getModel();
        int rows = df.getRowCount();
        for(int i = 0;i<rows;i++){
            if(df.getValueAt(i,0).toString().equals(id) ){
                double proPrice=Double.parseDouble(df.getValueAt(i,2).toString());
                double pqty=Double.parseDouble(df.getValueAt(i,3).toString());
                double nqty=QTY+pqty;
                double nprice=nqty*proPrice;
                df.setValueAt(String.format("%.0f", nqty), i,3);
                df.setValueAt(String.format("%.0f", nprice), i,4);
                Clearprodutc();
                calulateAll();
                return true;
            }
        }
        return false;
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
        pane21 = new panal.pane2();
        jLabel1 = new javax.swing.JLabel();
        button1 = new ALL_UI_1.Button();
        pane31 = new panal.pane3();
        jScrollPane2 = new javax.swing.JScrollPane();
        productTable = new panal.Table2();
        jLabel7 = new javax.swing.JLabel();
        button6 = new ALL_UI_1.Button();
        searchIDtxt = new ALL_UI_1.TextField();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        searchNAMEtxt = new ALL_UI_1.TextField();
        button7 = new ALL_UI_1.Button();
        button8 = new ALL_UI_1.Button();
        jLabel15 = new javax.swing.JLabel();
        searchCATEGORYcbox = new javax.swing.JComboBox<>();
        button12 = new ALL_UI_1.Button();
        button15 = new ALL_UI_1.Button();
        searchCODEtxt = new ALL_UI_1.TextField();
        jLabel19 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        detailTable = new panal.Table2();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        CHANGEtxt = new ALL_UI_1.TextField();
        SUBTOTALtxt = new ALL_UI_1.TextField();
        TAXtxt = new ALL_UI_1.TextField();
        TOTALtxt = new ALL_UI_1.TextField();
        RECIVEDtxt = new ALL_UI_1.TextField();
        button2 = new ALL_UI_1.Button();
        button3 = new ALL_UI_1.Button();
        button4 = new ALL_UI_1.Button();
        pane32 = new panal.pane3();
        button5 = new ALL_UI_1.Button();
        IDtxt = new ALL_UI_1.TextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        NAMEtxt = new ALL_UI_1.TextField();
        jLabel10 = new javax.swing.JLabel();
        sellPricetxt = new ALL_UI_1.TextField();
        jLabel11 = new javax.swing.JLabel();
        stocktxt = new ALL_UI_1.TextField();
        jLabel12 = new javax.swing.JLabel();
        QTYtxt = new ALL_UI_1.TextField();
        button9 = new ALL_UI_1.Button();
        mainSearchtxt = new ALL_UI_1.TextField();
        jLabel16 = new javax.swing.JLabel();
        DATEtxt = new ALL_UI_1.TextField();
        jLabel17 = new javax.swing.JLabel();
        INVOICEtxt = new ALL_UI_1.TextField();
        jLabel18 = new javax.swing.JLabel();
        button10 = new ALL_UI_1.Button();
        button11 = new ALL_UI_1.Button();
        button13 = new ALL_UI_1.Button();
        button14 = new ALL_UI_1.Button();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        bgpanal1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                bgpanal1KeyReleased(evt);
            }
        });
        bgpanal1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel1.setText("SALE ( POS )");
        pane21.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        button1.setText("X");
        button1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        button1.setShadowColor(new java.awt.Color(0, 0, 0));
        button1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button1ActionPerformed(evt);
            }
        });
        pane21.add(button1, new org.netbeans.lib.awtextra.AbsoluteConstraints(1240, 0, 50, 40));

        bgpanal1.add(pane21, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1290, 38));

        productTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "NAME", "CATEGORY", "PRICE", "STOCK", "CODE"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        productTable.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        productTable.setRowHeight(30);
        productTable.setShowGrid(true);
        productTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                productTableMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(productTable);

        pane31.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 200, 550, 500));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText(" PRODUCTS LOOKUP");
        pane31.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 560, 30));

        button6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ASSETS_files/icons8-search-20.png"))); // NOI18N
        button6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        button6.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        button6.setRippleColor(new java.awt.Color(0, 153, 153));
        button6.setShadowColor(new java.awt.Color(0, 0, 0));
        button6.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        button6.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        button6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button6ActionPerformed(evt);
            }
        });
        pane31.add(button6, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 120, 70, 40));

        searchIDtxt.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        searchIDtxt.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        searchIDtxt.setShadowColor(new java.awt.Color(0, 0, 0));
        searchIDtxt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                searchIDtxtKeyReleased(evt);
            }
        });
        pane31.add(searchIDtxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 40, 180, 40));

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel13.setText("Product ID");
        pane31.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 40, 100, 30));

        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel14.setText("Category");
        pane31.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 160, 100, 30));

        searchNAMEtxt.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        searchNAMEtxt.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        searchNAMEtxt.setShadowColor(new java.awt.Color(0, 0, 0));
        searchNAMEtxt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                searchNAMEtxtKeyReleased(evt);
            }
        });
        pane31.add(searchNAMEtxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 120, 180, 40));

        button7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ASSETS_files/icons8-refresh-36.png"))); // NOI18N
        button7.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        button7.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        button7.setRippleColor(new java.awt.Color(0, 153, 153));
        button7.setShadowColor(new java.awt.Color(0, 0, 0));
        button7.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        button7.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        button7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button7ActionPerformed(evt);
            }
        });
        pane31.add(button7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 710, 180, 60));

        button8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ASSETS_files/icons8-search-20.png"))); // NOI18N
        button8.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        button8.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        button8.setRippleColor(new java.awt.Color(0, 153, 153));
        button8.setShadowColor(new java.awt.Color(0, 0, 0));
        button8.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        button8.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        button8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button8ActionPerformed(evt);
            }
        });
        pane31.add(button8, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 40, 70, 40));

        jLabel15.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel15.setText("Product Name");
        pane31.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 120, 110, 30));

        searchCATEGORYcbox.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        searchCATEGORYcbox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ALL" }));
        searchCATEGORYcbox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchCATEGORYcboxActionPerformed(evt);
            }
        });
        pane31.add(searchCATEGORYcbox, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 160, 250, 30));

        button12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ASSETS_files/icons8-products-50.png"))); // NOI18N
        button12.setText("PRODUCTS ");
        button12.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        button12.setShadowColor(new java.awt.Color(0, 0, 0));
        pane31.add(button12, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 710, 180, 60));

        button15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ASSETS_files/icons8-search-20.png"))); // NOI18N
        button15.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        button15.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        button15.setRippleColor(new java.awt.Color(0, 153, 153));
        button15.setShadowColor(new java.awt.Color(0, 0, 0));
        button15.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        button15.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        button15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button15ActionPerformed(evt);
            }
        });
        pane31.add(button15, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 80, 70, 40));

        searchCODEtxt.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        searchCODEtxt.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        searchCODEtxt.setShadowColor(new java.awt.Color(0, 0, 0));
        searchCODEtxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchCODEtxtActionPerformed(evt);
            }
        });
        searchCODEtxt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                searchCODEtxtKeyReleased(evt);
            }
        });
        pane31.add(searchCODEtxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 80, 180, 40));

        jLabel19.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel19.setText("Product Code");
        pane31.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 80, 110, 30));

        bgpanal1.add(pane31, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 50, 570, 770));

        detailTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "NAME", "PRICE", "QTY", "TOTAL"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        detailTable.setRowHeight(20);
        detailTable.setShowGrid(true);
        jScrollPane1.setViewportView(detailTable);

        bgpanal1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 260, 540, 250));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setText("Tax %");
        bgpanal1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 530, 50, 40));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setText("Change");
        bgpanal1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 750, 60, 30));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setText("Sub Total");
        bgpanal1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 540, -1, -1));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel5.setText("Total");
        bgpanal1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 600, 60, 70));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel6.setText("Recived");
        bgpanal1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 690, 60, 30));

        CHANGEtxt.setEditable(false);
        CHANGEtxt.setBackground(new java.awt.Color(241, 239, 239));
        CHANGEtxt.setForeground(new java.awt.Color(204, 0, 51));
        CHANGEtxt.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        CHANGEtxt.setText("0");
        CHANGEtxt.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        CHANGEtxt.setShadowColor(new java.awt.Color(0, 0, 0));
        bgpanal1.add(CHANGEtxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 740, 250, 60));

        SUBTOTALtxt.setEditable(false);
        SUBTOTALtxt.setBackground(new java.awt.Color(241, 239, 239));
        SUBTOTALtxt.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        SUBTOTALtxt.setText("0");
        SUBTOTALtxt.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        SUBTOTALtxt.setShadowColor(new java.awt.Color(0, 0, 0));
        bgpanal1.add(SUBTOTALtxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 530, 180, -1));

        TAXtxt.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        TAXtxt.setText("0");
        TAXtxt.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        TAXtxt.setShadowColor(new java.awt.Color(0, 0, 0));
        TAXtxt.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                TAXtxtFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                TAXtxtFocusLost(evt);
            }
        });
        TAXtxt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TAXtxtKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                TAXtxtKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                TAXtxtKeyTyped(evt);
            }
        });
        bgpanal1.add(TAXtxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 530, 180, -1));

        TOTALtxt.setEditable(false);
        TOTALtxt.setBackground(new java.awt.Color(241, 239, 239));
        TOTALtxt.setForeground(new java.awt.Color(0, 51, 255));
        TOTALtxt.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        TOTALtxt.setText("0");
        TOTALtxt.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        TOTALtxt.setShadowColor(new java.awt.Color(0, 0, 0));
        bgpanal1.add(TOTALtxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 590, 250, 90));

        RECIVEDtxt.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        RECIVEDtxt.setText("0");
        RECIVEDtxt.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        RECIVEDtxt.setShadowColor(new java.awt.Color(0, 0, 0));
        RECIVEDtxt.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                RECIVEDtxtFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                RECIVEDtxtFocusLost(evt);
            }
        });
        RECIVEDtxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RECIVEDtxtActionPerformed(evt);
            }
        });
        RECIVEDtxt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RECIVEDtxtKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                RECIVEDtxtKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                RECIVEDtxtKeyTyped(evt);
            }
        });
        bgpanal1.add(RECIVEDtxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 680, 250, 60));

        button2.setBackground(new java.awt.Color(248, 248, 248));
        button2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ASSETS_files/icons8-home-40 (1).png"))); // NOI18N
        button2.setText("HOME");
        button2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        button2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        button2.setRippleColor(new java.awt.Color(0, 153, 153));
        button2.setShadowColor(new java.awt.Color(0, 0, 0));
        button2.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        button2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        button2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button2ActionPerformed(evt);
            }
        });
        bgpanal1.add(button2, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 580, 150, 80));

        button3.setBackground(new java.awt.Color(248, 248, 248));
        button3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ASSETS_files/icons8-print-36.png"))); // NOI18N
        button3.setText("PRINT");
        button3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        button3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        button3.setRippleColor(new java.awt.Color(0, 153, 153));
        button3.setShadowColor(new java.awt.Color(0, 0, 0));
        button3.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        button3.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        button3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button3ActionPerformed(evt);
            }
        });
        bgpanal1.add(button3, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 340, 150, 80));

        button4.setBackground(new java.awt.Color(248, 248, 248));
        button4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ASSETS_files/icons8-save-36.png"))); // NOI18N
        button4.setText("SAVE");
        button4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        button4.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        button4.setRippleColor(new java.awt.Color(0, 153, 153));
        button4.setShadowColor(new java.awt.Color(0, 0, 0));
        button4.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        button4.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        bgpanal1.add(button4, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 420, 150, 80));

        button5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ASSETS_files/icons8-clear-50.png"))); // NOI18N
        button5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        button5.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        button5.setRippleColor(new java.awt.Color(0, 153, 153));
        button5.setRound(70);
        button5.setShadowColor(new java.awt.Color(0, 0, 0));
        button5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button5ActionPerformed(evt);
            }
        });
        pane32.add(button5, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 80, 80, 80));

        IDtxt.setEditable(false);
        IDtxt.setBackground(new java.awt.Color(255, 204, 204));
        IDtxt.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        IDtxt.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        IDtxt.setShadowColor(new java.awt.Color(0, 0, 0));
        pane32.add(IDtxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 20, 180, 40));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel8.setText(" ID#");
        pane32.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 20, 80, 30));

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel9.setText("Product");
        pane32.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 20, 70, 30));

        NAMEtxt.setEditable(false);
        NAMEtxt.setBackground(new java.awt.Color(255, 204, 204));
        NAMEtxt.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        NAMEtxt.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        NAMEtxt.setShadowColor(new java.awt.Color(0, 0, 0));
        pane32.add(NAMEtxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 20, 210, 40));

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel10.setText("Sell Price");
        pane32.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 60, 80, 30));

        sellPricetxt.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        sellPricetxt.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        sellPricetxt.setShadowColor(new java.awt.Color(0, 0, 0));
        sellPricetxt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                sellPricetxtKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                sellPricetxtKeyTyped(evt);
            }
        });
        pane32.add(sellPricetxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 60, 180, 40));

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel11.setText("Stock");
        pane32.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 60, 70, 30));

        stocktxt.setEditable(false);
        stocktxt.setBackground(new java.awt.Color(255, 204, 204));
        stocktxt.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        stocktxt.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        stocktxt.setShadowColor(new java.awt.Color(0, 0, 0));
        pane32.add(stocktxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 60, 210, 40));

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel12.setText("Qty");
        pane32.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 100, 80, 40));

        QTYtxt.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        QTYtxt.setText("1");
        QTYtxt.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        QTYtxt.setShadowColor(new java.awt.Color(0, 0, 0));
        QTYtxt.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                QTYtxtFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                QTYtxtFocusLost(evt);
            }
        });
        QTYtxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                QTYtxtActionPerformed(evt);
            }
        });
        QTYtxt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                QTYtxtKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                QTYtxtKeyTyped(evt);
            }
        });
        pane32.add(QTYtxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 100, 180, 40));

        button9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ASSETS_files/icons8-add-64.png"))); // NOI18N
        button9.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        button9.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        button9.setRippleColor(new java.awt.Color(0, 153, 153));
        button9.setRound(70);
        button9.setShadowColor(new java.awt.Color(0, 0, 0));
        button9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button9ActionPerformed(evt);
            }
        });
        pane32.add(button9, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 0, 80, 80));

        bgpanal1.add(pane32, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 690, 160));

        mainSearchtxt.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        mainSearchtxt.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        mainSearchtxt.setShadowColor(new java.awt.Color(0, 0, 0));
        mainSearchtxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mainSearchtxtActionPerformed(evt);
            }
        });
        bgpanal1.add(mainSearchtxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 50, 140, 40));

        jLabel16.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel16.setText(" ID/Code #");
        bgpanal1.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 50, 80, 30));

        DATEtxt.setBackground(new java.awt.Color(239, 239, 239));
        DATEtxt.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        DATEtxt.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        DATEtxt.setShadowColor(new java.awt.Color(0, 0, 0));
        bgpanal1.add(DATEtxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 50, 140, 40));

        jLabel17.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel17.setText("Date");
        bgpanal1.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 50, 40, 30));

        INVOICEtxt.setBackground(new java.awt.Color(239, 239, 239));
        INVOICEtxt.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        INVOICEtxt.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        INVOICEtxt.setShadowColor(new java.awt.Color(0, 0, 0));
        bgpanal1.add(INVOICEtxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 50, 100, 40));

        jLabel18.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel18.setText("Invoice #");
        bgpanal1.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 80, 30));

        button10.setBackground(new java.awt.Color(248, 248, 248));
        button10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ASSETS_files/icons8-refresh-36.png"))); // NOI18N
        button10.setText("REFRESH");
        button10.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        button10.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        button10.setRippleColor(new java.awt.Color(0, 153, 153));
        button10.setShadowColor(new java.awt.Color(0, 0, 0));
        button10.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        button10.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        button10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button10ActionPerformed(evt);
            }
        });
        bgpanal1.add(button10, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 500, 150, 80));

        button11.setBackground(new java.awt.Color(235, 234, 234));
        button11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ASSETS_files/icons8-search-20.png"))); // NOI18N
        button11.setShadowColor(new java.awt.Color(0, 0, 0));
        button11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button11ActionPerformed(evt);
            }
        });
        bgpanal1.add(button11, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 50, 70, 40));

        button13.setBackground(new java.awt.Color(248, 248, 248));
        button13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ASSETS_files/icons8-delete-36.png"))); // NOI18N
        button13.setText("REMOVE ITEM");
        button13.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        button13.setHideActionText(true);
        button13.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        button13.setShadowColor(new java.awt.Color(0, 0, 0));
        button13.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        button13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button13ActionPerformed(evt);
            }
        });
        bgpanal1.add(button13, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 260, 150, 80));

        button14.setBackground(new java.awt.Color(204, 255, 255));
        button14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ASSETS_files/icons8-calculate-50.png"))); // NOI18N
        button14.setText("CALCULATE");
        button14.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        button14.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        button14.setShadowColor(new java.awt.Color(0, 0, 0));
        button14.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        button14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button14ActionPerformed(evt);
            }
        });
        bgpanal1.add(button14, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 660, 150, 160));

        getContentPane().add(bgpanal1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1290, 830));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void button1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button1ActionPerformed
        System.exit(0);
        // TODO add your handling code here:
    }//GEN-LAST:event_button1ActionPerformed

    private void button8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button8ActionPerformed
        // TODO add your handling code here:
        SearchForMainTable("ID", searchIDtxt.getText());
    }//GEN-LAST:event_button8ActionPerformed

    private void button7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button7ActionPerformed
        // TODO add your handling code here:
        searchIDtxt.setText("");
        searchNAMEtxt.setText("");
        searchCATEGORYcbox.setSelectedIndex(0);
        
    }//GEN-LAST:event_button7ActionPerformed

    private void searchCATEGORYcboxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchCATEGORYcboxActionPerformed
        // TODO add your handling code here:
        getAllProductByCategory(searchCATEGORYcbox.getSelectedItem().toString());
    }//GEN-LAST:event_searchCATEGORYcboxActionPerformed

    private void button6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button6ActionPerformed
        // TODO add your handling code here:
        SearchForMainTable("product_name", searchNAMEtxt.getText());
    }//GEN-LAST:event_button6ActionPerformed

    private void searchNAMEtxtKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchNAMEtxtKeyReleased
        // TODO add your handling code here:
        SearchForMainTable("product_name", searchNAMEtxt.getText());
        
    }//GEN-LAST:event_searchNAMEtxtKeyReleased

    private void button11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button11ActionPerformed
        // TODO add your handling code here:
        if(getProductByIDOrCode("ID",mainSearchtxt.getText())){
            mainSearchtxt.setText("");
            QTYtxt.requestFocus();   
        }
    }//GEN-LAST:event_button11ActionPerformed

    private void mainSearchtxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mainSearchtxtActionPerformed
        // TODO add your handling code here:
        if(getProductByIDOrCode("product_barcode",mainSearchtxt.getText())){
            mainSearchtxt.setText("");
            QTYtxt.requestFocus();   
        }
        
        
    }//GEN-LAST:event_mainSearchtxtActionPerformed

    private void button5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button5ActionPerformed
        // TODO add your handling code here:
        Clearprodutc();
    }//GEN-LAST:event_button5ActionPerformed

    private void QTYtxtFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_QTYtxtFocusGained
        // TODO add your handling code here:
        new FieldSetting().fieldFoucasGain(QTYtxt,0, "");
    }//GEN-LAST:event_QTYtxtFocusGained

    private void QTYtxtFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_QTYtxtFocusLost
        // TODO add your handling code here:
        new FieldSetting().fieldFoucasLost(QTYtxt,"", "0");
        
    }//GEN-LAST:event_QTYtxtFocusLost

    private void QTYtxtKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_QTYtxtKeyTyped
        // TODO add your handling code here:
          new FieldSetting().only_numbers(evt);
    }//GEN-LAST:event_QTYtxtKeyTyped

    private void sellPricetxtKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_sellPricetxtKeyTyped
        // TODO add your handling code here:
          new FieldSetting().only_numbers_with_point(evt);
    }//GEN-LAST:event_sellPricetxtKeyTyped

    private void TAXtxtKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TAXtxtKeyTyped
        // TODO add your handling code here:
          new FieldSetting().only_numbers_with_point(evt);
          calulateAll();
    }//GEN-LAST:event_TAXtxtKeyTyped

    private void RECIVEDtxtKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RECIVEDtxtKeyTyped
        // TODO add your handling code here:
        new FieldSetting().only_numbers_with_point(evt);
        if(evt.getKeyCode() == KeyEvent.VK_ENTER) {
            button14.requestFocus();
        }
        
    }//GEN-LAST:event_RECIVEDtxtKeyTyped

    private void TAXtxtFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_TAXtxtFocusGained
        // TODO add your handling code here:
        new FieldSetting().fieldFoucasGain(TAXtxt,0, "");
    }//GEN-LAST:event_TAXtxtFocusGained

    private void TAXtxtFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_TAXtxtFocusLost
        // TODO add your handling code here:
          new FieldSetting().fieldFoucasLost(TAXtxt,"", "0");
          calulateAll();
    }//GEN-LAST:event_TAXtxtFocusLost

    private void RECIVEDtxtFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_RECIVEDtxtFocusGained
        // TODO add your handling code here:
        new FieldSetting().fieldFoucasGain( RECIVEDtxt,0, "");
    }//GEN-LAST:event_RECIVEDtxtFocusGained

    private void RECIVEDtxtFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_RECIVEDtxtFocusLost
        // TODO add your handling code here:
           new FieldSetting().fieldFoucasLost(RECIVEDtxt,"", "0");
           
           calulateAll();
    }//GEN-LAST:event_RECIVEDtxtFocusLost

    private void button9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button9ActionPerformed
        // TODO add your handling code here
        if(checkProductSecton()==false){
            return;
        }
        addItem();
        
        
    }//GEN-LAST:event_button9ActionPerformed

    private void TAXtxtKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TAXtxtKeyReleased
        // TODO add your handling code here:
        
        
        calulateAll();
        
    }//GEN-LAST:event_TAXtxtKeyReleased

    private void RECIVEDtxtKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RECIVEDtxtKeyReleased
        // TODO add your handling code here:
        
        
       
        
        
        calulateAll();
    }//GEN-LAST:event_RECIVEDtxtKeyReleased

    private void sellPricetxtKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_sellPricetxtKeyReleased
        // TODO add your handling code here:
        enterPress(evt,QTYtxt);
    }//GEN-LAST:event_sellPricetxtKeyReleased

    private void TAXtxtKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TAXtxtKeyPressed
        // TODO add your handling code here:
        
        enterPress(evt, RECIVEDtxt);
        
    }//GEN-LAST:event_TAXtxtKeyPressed

    private void button13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button13ActionPerformed
        // TODO add your handling code here:
       DefaultTableModel df=(DefaultTableModel)detailTable.getModel();
       int srow =detailTable.getSelectedRow();
       df.removeRow(srow);
       calulateAll();
    }//GEN-LAST:event_button13ActionPerformed

    private void button14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button14ActionPerformed
        // TODO add your handling code here:
        calulateAll();
    }//GEN-LAST:event_button14ActionPerformed

    private void QTYtxtKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_QTYtxtKeyReleased
        // TODO add your handling code here:
        
        
        
        
        
    }//GEN-LAST:event_QTYtxtKeyReleased

    private void productTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_productTableMouseClicked
        // TODO add your handling code here:
        
        ProductTofields();
        productTable.clearSelection();
        QTYtxt.requestFocus();
    }//GEN-LAST:event_productTableMouseClicked

    private void RECIVEDtxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RECIVEDtxtActionPerformed
        // TODO add your handling code here:
        
       
     
    }//GEN-LAST:event_RECIVEDtxtActionPerformed

    private void bgpanal1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_bgpanal1KeyReleased
        // TODO add your handling code here:
        if(evt.getKeyCode() == KeyEvent.VK_F1) {
            // Enter was pressed. Your code goes here.
            button1 .requestFocus();
            calulateAll();
        }
    }//GEN-LAST:event_bgpanal1KeyReleased

    private void RECIVEDtxtKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RECIVEDtxtKeyPressed

        // TODO add your handling code here:
         
        
        if(evt.getKeyCode() == KeyEvent.VK_ENTER) {
          button1.requestFocus();
        }
        
        
        
        
        
        
        
    }//GEN-LAST:event_RECIVEDtxtKeyPressed

    private void button10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button10ActionPerformed
        // TODO add your handling code here:
        dispose();
        new SalePOS().setVisible(true);
    }//GEN-LAST:event_button10ActionPerformed

    private void button3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button3ActionPerformed
        // TODO add your handling code here:
        double re = Double.parseDouble(RECIVEDtxt.getText());
        double tol = Double.parseDouble(TOTALtxt.getText());
        if(re<=0){
            JOptionPane.showMessageDialog(this, "Invalid Amount\nRecived Amount is ="+re,"Invalid entry",2);
            return;
        }
        if(re<tol){
            JOptionPane.showMessageDialog(this, "Invalid Amount\nRecived Amount "+re+" is Less then total "+tol,"Invalid entry",2);
            return;
        }
        
        try{
            
            DefaultTableModel df=(DefaultTableModel)detailTable.getModel();
            JRTableModelDataSource DS =new JRTableModelDataSource(df);
            
            File currentDir = new File(".");
	    String basePath = currentDir.getCanonicalPath();
	    // Define file path
	    String filePath = basePath + "/src/reports/newReport.jrxml";
            InputStream in = new FileInputStream(filePath);
            JasperDesign jd = JRXmlLoader.load(in);
            JasperReport jr = JasperCompileManager.compileReport(jd);
            
            usermodel user = new usermodel();
            Map<String ,Object> para = new HashMap<>();
            
            GetStoreInfo store  =new GetStoreInfo();
            String []store_data =store.getData();
            byte[] im = store.getLogo();
           
            
            para.put("logo", im);
            para.put("shopName", store_data[1]);
            para.put("Cid", user.getID());
            para.put("cname", user.getName());
            para.put("Invoice", INVOICEtxt.getText());
            para.put("Subtotal",SUBTOTALtxt.getText());
            para.put("tax", TAXtxt.getText());
            para.put("totalprice", TOTALtxt.getText());
            para.put("recived", RECIVEDtxt.getText());
            para.put("change", CHANGEtxt.getText());
            para.put("phone", store_data[2]);
            para.put("address", store_data[3]);
            para.put("STORETIME", "Mon-Sat, 9 AM - 9 PM | Sun, 10 AM - 6 PM");
            
            
           
            JasperPrint j = JasperFillManager.fillReport(jr, para,DS);
           
            JasperViewer.viewReport(j, false);
            
        }catch(Exception ex){
             System.out.println("ERROR =>  "+ex);
        }
        
        
    }//GEN-LAST:event_button3ActionPerformed

    private void button2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button2ActionPerformed
        // TODO add your handling code here:
        dispose();
        new ADMIN_MAIN().setVisible(true);
    }//GEN-LAST:event_button2ActionPerformed

    private void QTYtxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_QTYtxtActionPerformed
        // TODO add your handling code here:
       
           if(checkProductSecton()==false){
            return;
           }
           addItem();
        
    }//GEN-LAST:event_QTYtxtActionPerformed

    private void button15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button15ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_button15ActionPerformed

    private void searchCODEtxtKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchCODEtxtKeyReleased
        // TODO add your handling code here:
        SearchForMainTable("product_barcode", searchCODEtxt.getText());
    }//GEN-LAST:event_searchCODEtxtKeyReleased

    private void searchCODEtxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchCODEtxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_searchCODEtxtActionPerformed

    private void searchIDtxtKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchIDtxtKeyReleased
        // TODO add your handling code here:
        if(searchIDtxt.getText().equals("")){
            getAllProductByCategory("ALL");
        }
    }//GEN-LAST:event_searchIDtxtKeyReleased

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
            java.util.logging.Logger.getLogger(SalePOS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SalePOS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SalePOS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SalePOS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SalePOS().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private ALL_UI_1.TextField CHANGEtxt;
    private ALL_UI_1.TextField DATEtxt;
    private ALL_UI_1.TextField IDtxt;
    private ALL_UI_1.TextField INVOICEtxt;
    private ALL_UI_1.TextField NAMEtxt;
    private ALL_UI_1.TextField QTYtxt;
    private ALL_UI_1.TextField RECIVEDtxt;
    private ALL_UI_1.TextField SUBTOTALtxt;
    private ALL_UI_1.TextField TAXtxt;
    private ALL_UI_1.TextField TOTALtxt;
    private panal.bgpanal bgpanal1;
    private ALL_UI_1.Button button1;
    private ALL_UI_1.Button button10;
    private ALL_UI_1.Button button11;
    private ALL_UI_1.Button button12;
    private ALL_UI_1.Button button13;
    private ALL_UI_1.Button button14;
    private ALL_UI_1.Button button15;
    private ALL_UI_1.Button button2;
    private ALL_UI_1.Button button3;
    private ALL_UI_1.Button button4;
    private ALL_UI_1.Button button5;
    private ALL_UI_1.Button button6;
    private ALL_UI_1.Button button7;
    private ALL_UI_1.Button button8;
    private ALL_UI_1.Button button9;
    private panal.Table2 detailTable;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private ALL_UI_1.TextField mainSearchtxt;
    private panal.pane2 pane21;
    private panal.pane3 pane31;
    private panal.pane3 pane32;
    private panal.Table2 productTable;
    private javax.swing.JComboBox<String> searchCATEGORYcbox;
    private ALL_UI_1.TextField searchCODEtxt;
    private ALL_UI_1.TextField searchIDtxt;
    private ALL_UI_1.TextField searchNAMEtxt;
    private ALL_UI_1.TextField sellPricetxt;
    private ALL_UI_1.TextField stocktxt;
    // End of variables declaration//GEN-END:variables
}
