/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import java.io.File;

/**
 *
 * @author hp
 */
public class CrearDirectorios {
    /**
     * Crea los directorios necesarios para el sistema en caso de que no existan
     */
    public CrearDirectorios() {
         File fileCesion=new File("./Cesion");
        if (fileCesion.exists()==false) {
            System.out.println("Creada:"+fileCesion.mkdir()); 
        }
        File filePerfil=new File("./Perfiles");
        if (filePerfil.exists()==false) {
            System.out.println("Crear 2"+ filePerfil.mkdir());
        }
    }
    
}
