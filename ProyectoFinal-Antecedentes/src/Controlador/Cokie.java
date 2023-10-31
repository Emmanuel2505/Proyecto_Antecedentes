/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver;

/**
 *
 * @author hp
 */
public class Cokie {

    private String REPO = "datos";
    private XStream xtrStream;

    /**
     * Metodo para establecer conexion con Xstream
     */
    public Cokie() {
        xtrStream = new XStream(new JettisonMappedXmlDriver());
        xtrStream.setMode(XStream.NO_REFERENCES);
    }
    /**
     * Termina la ejecucion del framework XStream
     */
    public void cerrar(){
        xtrStream=new XStream(new JettisonMappedXmlDriver());
    }

    /**
     * recibe un String repo
     *
     * @param REPO String
     */
    public void setREPO(String REPO) {
        this.REPO = REPO;
    }

    /**
     * retorna un String repo
     *
     * @return String
     */
    public String getREPO() {
        return REPO;
    }

    /**
     * retorna un Xtream
     *
     * @return Xtream
     */
    public XStream getXtrStream() {
        return xtrStream;
    }
}
