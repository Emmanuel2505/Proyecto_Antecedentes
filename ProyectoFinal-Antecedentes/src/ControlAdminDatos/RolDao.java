/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ControlAdminDatos;

import static ControlAdminDatos.PersonaDao.cnx;
import ControlAdminDatos.Utiles.Utiles;
import Controlador.Conexion;
import Modelo.Persona;
import Modelo.Rol;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * RolDao implementa la clase dao y tiene la funcion de 
 * de realizar operaciones en la base de datos como por ejemplo obtencion de datos
 * @author hp
 */
public class RolDao implements Dao<Rol>{
    Statement stmt;
    PreparedStatement stmt1;
    Conexion con = Conexion.getConexion();
    static Connection cnx;
    /**
     * Constructor de la clase RolDao
     */
    public RolDao(){
        cnx=con.getConnection();
    }
    
    /**
     * Este es un metodo desde el cual se puede hacer uso de el metodo
     * findEntities(boolean all, int maxResult, int firstResult) pero obiando sus parametros
     * @return ArrayList Rol lista de datos de Roles de usuarios
     */
    public ArrayList<Rol> findRolEntities() {
        return findEntities(true, -1, -1);
    }
     /**
     * Este es un metodo desde el cual se puede hacer uso de el metodo 
     * findEntities(boolean all, int maxResult, int firstResult) tomando en cuenta todos sus parametros
     * @param maxResult cantidad maxima de datos que se espera retornar
     * @param firstResult primer resultado desde el cual se requiere obtener los datos
     * @return ArrayList Rol lista de datos de Rles de usuarios
     */
    public ArrayList<Rol> findRolEntities(int maxResult, int firstResult) {
        return findEntities(false, maxResult, firstResult);
    }
    
        /**
     * Lista los datos de la entidad Rol contenidos en la tabla cuenta de la base de
     * datos de acuerdo a sus parametros
     * @param all Valor booleano true: obtiene todos los datos contenidos en la tabla rol
     * de la base de datos false: obtine los datos de acuerdo a los parametros requeridos
     * @param maxResult cantidad maxima de datos que se espera retornar
     * @param firstResult primer resultado desde el cual se requiere obtener los datos
     * @return ArrayList Rol lista de datos de cuentas de usuarios
     */
    @Override
    public ArrayList<Rol> findEntities(boolean all, int maxResult, int firstResult) {
        String query;
        ArrayList<Rol> list = new ArrayList<>();
        if (all) {
            query = "SELECT * FROM sistemaco_penal.rol";
        } else {
            query = "SELECT * FROM sistemaco_penal.rol where idrol>='" + firstResult + "' limit " + maxResult;
        }
        try {
            //Cargar la lista de cuentas
            stmt = (Statement) cnx.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            if (rs.next()) {
                do {
                    list.add(new Rol(rs.getLong(1),rs.getString(2)));
                } while (rs.next());
            }
        } catch (SQLException ex) {
            System.out.println("Error en la extaraccion de los datos de la base de datos "
                    + "detalles de error: " + ex);

        }
        return list;
    }

    @Override
    public void create(Rol object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void edit(Rol object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void destroy(Long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
        /**
     * Implementacion del metodo find para la entidad persona, este devuelve una Rol de la base datos
     * @param id Identificador del Rol el la cual se desea obtener 
     * @return  Rol obtenido de la base datos
     */
    @Override
    public Rol find(Long id) {
        Rol rol = null;
        try {
            //Cargar la lista de cuentas
            ResultSet rs = stmt.executeQuery("SELECT * FROM sistemaco_penal.rol where idrol="+id);
            if (rs.next()) {
                rol = new Rol(rs.getLong(1), rs.getString(2));
                return rol;
            }
        } catch (SQLException ex) {
            System.out.println("Error relizando la busqueda en la base de datos: " + ex);
        }
        return rol;
    }

    @Override
    public int getCount() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
