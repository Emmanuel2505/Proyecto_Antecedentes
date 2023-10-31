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
public class Juzgado {
    private Long idJuzgado;
    private String nombre;
    private String direccionJuzgado;
    private String estadoJuzgado;

    /**
     * Constructor parameterizado de la clase Juzgado
     * @param idJuzgado Identificador del juzgado
     * @param nombreJuzgado Nombre del juzgado
     * @param direccionJuzgado Direccion del juzgado
     * @param estadoJuzgado Estado del juzgado
     */
    public Juzgado(Long idJuzgado, String nombreJuzgado, String direccionJuzgado, String estadoJuzgado) {
        this.idJuzgado = idJuzgado;
        this.nombre = nombreJuzgado;
        this.direccionJuzgado = direccionJuzgado;
        this.estadoJuzgado = estadoJuzgado;
    }

    /**
     * Constructor vacío de la clase juzgado
     */
    public Juzgado() {
    }

    /**
     * Metodo get que devuelve el identificador del juzagdo
     * @return Identificador del juzgado
     */
    public Long getIdJuzgado() {
        return idJuzgado;
    }

    /**
     * Metodo set que permite ingresar el Identificador del juzagdo
     * @param idJuzgado Identificador del juzagdo
     */
    public void setIdJuzgado(Long idJuzgado) {
        this.idJuzgado = idJuzgado;
    }

    /**
     * Metodo get que devuelve el Nombre del juzagdo
     * @return Nombre del juzagdo
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Metodo set que permite ingresar el Nombre del juzagdo
     * @param nombre Nombre del juzagdo
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Metodo get que devuelve la direccion del juzagdo
     * @return Direccion del juzagdo
     */
    public String getDireccionJuzgado() {
        return direccionJuzgado;
    }

    /**
     * Metodo set que permite ingresar la direccion del juzagdo
     * @param direccionJuzgado Direccion del juzagdo
     */
    public void setDireccionJuzgado(String direccionJuzgado) {
        this.direccionJuzgado = direccionJuzgado;
    }

    /**
     * Metodo get que devuelve el estado del juzagdo
     * @return Estado del juzgado
     */
    public String getEstadoJuzgado() {
        return estadoJuzgado;
    }

    /**
     * Metodo set que permite ingresar el estado del juzagdo
     * @param estadoJuzgado Estado del juzagdo
     */
    public void setEstadoJuzgado(String estadoJuzgado) {
        this.estadoJuzgado = estadoJuzgado;
    }
}
