/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package panal;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author SHABBIR TRADERS
 */
public class Table2 extends JTable {
    
    public Table2(){
        setOpaque(false );
        setShowHorizontalLines (true) ;
        
        setRowHeight (53) ;
        getTableHeader ( ) .setReorderingAllowed (false) ;
        setBorder (new EmptyBorder (10,5,10,5 ));
        getTableHeader ( ).setDefaultRenderer (new DefaultTableCellRenderer ( ){
        @Override
        public Component getTableCellRendererComponent( JTable jtable , Object o , boolean bln, boolean bln1,int i,int i1){
            Tableheader header = new Tableheader (o +"");
            return header; 
        }    
        
    });
        
}
}