/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package panal;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.JLabel;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author SHABBIR TRADERS
 */
public class Tableheader extends JLabel {

    public Tableheader(String text) {
        super (text) ;
        setOpaque (true) ;
        
        setFont (new Font ("sansserif",3,14));
        setForeground (new Color (102,102,102));
        setBackground(Color.YELLOW);
        setBorder (new EmptyBorder (10,5,10,5 ));
    }
    
    @Override
    protected void paintComponent (Graphics grphcs){
        super . paintComponent (grphcs) ;
        grphcs . setColor (new Color (230, 230,230));
        grphcs .drawLine(0, getHeight( )-1,getWidth( ),getHeight ( )-1);
    }

}
