/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import ControlAdminDatos.Dao;
import Controlador.Conexion;
import Modelo.Delito;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author ASUS
 */
public class DelitoDao implements Dao<Delito>{
    Statement stmt;
    PreparedStatement stmt1;
    Conexion con = Conexion.getConexion();
    static Connection cnx;

    /**
     *Constructor de la clase DelitoDao
     */
    public DelitoDao() {
        cnx = con.getConnection();
    }

    /**
     * El siguiente método permite extrae un lista de delitos, pueden ser todos o solo las que estan activas
     *
     * @param todo Boolean true return la totalidad de los datos
     * @return  ArrayLis Delito 
     */
    public ArrayList<Delito> findDelitoEntities(boolean todo) {
        return findEntities(todo, -1, -1);
    }

    @Override
    public ArrayList<Delito> findEntities(boolean all, int maxResult, int firstResult) {
        String query;
        ArrayList<Delito> lista = new ArrayList<>();

        if (all) {
            query = "SELECT * FROM sistemaco_penal.delito";
        } else {
            query = "SELECT * FROM sistemaco_penal.delito where estadoDelito = 'Activo'";
        }

        try {
            //Cargar la lista de cuentas
            stmt = (Statement) cnx.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            if (rs.next()) {
                do {
                    lista.add(new Delito(rs.getLong(1), rs.getString(2), rs.getString(3), rs.getNString(4), rs.getNString(5), rs.getNString(6)));
                } while (rs.next());
            }
        } catch (SQLException ex) {
            System.out.println("Error en la extaraccion de los datos de la base de datos "
                    + "detalles de error: " + ex);

        }

        return lista;
    }

    @Override
    public void create(Delito object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void edit(Delito object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void destroy(Long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Delito find(Long id) {
        Delito delito = null;
        try {
            //Cargar la lista de cuentas
            stmt = (Statement) cnx.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM sistemaco_penal.delito where iddelito = " + id);
            if (rs.next()) {
                delito = new Delito(rs.getLong(1), rs.getString(2), rs.getString(3), rs.getNString(4), rs.getNString(5), rs.getNString(6));
                return delito;
            }
        } catch (SQLException ex) {
            System.out.println("Error relizando la busqueda en la base de datos: " + ex);
        }
        return delito;
    }

    @Override
    public int getCount() {
        int count = 0;
        try {
            //Cargar la lista de cuentas
            ResultSet rs = stmt.executeQuery("SELECT count(iddelito) FROM sistemaco_penal.delito where estadoDelito = 'Activo'");
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException ex) {
            System.out.println("Error relizando el conteo" + ex);
        }
        return count;
    }
    
}
