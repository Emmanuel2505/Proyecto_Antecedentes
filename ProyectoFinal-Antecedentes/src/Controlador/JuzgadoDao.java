/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import ControlAdminDatos.Dao;
import Controlador.Conexion;
import Modelo.Juzgado;
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
public class JuzgadoDao implements Dao<Juzgado> {

    Statement stmt;
    PreparedStatement stmt1;
    Conexion con = Conexion.getConexion();
    static Connection cnx;
    boolean seGuardo;
    /**
     * Constructor de la clase JuzgadoDao
     */
    public JuzgadoDao() {
        cnx = con.getConnection();
    }
    /**
     * Comprobacion de guardado exitoso
     * @return Boolean true: si se a guardado correctamente false: no se guardo
     */
    public boolean isSeGuardo() {
        return seGuardo;
    }

    /**
     * El siguiente método permite extrae un lista de juzgados, pueden ser todos o solo las que estan activas
     *
     * @param todo Boolean true: retorna la totalidad de los datos
     * @return ArrayList Jusjado
     */
    public ArrayList<Juzgado> findJuzgadoEntities(boolean todo) {
        return findEntities(todo, -1, -1);
    }

    @Override
    public ArrayList findEntities(boolean all, int maxResult, int firstResult) {
        String query;
        ArrayList<Juzgado> lista = new ArrayList<>();

        if (all) {
            query = "SELECT * FROM sistemaco_penal.juzgados";
        } else {
            query = "SELECT * FROM sistemaco_penal.juzgados where estadoJuzgado = 'Activado'";
        }

        try {
            //Cargar la lista de cuentas
            stmt = (Statement) cnx.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            if (rs.next()) {
                do {
                    lista.add(new Juzgado(rs.getLong(1), rs.getString(2), rs.getString(3), rs.getNString(4)));
                } while (rs.next());
            }
        } catch (SQLException ex) {
            System.out.println("Error en la extaraccion de los datos de la base de datos "
                    + "detalles de error: " + ex);

        }

        return lista;
    }

    @Override
    public void create(Juzgado juzgado) {
        int i = 0;
        try {
            String insertar = "INSERT INTO sistemaco_penal.juzgados (idjuzgados, nombreJuzgado, direccionJuzgado, estadoJuzgado) VALUES (?, ?, ?, ?)";
            PreparedStatement pstmt = (PreparedStatement) cnx.prepareStatement(insertar);
            pstmt.setLong(1, juzgado.getIdJuzgado());
            pstmt.setString(2, juzgado.getNombre());
            pstmt.setString(3, juzgado.getDireccionJuzgado());
            pstmt.setString(4, juzgado.getEstadoJuzgado());
            i = pstmt.executeUpdate();
            seGuardo = true;
        } catch (SQLException ex) {
            System.out.println("Error al guardar en la base de datos: " + ex);
            seGuardo = false;
        }
    }

    @Override
    public void edit(Juzgado juzgado) {
        int i = 0;
        try {
            Long id = juzgado.getIdJuzgado();
            String insertar = "UPDATE sistemaco_penal.juzgados SET nombreJuzgado = ?, direccionJuzgado = ? WHERE idjuzgados =" + id;
            PreparedStatement pstmt = (PreparedStatement) cnx.prepareStatement(insertar);
            pstmt.setString(1, juzgado.getNombre());
            pstmt.setString(2, juzgado.getDireccionJuzgado());
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
            ResultSet rs = stmt.executeQuery("SELECT * FROM sistemaco_penal.juzgados WHERE idjuzgados =" + id);
            if (rs.next()) {
                String insertar = null;
                if (rs.getString(4).contains("Activado")) {
                    insertar = "UPDATE sistemaco_penal.juzgados SET estadoJuzgado = 'Desactivado' WHERE idjuzgados =" + id;
                } else if (rs.getString(4).contains("Desactivado")) {
                    insertar = "UPDATE sistemaco_penal.juzgados SET estadoJuzgado = 'Activado' WHERE idjuzgados =" + id;
                }
                PreparedStatement pstmt = (PreparedStatement) cnx.prepareStatement(insertar);
                i = pstmt.executeUpdate();
                seGuardo = true;
            }
        } catch (SQLException ex) {
            System.out.println("Error al actualizar en la base de datos: " + ex);
            seGuardo = false;
        }
    }

    @Override
    public Juzgado find(Long id) {
        Juzgado juzgado = null;
        try {
            //Cargar la lista de cuentas
            stmt = (Statement) cnx.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM sistemaco_penal.juzgados where idjuzgados = " + id);
            if (rs.next()) {
                juzgado = new Juzgado(rs.getLong(1), rs.getString(2), rs.getString(3), rs.getString(4));
                return juzgado;
            }
        } catch (SQLException ex) {
            System.out.println("Error relizando la busqueda en la base de datos: " + ex);
        }
        return juzgado;
    }

    @Override
    public int getCount() {
        int count = 0;
        try {
            //Cargar la lista de cuentas
            ResultSet rs = stmt.executeQuery("SELECT count(idjuzgados) FROM sistemaco_penal.juzgados where estadoJuzgado = 'Activado'");
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException ex) {
            System.out.println("Error relizando el conteo" + ex);
        }
        return count;
    }
}
