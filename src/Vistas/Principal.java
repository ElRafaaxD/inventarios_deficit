/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Vistas;

import Controladores.AltaDeficitControlador;
import DAOS.DeficitDAO;
import java.awt.BorderLayout;
import java.awt.Color;
import java.util.Locale;
import javax.swing.JPanel;

/**
 *
 * @author miguelLlano
 */
public class Principal extends javax.swing.JFrame {

    /**
     * Creates new form Principal
     */
    public Principal() {
        initComponents();
        pnl_principal.setBackground(Color.WHITE);
        iniciarVistaAgregarDeificit();
        
        
    }

    public void agregarPanel(JPanel nuevoPanel) {
        nuevoPanel.setSize(800, 600);
        nuevoPanel.setLocation(0, 0);
        
        pnl_principal.removeAll();
        pnl_principal.add(nuevoPanel, BorderLayout.CENTER);
        pnl_principal.revalidate();
        pnl_principal.repaint();
    }
    
    public void iniciarVistaAgregarDeificit() {
        AltaDeficit vistaAltaDeficit = new AltaDeficit();
        DeficitDAO deficitDAO = new DeficitDAO();
        AltaDeficitControlador altaDecitiControlador = new AltaDeficitControlador(
            vistaAltaDeficit, 
            deficitDAO
        );
        
        vistaAltaDeficit.setControllador(altaDecitiControlador);
        //agrega la vista al panel
        agregarPanel(vistaAltaDeficit);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnl_principal = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout pnl_principalLayout = new javax.swing.GroupLayout(pnl_principal);
        pnl_principal.setLayout(pnl_principalLayout);
        pnl_principalLayout.setHorizontalGroup(
            pnl_principalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 800, Short.MAX_VALUE)
        );
        pnl_principalLayout.setVerticalGroup(
            pnl_principalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 600, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnl_principal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnl_principal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel pnl_principal;
    // End of variables declaration//GEN-END:variables
}
