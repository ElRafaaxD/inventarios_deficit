package Controladores;

import DAOS.DeficitDAO;
import Modelos.DeficitResultado;
import Vistas.MostrarDeficit;
import Vistas.Principal;
import java.util.List;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

public class MostrarDeficitControlador {

    private final MostrarDeficit mostrarDeficit;
    private final DeficitDAO deficitDAO;

    public MostrarDeficitControlador(MostrarDeficit mostrarDeficit, DeficitDAO deficitDAO) {
        this.mostrarDeficit = mostrarDeficit;
        this.deficitDAO = deficitDAO;
        mostrarTodosDeficits(); // Cargar datos al inicializar
    }

    private void mostrarTodosDeficits() {
        List<DeficitResultado> deficits = deficitDAO.obtenerTodosDeficitConResultados();

        DefaultTableModel modelo = (DefaultTableModel) mostrarDeficit.getTablaDeficit().getModel();
        modelo.setRowCount(0); // limpiar tabla

        System.out.println("DAO ha obtenido " + (deficits != null ? deficits.size() : 0) + " déficits.");

        if (deficits != null) {
            for (DeficitResultado d : deficits) {
                modelo.addRow(new Object[]{
                    d.getId_resultado(),                    // ID del resultado, no del déficit
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
    }

    public void seleccionarDeficit() {
        JTable tabla = mostrarDeficit.getTablaDeficit();
        int filaVisual = tabla.getSelectedRow();

        System.out.println("seleccionarDeficit() llamado.");
        System.out.println("Fila visual seleccionada: " + filaVisual);

        if (filaVisual != -1) {
            int filaModelo = tabla.convertRowIndexToModel(filaVisual);
            Object idObject = tabla.getModel().getValueAt(filaModelo, 0);

            // Depuración del tipo de dato
            System.out.println("Valor en columna 0: " + idObject + ", tipo: " +
                (idObject != null ? idObject.getClass().getName() : "null"));

            try {
                int id_deficit = Integer.parseInt(idObject.toString());
                System.out.println("ID del déficit seleccionado (desde la vista): " + id_deficit);

                Principal padre = (Principal) SwingUtilities.getWindowAncestor(mostrarDeficit);
                if (padre != null) {
                    padre.iniciarVistaAgregarDeificit(id_deficit);
                } else {
                    System.out.println("Error: No se pudo obtener la ventana Principal.");
                }
            } catch (NumberFormatException ex) {
                System.out.println("Error: El valor de la columna 0 no es un número válido. Valor: " + idObject);
            }
        } else {
            System.out.println("No hay ninguna fila seleccionada en la tabla.");
        }
    }

    public void cargarDeficitsEnTabla(JTable tabla) {
        List<DeficitResultado> deficits = deficitDAO.obtenerTodosDeficitConResultados();

        DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
        modelo.setRowCount(0); // limpiar tabla

        if (deficits != null) {
            for (DeficitResultado d : deficits) {
                modelo.addRow(new Object[]{
                    d.getId_resultado(),
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
    }

    public void exportarExcel() {
        System.out.println("Exportando a Excel");
    }
}
