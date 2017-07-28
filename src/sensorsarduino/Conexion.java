/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sensorsarduino;

import java.sql.Array;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author Juan Pablo
 */
public class Conexion {

    final static String MSSQL_DRIVER = "org.apache.derby.jdbc.ClientDriver";
    final static String MSSQL_JDBC_URL = "jdbc:derby://localhost:1527/";
    final static String MSSQL_USERNAME = "jp";
    final static String MSSQL_PASSWORD = "admin";
    final static String MSSQL_SCHEMA = "JP";
    final static long DAY_IN_MILLIS = 1000 * 60 * 60 * 24;
    final static Conexion conexionDB = new Conexion();
    //Connection con = new Connection();
    Statement stmt = null;
    ResultSet rs = null;

    public Connection dbConexion(String jdbcDriver, String jdbcUrl, String userName, String password,
            String schema) throws Exception {
        Class.forName(jdbcDriver);
        // Crear una conexión
        Connection con = DriverManager.getConnection(jdbcUrl, userName, password);
        return con;
    }

    public ArrayList<String> listaNombres(Connection coneccion) throws SQLException {
        try {
            ArrayList<String> nombres = new ArrayList<String>();
            stmt = coneccion.createStatement();
            rs = stmt.executeQuery("SELECT * FROM " + coneccion.getSchema() + ".VOLUNTARIO");
            while (rs.next()) {
                //System.out.println("Id = " + rs.getInt(1) + " - nombre = "+ rs.getString(2) + "");
                nombres.add(rs.getString(2));
            }
            return nombres;
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ignore) {
                }
            }
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException ignore) {
                }
            }
        }

    }

    public int insertarUsuario(Connection conexDB) throws Exception {
        try {
            stmt = conexDB.createStatement();
            rs = stmt.executeQuery("SELECT * FROM " + conexDB.getSchema() + ".VOLUNTARIO");
            int contadorUsuario = 0;
            int filasInsertadas = 0;
            while (rs.next()) {
                contadorUsuario = rs.getInt(1) + 1;
            }
            String nombreUsuario = "voluntario" + String.valueOf(contadorUsuario);
            System.out.println(contadorUsuario + "-" + nombreUsuario);
//            String query = "INSERT INTO " + conexDB.getSchema() + ".VOLUNTARIO VALUES (" + 
//                    contadorUsuario +",'"+nombreUsuario+"');";
//            int filasInsertadas = stmt.executeUpdate(query);
            try (PreparedStatement prepareInsert = conexDB.prepareStatement("INSERT INTO "
                    + conexDB.getSchema() + ".VOLUNTARIO (ID, NOMBRE) VALUES (?, ?)")) {
                int i = 1;
                prepareInsert.setInt(i++, contadorUsuario);
                prepareInsert.setString(i++, nombreUsuario);
                System.out.println("   - Ejecutar el insert");
                filasInsertadas = prepareInsert.executeUpdate();

            } catch (Exception e) {
                e.printStackTrace();
            }
            return filasInsertadas;

        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ignore) {
                }
            }
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException ignore) {
                }
            }
        }

    }

    public static void main(String[] args) {
        System.out.println(" * Iniciando demo de queries JDBC\n");

        try {
            System.out.println(" * Ejecutando demo MS SQL con JDBC...\n");
        } catch (Exception e) {
            System.out.println(" * Ha ocurrido el siguiente error:");
            e.printStackTrace();
        }
    }
}
