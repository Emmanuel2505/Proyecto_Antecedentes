/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ControlAdminDatos;

import Modelo.Persona;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *Interface dao para administrador de bases de datos
 * @author hp
 * @param <T> Tipo de dato
 */
public interface Dao<T> {
    
    /**
     * Metodo abstracto que devuelve una lista de objetos apartir de la base de
     * datos tomando en cuenta las condiciones de sus parametros
     * @param all Devuelve todos los objetos de la base de datos
     * @param maxResult Cantidad maxima de resultados que se espera obtener
     * @param firstResult Primer resultado desde el cual se espera obtener la lista de objetos
     * @return ArrayList de objetos devueltos por este metodo
     */
    public abstract ArrayList<T>  findEntities(boolean all, int maxResult,int firstResult);
    
    /**
     * Metodo abstracto que ingresa un objeto creado en la base de datos a la que hace referencia
     * @param object Objeto que sera insertado en la base de datos
     */
    public abstract void create(T object);

    /**
     * Metodo abstracto para editar a un objeto de bases de datos
     * @param object Objeto que sera editado en la base de datos
     */
    public abstract void edit(T object);

    /**
     * Metodo abstracto para dar de baja a un objeto de bases de datos
     * @param id Identificador de el objeto a destruir
     */
    public abstract void destroy(Long id);

    /**
     * Metodo abstracto para obtener una objeto apartir de su identificador
     * @param id Identificador que indica el objeto que sera modificado o editado
     * @return Retorna un objeto
     */
    public abstract T find(Long id);

    /**
     * Metdo abstracto que cuenta la cantidad de objetos que se encuentran en la base de datos
     * @return Cantidad de datos devueltos por la base de datos
     */
    public abstract int getCount();
}
