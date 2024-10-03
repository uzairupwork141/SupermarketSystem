/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package panal;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author SHABBIR TRADERS
 */
public class txtfld extends JTextField {
    txtfld(){
         setOpaque(true );
         setBorder (new EmptyBorder (10,5,10,5 ));
         this.setSize(10, 5);
    }
}
