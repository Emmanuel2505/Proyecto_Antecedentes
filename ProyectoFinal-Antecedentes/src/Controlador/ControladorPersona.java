/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import ControlAdminDatos.PersonaDao;
import ControlAdminDatos.RolDao;
import Modelo.Persona;
import Modelo.Rol;
import Vista.Utiles.TablaPersonas.Utilidades;
import java.util.ArrayList;

/**
 * Controlador de persona sirve como enlace entre el modelo y su informacion
 * relacionada con la interfaz de usuario
 * @author hp
 */
public class ControladorPersona {

    private static Persona pe = null;
    PersonaDao dao = new PersonaDao();
    private ArrayList<Persona> listComun = new ArrayList<>();
    ArrayList<Persona> temp = new ArrayList<>();
    /**
     * Crea una nueva persona
     */
    public void newPersona() {
        pe = new Persona();
    }
    /**
     * Metodo get que devuelve una persona
     * @return Persona creda con el metodo newPersona();
     */
    public Persona agregarPersona() {
        return pe;
    }
    /**
     * Permite ingresar una persona
     * @param p Persona que se ingresara
     */
    public void setPersona(Persona p) {
        pe = p;
    }
    /**
     * Guarda o almacena una persona en la base de datos
     */
    public void guardarPersona() {
        dao.create(pe);
    }
    /**
     * Verificacion de execucion de una transaccion o operacion en la base de datos
     * @return Boolean true: se executo con exito false: no se executo la operacion
     */
    public boolean isCorrect(){ 
        return dao.isValidTransaccion();
        
    }
    /**
     * Metodo get que obtiene la, lista de persona original
     * @return ArrayList Persona lista de personas
     */
    public ArrayList<Persona> getListComun() {
        return listComun;
    }
    /**
     * Metodo get que obtiene una lista temporal creada apartir de la lista original
     * @return ArrayList Persona lista de personas
     */
    public ArrayList<Persona> getListTemporal() {
        return temp;
    }
    /**
     * Vacia la lista original de personas
     */
    public void VaciarListComun() {
        listComun = new ArrayList<>();

    }
    /**
     * Vacia la lista temporal de personas
     */
    public void VaciarTemp() {
        temp = new ArrayList<>();

    }
    /**
     * Lista las personas contenidas en la base de datos de acuerdo al rol con el que se le a permitido ingresar
     * @return ArrayList Personas lista de personas
     */
    public ArrayList<Persona> listaPersonas() {
        PersonaDao pe = new PersonaDao();
        RolDao ro = new RolDao();
        ArrayList<Persona> list = pe.findPersonaEntities();

        ArrayList<Rol> list1 = ro.findRolEntities();
        Long id = 0L;
        for (int i = 0; i < list1.size(); i++) {
            if (list1.get(i).getTipo().equals("Comun")) {
                id = list1.get(i).getIdRol();
                for (int j = 0; j < list.size(); j++) {
                    if (list.get(j).getIdRol() == id) {
                        listComun.add(list.get(j));
                    }
                }
            }
        }
        return listComun;

    }
    /**
     * Convierte la lista de personas en una arreglo de personas para facilitar
     * la alimentacion con valores de la tabla o tablas de la vista
     * @param titulosList lista de los encabezados de columnas de la que sera la tabla
     * @return  Object[] arreglo de personas 
     */
    public Object[][] obtenerMatrizDatos(ArrayList<String> titulosList) {
        String informacion[][] = new String[temp.size()][titulosList.size()];

        for (int x = 0; x < informacion.length; x++) {

            informacion[x][Utilidades.CEDULA] = temp.get(x).getCedula() + "";
            informacion[x][Utilidades.NOMBRE] = temp.get(x).getNombre() + "";
            informacion[x][Utilidades.APELLIDO] = temp.get(x).getApellido() + "";
            informacion[x][Utilidades.FECHANACIMIENTO] = temp.get(x).getFechaNacimiento() + "";
            informacion[x][Utilidades.DIRECCION] = temp.get(x).getDireccion() + "";
            informacion[x][Utilidades.ESTADOCIVIL] = temp.get(x).getEstadoCivil() + "";
            informacion[x][Utilidades.TELEFONO] = temp.get(x).getTelefono() + "";
            informacion[x][Utilidades.MAIL] = temp.get(x).getMail() + "";
            informacion[x][Utilidades.PERFIL] = "PERFIL";
            informacion[x][Utilidades.EVENTO] = "EVENTO";
        }

        return informacion;
    }
    /**
     * Da de baja a una persona inabilitandola para aparecer en el sistema
     * @param id Identificado de la persona que sera dada de baja
     */
    public void darBaja(Long id) {
        dao.destroy(id);
    }
    /**
     * Modifica una persona en la basae de datos
     * @param p Persona modificada que sera actualizada en la base de datos
     */
    public void Editar(Persona p) {
        dao.edit(p);
    }
    /**
     * Busca  personas de acuerso a su parametro de busqueda
     * @param dato Dato buscado
     * @param parametro Parametro sobre el cual se realizara la busqueda: CEDULA,NOMBRE, ALL
     */
    public void buscar(String dato, String parametro) {
        if (parametro.equals("CEDULA")) {
            for (int i = 0; i < getListComun().size(); i++) {
                if (getListComun().get(i).getCedula().contains(dato)) {
                    temp.add(getListComun().get(i));
                }
            }
        } else if (parametro.equals("NOMBRE")) {
            for (int i = 0; i < getListComun().size(); i++) {
                if (getListComun().get(i).getNombre().contains(dato)) {
                    temp.add(getListComun().get(i));
                }
            }
        } else if (parametro.equals("ALL")) {
            for (int i = 0; i < getListComun().size(); i++) {
                temp.add(getListComun().get(i));
            }
        }
        {

        }
    }
    
}
