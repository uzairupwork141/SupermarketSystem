/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codeFiles;

import java.awt.Image;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.ImageIcon;

/**
 *
 * @author Gouhar Ali
 */
public class GetStoreInfo {
    
    Connection con=new ConnectDB().AccessDBConnection();
    PreparedStatement str;
    ResultSet rs;
    
    public String [] getData(){
      try{
        str = con.prepareStatement("Select* from store_details where ID=1");
        rs=str.executeQuery();
        while(rs.next()){
            String[]arr={rs.getString("ID"),rs.getString("NAME"),rs.getString("PHONE"),rs.getString("ADDRESS")};
            return arr;
        }
      }catch(Exception ex){
        System.err.println(ex);
      }
      return null;
    }
    
     
    
     
     
    public byte[] getLogo(){
      try{
        str = con.prepareStatement("Select logo from store_details where ID=1");
        rs=str.executeQuery();
        if(rs.next()){
            byte[] Img = rs.getBytes("logo");
            //Resize The ImageIcon
            ImageIcon image = new ImageIcon(Img);
            Image im = image.getImage();
//            Image myImg = im.getScaledInstance(190,190,Image.SCALE_SMOOTH);
            ImageIcon newImage = new ImageIcon(im);
            return Img;
        }
      }catch(Exception ex){
        System.err.println(ex);
      }
      return null;
    }
     
     
//     byte[] Img = rs.getBytes("img");
//                        //Resize The ImageIcon
//                        ImageIcon image = new ImageIcon(Img);
//                        Image im = image.getImage();
//                        Image myImg = im.getScaledInstance(190,190,Image.SCALE_SMOOTH);
//                        ImageIcon newImage = new ImageIcon(myImg);
    
}
