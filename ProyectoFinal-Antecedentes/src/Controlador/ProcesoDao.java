/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import ControlAdminDatos.Dao;
import Controlador.Conexion;
import Modelo.Proceso;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.Date;

/**
 *
 * @author ASUS
 */
public class ProcesoDao implements Dao<Proceso> {

    Statement stmt;
    PreparedStatement stmt1;
    Conexion con = Conexion.getConexion();
    static Connection cnx;
    boolean seGuardo;
    /**
     * Constructor de la clase ProcesoDao
     */
    public ProcesoDao() {
        cnx = con.getConnection();
    }
    /**
     * Verificacion de guardao extoso
     * @return Boolean true: Se a guardado correctamente false: no se guardo
     */
    public boolean isSeGuardo() {
        return seGuardo;
    }

    /**
     * El siguiente método permite extrae un lista de procesos, pueden ser todos o solo las que estan activas
     *
     * @param todo Boolean true retorna la totalidad de los datos
     * @return  ArrayList Proceso
     */
    public ArrayList<Proceso> findProcesoEntities(boolean todo) {
        return findEntities(todo, -1, -1);
    }

    @Override
    public ArrayList<Proceso> findEntities(boolean all, int maxResult, int firstResult) {
        String query;
        ArrayList<Proceso> lista = new ArrayList<>();

        if (all) {
            query = "SELECT * FROM sistemaco_penal.proceso";
        }else{
            query = "SELECT * FROM sistemaco_penal.proceso WHERE estadoProceso = 'Habilitado'";
        }
        try {
            //Cargar la lista de cuentas
            stmt = (Statement) cnx.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            if (rs.next()) {
                do {
                    lista.add(new Proceso(rs.getLong(1), rs.getInt(2), rs.getString(3), rs.getString(4), rs.getInt(5),
                            rs.getBytes(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10), rs.getLong(11), rs.getLong(12),
                            rs.getLong(13), rs.getLong(14)));//aqui estabas mal bro el orden es importante
                            // en la base de datos estadoproceso es el atributo 14 y tu lo has puesto en distinta posicion y numero
                            //no se apreciaba porque la exepcion la anulaba pero estaba persistente
                } while (rs.next());
            }
        } catch (SQLException ex) {
            System.out.println("Error en la extaraccion de los datos de la base de datos tabla proceso "
                    + "detalles de error: " + ex);

        }

        return lista;
    }

    @Override
    public void create(Proceso proceso) {
        int i = 0;
        try {
            String insertar = "INSERT INTO sistemaco_penal.proceso (idproceso, instancia, fechaInicio, fechaFinal, nroAudiencia, documento, nombreDocumento, estadoVictimario, estadoDemanda, estadoProceso,idDelito, idPersona, idCondena, idJuzgado) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = (PreparedStatement) cnx.prepareStatement(insertar);
            pstmt.setLong(1, proceso.getIdProceso());
            pstmt.setInt(2, proceso.getInstancia());
            pstmt.setString(3, proceso.getFechaInicio());
            pstmt.setString(4, proceso.getFechaFinal());
            pstmt.setInt(5, proceso.getNrAudiencias());
            pstmt.setBytes(6, proceso.getText());
            pstmt.setString(7, proceso.getNombreDocumento());
            pstmt.setString(8, proceso.getEstadoVictimario());
            pstmt.setString(9, proceso.getEstadoDemanda());
            pstmt.setString(10, proceso.getEstadoProceso());
            pstmt.setLong(11, proceso.getIdDelito());
            pstmt.setLong(12, proceso.getIdPersona());
            pstmt.setLong(13, proceso.getIdCondena());
            pstmt.setLong(14, proceso.getIdJuzgado());

            i = pstmt.executeUpdate();
            seGuardo = true;
        } catch (SQLException ex) {
            System.out.println("Error al guardar en la base de datos: " + ex);
            seGuardo = false;
        }
    }

    @Override
    public void edit(Proceso proceso) {
        int i = 0;
        try {
            Long id = proceso.getIdProceso();
            String insertar = "UPDATE sistemaco_penal.proceso SET instancia = ?, fechaFinal = ?, nroAudiencia = ?, documento = ?, nombreDocumento = ?, estadoVictimario = ?, estadoDemanda = ? WHERE idproceso =" + id;
            PreparedStatement pstmt = (PreparedStatement) cnx.prepareStatement(insertar);
            pstmt.setInt(1, proceso.getInstancia());
            pstmt.setString(2, proceso.getFechaFinal());
            pstmt.setInt(3, proceso.getNrAudiencias());
            pstmt.setBytes(4, proceso.getText());
            pstmt.setString(5, proceso.getNombreDocumento());
            pstmt.setString(6, proceso.getEstadoVictimario());
            pstmt.setString(7, proceso.getEstadoDemanda());
            i = pstmt.executeUpdate();
            seGuardo = true;
        } catch (SQLException ex) {
            System.out.println("Error al actualizar en la base de datos: " + ex);
            seGuardo = false;
        }
    }

    @Override
    public void destroy(Long id) {
        int i = 0;
        try {
            ResultSet rs = stmt.executeQuery("SELECT * FROM sistemaco_penal.proceso WHERE idproceso =" + id);
            if (rs.next()) {
                String insertar = null;
                if (rs.getString(10).contains("Habilitado")) {
                    insertar = "UPDATE sistemaco_penal.proceso SET estadoProceso = 'Deshabilitado' WHERE idproceso =" + id;
                } else if (rs.getString(10).contains("Deshabilitado")) {
                    insertar = "UPDATE sistemaco_penal.proceso SET estadoProceso = 'Habilitado' WHERE idproceso =" + id;
                }
                PreparedStatement pstmt = (PreparedStatement) cnx.prepareStatement(insertar);
                i = pstmt.executeUpdate();
                seGuardo = true;
            }
        } catch (SQLException ex) {
            System.out.println("Error al actualizar en la base de datos: " + ex);
            seGuardo = false;
        }    }

    @Override
    public Proceso find(Long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getCount() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * El siguiente método permite extrae un lista de procesos dependiendo del id de la persona, pueden ser todos o solo las que estan activas
     *
     * @param id Identificador del proceso que se quire obtener
     * @param todo Boolena true: retorna la totalidad de los datos
     * @return ArrayList Proceso
     */
    public ArrayList<Proceso> listaProcesoPersona(Long id, boolean todo) {
        String query;
        ArrayList<Proceso> lista = new ArrayList<>();
        
        if (todo) {
            query = "SELECT * FROM sistemaco_penal.proceso where idPersona = " + id;
        }else{
            query = "SELECT * FROM sistemaco_penal.proceso where estadoProceso = 'Habilitado' and idPersona = " + id;
        }
        

        try {
            //Cargar la lista de cuentas
            stmt = (Statement) cnx.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            if (rs.next()) {
                do {
                    lista.add(new Proceso(rs.getLong(1), rs.getInt(2), rs.getString(3), rs.getString(4), rs.getInt(5),
                            rs.getBytes(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10), rs.getLong(11), rs.getLong(12),
                            rs.getLong(13), rs.getLong(14)));
                } while (rs.next());
            }
        } catch (SQLException ex) {
            System.out.println("Error en la extaraccion de los datos de la base de datos  tabla proceso1 "
                    + "detalles de error : " + ex);

        }
        return lista;
    }
}
