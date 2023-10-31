/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista.Utiles.TablaPersonas;

import Vista.Utiles.TablaPersonas.EstadoCivil;
import Vista.Utiles.TablaPersonas.Sexo;

/**
 *
 * @author hp
 */
public class ConvertirEnums {

    /**
     * Estado civil 
     * @param arr Arr de estado civil
     * @return Array de estado civil
     */
    public String[] convertEstadoCivil(EstadoCivil arr[]){
        String estado[]=new String[arr.length];
        EstadoCivil est[]=EstadoCivil.values();
        for (int i = 0; i < EstadoCivil.values().length; i++) {
            estado[i]=est[i].name();
        }
        return estado;
        
    }

    /**
     * Sexo
     * @param arr Arreglo de generos 
     * @return array de generos
     */
    public String[] convertSexo(Sexo arr[]){
        String sexo[]=new String[Sexo.values().length];
        Sexo sx[]=Sexo.values();
        for (int i = 0; i < Sexo.values().length; i++) {
            sexo[i]=sx[i].name();
        }
        return sexo;
        
    }
    
}
