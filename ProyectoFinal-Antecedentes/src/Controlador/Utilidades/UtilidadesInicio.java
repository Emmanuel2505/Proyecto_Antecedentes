/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador.Utilidades;

import Controlador.DelitoDao;
import static Controlador.Utilidades.UtilAgreGesAnt.listaDelito;
import Modelo.Proceso;
import Vista.Utiles.TablaAntecedentes.UtilTablaAnteInicio;
import java.util.ArrayList;

/**
 *
 * @author ASUS
 */
public class UtilidadesInicio {
    /**
     * Obtiene la matriz que llenará la tabla del inicio con los datos del delito cometido por la persona
     * @param titulosList
     * @param listaProceso
     * @param dd
     * @return 
     */
    public static Object[][] obtenerMatrizDatos(ArrayList<String> titulosList, ArrayList<Proceso> listaProceso, DelitoDao dd) {

        /*se crea la matriz donde las filas son dinamicas pues corresponde
		 * a todos los usuarios, mientras que las columnas son estaticas
		 * correspondiendo a las columnas definidas por defecto
         */
        String informacion[][] = new String[listaProceso.size()][titulosList.size()];
        for (int x = 0; x < informacion.length; x++) {
            informacion[x][UtilTablaAnteInicio.DELITO] = dd.find(listaProceso.get(x).getIdDelito()).getNombre() + "";
        }

        return informacion;
    }
}
