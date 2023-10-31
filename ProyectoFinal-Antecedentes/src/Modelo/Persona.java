package Modelo;

import java.io.File;

/**
 * Clase Persona
 * @author hp
 */
public class Persona {
	private Long idPersona;
	private String cedula;
	private String nombre;
	private String apellido;
	private String fechaNacimiento;
	private String direccion;
	private String estadoCivil;
        private String sexo;
	private String telefono;
	private String mail;
        private Boolean estado;
        private File imagen;
        private Long idRol;
        
    /**
     * Constructor Vacío del la clase Persona
     */
    public Persona(){
        
    }

    /**
     * CXonstructor parameterizado del la clase Persona
     * @param idPersona Identificador de la persona
     * @param cedula Cedula de la Persona
     * @param nombre Nombre de la persona
     * @param apellido Apellido de la persona
     * @param fechaNacimiento Fecha de nacimiento de la persona
     * @param direccion Direccion de residencia de la persona
     * @param estadoCivil Estado civil de la persona
     * @param sexo Sexo o genero de la persona
     * @param telefono Telefono Celular de contacto de la persona
     * @param mail Direccion de correo electronico de la persona
     * @param estado Estado de la persona
     * @param imagen Imgen de perfil de la persona
     * @param idRol Rol que ejerce la persona
     */
    public Persona(Long idPersona, String cedula, String nombre, String apellido, String fechaNacimiento, String direccion, String estadoCivil,String sexo, String telefono, String mail, Boolean estado, File imagen,Long idRol) {
        this.idPersona = idPersona;
        this.cedula = cedula;
        this.nombre = nombre;
        this.apellido = apellido;
        this.fechaNacimiento = fechaNacimiento;
        this.direccion = direccion;
        this.estadoCivil = estadoCivil;
        this.sexo = sexo;
        this.telefono = telefono;
        this.mail = mail;
        this.estado = estado;
        this.imagen = imagen;
        this.idRol=idRol;
    }
    
    /**
     * Metodo getque permite obtener el Identificador de la persona
     * @return Identificador de la persona
     */
    public Long getIdPersona() {
        return idPersona;
    }

    /**
     * Metodo set que permite ingresar el identificador de la persona
     * @param idPersona Identificador de la persona
     */
    public void setIdPersona(Long idPersona) {
        this.idPersona = idPersona;
    }

    /**
     * Metodo get que devuelve el numero de Cedula de la persona
     * @return Cedula de la persona
     */
    public String getCedula() {
        return cedula;
    }

    /**
     * Metodo set que permite inbgresar el numero de cedula de la persona
     * @param cedula Numero de cedula de la persona
     */
    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    /**
     * Metodo get que permite obtener el Nombre de la persona
     * @return Nombre de la persona
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Metodo set que permite ingresar el nombre de la persona
     * @param nombre Nombre de la persona
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Metodo get que devuelve el apellido de la persona
     * @return Apellido de la persona
     */
    public String getApellido() {
        return apellido;
    }

    /**
     * Metodo set que permite ingresar el apellido de la persona
     * @param apellido Apellido de la persona
     */
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    /**
     * Metodo get que devulve la fecha de nacimiento de la persona
     * @return Fecha de nacimiento de la persona
     */
    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    /**
     * Metodo set que permite ingresar la fecha de nacimiento de la persona
     * @param fechaNacimiento Fecha de nacimiento de la persona
     */
    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    /**
     * Metodo get que devuelve  la direccion de residencia de la persona
     * @return Direccion de la persona
     */
    public String getDireccion() {
        return direccion;
    }

    /**
     * Metodo set que permite ingresar la dierccion de la persona
     * @param direccion Direccion de la persona
     */
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    /**
     * Metodo get que devuelve el estado civil de la persona
     * @return Estado civil de la persona
     */
    public String getEstadoCivil() {
        return estadoCivil;
    }

    /**
     * Metodo set que permite ingresar el estado civil de la persona
     * @param estadoCivil Estado civil de la persona
     */
    public void setEstadoCivil(String estadoCivil) {
        this.estadoCivil = estadoCivil;
    }

    /**
     * Metodo get que devuelve el sexo o genero de la persona
     * @return Sexo oi genero de la persona
     */
    public String getSexo() {
        return sexo;
    }

    /**
     * Metodo set que permite ingresar el sexo o genero de la persona
     * @param sexo Sexo o genero de la persona
     */
    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    /**
     * Metodo get que devuelve el trelefono de contacto de la persona
     * @return Telefono de contacto de la persona
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * Metodo set que permite ingresar el numero de telefono de la persona
     * @param telefono Numero de telefono de la persona
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    /**
     * Metodo get que permite obtener el correo electronico de la persona
     * @return Correo electronico de la persona
     */
    public String getMail() {
        return mail;
    }

    /**
     * Metodo set que permite ingresar el correo electronico de la persona
     * @param mail correo electronico de la persona
     */
    public void setMail(String mail) {
        this.mail = mail;
    }

    /**
     * Metodo get que devuelve el estado de la persona 
     * @return Estado de la persona
     */
    public Boolean getEstado() {
        return estado;
    }
    
    /**
     * Metodo set que permite ingresar el estado de la persona
     * @param estado Estado de la persona
     */
    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    /**
     * Metodo get que permite obtener un archivo de imagen
     * @return Un archivo de imagen
     */
    public File getFile() {
        return imagen;
    }

    /**
     * Metodo set que permite ingresar el archivo de imagen de perfil de la persona
     * @param imagen Archivo de Imgen de la persona
     */
    public void setFile(File imagen) {
        this.imagen = imagen;
    }

    /**
     * Metodo get que devuelve el Identificador del rol
     * @return Identificador del rol
     */
    public Long getIdRol(){
        return idRol;
    }

    /**
     * Metodo set que permite ingresar el Identificador del rol que cumple la persona
     * @param idRol Rol que cumple la persona
     */
    public void setIdRol(Long idRol){
       this.idRol=idRol; 
    }

    @Override
    public String toString() {
        return "Persona{" + "idPersona=" + idPersona + ", cedula=" + cedula + ", nombre=" + nombre + ", apellido=" + apellido + ", fechaNacimiento=" + fechaNacimiento + ", direccion=" + direccion + ", estadoCivil=" + estadoCivil + ", sexo=" + sexo + ", telefono=" + telefono + ", mail=" + mail + ", estado=" + estado + ", imagen=" + imagen + ", idRol=" + idRol + '}';
    }
}
