/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador.Utilidades;

import Modelo.Juzgado;
import Vista.Utiles.TablaJuzgados.UtilidadesTablaJuzgado;
import java.util.ArrayList;

/**
 *
 * @author ASUS
 */
public class UtilGesJuz {
    /**
     * Obtiene la posición de la lista en el que se encuentra determinado juzgado
     * @param lista
     * @param nombre
     * @return 
     */
    public static int obtenerJuzgadoContenido(ArrayList<Juzgado> lista, String nombre){
       int pos = -1;
        for (int i = 0; i < lista.size(); i++) {
            Juzgado aux = lista.get(i);
            if (aux.getNombre().equalsIgnoreCase(nombre)) {
                pos = i;
                break;
            }
        }
       return pos;
    }
    /**
     * Obtiene la matriz que llenará la tabla de gestionar juzgados
     * con los datos de cada juzgado
     * @param titulosList
     * @param listaJuzgado
     * @return 
     */
    public static Object[][] obtenerMatrizDatos(ArrayList<String> titulosList, ArrayList<Juzgado> listaJuzgado) {

        /*se crea la matriz donde las filas son dinamicas pues corresponde
		 * a todos los usuarios, mientras que las columnas son estaticas
		 * correspondiendo a las columnas definidas por defecto
         */
        String informacion[][] = new String[listaJuzgado.size()][titulosList.size()];
        for (int x = 0; x < informacion.length; x++) {

            informacion[x][UtilidadesTablaJuzgado.NOMBRE] = listaJuzgado.get(x).getNombre() + "";
            informacion[x][UtilidadesTablaJuzgado.DIRECCION] = listaJuzgado.get(x).getDireccionJuzgado() + "";
            informacion[x][UtilidadesTablaJuzgado.ESTADO] = listaJuzgado.get(x).getEstadoJuzgado() + "";
            //se asignan las plabras clave para que en la clase GestionCeldas se use para asignar el icono correspondiente
            informacion[x][UtilidadesTablaJuzgado.BORRAR] = "PERFIL";
            informacion[x][UtilidadesTablaJuzgado.EDITAR] = "EVENTO";
        }

        return informacion;
    }
    /**
     * Comprueba que el juzgado no se repita
     * @param listaJuzgado
     * @param dato
     * @return 
     */
    public static boolean datoRepetido(ArrayList<Juzgado> listaJuzgado, Juzgado dato){
        boolean seRepite = false;
        for (int i = 0; i < listaJuzgado.size(); i++) {
            Juzgado aux = listaJuzgado.get(i);
            if (aux.getNombre().equalsIgnoreCase(dato.getNombre()) && aux.getDireccionJuzgado().equalsIgnoreCase(dato.getDireccionJuzgado())) {
                seRepite = true;
                break;
            }
        }
        return seRepite;
    }
}
