package Controladores;

import DAOS.DeficitDAO;
import Modelos.Deficit;
import Modelos.DeficitResultado;
import Vistas.AltaDeficit;
import Vistas.Principal;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class AltaDeficitControlador {
    private AltaDeficit altaDeficit;           //vista para deficit (CRUD)
    private DeficitDAO deficitDAO;             //conectar a la tabla deficit
    private DeficitResultado deficitResultado; //deficit para editar o eliminar
    
    //iniciar controlador
    public AltaDeficitControlador (
        AltaDeficit altaDeficit,
        DeficitDAO deficitDAO
    ) {
        this.altaDeficit = altaDeficit;
        this.deficitDAO = deficitDAO;
        this.altaDeficit.funcionesAgregarFormulario();
    }
    
    //iniciar controlador
    public AltaDeficitControlador (
        AltaDeficit altaDeficit,
        DeficitDAO deficitDAO,
        int id_deficit
    ) {
        this.altaDeficit = altaDeficit;
        this.deficitDAO = deficitDAO;
        cargarDeficitFormulario(id_deficit); //cargar el deficit a mostrar
    }
    
    public void darAlta(Deficit deficit) {
        //si el deficit enviado esta vacio
        if(deficit == null) {
            JOptionPane.showMessageDialog(
                altaDeficit, 
                "Error: Deficit esta vacio, llena los datos por favor"
            );
            return;
        }
        
        //calcular deficit
        altaDeficit.mostrarCO(deficit);
        
        //obtenemos el id (Si lo necesitamos) y lo guardamos
        int idDeficit = deficitDAO.insertarDeficit(deficit);
        
        //si el deficit tuvo un error al registrarse en la db
        if(idDeficit == -1) {
            JOptionPane.showMessageDialog(
                altaDeficit, 
                "Error: No se registro deficit, intenta de nuevo."
            );
            return;
        }
        
        //Si todo salio bien
        JOptionPane.showMessageDialog(
            altaDeficit, 
            "Deficit agregado exitosamente."
        );
    }
    
    public void cargarDeficitFormulario(int id_deficit) {
        DeficitResultado deficit = deficitDAO.obtenerDeficitID(id_deficit);
        
        //el deficit esta vacio
        if (deficit == null) {
            JOptionPane.showMessageDialog(
                altaDeficit, 
                "Ups.. No se encontro deficit seleciconado, Intente de nuevo", 
                "Error al cargar Deficit",
                JOptionPane.ERROR_MESSAGE
            );
            //agregar nueva deficit
            altaDeficit.funcionesAgregarFormulario();
            return;
        }
        //obtener el deficit actual para editar o eliminar el actual
        deficitResultado = deficit;
        //enviar el deficit para editar o eliminarlo
        altaDeficit.setDeficitEditarFormulario(deficit);
    }
    
    public void eliminarDeficit() {
        int id_eliminar = deficitResultado.getId(); //obtener id deficit actual
        
        int opcion = JOptionPane.showConfirmDialog(
            altaDeficit, 
            "¿Deseas eliminar Deficit '" + deficitResultado.getNombre() +"'?", 
            "Selecciona una opcion",
            JOptionPane.YES_NO_OPTION, 
            JOptionPane.QUESTION_MESSAGE
        );
        
        if(!(opcion == JOptionPane.YES_OPTION)) return;
        
        boolean eliminado = deficitDAO.eliminarDeficit(id_eliminar);

        if(!eliminado)
            JOptionPane.showMessageDialog(
                altaDeficit,
                "Ups... Hubo un error al eliminar el deficit '"+ deficitResultado.getNombre() +"'",
                "Error al eliminar id actual", 
                JOptionPane.ERROR_MESSAGE
            );
        else 
            JOptionPane.showMessageDialog(
                altaDeficit,
                "Se elimino '" + deficitResultado.getNombre() +"' exitosamente.",
                "Elimado Exitosamente", 
                JOptionPane.INFORMATION_MESSAGE
            );
        
        //cambiar de ventana y cerrar la actual
        Principal padre = (Principal) (JFrame) SwingUtilities.getWindowAncestor(altaDeficit);
        padre.iniciarVistaMostrarDeificit();
    }   
    
    public void editarDeficit(Deficit nuevoDeficit) {
        int id_editar = deficitResultado.getId(); //obtener id deficit actual
        
        int opcion = JOptionPane.showConfirmDialog(
            altaDeficit, 
            "¿Deseas guardar cambios Deficit?", 
            "Selecciona una opcion",
            JOptionPane.YES_NO_OPTION, 
            JOptionPane.QUESTION_MESSAGE
        );
        
        if(!(opcion == JOptionPane.YES_OPTION)) return;
        
        boolean editado = deficitDAO.editarDeficit(
            id_editar, 
            nuevoDeficit
        );

        if(!editado)
            JOptionPane.showMessageDialog(
                altaDeficit,
                "Ups... Hubo un error al editar el deficit '"+ deficitResultado.getNombre() +"'",
                "Error al guardar id actual", 
                JOptionPane.ERROR_MESSAGE
            );
        else 
            JOptionPane.showMessageDialog(
                altaDeficit,
                "Se edito '" + deficitResultado.getNombre() +"' exitosamente.",
                "guardado Exitosamente", 
                JOptionPane.INFORMATION_MESSAGE
            );
        
        //cambiar de ventana y cerrar la actual
        Principal padre = (Principal) (JFrame) SwingUtilities.getWindowAncestor(altaDeficit);
        padre.iniciarVistaMostrarDeificit();
    }
    
    public void limpiarForm() {
        //aqui la logica para limpiar el formulario de deficit
    }
}
