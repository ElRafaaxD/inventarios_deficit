package DAOS;

import Modelos.Deficit;
import java.sql.Connection;
import java.sql.SQLException;

public class DeficitDAO {
    
    public DeficitDAO () {}
    
    public int insertarUsuario(Deficit deficit) {
        String sql = "INSERT INTO escenarios_inventario (nombre, D, S, H, C, con_deficit) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = Conexion.conectar()) {
            Conexion conexion = new Conexion();
            
            int idGenerado = conexion.ejecutarActualizacionRetornarID(conn, sql,
                deficit.getNombre(),
                deficit.getDemanda_anual(),
                deficit.getCosto_por_Pedido(),
                deficit.getCosto_mantenimiento(),
                deficit.getCosto_por_unidad_faltante(),
                deficit.is_deficit()
            );
            
            System.out.println("Deficit insertado con ID: " + idGenerado);
            return idGenerado;
        } catch (SQLException e) {
            Conexion.manejarError(e, "Error al insertar Deficit");
            return -1;
        }
    }
    
}
