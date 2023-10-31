/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

/**
 *
 * @author ASUS
 */
public class Delito {
    private Long idDelito;
    private String nombre;
    private String articulo;
    private String descripcion;
    private String tipoSancion;
    private String estadoDelito;
    
    /**
     * Constructor vacío de la clase cuenta
     */
    public Delito() {
    }
    /**
     * Constructor parameterizado de la clase Delito
     * @param idDelito Identificador de delito
     * @param nombre Nombre del delito
     * @param articulo Articulo en el que se fundamenta el delito
     * @param descripcion Descripcion del delito
     * @param tipoSancion Tipo de sancion impuesta
     * @param estadoDelito  Estado del delito
     */
    public Delito(Long idDelito, String nombre, String articulo, String descripcion, String tipoSancion, String estadoDelito) {
        this.idDelito = idDelito;
        this.nombre = nombre;
        this.articulo = articulo;
        this.descripcion = descripcion;
        this.tipoSancion = tipoSancion;
        this.estadoDelito = estadoDelito;
    }

    /**
     * Metodo get que devuelve el identificador de el delito
     * @return Identificador del delito
     */
    public Long getIdDelito() {
        return idDelito;
    }

    /**
     * Metodo set que permite ingresar el identificador del delito
     * @param idDelito Identificador del delito
     */
    public void setIdDelito(Long idDelito) {
        this.idDelito = idDelito;
    }

    /**
     * Metodo get que devuelve el nombre del delito
     * @return Nombre del delito
     */
    public String getNombre() {
        return nombre;
    }
    /**
     * Metodo set que permite ingresar el nombre del delito
     * @param nombre Nombre del delito
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Metodo get que devuelve el Articulo en el que se fundamntea el delito
     * @return Articulo en el que se fundamenta el delito
     */
    public String getArticulo() {
        return articulo;
    }
    
    /**
     * Metodo set que permite ingrear el articulo que fundamenta el delito
     * @param articulo Articulo
     */
    public void setArticulo(String articulo) {
        this.articulo = articulo;
    }

    /**
     * Metodo get que devuelve la Descripcion de el delito
     * @return Descripcion del delito
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Metodo set que permite ingresar la descripcion de el delito
     * @param descripcion Descripcion del delito
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Metodo get que devuelve el tipo de sancion 
     * @return Tipo de sancion
     */
    public String getTipoSancion() {
        return tipoSancion;
    }

    /**
     * Metodo set que permite ingresar el tipo de sancion 
     * @param tipoSancion Tipo de sancion
     */
    public void setTipoSancion(String tipoSancion) {
        this.tipoSancion = tipoSancion;
    }

    /**
     * Metodo get que devuelve el estado del delito
     * @return Estado del delito
     */
    public String getEstadoDelito() {
        return estadoDelito;
    }

    /**
     * Metodo set que permite ingrear el estado del delito
     * @param estadoDelito Estado del delito
     */
    public void setEstadoDelito(String estadoDelito) {
        this.estadoDelito = estadoDelito;
    }

    @Override
    public String toString() {
        return "Delito{" + "idDelito=" + idDelito + ", nombre=" + nombre + ", articulo=" + articulo + ", descripcion=" + descripcion + ", tipoSancion=" + tipoSancion + ", estadoDelito=" + estadoDelito + '}';
    }
}
