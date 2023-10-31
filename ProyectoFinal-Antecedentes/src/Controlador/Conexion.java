/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;
/**
 *
 * @author ASUS
 */
public class Conexion {
    private static Conexion conexion = new Conexion();
    static Connection cn;
    static boolean estado=true;
    /**
     * Constructor de la clase conexion
     */
    private Conexion() {
        
    }
    
    public  static Conexion getConexion(){
        IniciarConexion();
        return conexion;
    }
    /**
     * Metodo get que permite establecer conexion con la base de datos
     *
     * @return Retiorna una conexion de BDD
     */
    public Connection getConnection() {
        return cn;
    }

    /**
     * Inicia o establece la coneccion con la base de datos
     */
    private static void IniciarConexion() {
        Connection con = null;
        String Driver = "com.mysql.cj.jdbc.Driver";
        String Usuario = "root";
        String Contraseña = "12345";
        String NombreDB = "sistemaco_penal";
        String Puerto = "3306";
        String TimeZone = "?useUnicode=true&use"
                + "JDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false"
                + "&serverTimezone=UTC";
        try {
            Class.forName(Driver);
            con = DriverManager.getConnection("jdbc:mysql://localhost:" + Puerto + "/" + NombreDB + TimeZone, Usuario, Contraseña);
            System.out.println("Conexion establecida correctamente con la base de datos: " + NombreDB);
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Error estableciendo conexion con la base de datos: " + NombreDB
                    + "\nDetalles del error: \n" + e.getMessage());
        }
        cn = con;

    }
}
