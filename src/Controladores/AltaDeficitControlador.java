package Controladores;

import DAOS.DeficitDAO;
import Modelos.Deficit;
import Vistas.AltaDeficit;
import javax.swing.JOptionPane;

public class AltaDeficitControlador {
    private AltaDeficit altaDeficit;
    private DeficitDAO deficitDAO;
    
    //iniciar controlador
    public AltaDeficitControlador (
        AltaDeficit altaDeficit,
        DeficitDAO deficitDAO
    ) {
        this.altaDeficit = altaDeficit;
        this.deficitDAO = deficitDAO;
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
        
        //obtenemos el id (Si lo necesitamos) y lo guardamos
        int idDeficit = deficitDAO.insertarUsuario(deficit);
        
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
    
    public void limpiarForm() {
        //aqui la logica para limpiar el formulario de deficit
    }
}
