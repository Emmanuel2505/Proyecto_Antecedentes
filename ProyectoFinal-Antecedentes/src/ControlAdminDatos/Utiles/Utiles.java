/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ControlAdminDatos.Utiles;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Cris2
 */
public class Utiles {

    static Connection conexion;
    static boolean estado = true;

    public Utiles() {
        if (estado) {
            IniciarConexion();
            estado = false;
        }

    }

    /**
     * Metodo get estatico que permite establecer conexion con la base de datos
     *
     * @return Retiorna una conexion de BDD
     */
    public Connection getConexion() {
        return conexion;
    }

    /**
     * Inicia o establece la coneccion con la base de datos
     */
    private void IniciarConexion() {
        Connection con = null;
        String Driver = "com.mysql.cj.jdbc.Driver";
        String Usuario = "root";
        String Contraseña = "12345";
        String NombreDB = "sistemaco_penal";
        String Puerto = "3306";
        String TimeZone = "?useUnicode=true&use"
                + "JDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false"
                + "&serverTimezone=UTC";
        try {
            Class.forName(Driver);
            con = DriverManager.getConnection("jdbc:mysql://localhost:" + Puerto + "/" + NombreDB + TimeZone, Usuario, Contraseña);
            System.out.println("Conexion establecida correctamente con la base de datos: " + NombreDB);
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Error estableciendo conexion con la base de datos: " + NombreDB
                    + "\nDetalles del error: \n" + e.getMessage());
        }
        conexion = con;

    }

    /**
     * Metodo que permite realizar la busqueda de imagenes en el directorio de
     * el computador
     *
     * @return Retorna una archivo de tipo imagen
     */
    public File BuscarImagen() {
        File file = null;
        JFileChooser archivo = new JFileChooser();
        archivo.setBackground(new Color(255, 204, 51));
        FileNameExtensionFilter filtro = new FileNameExtensionFilter("Formatos de Archivos JPEG(*.JPG;*.JPEG)", "jpg", "jpeg");
        archivo.addChoosableFileFilter(filtro);
        archivo.setDialogTitle("Abrir Archivo");
        File ruta = new File("C:/Pictures");
        archivo.setCurrentDirectory(ruta);
        int ventana = archivo.showOpenDialog(null);
        if (ventana == JFileChooser.APPROVE_OPTION) {
            file = archivo.getSelectedFile();
        }
        return file;
    }

    /**
     * Metodo que permite convertir imagenes de formato Blod a Imagen
     *
     * @param imagen Imagen Blod
     * @param d Dimensiones de la imagen
     * @return Retorna una imagen ImageIcon
     */
    public Image img(Blob imagen, Dimension d) {
        if (imagen != null) {
            Image rpta = null;
            try {
                rpta = javax.imageio.ImageIO.read(imagen.getBinaryStream());
                rpta = rpta.getScaledInstance(d.width, d.height, Image.SCALE_DEFAULT);
                return rpta;
            } catch (SQLException ex) {
                System.err.println("Error: " + ex.getMessage());
            } catch (IOException ex) {
                System.err.println("Error: " + ex.getMessage());
            }
        }
        return null;

    }
    /**
     * Metodo que permite scalar una imagen de acuerdo a su contenedor
     * @param imagen Imagen que ser escalada
     * @param d dimension que se necesita tenga la imagen
     * @return Imgen Scalada de acuerdo a la dimension requerida
     */
    public Image img(Image imagen, Dimension d) {
        if (imagen != null) {
            Image rpta = imagen;
            rpta = rpta.getScaledInstance(d.width, d.height, Image.SCALE_DEFAULT);
            return rpta;

        }
        return null;

    }
    /**
     * Convierte una Imagen de tipo Blod a Image
     * @param imagen Imgen de tipo Blod
     * @return Image 
     */
    public Image img(Blob imagen) {
        if (imagen != null) {
            Image rpta = null;
            try {

                rpta = javax.imageio.ImageIO.read(imagen.getBinaryStream());

                // rpta = rpta.getScaledInstance(d.width, d.height, Image.SCALE_DEFAULT);
                return rpta;
            } catch (SQLException ex) {
                System.err.println("Error: " + ex.getMessage());
            } catch (IOException ex) {
                System.err.println("Error: " + ex.getMessage());
            }
        }
        return null;

    }
    int cant = 0;
    /**
     * Convierte una Imagen de tipo Blod a una archivo de tipo File que seran guardados en la carpeta Perfiles
     * @param input Imgen Blod 
     * @param nombre Nombre que se desea tenga la imagen
     * @return File archivo de imagen almacenado en la carpeta Perfiles
     * @throws FileNotFoundException Excepcion
     * @throws IOException Excepcion
     * @throws SQLException  Excepcion
     */
    public File imagen(Blob input, String nombre) throws FileNotFoundException, IOException, SQLException {
        cant++;
        if (input != null) {
            long size = input.length();
            FileOutputStream output = null;
            File file = new File(nombre.hashCode() + cant + ".png");// el cant era con fines
            // didacticos para poder hacer pruebas por que usamos la misma cedula para probar
            // el software ya que usamos el numero de cedula como nombre de la imagen
            //ya que es unica nunca se repetira
            if (file.exists()) {
                file.delete();
            }
            output = new FileOutputStream("Perfiles/" + file.getPath());// le especifico la carpeta en que quiero 
            //que almacene la imagenes
            byte[] buffer = input.getBytes(1, Integer.parseInt(String.valueOf(size)));
            output.write(buffer);
            output.flush();
            System.out.println("Guardar en: " + file.getAbsolutePath() + "Original: " + nombre);
            return file;

        }
        return null;

    }
    /**
     * Convierte un archivo File en FileInputStream
     * @param file Archivo a convertir
     * @return FileInputStrean archivo de Bytes
     * @throws FileNotFoundException Excepcion
     * @throws IOException  Excepcion
     */
    public FileInputStream imagen(File file) throws FileNotFoundException, IOException {
        FileInputStream input = null;
        input = new FileInputStream(file);
        return input;

    }

    /**
     * Metodo que permite convertir imagenes de Image a Blod;
     *
     * @param img
     * @return
     */
    /**
     * Metodo que permite obtener la fecha actual
     *
     * @return Fecha y hora actual del sistema año-mes-dia hora:minutos:segundos
     */
    public String fechaActual() {
        String fechaHora = "";
        Calendar fecha = new GregorianCalendar();
        int año = fecha.get(Calendar.YEAR);
        int mes = fecha.get(Calendar.MONTH);
        int dia = fecha.get(Calendar.DAY_OF_MONTH);
        int hora = fecha.get(Calendar.HOUR_OF_DAY);
        int minuto = fecha.get(Calendar.MINUTE);
        int segundo = fecha.get(Calendar.SECOND);
        System.out.println("Fecha Actual: " + dia + "/" + (mes + 1) + "/" + año);
        System.out.printf("Hora Actual: %02d:%02d:%02d %n", hora, minuto, segundo);
        fechaHora = String.valueOf(año) + "-" + String.valueOf(mes + 1) + "-" + String.valueOf(dia) + " " + String.valueOf(hora) + ":" + String.valueOf(minuto) + ":" + String.valueOf(segundo);
        return fechaHora;
    }

    /**
     * Devuelve un sql con el idDetalle
     *
     * @param idDetalle Long
     * @return String - sql
     */
    public String tr(Long idDetalle) {
        String tr = "SELECT * FROM salidaservicio inner join servicio  using (idsalidaServicio) where idDetalle=" + idDetalle;
        return tr;
    }
    /**
     * Retorna un arreglo de File
     *
     * @return File[]
     */
    public File[] traerDirectorio(String ruta) {

        String path = "./img/";
        String files;
        File folder = new File(ruta);
        File[] listOfFiles = folder.listFiles();
        System.out.println(listOfFiles.length);
        File img[] = null;
        File imgaux[] = null;
        for (int i = 0; i < listOfFiles.length; i++) {
            //files=listOfFiles[i].getName();
            if (listOfFiles[i].isFile()) {
                files = listOfFiles[i].getName();
                if (files.endsWith(".jpg") || files.endsWith(".png")) {
                    if (img == null) {
                        System.out.println("Porque entra 1");
                        img = new File[1];
                    } else {
                        System.out.println("Porque entra 2");
                        imgaux = img;
                        img = new File[imgaux.length + 1];
                        copiar(img, imgaux);
                        imgaux = null;
                        System.out.println(files);
                    }

                    img[img.length - 1] = listOfFiles[i];
                }
            }

        }
        System.out.println("tm: " + img.length);
        return img;
    }

    /**
     * Clona un File
     *
     * @param uno File[]
     * @param dos File[]
     */
    public static void copiar(File[] uno, File[] dos) {
        for (int i = 0; i < dos.length; i++) {
            uno[i] = dos[i];
        }
    }
    /**
     * Metodo que valida un numero de Cedula (Ecuatoriano) sea valido haciendo
     * uso de los algoritmos de validacion mas comun
     * @param document String numero de cedula a verificar
     * @return Boolean true: numero de cedula valido false: numero de cedula invalido
     */
    public boolean isEcuadorianDocumentValid(String document) {
        byte sum = 0;
        try {
            if (document.trim().length() != 10) {
                return false;
            }
            String[] data = document.split("");
            byte verifier = Byte.parseByte(data[0] + data[1]);
            if (verifier < 1 || verifier > 24) {
                return false;
            }
            byte[] digits = new byte[data.length];
            for (byte i = 0; i < digits.length; i++) {
                digits[i] = Byte.parseByte(data[i]);
            }
            if (digits[2] > 6) {
                return false;
            }
            for (byte i = 0; i < digits.length - 1; i++) {
                if (i % 2 == 0) {
                    verifier = (byte) (digits[i] * 2);
                    if (verifier > 9) {
                        verifier = (byte) (verifier - 9);
                    }
                } else {
                    verifier = (byte) (digits[i] * 1);
                }
                sum = (byte) (sum + verifier);
            }
            if ((sum - (sum % 10) + 10 - sum) == digits[9]) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    /**
     * Valida la legitimidad de una direccion de correo electronico, no es totalmente efectivo pero,
     * pero funciona para la mayoria de Correos
     * @param correo Correo que se necesita validar
     * @return Boolean true: Correo valido, false: correo invalido
     */
    public Boolean validarDireccionCorreoElectronico(String correo) {
        Pattern pattern = Pattern
                .compile("^[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?$");
        Matcher mather = pattern.matcher(correo);
        return mather.find();

    }

    /**
     * El siguiente método permite extraer un dato
     *
     * @param obj
     * @param atributoClase
     * @return
     */
    public static String extraccionDato(Object obj, String atributoClase) {
        Class clase = obj.getClass();
        Field atributo = null;
        Object informacion = null;
        for (Field f : clase.getDeclaredFields()) {
            if (f.getName().toString().equalsIgnoreCase(atributoClase)) {
                atributo = f;
            }
        }
        if (atributo != null) {
            //  Method metodo = null;
            for (Method metodoAux : clase.getMethods()) {
                if (metodoAux.getName().startsWith("get")) {
                    if (metodoAux.getName().toLowerCase().endsWith(atributo.getName())) {
                        try {
                            informacion = metodoAux.invoke(obj);

                            break;
                        } catch (Exception e) {
                            System.out.println("Error de metodo " + e);
                        }
                    }
                }
            }
        }
        return (informacion != null) ? informacion.toString() : null;
    }
}
