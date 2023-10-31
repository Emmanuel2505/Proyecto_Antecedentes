/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ControlAdminDatos;

import ControlAdminDatos.Utiles.Utiles;
import Controlador.Conexion;
import Modelo.Persona;
import java.io.File;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.Connection;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * PersonaDao implementa la interface Dao la cual tiene la funcion de realizar operaciones
 * a la base de datos
 * @author hp
 */
public class PersonaDao implements Dao<Persona> {

    Statement stmt;
    PreparedStatement stmt1;
    Conexion con = Conexion.getConexion();
    Utiles uti = new Utiles();
    static Connection cnx;
    /**
     * Constructor de la clase PersonaDao
     */
    public PersonaDao() {
        cnx = con.getConnection();

    }
    Boolean isValid;
    /**
     * Confirmacion de transaccion o operacion realizada a la base de datos
     * @return valor booleano true: operacion o transaccion realizada con exito
     * false: operacion o transaccion no realizada
     */
    public Boolean isValidTransaccion() {

        return isValid;

    }
    /**
     * Este es un metodo desde el cual se puede hacer uso de el metodo
     * findEntities(boolean all, int maxResult, int firstResult) pero obiando sus parametros
     * @return ArrayList Persona lista de datos de personas
     */
    public ArrayList<Persona> findPersonaEntities() {
        return findEntities(true, -1, -1);
    }
    /**
     * Este es un metodo desde el cual se puede hacer uso de el metodo 
     * findEntities(boolean all, int maxResult, int firstResult) tomando encuenta todos sus parametros
     * @param maxResult cantidad maxima de datos que se espera retornar
     * @param firstResult primer resultado desde el cual se requiere obtener los datos
     * @return ArrayList Persona lista de datos de personas
     */
    public ArrayList<Persona> findPersonaEntities(int maxResult, int firstResult) {
        return findEntities(false, maxResult, firstResult);
    }
    /**
     * Lista los datos de la entidad Persona contenidos en la tabla personas de la base de
     * datos de acuerdo a sus parametros
     * @param all Valor booleano true: obtiene todos los datos contenidos en la tabla persona
     * de la base de datos false: obtine los datos de acuerdo a los parametros requeridos
     * @param maxResult cantidad maxima de datos que se espera retornar
     * @param firstResult primer resultado desde el cual se requiere obtener los datos
     * @return ArrayList Persona lista de datos de personas
     */
    @Override
    public ArrayList<Persona> findEntities(boolean all, int maxResult, int firstResult) {

        String query;
        ArrayList<Persona> list = new ArrayList<>();
        if (all) {
            query = "SELECT * FROM sistemaco_penal.personas where estado='T'";
        } else {
            query = "SELECT * FROM sistemaco_penal.personas where idpersona>='" + firstResult + "' and estado='T' limit " + maxResult;
        }
        try {
            //Cargar la lista de cuentas
            stmt = (Statement) cnx.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            if (rs.next()) {
                do {
                    Boolean Bool = false;
                    if (rs.getString(11).contains("T")) {
                        Bool = true;
                    }

                    list.add(new Persona(rs.getLong(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10), Bool, uti.imagen(rs.getBlob(12), rs.getString(2)), rs.getLong(13)));
                } while (rs.next());
            }
            isValid = true;
        } catch (SQLException ex) {
            System.out.println("Error en la extaraccion de los datos de la base de datos "
                    + "detalles de error: " + ex);
            isValid = false;
        } catch (IOException ex) {
            isValid = false;
            Logger.getLogger(PersonaDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;

    }
    /**
     * Implementacion del metodo create para la entidad Persona,
     * inserta una persona en la tabla persona en la basede datos
     * @param persona Entidad persona que se insertara en la tabla personas de la base de datos
     */
    @Override
    public void create(Persona persona) {
        int i = 0;
        try {
            String insertar = "INSERT INTO `sistemaco_penal`.`personas` (`idpersona`, `cedula`, `nombre`, `apellido`, `fechaNacimiento`, `direccion`, `estadoCivil`, sexo, `telefono`, `mail`, `estado`,imagen,idRol) VALUES (?, ?, ?, ?, ?, ?, ?, ?,?,?,?,?,?)";
            PreparedStatement stmt = (PreparedStatement) cnx.prepareStatement(insertar);
            System.out.println("idPersona que da fallo: " + persona.getIdPersona());
            stmt.setLong(1, persona.getIdPersona());
            stmt.setString(2, persona.getCedula());
            stmt.setString(3, persona.getNombre());
            stmt.setString(4, persona.getApellido());
            stmt.setString(5, persona.getFechaNacimiento());
            stmt.setString(6, persona.getDireccion());
            stmt.setString(7, persona.getEstadoCivil());
            stmt.setString(8, persona.getSexo());
            stmt.setString(9, persona.getTelefono());
            stmt.setString(10, persona.getMail());
            stmt.setString(11, "T");
            stmt.setBinaryStream(12, uti.imagen(persona.getFile()));
            stmt.setLong(13, persona.getIdRol());
            i = stmt.executeUpdate();
            isValid = true;

        } catch (SQLException ex) {
            isValid = false;
            System.out.println("Error al guardar en la base de datos: Persona" + ex);
        } catch (IOException ex) {
            isValid = false;
            Logger.getLogger(PersonaDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /**
     * Implementacion del metodo edit para la entidad persona el cual permite modificar
     * los datos de personas en la tabla personas de la base de datos
     * @param persona Persona con los nuevos datos luego de modificarlos para ser actualizada en la base de datos
     */
    @Override
    public void edit(Persona persona) {
        int i = 0;
        try {
            String insertar = "UPDATE `sistemaco_penal`.`personas` SET `direccion` = ?, `estadoCivil` = ?, `telefono` = ?, `mail`=?, imagen = ? WHERE `idpersona` =" + persona.getIdPersona();
            PreparedStatement stmt = (PreparedStatement) cnx.prepareStatement(insertar);
            stmt.setString(1, persona.getDireccion());
            stmt.setString(2, persona.getEstadoCivil());
            stmt.setString(3, persona.getTelefono());
            stmt.setString(4, persona.getMail());

            stmt.setBinaryStream(5, uti.imagen(persona.getFile()));
            i = stmt.executeUpdate();
            isValid = true;
        } catch (SQLException ex) {
            isValid = false;
            System.out.println("Error al actualizar en la base de datos: " + ex);
        } catch (IOException ex) {
            isValid = false;
            Logger.getLogger(PersonaDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /**
     * Im-plementacion del metodo destroy para la entidad Persona la cual inactiva
     * a una persona y no volvera a aparecer en los listados de el sistema, "Dar de baja a una persona"
     * @param id Identificador de la Persona la cual sera dada de baja
     */
    @Override
    public void destroy(Long id) {
        int i = 0;
        try {
            String insertar = "UPDATE `sistemaco_penal`.`personas` SET `estado` ='F' WHERE `idpersona` =" + id;
            PreparedStatement stmt = (PreparedStatement) cnx.prepareStatement(insertar);
            i = stmt.executeUpdate();
            isValid = true;
        } catch (SQLException ex) {
            isValid = false;
            System.out.println("Error al actualizar en la base de datos: " + ex);
        }
    }
    /**
     * Implementacion del metodo find para la entidad persona, este devuelve una persona de la base datos
     * @param id Identificador de la persona la cual se desea obtener sus datos 
     * @return  Persona obtenida de la base datos
     */
    @Override
    public Persona find(Long id) {
        Persona persona = null;
        try {
            //Cargar la lista de cuentas
            stmt = (Statement) cnx.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM sistemaco_penal.personas where estado='T' and idpersona=" + String.valueOf(id));
            if (rs.next()) {
                Boolean Bool = false;
                if (rs.getString(11).contains("T")) {
                    System.out.println("Revision estado");
                    Bool = true;
                }
                persona = new Persona(rs.getLong(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7),rs.getString(8), rs.getString(9), rs.getString(10), Bool,uti.imagen(rs.getBlob(12),rs.getString(2)+"33ghj"),rs.getLong(13));
                return persona;
            }
            isValid = true;
        } catch (SQLException ex) {
            isValid = false;
            System.out.println("Error relizando la busqueda en la base de datos: " + ex);
        } catch (IOException ex) {
            isValid = false;
            Logger.getLogger(PersonaDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return persona;
    }
    /**
     * Implementacion del metodo getCount para la entidad persona la cual permite
     * obtenerla cantidad de Personas que existen en la tabla personas de la base de datos
     * @return Int cantidad de personas
     */
    @Override
    public int getCount() {
        int count = 0;
        try {
            //Cargar la lista de cuentas
            ResultSet rs = stmt.executeQuery("SELECT count(idpersona) FROM sistemaco_penal.personas where iestado='T'");
            if (rs.next()) {
                count = rs.getInt(1);
            }
            isValid = true;
        } catch (SQLException ex) {
            System.out.println("Error relizando el conteo" + ex);
            isValid = false;
        }
        return count;
    }
    
    /**
     * El siguiente método permite consultar o extraer los datos de una persona directo de la base de datos consultando con cedula o id 
     *
     * @param dato dato buscado
     * @param tipoBusqueda tipo de busqueda
     * @return  Persona
     * @throws java.sql.SQLException Excepcion
     */
    public Persona obtenerPersona(String dato, String tipoBusqueda) throws SQLException {
        Persona persona = null;
        stmt = (Statement) cnx.createStatement();
        ResultSet rs = null;
        if (tipoBusqueda.equalsIgnoreCase("cedula")) {
            rs = stmt.executeQuery("SELECT * FROM sistemaco_penal.personas where estado='T' and cedula=" + dato);
        }else if(tipoBusqueda.equalsIgnoreCase("id")){
            rs = stmt.executeQuery("SELECT * FROM sistemaco_penal.personas where estado='T' and idpersona=" + dato);
        }
        try {
                if (rs.next()) {
                    Boolean Bool = false;
                    if (rs.getString(11).contains("T")) {
                        System.out.println("Revision estado");
                        Bool = true;
                    }
                    persona = new Persona(rs.getLong(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10), Bool, uti.imagen(rs.getBlob(12), rs.getString(2)), rs.getLong(13));
                    return persona;
                }
            } catch (SQLException ex) {
                System.out.println("Error relizando la busqueda en la base de datos: " + ex);
            } catch (IOException ex) {
                Logger.getLogger(PersonaDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        return persona;
    }
}
