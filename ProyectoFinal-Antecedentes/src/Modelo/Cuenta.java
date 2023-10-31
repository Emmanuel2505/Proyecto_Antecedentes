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
public class Cuenta {
    Long idCuenta;
    String usuario;
    String clave;
    Long idPersona;

    /**
     * Constructor vacío de la clase Cuenta
     */
    public Cuenta() {
    }
    
    /**
     * Constructor paramterizado de la clase Cuenta
     * @param idCuenta Identificador de Cuenta
     * @param usuario Usuario de la Cuenta
     * @param clave Clave de la Cuenta
     * @param idPersona Identificador de la persona propietaria de la cuenta
     */
    public Cuenta(Long idCuenta, String usuario, String clave, Long idPersona) {
        this.idCuenta = idCuenta;
        this.usuario = usuario;
        this.clave = clave;
        this.idPersona = idPersona;
    }
    
    /**
     * Metodo get que devuelve el Identificador de la cuenta
     * @return Long Identificador de la cuenta
     */
    public Long getIdCuenta(){
        return idCuenta;
    }

    /**
     * Metodo set que peremite ingresar el Identificador de la cuenta
     * @param idCuenta Identificador de la cuenta
     */
    public void setIdCuenta(Long idCuenta){
        this.idCuenta=idCuenta;
    }

    /**
     * Metodo get que devuelve el Nombre de Usuario de la cuenta
     * @return Nombre de Usuario de la cuenta
     */
    public String getUsuario(){
        return usuario;
    }

    /**
     * Metodo set que permite ingresar el nombre de usuario de la cuenta
     * @param usuario Nombre de usuario de la cuenta
     */
    public void setUsuario(String usuario){
        this.usuario=usuario;
    }

    /**
     * Metodo get que devuelve la clave o contraseña de la cuenta
     * @return Cloave de la cuenta
     */
    public String getClave(){
        return clave;
    }

    /**
     * Metodo set que permite ingresar loa clave de la cuenta
     * @param clave Clave de la cuenta
     */
    public void setClave(String clave){
        this.clave=clave;
    } 

    /**
     * Metodo get que devuelve el Identificador de la persona a la que le pertenece la cuenta
     * @return Identificador de la persona
     */
    public Long getIdPersona(){
        return idPersona;
    }

    /**
     * Metodo set que permite ingresar el identificador de la persona propietaria de la cuenta
     * @param idPersona Identificador de la persona
     */
    public void setIdPersona(Long idPersona){
        this.idPersona=idPersona;
    }
}
