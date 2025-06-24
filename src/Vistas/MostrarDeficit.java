package Vistas;

import javax.swing.*;
import java.util.List;
import Modelos.DeficitResultado;
import Controladores.MostrarDeficitControlador; // Todavía se usa para cargar datos inicialmente
import DAOS.DeficitDAO; // Puede que ya no necesites importarlo aquí si el controlador se encarga de la carga
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author miguelLlano
 */
public class MostrarDeficit extends PanelPrincipal {

    // Ya no necesitamos esta variable para obtener el ID en el doble clic,
    // pero se mantiene si el controlador sigue siendo responsable de cargar los datos inicialmente.
    private MostrarDeficitControlador mostrarDeficitControlador;

    // Constructor principal
    public MostrarDeficit() {
        initComponents();      // código generado por NetBeans, no modificar
        configurarModeloTabla();  // aquí defines el modelo con columnas
        iniciarMisComponentes();  // inicializas eventos y demás
    }

    // Método nuevo para definir el modelo de la tabla
    private void configurarModeloTabla() {
        String[] columnas = {
            "ID", "Nombre", "Demanda Anual", "Costo Pedido", "Costo Mantenimiento",
            "Costo Faltante", "Año", "Con Déficit", "Q", "N", "T", "CT", "S"
        };
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0);
        Tabla.setModel(modelo);
    }

    // Asigna el controlador desde la clase Principal.
    // Esto se mantiene si el controlador es quien carga los datos inicialmente.
    public void setControllador(MostrarDeficitControlador mostrarDeficitControlador) {
        this.mostrarDeficitControlador = mostrarDeficitControlador;
        // Solo carga los datos aquí si el controlador es quien maneja eso.
        // Si no, esta línea se podría mover a otro lugar o eliminar.
        if (mostrarDeficitControlador != null) {
            mostrarDeficitControlador.cargarDeficitsEnTabla(Tabla);
        } else {
            System.out.println("Advertencia: El controlador no fue asignado a MostrarDeficit para cargar la tabla.");
        }
    }

    // Permite al controlador acceder a la tabla (todavía útil si el controlador carga los datos)
    public JTable getTablaDeficit() {
        return Tabla;
    }

    private void aplicarFiltro() {
        String texto = txtBuscador.getText().trim();
        String filtro = cbbFiltrar.getSelectedItem().toString();

        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(
                (DefaultTableModel) Tabla.getModel()
        );
        Tabla.setRowSorter(sorter);

        if (texto.isEmpty()) {
            sorter.setRowFilter(null); // mostrar todo
            return;
        }

        RowFilter<Object, Object> filtroAplicado;

        switch (filtro) {
            case "Año":
                if (texto.matches("\\d{4}")) {  // Solo acepta años con 4 dígitos
                    filtroAplicado = RowFilter.regexFilter("^" + texto + "$", 6); // Columna 6 = año
                } else {
                    filtroAplicado = RowFilter.regexFilter("^$", 6); // No mostrar nada si no es número
                }
                break;

            case "Tipo Deficit":
                if (texto.equalsIgnoreCase("si") || texto.equalsIgnoreCase("sí") || texto.equalsIgnoreCase("true")) {
                    filtroAplicado = RowFilter.regexFilter("Sí", 7);
                } else if (texto.equalsIgnoreCase("no") || texto.equalsIgnoreCase("false")) {
                    filtroAplicado = RowFilter.regexFilter("No", 7);
                } else {
                    filtroAplicado = RowFilter.regexFilter("^$", 7); // no muestra nada
                }
                break;

            default:
                // Búsqueda global (ignore case)
                filtroAplicado = RowFilter.regexFilter("(?i)" + texto);
                break;
        }

        sorter.setRowFilter(filtroAplicado);
    }

// En Vistas.MostrarDeficit.iniciarMisComponentes()
    // En Vistas.MostrarDeficit
public void iniciarMisComponentes() {
    // Configurar filtro ComboBox y campo búsqueda
    cbbFiltrar.addActionListener(e -> aplicarFiltro());
    txtBuscador.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
        @Override
        public void insertUpdate(javax.swing.event.DocumentEvent e) {
            aplicarFiltro();
        }

        @Override
        public void removeUpdate(javax.swing.event.DocumentEvent e) {
            aplicarFiltro();
        }

        @Override
        public void changedUpdate(javax.swing.event.DocumentEvent e) {
            aplicarFiltro();
        }
    });

    // Listener para detectar CLIC SIMPLE y obtener el ID directamente
    Tabla.addMouseListener(new java.awt.event.MouseAdapter() {
        @Override
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            // No es necesario verificar evt.getClickCount() == 2 para clic simple
            
            JTable tabla = (JTable) evt.getSource();
            int filaVisual = tabla.rowAtPoint(evt.getPoint()); // Obtiene la fila bajo el cursor

            if (filaVisual != -1) { // Si se hizo clic en una fila válida
                int filaModelo = tabla.convertRowIndexToModel(filaVisual);
                Object idObject = tabla.getModel().getValueAt(filaModelo, 0);

                if (idObject instanceof Integer) {
                    int id_deficit = (int) idObject;
                    
                    // Llama a la ventana Principal para iniciar la vista de agregar/editar
                    Principal padre = (Principal) SwingUtilities.getWindowAncestor(Tabla);
                    if (padre != null) {
                        padre.iniciarVistaAgregarDeificit(id_deficit);
                    } 
                    // Ya no imprimimos errores en consola para una aplicación final.
                    // Si necesitas depurar de nuevo, puedes volver a añadir los System.out.println
                } 
            } 
        }
    });
}

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        Tabla = new javax.swing.JTable();
        txtBuscador = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        cbbFiltrar = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        btnExportarExcel = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();

        Tabla.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(Tabla);

        txtBuscador.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N

        jLabel1.setFont(new java.awt.Font("Arial", 0, 20)); // NOI18N
        jLabel1.setText("Buscar");

        cbbFiltrar.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        cbbFiltrar.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Busqueda Global", "Año", "Tipo Deficit" }));
        cbbFiltrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbbFiltrarActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Arial", 0, 20)); // NOI18N
        jLabel2.setText("Filtrar por");

        btnExportarExcel.setBackground(new java.awt.Color(51, 204, 0));
        btnExportarExcel.setFont(new java.awt.Font("Arial", 1, 20)); // NOI18N
        btnExportarExcel.setForeground(new java.awt.Color(255, 255, 255));
        btnExportarExcel.setText("Exportar Excel");

        jLabel3.setFont(new java.awt.Font("Arial Black", 1, 48)); // NOI18N
        jLabel3.setText("Mostrar Deficit");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 988, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
                            .addComponent(txtBuscador))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(cbbFiltrar, 0, 250, Short.MAX_VALUE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnExportarExcel)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnExportarExcel, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 53, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtBuscador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbbFiltrar, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 360, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(64, 64, 64))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void cbbFiltrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbFiltrarActionPerformed
        aplicarFiltro();
    }//GEN-LAST:event_cbbFiltrarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable Tabla;
    private javax.swing.JButton btnExportarExcel;
    private javax.swing.JComboBox<String> cbbFiltrar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField txtBuscador;
    // End of variables declaration//GEN-END:variables
}
