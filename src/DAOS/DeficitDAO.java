package DAOS;

import Modelos.Deficit;
import Modelos.DeficitResultado;

import java.util.List;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;

public class DeficitDAO {

    public DeficitDAO() {
    }

    public int insertarDeficit(Deficit deficit) {
        String sqlDeficit = "INSERT INTO deficit ("
                + "nombre, D, S, H, C, anio_calculo, con_deficit"
                + ") VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = Conexion.conectar()) {
            Conexion conexion = new Conexion();
            // C y S solo cuando hay deficit
            int idDeficit = conexion.ejecutarActualizacionRetornarID(conn, sqlDeficit,
                    deficit.getNombre(),
                    deficit.getDemanda_anual(),
                    deficit.getCosto_por_Pedido(),
                    deficit.getCosto_mantenimiento(),
                    deficit.getCosto_por_unidad_faltante(),
                    deficit.getAnio_calculo(),
                    deficit.is_deficit());

            // insertar resultados calculados
            String sqlResultado = "INSERT INTO resultados_deficit ("
                    + "resultado_id, Q_optimo, N_pedidos, T_tiempo, CT_total, S_maximo"
                    + ") VALUES (?, ?, ?, ?, ?, ?)";

            int idResultado = conexion.ejecutarActualizacionRetornarID(conn, sqlResultado,
                    idDeficit,
                    deficit.Q(),
                    deficit.N(),
                    deficit.T(),
                    deficit.CT(),
                    deficit.S());

            return idResultado;
        } catch (SQLException e) {
            Conexion.manejarError(e, "Error al insertar Deficit");
            return -1;
        }
    }

    public List<DeficitResultado> obtenerTodosDeficitConResultados() {
        String sqlDeficit = "SELECT "
                + "deficit.id AS deficit_id, "
                + "deficit.nombre, "
                + "deficit.D, "
                + "deficit.S, "
                + "deficit.H, "
                + "deficit.C, "
                + "deficit.anio_calculo, "
                + "deficit.con_deficit, "
                + "resultados_deficit.id AS resultado_id, "
                + "resultados_deficit.Q_optimo, "
                + "resultados_deficit.N_pedidos, "
                + "resultados_deficit.T_tiempo, "
                + "resultados_deficit.CT_total, "
                + "resultados_deficit.S_maximo "
                + "FROM resultados_deficit "
                + "INNER JOIN deficit ON resultados_deficit.resultado_id = deficit.id";

        try (Connection conn = Conexion.conectar()) {
            Conexion conexion = new Conexion();

            ResultSet result_deficit = conexion.ejecutarConsulta(
                    conn, sqlDeficit);
            // almacenar los deficits
            List<DeficitResultado> deficits = new ArrayList<>();
            ;
            // obtener lso deficits de la consulta
            while (result_deficit.next()) {
                // Almacenar el deficit Actual
                DeficitResultado d = new DeficitResultado(
                        result_deficit.getInt("deficit_id"),
                        result_deficit.getString("nombre"),
                        result_deficit.getFloat("D"),
                        result_deficit.getFloat("S"),
                        result_deficit.getFloat("H"),
                        result_deficit.getFloat("C"),
                        result_deficit.getInt("anio_calculo"),
                        result_deficit.getBoolean("con_deficit"),
                        result_deficit.getInt("resultado_id"),
                        result_deficit.getFloat("Q_optimo"),
                        result_deficit.getFloat("CT_total"),
                        result_deficit.getFloat("S_maximo"),
                        result_deficit.getFloat("N_pedidos"),
                        result_deficit.getFloat("T_tiempo"));

                // almacenarlo en la lista
                deficits.add(d);
            }

            return !deficits.isEmpty() ? deficits : Collections.emptyList();

        } catch (SQLException e) {
            Conexion.manejarError(
                    e,
                    "Error al obtener los Deficits con los resultados");
            return Collections.emptyList();
        }
    }

    public DeficitResultado obtenerDeficitID(int id) {
        String sqlDeficit = "SELECT "
                + "deficit.id AS deficit_id, "
                + "deficit.nombre, "
                + "deficit.D, "
                + "deficit.S, "
                + "deficit.H, "
                + "deficit.C, "
                + "deficit.anio_calculo, "
                + "deficit.con_deficit, "
                + "resultados_deficit.id AS resultado_id, "
                + "resultados_deficit.Q_optimo, "
                + "resultados_deficit.N_pedidos, "
                + "resultados_deficit.T_tiempo, "
                + "resultados_deficit.CT_total, "
                + "resultados_deficit.S_maximo "
                + "FROM resultados_deficit "
                + "INNER JOIN deficit ON resultados_deficit.resultado_id = deficit.id "
                + "WHERE deficit.id = ?";

        try (Connection conn = Conexion.conectar()) {
            Conexion conexion = new Conexion();
            // C y S solo cuando hay deficit
            ResultSet result_deficit = conexion.ejecutarConsulta(conn, sqlDeficit,
                    id);

            // SI devuelve al menos un resultado, manda el primero en encontrar
            return result_deficit.next() ? new DeficitResultado(
                    result_deficit.getInt("deficit_id"),
                    result_deficit.getString("nombre"),
                    result_deficit.getFloat("D"),
                    result_deficit.getFloat("S"),
                    result_deficit.getFloat("H"),
                    result_deficit.getFloat("C"),
                    result_deficit.getInt("anio_calculo"),
                    result_deficit.getBoolean("con_deficit"),
                    result_deficit.getInt("resultado_id"),
                    result_deficit.getFloat("Q_optimo"),
                    result_deficit.getFloat("CT_total"),
                    result_deficit.getFloat("S_maximo"),
                    result_deficit.getFloat("N_pedidos"),
                    result_deficit.getFloat("T_tiempo"))
                    : null;

        } catch (SQLException e) {
            Conexion.manejarError(e, "Error al obtener Deficit por id");
            return null;
        }
    }

    public List<Deficit> obtenerCoincidenciaDeficit(String busqueda) {
        return null;
    }

    public List<Deficit> obtenerPorAnioDeficit(int anio) {
        return null;
    }

    /*
     * Obtener con deficit o sin deficit
     */
    public List<Deficit> obtenerTipoficit(boolean deficit) {
        return null;
    }

    /**
     * eliminar deficit por id
     * 
     * @param id_deficit
     * @return
     **/
    public boolean eliminarDeficit(int id_deficit) {
        String sqlEliminarResultados = "DELETE FROM resultados_deficit WHERE resultado_id = ?";
        String sqlEliminarDeficit = "DELETE FROM deficit WHERE id = ?";

        try (Connection conn = Conexion.conectar()) {
            Conexion conexion = new Conexion();

            // Eliminar primero los resultados
            int filasResultados = conexion.ejecutarActualizacion(conn, sqlEliminarResultados, id_deficit);

            // Luego eliminar el déficit
            int filasDeficit = conexion.ejecutarActualizacion(conn, sqlEliminarDeficit, id_deficit);

            // Si se elimino al menos una fila, devuelve true
            return (filasResultados > 0 || filasDeficit > 0);

        } catch (SQLException e) {
            System.out.println(e + "error en deficit");
            Conexion.manejarError(e, "Error al eliminar el Deficit");
            return false;
        }
    }

    /**
     * eliminar deficit por id
     * 
     * @param id_deficit
     * @param nuevoDeficit
     * @return
     **/
    public boolean editarDeficit(int id_deficit, Deficit nuevoDeficit) {
        String sqlDeficitResultado = "UPDATE deficit " 
            + "INNER JOIN resultados_deficit " 
            + "ON deficit.id = resultados_deficit.resultado_id " 
            + "SET " 
            + "deficit.nombre = ?, " 
            + "deficit.D = ?, " 
            + "deficit.S = ?, " 
            + "deficit.H = ?, " 
            + "deficit.C = ?, " 
            + "deficit.anio_calculo = ?, " 
            + "deficit.con_deficit = ?, " 
            + "resultados_deficit.Q_optimo = ?, " 
            + "resultados_deficit.N_pedidos = ?, " 
            + "resultados_deficit.T_tiempo = ?, " 
            + "resultados_deficit.CT_total = ?, " 
            + "resultados_deficit.S_maximo = ?, " 
            + "resultados_deficit.fecha_calculo = NOW() " 
            + "WHERE deficit.id = ?;";

        try (Connection conn = Conexion.conectar()) {
            Conexion conexion = new Conexion();

            // Editar primero los resultados
            int filasResultados = conexion.ejecutarActualizacion(conn, sqlDeficitResultado, 
                    nuevoDeficit.getNombre(),
                    nuevoDeficit.getDemanda_anual(),
                    nuevoDeficit.getCosto_por_Pedido(),
                    nuevoDeficit.getCosto_mantenimiento(),
                    nuevoDeficit.getCosto_por_unidad_faltante(),
                    nuevoDeficit.getAnio_calculo(),
                    nuevoDeficit.is_deficit(),
                    nuevoDeficit.Q(),
                    nuevoDeficit.N(),
                    nuevoDeficit.T(),
                    nuevoDeficit.CT(),
                    nuevoDeficit.S(),
                    id_deficit
            );


            // Si se elimino al menos una fila, devuelve true
            return filasResultados > 0;

        } catch (SQLException e) {
            System.out.println(e + "error en deficit");
            Conexion.manejarError(e, "Error al editar el Deficit");
            return false;
        }
    }
}
