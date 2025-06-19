/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controladores;

import DAOS.DeficitDAO;
import Modelos.DeficitResultado;
import Vistas.MostarDeficit;
import Vistas.Principal;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 *
 * @author miguelLlano
 */
public class MostrarDeficitControlador {
    
    private MostarDeficit mostrarDeficit;
    private DeficitDAO deficitDAO;
    
    public MostrarDeficitControlador(
        MostarDeficit mostrarDeficit,
        DeficitDAO deficitDAO) {
        this.mostrarDeficit = mostrarDeficit;
        this.deficitDAO = deficitDAO;
        
        mostrarTodosDeficits();
    }
    
    private void mostrarTodosDeficits() {
        //obtener deficits
        List<DeficitResultado> deficits = 
            deficitDAO.obtenerTodosDeficitConResultados();
        
        //cargar los en un JTable (TOÑiTO) 
        //No cargues el id y id_resultado de la tabla resultados deficit. 
        //solo el id deficit 
        for(DeficitResultado d : deficits)
            System.out.println(d.toString());
    }
    
    public void seleccionarDeficit() {
        //debe de estar al pendiente que deficit de la tabla selecciono (fila)
        
        //cuando de 2 clicks en un fila de JTable debe de mandar a llamar esta 
        //obtener el id de ese deficit del JTable
        int id_deficit = 19;
        //manda a llmar la vista de agregar deficit para editarlo
        Principal padre = (Principal) (JFrame) SwingUtilities.getWindowAncestor(mostrarDeficit);
        padre.iniciarVistaAgregarDeificit(id_deficit);
    }
    
    public void exportarExcel() {
        //obtener los datos de Jtable y exportarlos a excel :D
        System.out.println("exportando a excel");
    }
}
