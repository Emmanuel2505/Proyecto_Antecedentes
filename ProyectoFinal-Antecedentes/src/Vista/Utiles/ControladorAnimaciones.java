/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista.Utiles;

import java.awt.Color;
import java.awt.Dimension;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 *
 * @author Cris2
 */
public class ControladorAnimaciones {

    /**
     * Este metodo sirve para Desplegar o plegar el menu de opciones lateral en
     * su totalidad
     *
     * @param panel Contenedor del menu de opciones
     * @param boton Emite la accion para que la animacion o transicion se
     * produca
     */
    public void animacionPanelMenu(JPanel panel, JLabel boton) {
        if (panel.getSize().width == 230) {
            panel.setSize(230, 600);
            boton.setLocation(230, 35);
            Timer timer;
            timer = new Timer();
            TimerTask task = new TimerTask() {
                int tic = 230;
                int cont = 770;

                /**
                 * Meto abstracto de la clase Timer que inicia la animacion
                 */
                @Override
                public void run() {
                    panel.setSize(tic, 600);
                    boton.setLocation(tic, 35);
                    if (tic == 0) {
                        boolean cancel = cancel();
                    }
                    cont++;
                    tic--;

                }
            };
            timer.schedule(task, 1, 1);
        } else if (panel.getSize().getWidth() == 0) {
            panel.setSize(0, 600);
            boton.setLocation(0, 35);
            Timer timer;
            timer = new Timer();
            TimerTask task = new TimerTask() {
                int tic = 0;
                int cont = 1000;

                /**
                 * Meto abstracto de la clase Timer que inicia la animacion
                 */
                @Override
                public void run() {
                    panel.setSize(tic, 600);
                    boton.setLocation(tic, 35);
                    if (tic == 230) {
                        boolean cancel = cancel();
                    }
                    cont--;
                    tic++;

                }
            };
            timer.schedule(task, 1, 1);

        }

    }

    /**
     * Este metodo tine la tarea de Animar los items cuando estos son
     * desplegados
     *
     * @param item1 Item principal sobre el cual se desencadena el depliegue de
     * los subitems
     * @param conten Contenedor que contiene al Itemp padre y los subitems
     * @param visto Pestaña que indica que se ha desplegado los subitems
     * @param heighmax amplitud de altura maximo que tendra la secion
     * desplegable de los subitems
     * @param heightmin amplitud minima que tendra la seccion desplegable de
     * subitems
     * @param time tiempo en segundos que tarda la animacion
     * @param contraer estado de la accion desplega o plega el submenu o
     * subitems
     */
    public void animacionItems(JPanel item1, JPanel conten, JLabel visto, int heighmax, int heightmin, int time, int contraer) {
        if (contraer == 1) {
            if (conten.getSize().height == heighmax) {
                item1.setOpaque(true);
                conten.setSize(260, heighmax);
                visto.setText("◄");
                Timer timer;
                timer = new Timer();
                TimerTask task = new TimerTask() {
                    int tic = heighmax;
                    int cont = 0;

                    @Override
                    public void run() {
                        conten.setSize(260, tic);
                        if (tic == heightmin) {
                            boolean cancel = cancel();

                        }
                        tic--;

                    }
                };
                timer.schedule(task, time, time);
            }
        } else {
            if (conten.getSize().height == heighmax) {
                item1.setOpaque(true);
                conten.setSize(260, heighmax);
                visto.setText("◄");
                Timer timer;
                timer = new Timer();
                TimerTask task = new TimerTask() {
                    int tic = heighmax;
                    int cont = 0;

                    @Override
                    public void run() {
                        conten.setSize(260, tic);
                        if (tic == heightmin) {
                            boolean cancel = cancel();

                        }
                        tic--;

                    }
                };
                timer.schedule(task, time, time);
            } else if (conten.getSize().height == heightmin) {
                item1.setOpaque(false);
                conten.setBackground(new Color(25, 51, 89));
                conten.setSize(260, heightmin);
                visto.setText("▼");
                Timer timer;
                timer = new Timer();
                TimerTask task = new TimerTask() {
                    int tic = heightmin;
                    int cont = 0;

                    @Override
                    public void run() {
                        conten.setSize(260, tic);
                        conten.repaint();
                        if (tic == heighmax) {
                            boolean cancel = cancel();

                        }
                        tic++;

                    }
                };
                timer.schedule(task, time, time);

            }
        }

    }

    /**
     * Este metodo tiene la función de mover o empujar hacia abajo cambiando la
     * posicion de los items del menu cuando otro item o submenu es desplegado
     *
     * @param conten Subcontenedor de el submenu o subitems
     * @param posicion1 Posicion inicial del submenu o item
     * @param posicion2 Posicion final o tope de submenu o item
     * @param time tiempo de duracion de la animacion
     * @param contraer Estao posicion inicial a final o viceversa no usado
     */
    public void animacionItemsLocalizacion(JPanel conten, int posicion1, int posicion2, int time, int contraer) {

        if (conten.getLocation().y == posicion2) {

            conten.setLocation(0, posicion2);
            Timer timer;
            timer = new Timer();
            TimerTask task = new TimerTask() {
                int tic = posicion2;
                int cont = 0;

                @Override
                public void run() {
                    conten.setLocation(0, tic);

                    if (tic == posicion1) {

                        boolean cancel = cancel();

                    }
                    tic--;

                }
            };
            timer.schedule(task, time, time);
        } else if (conten.getLocation().y == posicion1) {
            conten.setLocation(0, posicion1);
            Timer timer;
            timer = new Timer();
            TimerTask task = new TimerTask() {
                int tic = posicion1;
                int cont = 0;

                @Override
                public void run() {
                    conten.setLocation(0, tic);
                    conten.repaint();
                    if (tic == posicion2) {
                        boolean cancel = cancel();

                    }
                    tic++;

                }
            };
            timer.schedule(task, time, time);

        }
    }

    /**
     * Metodo encargado de mostrar visualmente el conflicto producto de que se
     * deplegue mas de un submenu o sub item, esto debido a el proceso de
     * animacion de el menu que falla si esto no es controlado pinta de un color
     * rogiso cuando sucede por unos segundos
     *
     * @param item item controlado
     */
    public void colorColapse(JPanel item) {
        Color color = item.getBackground();
        Timer timer;
        item.setOpaque(true);
        timer = new Timer();
        TimerTask task = new TimerTask() {
            int tic = 255;
            int cont = 0;

            @Override
            public void run() {
                item.setBackground(new Color(tic, 105, 105));

                if (tic == 70) {
                    item.setOpaque(false);
                    item.setBackground(color);
                    boolean cancel = cancel();
                }
                tic--;

            }
        };
        timer.schedule(task, 1, 1);
    }

    /**
     * Metodo utilitario no usado aun
     *
     * @param time tiempo de respuesta
     * @return
     */
    public boolean retardo(int time) {
        Timer timer;
        timer = new Timer();
        int co = 0;
        TimerTask task;
        task = new TimerTask() {
            int tic = 0;
            int cantidad = 100;

            @Override
            public void run() {
                if (tic == cantidad) {
                    boolean cancel = cancel();
                }
                tic++;
            }
        };
        timer.schedule(task, time, time);
        return true;
    }

    /**
     * Metodo que se uso en una version anterior de este proyecto para animar un
     * ScrollBar metodo sin uso por el momento
     *
     * @param scroll
     * @param posicion1
     * @param posicion2
     * @param time
     */
    public void animacionScroll(JScrollPane scroll, int posicion1, int posicion2, int time) {

        if (scroll.getVerticalScrollBar().getValue() < posicion2) {
            Timer timer;
            timer = new Timer();
            TimerTask task = new TimerTask() {
                int tic = posicion1;
                int cont = 0;

                @Override
                public void run() {
                    scroll.getVerticalScrollBar().setValue(tic);
                    scroll.updateUI();
                    scroll.repaint();
                    if (tic == posicion2) {
                        boolean cancel = cancel();
                    }
                    tic++;

                }
            };
            timer.schedule(task, time, time);

        }
    }
}
