/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOS;

import java.sql.*;

/**
 *
 * @author miguelLlano
 */
public class Conexion {
    // Configuracion de MySQL
    private static final String URL = "jdbc:mysql://localhost:3306/inventario";
    private static final String USUARIO = "root";
    private static final String CONTRASENA = "123456789";

    
    // Obtener Conexion
    public static Connection conectar() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");  // Carga driver
        } catch (ClassNotFoundException e) {
            System.err.println("Error: driver MySQL no encontrado.");
            e.printStackTrace();
        }
        
        try {
            Connection conn = DriverManager.getConnection(URL, USUARIO, CONTRASENA);
            System.out.println("Conectado a MySQL exitosamente.");
            return conn;
        } catch (SQLException e) {
            System.err.println("Error al conectar a MySQL: " + e.getMessage());
            throw e;
        }
    }

    // Crear tabla si no existe (muy util)
    public void crearTablaSiNoExiste(Connection conn, String nombreTabla, String estructuraTabla) throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS " + nombreTabla + " " + estructuraTabla;
        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            System.err.println("Error al crear la tabla: " + nombreTabla);
            throw e;
        }
    }

    // Ejecutar SELECT
    public ResultSet ejecutarConsulta(Connection conn, String sql, Object... params) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement(sql);
        for (int i = 0; i < params.length; i++)
            stmt.setObject(i + 1, params[i]);
        return stmt.executeQuery();
    }

    public ResultSet ejecutarConsulta(Connection conn, String sql) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement(sql);
        return stmt.executeQuery();
    }
    
    // Ejecutar INSERT, UPDATE, DELETE
    public int ejecutarActualizacion(Connection conn, String sql, Object... params) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            for (int i = 0; i < params.length; i++)
                stmt.setObject(i + 1, params[i]);
            return stmt.executeUpdate();
        }
    }

    // Insertar y obtener ID generado
    public int ejecutarActualizacionRetornarID(Connection conn, String sql, Object... params) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            for (int i = 0; i < params.length; i++)
                stmt.setObject(i + 1, params[i]);

            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) return rs.getInt(1);
            }
        }
        return -1;
    }

    // Cerrar conexion
    public void cerrarConexion(Connection conn) {
        try {
            if (conn != null) conn.close();
        } catch (SQLException e) {
            System.err.println("Error al cerrar conexion.");
        }
    }

    // Manejo general de errores
    public static void manejarError(SQLException e, String mensaje) {
        System.err.println(mensaje);
        e.printStackTrace();
    }
}
