
package com.techclothes.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
    private static Connection con;
    private static Conexion instancia;

    private static final String URL = "jdbc:mysql://localhost:3306/techclothes";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "admin123";

    // Constructor privado para Singleton
    private Conexion() {

    }

    public Connection getConexion() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); 
            con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            return con;
        } catch (Exception e) {
            System.out.println("Error de conexion: " + e);
        }
        return null;
    }

    public void closeConexion() {
        try {
            if (con != null && !con.isClosed()) {
                con.close();
            }
        } catch (SQLException e) {
            System.out.println("Error cerrando la conexión: " + e);
        }
    }

    // Patrón Singleton
    public static Conexion getInstance() {
        if (instancia == null) {
            instancia = new Conexion();
        }
        return instancia;
    }
}