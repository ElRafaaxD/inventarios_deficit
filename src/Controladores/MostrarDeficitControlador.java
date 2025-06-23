package Controladores;

import DAOS.DeficitDAO;
import Modelos.DeficitResultado;
import Vistas.MostrarDeficit;
import Vistas.Principal;
import java.util.List;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Controlador encargado de mostrar los déficits registrados en un JTable
 * y permitir la selección de un registro para editarlo.
 * 
 * Autor: miguelLlano (ajustado por Antonio Ruiz)
 */
public class MostrarDeficitControlador {

    private final MostrarDeficit mostrarDeficit;
    private final DeficitDAO deficitDAO;

    public MostrarDeficitControlador(MostrarDeficit mostrarDeficit, DeficitDAO deficitDAO) {
        this.mostrarDeficit = mostrarDeficit;
        this.deficitDAO = deficitDAO;

        mostrarTodosDeficits();  // Cargar la tabla al abrir la vista
        configurarDobleClic();   // Activar doble clic en filas
    }

    private void mostrarTodosDeficits() {
        // Obtener los déficits con sus resultados desde la base de datos
        List<DeficitResultado> deficits = deficitDAO.obtenerTodosDeficitConResultados();

        // Obtener el modelo de la tabla de la vista
        DefaultTableModel modelo = (DefaultTableModel) mostrarDeficit.getTablaDeficit().getModel();
        modelo.setRowCount(0); // Limpiar tabla antes de cargar

        // Cargar los datos en el JTable
        // No mostrar id_resultado, solo id del déficit
        for (DeficitResultado d : deficits) {
            modelo.addRow(new Object[]{
                d.getId(),                          // id del déficit (PK)
                d.getNombre(),
                d.getDemanda_anual(),
                d.getCosto_por_Pedido(),
                d.getCosto_mantenimiento(),
                d.getCosto_por_unidad_faltante(),
                d.getAnio_calculo(),
                d.is_deficit() ? "Sí" : "No",
                d.Q(), d.N(), d.T(), d.CT(), d.S()
            });
        }
    }

    public void seleccionarDeficit() {
        // Este método se invoca cuando se da doble clic sobre una fila del JTable

        // Obtener la fila seleccionada
        JTable tabla = mostrarDeficit.getTablaDeficit();
        int fila = tabla.getSelectedRow();

        if (fila != -1) {
            // Obtener el id del déficit desde la columna 0
            int id_deficit = (int) tabla.getValueAt(fila, 0);

            // Obtener la ventana principal
            Principal padre = (Principal) SwingUtilities.getWindowAncestor(mostrarDeficit);

            // Mandar a llamar la vista para editar el déficit
            padre.iniciarVistaAgregarDeificit(id_deficit);
        }
    }

    private void configurarDobleClic() {
        // Detectar doble clic en una fila del JTable
        mostrarDeficit.getTablaDeficit().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    seleccionarDeficit();
                }
            }
        });
    }
    
    public void cargarDeficitsEnTabla(JTable tabla) {
        
        DeficitDAO dao = new DeficitDAO();
    
        // Obtener los déficits con sus resultados desde la base de datos
        List<DeficitResultado> deficits = dao.obtenerTodosDeficitConResultados();

        // Obtener el modelo de la tabla recibida
        DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
        modelo.setRowCount(0); // Limpiar tabla

        // Cargar los datos al modelo con nombres en el arreglo para claridad
        for (DeficitResultado d : deficits) {
            int idDeficit = d.getId();
            String nombre = d.getNombre();
            float demandaAnual = d.getDemanda_anual();
            float costoPedido = d.getCosto_por_Pedido();
            float costoMantenimiento = d.getCosto_mantenimiento();
            float costoFaltante = d.getCosto_por_unidad_faltante();
            int anioCalculo = d.getAnio_calculo();
            String conDeficit = d.is_deficit() ? "Sí" : "No";
            float Qoptimo = d.Q();
            float numeroPedidos = d.N();
            float tiempo = d.T();
            float costoTotal = d.CT();
            float stockMaximo = d.S();

            modelo.addRow(new Object[]{
                idDeficit,
                nombre,
                demandaAnual,
                costoPedido,
                costoMantenimiento,
                costoFaltante,
                anioCalculo,
                conDeficit,
                Qoptimo,
                numeroPedidos,
                tiempo,
                costoTotal,
                stockMaximo
            });
        }
    }

    public void exportarExcel() {
        //obtener los datos de Jtable y exportarlos a excel :D
        System.out.println("exportando a excel");
    }
}
