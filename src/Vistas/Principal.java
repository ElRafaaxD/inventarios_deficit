package Vistas;

import Controladores.AltaDeficitControlador;
import Controladores.MostrarDeficitControlador;
import DAOS.DeficitDAO;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

public class Principal extends javax.swing.JFrame {

    public Principal() {
        initComponents();
        this.setTitle("Inventarios");
        pnl_principal.setBackground(Color.WHITE);
        
        Font fuenteGrande = new Font("Arial", Font.PLAIN, 20);
        // Aplicar la fuente al JMenuBar
        mnbMenuPrincipal.setFont(fuenteGrande);
        mnbMenuPrincipal.setBackground(Color.WHITE);
        mnbMenuPrincipal.setOpaque(true);
        mnbMenuPrincipal.setBorderPainted(false);

        // Si ya tienes menús dentro, también debes cambiarles la fuente
        for (int i = 0; i < mnbMenuPrincipal.getMenuCount(); i++) {
            JMenu menu = mnbMenuPrincipal.getMenu(i);
            if (menu != null) {
                menu.setFont(fuenteGrande);
                for (int j = 0; j < menu.getItemCount(); j++) {
                    JMenuItem item = menu.getItem(j);
                    if (item != null) {
                        item.setFont(fuenteGrande);
                    }
                }
            }
        }
        
        //iniciarVistaAgregarDeificit();
        iniciarVistaMostrarDeificit();
    }

    private void agregarPanel(JPanel nuevoPanel) {
        nuevoPanel.setSize(1000, 600);
        nuevoPanel.setLocation(0, 0);
        
        pnl_principal.removeAll();
        pnl_principal.add(nuevoPanel, BorderLayout.CENTER);
        pnl_principal.revalidate();
        pnl_principal.repaint();
    }
    
    /*
    * funcion para mandar a llamar la vista de agregar deficit (nuevo deficit)
    */
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
    
    /*
    * funcion para mandar a llamar la vista de editar deficit (editar deficit)
    */
    public void iniciarVistaAgregarDeificit(int id_deficit) {
        AltaDeficit vistaAltaDeficit = new AltaDeficit();
        DeficitDAO deficitDAO = new DeficitDAO();
        AltaDeficitControlador altaDecitiControlador = new AltaDeficitControlador(
            vistaAltaDeficit, 
            deficitDAO,
            id_deficit
        );
        
        vistaAltaDeficit.setControllador(altaDecitiControlador);
        //agrega la vista al panel
        agregarPanel(vistaAltaDeficit);
    }
    
    /*
    * funcion para mandar a llamar la vista de mostrar deficits
    */
    public void iniciarVistaMostrarDeificit() {
        MostrarDeficit vistaMostrarDeficit = new MostrarDeficit();
        DeficitDAO deficitDAO = new DeficitDAO();
        MostrarDeficitControlador MostrarDeficitControlador = new MostrarDeficitControlador(
            vistaMostrarDeficit, 
            deficitDAO
        );
        
        vistaMostrarDeficit.setControllador(MostrarDeficitControlador);
        //agrega la vista al panel
        agregarPanel(vistaMostrarDeficit);
        
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnl_principal = new javax.swing.JPanel();
        mnbMenuPrincipal = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout pnl_principalLayout = new javax.swing.GroupLayout(pnl_principal);
        pnl_principal.setLayout(pnl_principalLayout);
        pnl_principalLayout.setHorizontalGroup(
            pnl_principalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1000, Short.MAX_VALUE)
        );
        pnl_principalLayout.setVerticalGroup(
            pnl_principalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 558, Short.MAX_VALUE)
        );

        mnbMenuPrincipal.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        mnbMenuPrincipal.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        mnbMenuPrincipal.setMargin(new java.awt.Insets(5, 5, 5, 5));

        jMenu1.setText("Menu");

        jMenuItem1.setText("jMenuItem1");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuItem2.setText("jMenuItem2");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        mnbMenuPrincipal.add(jMenu1);

        setJMenuBar(mnbMenuPrincipal);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnl_principal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnl_principal, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        iniciarVistaMostrarDeificit();
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        iniciarVistaAgregarDeificit();
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuBar mnbMenuPrincipal;
    private javax.swing.JPanel pnl_principal;
    // End of variables declaration//GEN-END:variables
}
