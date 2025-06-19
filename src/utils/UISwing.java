/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.text.JTextComponent;

/**
 *
 * @author miguelLlano
 */
public class UISwing {
    
    public static void cambiarColorFondo(
        JComponent component, 
        Color nuevoBackground){
        component.setBackground(nuevoBackground);
    }
    
    public static void cambiarTexto(
        JComponent component,
        String nuevoTexto) {

        if(component instanceof JLabel)
            ((JLabel)component).setText(nuevoTexto);
        else if(component instanceof JButton) 
            ((JButton)component).setText(nuevoTexto);
        else if(component instanceof JButton)
            ((JTextComponent) component).setText(nuevoTexto);
    }
    
    public static void cambiarColorFondoyTexto(
        JComponent component, 
        Color nuevoBackground, 
        String nuevoTexto) {
        //cambiar el color
        cambiarColorFondo(component, nuevoBackground);
        //cambiar textp
        cambiarTexto(component, nuevoTexto);
    }
}
