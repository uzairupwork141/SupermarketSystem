/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codeFiles;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.DriverManager;
import java.sql.Connection;

import javax.swing.JOptionPane;

/**
 *
 * @author Gouhar Ali
 */

public class ConnectDB {
    
    Connection con;
    
    
    
  
       
       
    public Connection AccessDBConnection(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");  
            con= DriverManager.getConnection("jdbc:mysql://localhost/shop_db","root","");
        }catch(Exception e){
            
            JOptionPane.showMessageDialog(null,"DATABASE ERROR\n"+e.getMessage(),"ERROR",2);
        }
        return con;
    }
    public static void main(String[] args) throws IOException {


        
        System.out.println(new ConnectDB().AccessDBConnection());
        
    }
     
}
