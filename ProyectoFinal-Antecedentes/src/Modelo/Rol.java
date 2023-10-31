/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

/**
 *
 * @author hp
 */
public class Rol {
   Long idRol;
   String tipo;

    /**
     * Constructor de la clase rol
     * @param idRol Identificador del Rol
     * @param tipo Tipo de rol
     */
    public Rol(Long idRol, String tipo) {
        this.idRol = idRol;
        this.tipo = tipo;
    }

    /**
     * Metodo get que devuelve el Identificador del Rol
     * @return Identificador del rol
     */
    public Long getIdRol() {
        return idRol;
    }

    /**
     * Metodo set que permite ingresar el Identificador del rol
     * @param idRol Identificador del rol
     */
    public void setIdRol(Long idRol) {
        this.idRol = idRol;
    }

    /**
     * Metodo get que devuelve el tipo  de rol
     * @return Tipo de rol
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * Metodo set que permite ingresar el tipo de rol
     * @param tipo TRipo de rol
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
   
}
