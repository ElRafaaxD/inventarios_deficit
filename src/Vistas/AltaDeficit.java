package Vistas;

import Controladores.AltaDeficitControlador;
import Modelos.Deficit;
import Modelos.DeficitResultado;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.Locale;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.text.NumberFormatter;
import utils.UISwing;

public class AltaDeficit extends PanelPrincipal {

    AltaDeficitControlador controlador;

    public AltaDeficit() {
        initComponents();
        iniciarMisComponentes();
    }

    public void setControllador(AltaDeficitControlador controlador) {
        this.controlador = controlador;
    }
    
    /*
    * Eventos del formulario
    */
    private void iniciarEventos() {
        rbConDeficit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (rbConDeficit.isSelected()) {
                    //activar la unidad faltate
                    ftxtCostoPorUnidadFaltante.setEnabled(true);
                    jLabel8.setEnabled(true);
                    lblTituloS.setEnabled(true);
                    lblS.setEnabled(true);
                }
            }
        });

        rbSinDeficit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (rbSinDeficit.isSelected()) {
                    //desactivar la unidad faltate
                    ftxtCostoPorUnidadFaltante.setEnabled(false);
                    jLabel8.setEnabled(false);
                    lblTituloS.setEnabled(false);
                    lblS.setEnabled(false);
                }
            }
        });
    }

    /*
    * Iniciar los componentes del formulario
    */
    private void iniciarMisComponentes() {
        //añadir los radio buttons en grupo
        btnGpDeficit.add(rbConDeficit);
        btnGpDeficit.add(rbSinDeficit);
        
        spnAnioRegistro.setValue(2025);
        
        //iniciar eventos
        iniciarEventos();
    }
    
    /*
    * obtener el deficit del formulario
    */
    public Deficit obtenerDeficitFormulario() {
        try {
            String nombre = txtNombreProducto.getText().trim();
            float demandaAnual = Float.parseFloat(ftxtDemandaAnual.getText());
            float costoPorPedido = Float.parseFloat(ftxtCostoPorPedido.getText());
            float costoMantenimiento = Float.parseFloat(ftxtCostoMantenimiento.getText());
            boolean conDeficit = rbConDeficit.isSelected();
            float costoPorUnidadFaltante = (conDeficit)
            ? Float.parseFloat(ftxtCostoPorUnidadFaltante.getText()) : -1;
            int anioRegistro = (int) spnAnioRegistro.getValue();

            // Si está seleccionado sin deficit, el costo por unidad faltante debe ser 0
            if (!conDeficit) costoPorUnidadFaltante = 0;

            return new Deficit(
                nombre,
                demandaAnual,
                costoPorPedido,
                costoMantenimiento,
                costoPorUnidadFaltante,
                anioRegistro,
                conDeficit
            );
        } catch (NumberFormatException ex) {
            System.out.println("Error: " + ex.getMessage());
            return null;
        }
    }
    
    /*
    * calcular resultados CO y mostrarlo
    */
    public void mostrarCO(Deficit deficit) {
        lblQ.setText(String.valueOf(deficit.Q()));
        lblN.setText(String.valueOf(deficit.N()));
        lblT.setText(String.valueOf(deficit.T()));
        lblCT.setText(String.valueOf(deficit.CT())); //Costo Total Optimo
        //Si es con deficit
        lblS.setText(
            deficit.is_deficit()? String.valueOf(deficit.S()) : ""
        );
    }
    
    /*
    * asignar deficit al formulario (para editar o eliminar)
    */
    public void setDeficitDatosFormulario(DeficitResultado deficit) {
        txtNombreProducto.setText(deficit.getNombre());
        ftxtDemandaAnual.setText(
            String.valueOf(deficit.getDemanda_anual())
        );
        ftxtCostoPorPedido.setText(
            String.valueOf(deficit.getCosto_por_Pedido())
        );
        ftxtCostoMantenimiento.setText(
            String.valueOf(deficit.getCosto_mantenimiento())
        );
        
        if(deficit.is_deficit()) {
            rbConDeficit.setSelected(true);
            ftxtCostoPorUnidadFaltante.setEnabled(true);
            jLabel8.setEnabled(true);
            lblTituloS.setEnabled(true);
            lblS.setEnabled(true);
        }
        else{
            rbSinDeficit.setSelected(true);
            ftxtCostoPorUnidadFaltante.setEnabled(false);
            jLabel8.setEnabled(false);
            lblTituloS.setEnabled(false);
            lblS.setEnabled(false);
        }
        
        ftxtCostoPorUnidadFaltante.setText(
            String.valueOf(deficit.getCosto_por_unidad_faltante())
        );
        spnAnioRegistro.setValue(deficit.getAnio_calculo());
    }
    
    /*
    * asignar deficit al formulario (los resultados)
    */
    public void setDeficitReusltadosFormulario(DeficitResultado deficit) {
        lblQ.setText(String.valueOf(deficit.getQ()));
        lblN.setText(String.valueOf(deficit.getN()));
        lblT.setText(String.valueOf(deficit.getT()));
        lblCT.setText(String.valueOf(deficit.getCT()));
        lblS.setText(String.valueOf(deficit.getS()));
    }
    
    /*
    * asignar todos los datos de deficit
    */
    public void setDeficitEditarFormulario(DeficitResultado deficit) {
        setDeficitDatosFormulario(deficit);
        setDeficitReusltadosFormulario(deficit);
        //nuevas funciones para editar y eliminar
        funcionesEditarFormulario();
    }
    
    /*
    * funcion para la vista editar y eliminar el deficit
    */
    public void funcionesEditarFormulario() {
        UISwing.cambiarColorFondoyTexto(
            btnDarAlta, 
            Color.BLUE, 
            "Editar"
        );
        UISwing.cambiarTexto(
            btnCancelar,
            "Eliminar"
        );
        
        //este boton sera para editar
        btnDarAlta.addActionListener(e ->
            controlador.editarDeficit(obtenerDeficitFormulario())
        );
        
        //este boton sera para eliminar el deficit actual
        btnCancelar.addActionListener(e -> {
            controlador.eliminarDeficit();
        });
    }
    
     /*
    * funcion para la vista agregar y cancelar (limpiar) formulario
    */
    public void funcionesAgregarFormulario() {
        UISwing.cambiarColorFondoyTexto(
            btnDarAlta, 
            Color.GREEN, 
            "Dar Alta"
        );
        UISwing.cambiarTexto(
            btnCancelar,
            "Cancelar"
        );
        
        //este boton sera para editar
        btnDarAlta.addActionListener(e -> controlador.darAlta(
            obtenerDeficitFormulario()
        ));
        
        //este boton sera para cancelar el deficit actual (limpiar)
        btnCancelar.addActionListener(e -> limpiarDeficitResultadosDeficit());
    }
    
    /*
    * funcion para eliminar los datos del formulario
    */
    public void limpiarFormularioDeficit() {
        txtNombreProducto.setText("");
        ftxtDemandaAnual.setText("");
        ftxtCostoPorPedido.setText("");
        ftxtCostoMantenimiento.setText("");
        rbConDeficit.setSelected(true);
        ftxtCostoPorUnidadFaltante.setText("");
        spnAnioRegistro.setValue(2025);
    }
    
    /*
    * funcion para eliminar los resultados del formulario (CO)
    */
    public void limpiarResultadoDeficit() {
        lblQ.setText("");
        lblN.setText("");
        lblT.setText("");
        lblCT.setText("");
        lblS.setText("");
    }
    
    /*
    * funcion para eliminar los resultados del formulario
    */
    public void limpiarDeficitResultadosDeficit() {
        limpiarFormularioDeficit();
        limpiarResultadoDeficit();
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnGpDeficit = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        rbConDeficit = new javax.swing.JRadioButton();
        rbSinDeficit = new javax.swing.JRadioButton();
        btnCancelar = new javax.swing.JButton();
        ftxtDemandaAnual = new javax.swing.JFormattedTextField();
        ftxtCostoPorPedido = new javax.swing.JFormattedTextField();
        ftxtCostoMantenimiento = new javax.swing.JFormattedTextField();
        ftxtCostoPorUnidadFaltante = new javax.swing.JFormattedTextField();
        jLabel8 = new javax.swing.JLabel();
        SpinnerNumberModel modeloN = new SpinnerNumberModel(1985, 0, 9999, 1);
        spnAnioRegistro = new javax.swing.JSpinner(modeloN);
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        lblQ = new javax.swing.JLabel();
        lblN = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        lblT = new javax.swing.JLabel();
        lblCT = new javax.swing.JLabel();
        lblTituloS = new javax.swing.JLabel();
        lblS = new javax.swing.JLabel();
        txtNombreProducto = new javax.swing.JFormattedTextField();
        btnDarAlta = new javax.swing.JButton();

        jLabel1.setFont(new java.awt.Font("Arial Black", 1, 48)); // NOI18N
        jLabel1.setText("Alta de Deficit");

        jLabel2.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jLabel2.setText("Nombre Producto");

        jLabel3.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jLabel3.setText("Demanda anual");

        jLabel4.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jLabel4.setText("Costo por pedido");

        jLabel5.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jLabel5.setText("Costo de mantenimiento por u/a");

        jLabel6.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jLabel6.setText("Tipo de Deficit");

        jLabel7.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jLabel7.setText("Año de deficit");

        rbConDeficit.setBackground(new java.awt.Color(255, 255, 255));
        rbConDeficit.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        rbConDeficit.setSelected(true);
        rbConDeficit.setText("Con");
        rbConDeficit.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        rbConDeficit.setIconTextGap(10);
        rbConDeficit.setMargin(new java.awt.Insets(5, 6, 5, 6));

        rbSinDeficit.setBackground(new java.awt.Color(255, 255, 255));
        rbSinDeficit.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        rbSinDeficit.setText("Sin");
        rbSinDeficit.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        rbSinDeficit.setMargin(new java.awt.Insets(5, 6, 5, 6));

        btnCancelar.setBackground(new java.awt.Color(153, 0, 0));
        btnCancelar.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        btnCancelar.setForeground(new java.awt.Color(255, 255, 255));
        btnCancelar.setText("Cancelar");
        btnCancelar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        ftxtDemandaAnual.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        ftxtDemandaAnual.setMargin(new java.awt.Insets(5, 6, 5, 6));

        ftxtCostoPorPedido.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        ftxtCostoPorPedido.setMargin(new java.awt.Insets(5, 6, 5, 6));

        ftxtCostoMantenimiento.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        ftxtCostoMantenimiento.setMargin(new java.awt.Insets(5, 6, 5, 6));

        ftxtCostoPorUnidadFaltante.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        ftxtCostoPorUnidadFaltante.setMargin(new java.awt.Insets(5, 6, 5, 6));

        jLabel8.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jLabel8.setText("Costo por unidad faltante");

        spnAnioRegistro.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        spnAnioRegistro.setToolTipText("");
        spnAnioRegistro.setMinimumSize(new java.awt.Dimension(64, 45));
        spnAnioRegistro.setPreferredSize(new java.awt.Dimension(64, 38));
        spnAnioRegistro.setValue(0);

        jLabel9.setFont(new java.awt.Font("Arial", 0, 30)); // NOI18N
        jLabel9.setText("Datos a ingresar");

        jLabel10.setFont(new java.awt.Font("Arial", 0, 30)); // NOI18N
        jLabel10.setText("Calculo");

        jLabel11.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jLabel11.setText("Q");

        jLabel12.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jLabel12.setText("N");

        lblQ.setBackground(new java.awt.Color(255, 255, 255));
        lblQ.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        lblQ.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        lblN.setBackground(new java.awt.Color(255, 255, 255));
        lblN.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        lblN.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jLabel15.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jLabel15.setText("T");

        jLabel16.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jLabel16.setText("CT");

        lblT.setBackground(new java.awt.Color(255, 255, 255));
        lblT.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        lblT.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        lblCT.setBackground(new java.awt.Color(255, 255, 255));
        lblCT.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        lblCT.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        lblTituloS.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        lblTituloS.setText("S");

        lblS.setBackground(new java.awt.Color(255, 255, 255));
        lblS.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        lblS.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        txtNombreProducto.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        txtNombreProducto.setMargin(new java.awt.Insets(5, 6, 5, 6));

        btnDarAlta.setBackground(new java.awt.Color(0, 153, 0));
        btnDarAlta.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        btnDarAlta.setForeground(new java.awt.Color(255, 255, 255));
        btnDarAlta.setText("Calcular CO");
        btnDarAlta.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                        .addComponent(ftxtCostoMantenimiento, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                        .addComponent(ftxtDemandaAnual, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(ftxtCostoPorUnidadFaltante)
                                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(txtNombreProducto, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(rbConDeficit, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(rbSinDeficit, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ftxtCostoPorPedido, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(spnAnioRegistro, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(26, 26, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel10)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(lblT, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lblTituloS, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lblS, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGap(18, 18, 18)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(lblCT, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGroup(layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lblQ, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGap(18, 18, 18)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(lblN, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(btnDarAlta, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addContainerGap(22, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addContainerGap(771, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 29, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jLabel10))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(183, 183, 183)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ftxtCostoMantenimiento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(ftxtCostoPorUnidadFaltante, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel11)
                                    .addComponent(jLabel12))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblN, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(47, 47, 47)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel15)
                                    .addComponent(jLabel16))
                                .addGap(6, 6, 6)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblT, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblCT, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(lblQ, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(140, 140, 140)
                                    .addComponent(lblTituloS)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(lblS, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                    .addComponent(jLabel6)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(rbConDeficit)
                                        .addComponent(rbSinDeficit)
                                        .addComponent(txtNombreProducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGap(48, 48, 48)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel4)
                                        .addComponent(jLabel3))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(ftxtCostoPorPedido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(ftxtDemandaAnual, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGap(18, 18, 18)
                                    .addComponent(jLabel7)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(spnAnioRegistro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(49, 49, 49)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnDarAlta, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(68, 68, 68))
        );

        JSpinner.NumberEditor editor = new JSpinner.NumberEditor(spnAnioRegistro, "#");
        spnAnioRegistro.setEditor(editor);
        NumberFormat formato3 = NumberFormat.getNumberInstance(Locale.US);
        formato3.setGroupingUsed(false);

        NumberFormatter formatter3 = new NumberFormatter(formato3);
        formatter3.setValueClass(Double.class);
        formatter3.setMinimum(0.0);
        formatter3.setAllowsInvalid(true);
        formatter3.setCommitsOnValidEdit(true);

        ftxtDemandaAnual.setFormatterFactory(
            new javax.swing.text.DefaultFormatterFactory(formatter3)
        );

        ftxtDemandaAnual.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                if (ftxtDemandaAnual.getText().trim().isEmpty()) {
                    ftxtDemandaAnual.setValue(0.0);
                }
            }
        });
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnDarAlta;
    private javax.swing.ButtonGroup btnGpDeficit;
    private javax.swing.JFormattedTextField ftxtCostoMantenimiento;
    private javax.swing.JFormattedTextField ftxtCostoPorPedido;
    private javax.swing.JFormattedTextField ftxtCostoPorUnidadFaltante;
    private javax.swing.JFormattedTextField ftxtDemandaAnual;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel lblCT;
    private javax.swing.JLabel lblN;
    private javax.swing.JLabel lblQ;
    private javax.swing.JLabel lblS;
    private javax.swing.JLabel lblT;
    private javax.swing.JLabel lblTituloS;
    private javax.swing.JRadioButton rbConDeficit;
    private javax.swing.JRadioButton rbSinDeficit;
    private javax.swing.JSpinner spnAnioRegistro;
    private javax.swing.JFormattedTextField txtNombreProducto;
    // End of variables declaration//GEN-END:variables
}
