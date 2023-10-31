/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.io.File;
import java.util.Date;

/**
 *
 * @author ASUS
 */
public class Proceso {
    private Long idProceso;
    private int instancia;
    private String fechaInicio;
    private String fechaFinal;
    private int nrAudiencias;
    private byte[] text;
    private String nombreDocumento;
    private String estadoVictimario;
    private String estadoDemanda;
    private String estadoProceso;
    private Long idDelito;
    private Long idPersona;
    private Long idCondena;
    private Long idJuzgado;

    /**
     * Constructor parameterizado de la clase Proceso
     * @param idProceso Identificador del proceso
     * @param instancia Instancia en la que se encuentra el proceso
     * @param fechaInicio Fecha de inicio del proceso
     * @param fechaFinal Fecha de finalizacion del proceso
     * @param nrAudiencias Nro de audiencias del proceso
     * @param text Documento
     * @param nombreDocumento Nombre del documento
     * @param estadoVictimario Estado del victimario o acusado
     * @param estadoDemanda Estado de la demanda
     * @param estadoProceso Estado del proceso
     * @param idDelito Identificador del delito cometido
     * @param idPersona Identificador de la persona demandada
     * @param idCondena Condena establecida para la persona demandada
     * @param idJuzgado Identificador del juzgado en el que se lleva el proceso
     */
    public Proceso(Long idProceso, int instancia, String fechaInicio, String fechaFinal, int nrAudiencias, byte[] text, String nombreDocumento, String estadoVictimario, String estadoDemanda, String estadoProceso, Long idDelito, Long idPersona, Long idCondena, Long idJuzgado) {
        this.idProceso = idProceso;
        this.instancia = instancia;
        this.fechaInicio = fechaInicio;
        this.fechaFinal = fechaFinal;
        this.nrAudiencias = nrAudiencias;
        this.text = text;
        this.nombreDocumento = nombreDocumento;
        this.estadoVictimario = estadoVictimario;
        this.estadoDemanda = estadoDemanda;
        this.estadoProceso = estadoProceso;
        this.idDelito = idDelito;
        this.idPersona = idPersona;
        this.idCondena = idCondena;
        this.idJuzgado = idJuzgado;
    }

    /**
     * Metodo que permite obtener el Identificador de el proceso
     * @return Identificador del proceso
     */
    public Long getIdProceso() {
        return idProceso;
    }

    /**
     * Metodo set que permite ingresar el identificador de el proceso
     * @param idProceso Identificador del proceso
     */
    public void setIdProceso(Long idProceso) {
        this.idProceso = idProceso;
    }

    /**
     * Metodo get que devuelve la instacia del proceso
     * @return Instancia del proceso
     */
    public int getInstancia() {
        return instancia;
    }

    /**
     * Metodo set que permite ingresar la instancia de el proceso
     * @param instancia Instancia del proceso
     */
    public void setInstancia(int instancia) {
        this.instancia = instancia;
    }

    /**
     * Metodo get que devuelve la fecha de inicio del proceso
     * @return Fecha de inicio del proceso
     */
    public String getFechaInicio() {
        return fechaInicio;
    }

    /**
     * Metodo set que permite ingresar la fecha de inicio del proceso
     * @param fechaInicio Fecha de inicio del proceso
     */
    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    /**
     * Metodo get que devuelve la fecha de finalizacion del proceso
     * @return Fecha de finalizacion del proceso
     */
    public String getFechaFinal() {
        return fechaFinal;
    }

    /**
     * Metodo set que permite ingresar la fecha de inicio del proceso
     * @param fechaFinal Fecha de finalizacion del proceso
     */
    public void setFechaFinal(String fechaFinal) {
        this.fechaFinal = fechaFinal;
    }

    /**
     * Metodo get que devuelve el nro de audiencias 
     * @return Nro de audiencias
     */
    public int getNrAudiencias() {
        return nrAudiencias;
    }

    /**
     * Metodo set que permite ingresar el nro de audiencias que se han llevado a cabo
     * @param nrAudiencias Nro de audiencias 
     */
    public void setNrAudiencias(int nrAudiencias) {
        this.nrAudiencias = nrAudiencias;
    }

    /**
     * Metodo get que devuelve un array de bytes del archivo
     * @return Arreglo de bytes del archivo
     */
    public byte[] getText() {
        return text;
    }

    /**
     *Metodo que permite ingresar un array de bytes del documento pdf
     * @param text Array de bytes del documento pdf
     */
    public void setText(byte[] text) {
        this.text = text;
    }

    /**
     * Metodo get que devuelve el nombre del documento
     * @return Nombre del documento
     */
    public String getNombreDocumento() {
        return nombreDocumento;
    }

    /**
     * Metodo set que permite ingresar el Nnombre de documento pdf
     * @param nombreDocumento Nombre del documento pdf
     */
    public void setNombreDocumento(String nombreDocumento) {
        this.nombreDocumento = nombreDocumento;
    }

    /**
     * Metodo get que devuelve el estado del victimario o demndado
     * @return Estado del victimario o demandado
     */
    public String getEstadoVictimario() {
        return estadoVictimario;
    }

    /**
     * Metodo set que permite ingresar el estado de la persona acusado o demndada
     * @param estadoVictimario Estado de la persona acusada o demandada
     */
    public void setEstadoVictimario(String estadoVictimario) {
        this.estadoVictimario = estadoVictimario;
    }

    /**
     *Metodo get que devuelve el estado de la demanda
     * @return Estado de la demanda
     */
    public String getEstadoDemanda() {
        return estadoDemanda;
    }

    /**
     * Metodo set que permite ingresar el estado de la demanda
     * @param estadoDemanda Estado de la demnada
     */
    public void setEstadoDemanda(String estadoDemanda) {
        this.estadoDemanda = estadoDemanda;
    }

    /**
     * Metodo get que devuelve el etado del proceso
     * @return Estado del proceso
     */
    public String getEstadoProceso() {
        return estadoProceso;
    }

    /**
     * Metodo set que permite ingresar el estado del proceso
     * @param estadoProceso Estado del proceso
     */
    public void setEstadoProceso(String estadoProceso) {
        this.estadoProceso = estadoProceso;
    }

    /**
     * Metod get que devuelve el Identificador del delito
     * @return Identificador del delito
     */
    public Long getIdDelito() {
        return idDelito;
    }

    /**
     * Metodo set que permite ingresar el Identificador del delito
     * @param idDelito Identificador del delito
     */
    public void setIdDelito(Long idDelito) {
        this.idDelito = idDelito;
    }

    /**
     * Metodo get que devuleve el Identificador de la persona acusada
     * @return Identificador de la persona acusada
     */
    public Long getIdPersona() {
        return idPersona;
    }

    /**
     * Metodo set que permite ingresar el Identificador de la perona involucrada
     * @param idPersona Identificador de la persona
     */
    public void setIdPersona(Long idPersona) {
        this.idPersona = idPersona;
    }

    /**
     * Metodo get que devuelve el Identificador de la condena 
     * @return Identificador de la condena
     */
    public Long getIdCondena() {
        return idCondena;
    }

    /**
     * Metodo set que poermite ingresar el Identificador de la persona
     * @param idCondena Identificador de la persona
     */
    public void setIdCondena(Long idCondena) {
        this.idCondena = idCondena;
    }

    /**
     * Metodo get que devuelve el Identificador del juzgado en el que se lleva el proceso
     * @return Identificador de juzagdo
     */
    public Long getIdJuzgado() {
        return idJuzgado;
    }

    /**
     * Metodo set que permite ingresar em Identificador del Juzagado en el que se lleva el proceso
     * @param idJuzgado Identificador del juzagado
     */
    public void setIdJuzgado(Long idJuzgado) {
        this.idJuzgado = idJuzgado;
    }
}
