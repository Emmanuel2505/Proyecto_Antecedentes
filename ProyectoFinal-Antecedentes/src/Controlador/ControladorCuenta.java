/*
 * To change this license header, choose License Headers in Project Procerties.
 * To change this template file, choose Tools | Templates
 * and ocen the template in the editor.
 */
package Controlador;

import ControlAdminDatos.CuentaDao;
import ControlAdminDatos.PersonaDao;
import Modelo.Cuenta;
import Modelo.Persona;
import Vista.Acces.InicioSesion;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.spec.SecretKeySpec;

/**
 * ControladorCuenta es una clase de vinculacion entre el modelo y su
 * informacion relacionada con las vista de usuario
 *
 * @author hp
 */
public class ControladorCuenta {

    private static Cuenta ce = null;
    CuentaDao dao = new CuentaDao();
    private ArrayList<Cuenta> listCuenta = new ArrayList<>();

    /**
     * Crea una nueva Cuenta
     */
    public void newCuenta() {
        ce = new Cuenta();
    }

    /**
     * Metodo get que devuelve una cuenta
     *
     * @return Cuenta cuenta creda con el metodo newCuenta();
     */
    public Cuenta agregarCuenta() {
        return ce;
    }

    /**
     * Metodo set Que permite ingresar una cuenta
     *
     * @param c Cuenta que se desea ingresar
     */
    public void setCuenta(Cuenta c) {
        ce = c;
    }

    /**
     * Guarda una Cuenta en la base de datos
     */
    public void guardarCuenta() {
        dao.create(ce);
    }

    /**
     * Metodo get que permite obtener la lista de cuentas
     *
     * @return ArrayList Cuenta lista de cuentas
     */
    public ArrayList<Cuenta> getListCuenta() {
        return listCuenta;
    }

    /**
     * Vacia la lista de cuentas
     */
    public void VaciarListCuenta() {
        listCuenta = new ArrayList<>();

    }

    /**
     * Obtiene los datos de cuentas de la base de datos y las lista
     *
     * @return ArrayList Cuentas lista de cuentas
     */
    public ArrayList<Cuenta> listaCuentas() {
        ArrayList<Cuenta> list = dao.findCuentaEntities();

        for (int j = 0; j < list.size(); j++) {
            listCuenta.add(list.get(j));
        }
        return listCuenta;

    }

    /**
     * Permite dar de baja a una cuenta por su mediante su identificador
     * principal;
     *
     * @param id Identificador principal o clave de la cuenta
     */
    public void darBaja(Long id) {
        dao.destroy(id);
    }

    /**
     * Modifica una Cuenta en la base de datos
     *
     * @param p Cuenta modificada que sera actualizada en la base de datos
     */
    public void Editar(Cuenta p) {
        dao.edit(p);
    }
    Decrypt ctre = new Decrypt();
    Boolean pase=false;

    /**
     * Busca la cuenta que cumpla las condiciones de sus parametros
     *
     * @param Usuario Usuario de la persona a buscar
     * @param Clave Clave o contraseña encriptada para buscar
     * @throws GeneralSecurityException Exepcion 
     * @throws IOException Excepcion
     */
    public void buscarCuenta(String Usuario, String Clave) throws GeneralSecurityException, IOException {
        listaCuentas();

        String del = ctre.decodeString(Clave, ctre.KEY);
        System.out.println("Encriptado campo: " + Clave);
        System.out.println("Desencriptado campo: " + del);

        String clavel = getListCuenta().get(1).getClave();
        String de = ctre.decodeString(clavel, ctre.KEY);
        System.out.println("Encriptado bd: " + clavel);
        System.out.println("Desencriptado bd: " + de);

        for (int i = 0; i < getListCuenta().size(); i++) {
            System.out.println("lista: "+getListCuenta().get(i).getUsuario()+" "+getListCuenta().get(i).getClave());
            if (getListCuenta().get(i).getUsuario().equals(Usuario) && getListCuenta().get(i).getClave().equals(Clave)) {
                
                ce = getListCuenta().get(i);
                System.out.println("Entarar alif");
                pase = true;
                System.out.println("Contraseña correcta: "+pase);
                break;
            } else {
                pase = false;
            }
        }

    }

    /**
     * Retorna una coinfirmacion boolena de la busqueda de cuentas
     *
     * @return Boolean true: busqueda exitosa, false: busqueda fracasada
     */
    public Boolean getBuscarPersonaCuenta() {
        return pase;

    }

    /**
     * Busca la cuenta de una persona
     *
     * @param idPersona identificador de la perona
     * @return Perosna persona a la que le pertenece la cuenta
     */
    public Persona buscarPersonaCuenta(Long idPersona) {
        PersonaDao dao = new PersonaDao();
        return dao.find(idPersona);

    }

    /**
     * Encripta la contraseña de los usuarios que se registren o logeen en el
     * sistema
     *
     * @param pasword char[] Contraseña o clave a encriptar
     * @return Clave o contraseña encriptada
     * @throws GeneralSecurityException Excepcion
     */
    public String encriptar(char[] pasword) throws GeneralSecurityException {

        String clave = "";
        for (int i = 0; i < pasword.length; i++) {
            clave += String.valueOf(pasword[i]);
        }
        return ctre.encodeString(clave, ctre.KEY);
    }

}
