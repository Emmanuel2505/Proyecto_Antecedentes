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
public class Condena {
   private Long idCondena;
   private String sentencia;
   private String estadoCondena;
   /**
    * Constructor de la clase Condena 
    * @param idCondena Identificadore de condena
    * @param sentencia Setencia emitida
    * @param estadoCondena  Estado de la condena
    */
    public Condena(Long idCondena, String sentencia, String estadoCondena) {
        this.idCondena = idCondena;
        this.sentencia = sentencia;
        this.estadoCondena = estadoCondena;
    }

    /**
     * Metodo get que devuelve el ienctificador principal de la condena
     * @return Identificado de la condena
     */
    public Long getIdCondena() {
        return idCondena;
    }
    /**
     * Metodo set que permite ingresar el identificado de condena
     * @param idCondena  Identificado de Condena
     */
    public void setIdCondena(Long idCondena) {
        this.idCondena = idCondena;
    }

    /**
     * Metodo get que devuelve la informacion de la sentencia
     * @return Sentencia
     */
    public String getSentencia() {
        return sentencia;
    }

    /**
     * Metodo se que permite ingresar la informacion de la sentencia
     * @param sentencia Informacion de la sentencia
     */
    public void setSentencia(String sentencia) {
        this.sentencia = sentencia;
    }

    /**
     * Metodo get que devuelve esl estado de la condena
     * @return Estado de la condena
     */
    public String getEstadoCondena() {
        return estadoCondena;
    }

    /**
     * Metodo set que permite ingresar el estado de la condena
     * @param estadoCondena Estado de la condena
     */
    public void setEstadoCondena(String estadoCondena) {
        this.estadoCondena = estadoCondena;
    }
}
