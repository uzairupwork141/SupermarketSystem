/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codeFiles;

import java.awt.event.KeyEvent;
import javax.swing.JTextField;


public class OnlyNumbers{
    
    
    
    
    public void only_numbers(KeyEvent ke){
        
        if(!(ke.getKeyChar()>='0' && ke.getKeyChar()<='9' || ke.getKeyChar()=='.' ) ){
            ke.consume();
        }
        
    }
    
       
    
}
