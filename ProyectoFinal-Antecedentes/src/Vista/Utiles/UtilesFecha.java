package Vista.Utiles;


import com.toedter.calendar.JDateChooser;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Clase Utiles que permite dar formato a fechas para que sea posible su ingreso a la base de datos
 * @author hp
 */
public class UtilesFecha {

    SimpleDateFormat Formato = new SimpleDateFormat("yyy-MM-dd");
    //
    /**
     * Metodo get que devuelve una fecha
     * @param jd JDateChoser 
     * @return Fecha con formato
     */
    public String getFecha(JDateChooser jd) {
        if (jd.getDate() != null) {
            return Formato.format(jd.getDate());
        } else {
            return null;
        }
    }
    /**
     * Permite transformar una fecha en string en un dato tipo Date
     * @param fecha fecha que se desea transformar
     * @return  Date fecha obtenida apartir de el string
     */
    public java.util.Date StringADate(String fecha) {
        SimpleDateFormat formato_del_Texto = new SimpleDateFormat("yyy-MM-dd");
        Date fechaE = null;
        try {
            fechaE = formato_del_Texto.parse(fecha);
            return fechaE;
        } catch (ParseException ex) {
            return null;
        }
    }
}
