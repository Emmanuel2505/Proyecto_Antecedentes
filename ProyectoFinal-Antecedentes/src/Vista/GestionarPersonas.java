/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

import Modelo.Persona;
import Modelo.Persona;
import Vista.Utiles.GestionCeldas;
import Vista.Utiles.GestionEncabezadoTabla;
import Vista.Utiles.ModeloTabla;
import Vista.Utiles.TablaPersonas.Utilidades;
import ControlAdminDatos.Utiles.Utiles;
import Controlador.ControladorPersona;
import Vista.Utiles.TablaPersonas.ConvertirEnums;
import Vista.Utiles.TablaPersonas.EstadoCivil;
import java.awt.Color;
import java.awt.Image;
import java.awt.ScrollPane;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.table.JTableHeader;

/**
 *
 * @author hp
 */
public class GestionarPersonas extends javax.swing.JPanel {

    /**
     * Creates new form GestionarPersonas
     */
    private JPanel contentPane;
    private JScrollPane scrollPaneTabla;
    
    ModeloTabla modelo;
    private int filasTabla;
    private int columnasTabla;
    Utiles uti = new Utiles();
    ConvertirEnums enums = new ConvertirEnums();
    ControladorPersona ctr = new ControladorPersona();
    ImageIcon icoVacio;
    
    public GestionarPersonas() {
        initComponents();
        ctr.listaPersonas();
        ctr.buscar(null, "ALL");
        construirTabla();
        icoVacio = new ImageIcon("Vista/Acces/Imagenes/UsuarioImg.png");
        jScrollPane1.getViewport().setBackground(Color.white);
//        jScrollPane1.setVerticalScrollBar(new JScrollBar(JScrollBar.VERTICAL));
        cbEstadoCivil.setModel(new javax.swing.DefaultComboBoxModel<>(enums.convertEstadoCivil(EstadoCivil.values())));
        
    }
    /**
     * Actuliza los datos de las listas para mostrarlos en la tabla
     */
    public void actualizar() {
        ctr.VaciarListComun();
        ctr.listaPersonas();
        ctr.VaciarTemp();
        ctr.buscar(null, "ALL");
        construirTabla();
        System.out.println("Entro a este metodo");
    }
    /**
     * llama al metodo Construir tabla y lo ejecuta
     */
    private void construirTabla() {
        
        ArrayList<String> titulosList = new ArrayList<>();
        titulosList.add("Cedula");
        titulosList.add("Nombre");
        titulosList.add("Apellido");
        titulosList.add("F.Nacimiento");
        titulosList.add("Dirección");
        titulosList.add("E.Civil");
        titulosList.add("Telefono");
        titulosList.add("E-mail");
        titulosList.add(" ");
        titulosList.add(" ");
        
        String titulos[] = new String[titulosList.size()];
        for (int i = 0; i < titulos.length; i++) {
            titulos[i] = titulosList.get(i);
        }
        Object[][] data = ctr.obtenerMatrizDatos(titulosList);
        construirTabla(titulos, data);
        
    }
    /**
     * Construye una tabla ingresandole su data y los datos del encabezado o header
     * @param titulos Datos que conformaran el Header de la tabla
     * @param data Informacion que rellenara la tabla
     */
    private void construirTabla(String[] titulos, Object[][] data) {
        modelo = new ModeloTabla(data, titulos);
        modelo.setRowCount(0);
        modelo = new ModeloTabla(data, titulos);
        tablaPersonas.setModel(modelo);
        columnasTabla = tablaPersonas.getColumnCount();
        tablaPersonas.getColumnModel().getColumn(Utilidades.PERFIL).setCellRenderer(new GestionCeldas("icono"));
        tablaPersonas.getColumnModel().getColumn(Utilidades.EVENTO).setCellRenderer(new GestionCeldas("icono"));
        for (int i = 0; i < titulos.length - 2; i++) {
            tablaPersonas.getColumnModel().getColumn(i).setCellRenderer(new GestionCeldas("texto"));
        }
        
        tablaPersonas.getTableHeader().setReorderingAllowed(false);
        tablaPersonas.setRowHeight(25);
        tablaPersonas.setGridColor(new java.awt.Color(0, 0, 0));
        tablaPersonas.getColumnModel().getColumn(Utilidades.CEDULA).setPreferredWidth(125);
        tablaPersonas.getColumnModel().getColumn(Utilidades.NOMBRE).setPreferredWidth(202);
        tablaPersonas.getColumnModel().getColumn(Utilidades.APELLIDO).setPreferredWidth(202);
        tablaPersonas.getColumnModel().getColumn(Utilidades.FECHANACIMIENTO).setPreferredWidth(125);
        tablaPersonas.getColumnModel().getColumn(Utilidades.DIRECCION).setPreferredWidth(225);
        tablaPersonas.getColumnModel().getColumn(Utilidades.ESTADOCIVIL).setPreferredWidth(90);
        tablaPersonas.getColumnModel().getColumn(Utilidades.TELEFONO).setPreferredWidth(110);
        tablaPersonas.getColumnModel().getColumn(Utilidades.MAIL).setPreferredWidth(100);
        tablaPersonas.getColumnModel().getColumn(Utilidades.PERFIL).setPreferredWidth(30);
        tablaPersonas.getColumnModel().getColumn(Utilidades.EVENTO).setPreferredWidth(30);
        JTableHeader jtableHeader = tablaPersonas.getTableHeader();
        jtableHeader.setDefaultRenderer(new GestionEncabezadoTabla());
        tablaPersonas.setTableHeader(jtableHeader);
        
    }
    /**
     * Valida la seleccion de el boton dar de baja de la tabla
     * @param fila fila en la cual se ejecutara el evento de dar de baja y se encuentra el boton presionado
     */
    private void validarSeleccionMouse(int fila) {
        Utilidades.filaSeleccionada = fila;
        Long id = ctr.getListComun().get(tablaPersonas.getSelectedRow()).getIdPersona();
        int confirm = JOptionPane.showConfirmDialog(null, "Esta seguro de su decision??");
        if (confirm == 0) {
            ctr.darBaja(id);
            ctr.VaciarListComun();
            ctr.VaciarTemp();
            ctr.listaPersonas();
            ctr.buscar(null, "ALL");
            construirTabla();
            if (ctr.isCorrect()) {
                JOptionPane.showMessageDialog(null, "Se ha dado de baja correctamente");
            } else {
                JOptionPane.showMessageDialog(null, "Se ha producido un error mientras intentaba dar de baja");
            }
        }
    }
    /**
     * Valida la seleccion del boton Editar de la tabla
     * @param fila fila donde se encuentra el boton de editar presionado
     * @throws IOException 
     */
    private void validarSeleccionMouse2(int fila) throws IOException {
        Utilidades.filaSeleccionada = fila;
        ctr.setPersona(ctr.getListTemporal().get(tablaPersonas.getSelectedRow()));
        txtEmail.setText(ctr.agregarPersona().getMail());
        txtTelefono.setText(ctr.agregarPersona().getTelefono());
        txtDireccion.setText(ctr.agregarPersona().getDireccion());
        cbEstadoCivil.setSelectedItem(ctr.agregarPersona().getEstadoCivil());
        foto.setImagen(icoVacio);
        if (ctr.agregarPersona().getFile() != null) {
            ImageIcon ico = new ImageIcon("Perfiles/"+ctr.agregarPersona().getFile().getPath());
            foto.setImagen(ico);
        } else {
            foto.setImagen(icoVacio);
        }
        
    }

    //versiones
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tablaPersonas = new javax.swing.JTable();
        txtEmail = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtTelefono = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtDireccion = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        cbEstadoCivil = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel10 = new javax.swing.JLabel();
        txtBucarCedula = new javax.swing.JTextField();
        txtBuscarNombre = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        btnGuardar = new javax.swing.JLabel();
        lblEmailValid = new javax.swing.JLabel();
        lblTelefono = new javax.swing.JLabel();
        lblDireccion = new javax.swing.JLabel();
        foto = new rojerusan.RSPanelCircleImage();
        botonluna = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));
        setMinimumSize(new java.awt.Dimension(1000, 610));
        setPreferredSize(new java.awt.Dimension(1000, 610));
        setLayout(null);

        jScrollPane1.setBackground(new java.awt.Color(255, 255, 255));

        tablaPersonas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tablaPersonas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaPersonasMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tablaPersonas);

        add(jScrollPane1);
        jScrollPane1.setBounds(26, 124, 956, 200);

        txtEmail.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
        txtEmail.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtEmailKeyReleased(evt);
            }
        });
        add(txtEmail);
        txtEmail.setBounds(30, 390, 290, 30);

        jLabel6.setForeground(new java.awt.Color(49, 49, 49));
        jLabel6.setText("E-mail");
        add(jLabel6);
        jLabel6.setBounds(30, 370, 290, 14);

        txtTelefono.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
        txtTelefono.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTelefonoKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtTelefonoKeyTyped(evt);
            }
        });
        add(txtTelefono);
        txtTelefono.setBounds(30, 450, 290, 30);

        jLabel7.setForeground(new java.awt.Color(49, 49, 49));
        jLabel7.setText("Telefono");
        add(jLabel7);
        jLabel7.setBounds(30, 430, 290, 14);

        txtDireccion.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
        txtDireccion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtDireccionKeyReleased(evt);
            }
        });
        add(txtDireccion);
        txtDireccion.setBounds(30, 510, 290, 30);

        jLabel8.setForeground(new java.awt.Color(49, 49, 49));
        jLabel8.setText("Dirección");
        add(jLabel8);
        jLabel8.setBounds(30, 490, 290, 14);
        add(jLabel1);
        jLabel1.setBounds(80, 340, 240, 20);

        jLabel2.setText("Nombre: ");
        add(jLabel2);
        jLabel2.setBounds(30, 340, 50, 20);
        add(jLabel3);
        jLabel3.setBounds(390, 340, 240, 20);

        jLabel4.setText("Cedula:");
        add(jLabel4);
        jLabel4.setBounds(340, 340, 50, 20);

        cbEstadoCivil.setBackground(new java.awt.Color(240, 240, 240));
        cbEstadoCivil.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Estado Civil" }));
        add(cbEstadoCivil);
        cbEstadoCivil.setBounds(550, 390, 190, 30);

        jLabel5.setForeground(new java.awt.Color(49, 49, 49));
        jLabel5.setText("Estado Civil");
        add(jLabel5);
        jLabel5.setBounds(550, 370, 290, 14);

        jLabel9.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabel9.setText("Gestionar Personas");
        add(jLabel9);
        jLabel9.setBounds(70, 10, 320, 40);
        add(jSeparator1);
        jSeparator1.setBounds(10, 50, 980, 10);

        jLabel10.setForeground(new java.awt.Color(49, 49, 49));
        jLabel10.setText("Cedula");
        add(jLabel10);
        jLabel10.setBounds(25, 65, 290, 10);

        txtBucarCedula.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(13, 117, 225)));
        txtBucarCedula.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBucarCedulaKeyReleased(evt);
            }
        });
        add(txtBucarCedula);
        txtBucarCedula.setBounds(25, 80, 290, 30);

        txtBuscarNombre.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(13, 117, 225)));
        txtBuscarNombre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscarNombreKeyReleased(evt);
            }
        });
        add(txtBuscarNombre);
        txtBuscarNombre.setBounds(430, 80, 290, 30);

        jLabel11.setForeground(new java.awt.Color(49, 49, 49));
        jLabel11.setText("Nombre");
        add(jLabel11);
        jLabel11.setBounds(430, 65, 290, 10);

        btnGuardar.setBackground(new java.awt.Color(18, 44, 82));
        btnGuardar.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnGuardar.setForeground(new java.awt.Color(255, 255, 255));
        btnGuardar.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnGuardar.setText("Guardar");
        btnGuardar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnGuardar.setOpaque(true);
        btnGuardar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnGuardarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnGuardarMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnGuardarMousePressed(evt);
            }
        });
        btnGuardar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                btnGuardarKeyReleased(evt);
            }
        });
        add(btnGuardar);
        btnGuardar.setBounds(620, 500, 120, 40);

        lblEmailValid.setFont(new java.awt.Font("Tahoma", 0, 8)); // NOI18N
        add(lblEmailValid);
        lblEmailValid.setBounds(30, 420, 150, 14);

        lblTelefono.setFont(new java.awt.Font("Tahoma", 0, 8)); // NOI18N
        add(lblTelefono);
        lblTelefono.setBounds(30, 480, 150, 14);

        lblDireccion.setFont(new java.awt.Font("Tahoma", 0, 8)); // NOI18N
        add(lblDireccion);
        lblDireccion.setBounds(30, 540, 150, 14);

        foto.setImagen(new javax.swing.ImageIcon(getClass().getResource("/Vista/Acces/Imagenes/UsuarioImg.png"))); // NOI18N
        foto.setLayout(null);

        botonluna.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Vista/Iconos/botonluna.png"))); // NOI18N
        botonluna.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        botonluna.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                botonlunaMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                botonlunaMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                botonlunaMousePressed(evt);
            }
        });
        foto.add(botonluna);
        botonluna.setBounds(0, 100, 150, 60);

        add(foto);
        foto.setBounds(370, 390, 150, 150);
    }// </editor-fold>//GEN-END:initComponents

    private void tablaPersonasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaPersonasMouseClicked
        int fila = tablaPersonas.rowAtPoint(evt.getPoint());
        int columna = tablaPersonas.columnAtPoint(evt.getPoint());
        if (columna == Utilidades.PERFIL) {
            validarSeleccionMouse(fila);
            
        } else if (columna == Utilidades.EVENTO) {
            try {
                validarSeleccionMouse2(fila);
            } catch (IOException ex) {
                Logger.getLogger(GestionarPersonas.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_tablaPersonasMouseClicked

    private void btnGuardarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGuardarMouseEntered
        btnGuardar.setBackground(new Color(18, 79, 82));
    }//GEN-LAST:event_btnGuardarMouseEntered

    private void btnGuardarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGuardarMouseExited
        btnGuardar.setBackground(new Color(18, 44, 82));
    }//GEN-LAST:event_btnGuardarMouseExited

    private void btnGuardarMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGuardarMousePressed
        guardar();

    }//GEN-LAST:event_btnGuardarMousePressed
    public void guardar(){
               if (validaCampoEmail && validaDireccion && telefonoValido) {
            
            int confirm = JOptionPane.showConfirmDialog(null, "Esta seguro de su decision??");
            if (confirm == 0) {
                ctr.agregarPersona().setMail(txtEmail.getText());
                ctr.agregarPersona().setTelefono(txtTelefono.getText());
                ctr.agregarPersona().setDireccion(txtDireccion.getText());
                ctr.agregarPersona().setEstadoCivil(cbEstadoCivil.getSelectedItem().toString());
                ctr.agregarPersona().setFile(file);
                ctr.Editar(ctr.agregarPersona());
                if (ctr.isCorrect()) {
                    
                    txtDireccion.setText("");
                    txtEmail.setText("");
                    txtTelefono.setText("");
                    lblDireccion.setText("");
                    lblEmailValid.setText("");
                    lblTelefono.setText("");
                    Image img = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/Vista/Acces/Imagenes/UsuarioImg.png"));
                    foto.setImagen(new ImageIcon(uti.img(img, foto.getSize())));
                    JOptionPane.showMessageDialog(null, "Se a modificado correctamente");
                } else {
                    JOptionPane.showMessageDialog(null, "Se a producido un error mientras intentaba actualizar");
                }
                ctr.VaciarListComun();
                ctr.VaciarTemp();
                ctr.listaPersonas();
                ctr.buscar(null, "ALL");
                construirTabla();
            }
        } else {
            JOptionPane.showMessageDialog(null, "Error datos faltantes o erroneos");
        }
    }    String buscar;
    private void txtBucarCedulaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBucarCedulaKeyReleased
        ctr.VaciarTemp();
        buscar = txtBucarCedula.getText();
        ctr.buscar(buscar, "CEDULA");
        construirTabla();        // TODO add your handling code here:
    }//GEN-LAST:event_txtBucarCedulaKeyReleased

    private void txtBuscarNombreKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarNombreKeyReleased
        ctr.VaciarTemp();
        buscar = txtBuscarNombre.getText();
        ctr.buscar(buscar, "NOMBRE");
        construirTabla();         // TODO add your handling code here:
    }//GEN-LAST:event_txtBuscarNombreKeyReleased
    boolean validaCampoEmail;
    private void txtEmailKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtEmailKeyReleased
        validaCampoEmail = uti.validarDireccionCorreoElectronico(txtEmail.getText());
        if (validaCampoEmail) {
            lblEmailValid.setText("Correo. Valido");
            lblEmailValid.setForeground(Color.green);
        } else {
            lblEmailValid.setText("Correo. Invalido");
            lblEmailValid.setForeground(Color.red);
        }        // TODO add your handling code here:
    }//GEN-LAST:event_txtEmailKeyReleased
    boolean telefonoValido;
    private void txtTelefonoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTelefonoKeyReleased
        if (txtTelefono.getText().length() == 10) {
            telefonoValido = true;
            lblTelefono.setText("Telefono. Valido");
            lblTelefono.setForeground(Color.green);
            
        } else {
            telefonoValido = false;
            lblTelefono.setText("Telefono. Invalido");
            lblTelefono.setForeground(Color.red);
        }// TODO add your handling code here:
    }//GEN-LAST:event_txtTelefonoKeyReleased

    private void txtTelefonoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTelefonoKeyTyped
        char caracter = evt.getKeyChar();
        // Verificar si la tecla pulsada no es un digito

        if (((caracter < '0')
                || (caracter > '9'))
                && (caracter != '\b' /*corresponde a BACK_SPACE*/)) {
            evt.consume();
            JOptionPane.showMessageDialog(null, "Caracteres invalidos solo puede ingresar numeros");
            // ignorar el evento de teclado
        }        // TODO add your handling code here:
    }//GEN-LAST:event_txtTelefonoKeyTyped
    boolean validaDireccion;
    private void txtDireccionKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDireccionKeyReleased
        if (txtDireccion.getText().length() > 7) {
            validaDireccion = true;
            lblDireccion.setText("Dirección. Valida");
            lblDireccion.setForeground(Color.green);
        } else {
            validaDireccion = false;
            lblDireccion.setText("Dirección. Invalida");
            lblDireccion.setForeground(Color.red);
        }         // TODO add your handling code here:
    }//GEN-LAST:event_txtDireccionKeyReleased

    private void botonlunaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_botonlunaMouseEntered
        botonluna.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Vista/Iconos/botonluna2.png"))); // TODO add your handling code here:
    }//GEN-LAST:event_botonlunaMouseEntered

    private void botonlunaMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_botonlunaMouseExited
        botonluna.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Vista/Iconos/botonluna.png")));// TODO add your handling code here:
    }//GEN-LAST:event_botonlunaMouseExited
    File file = null;
    private void botonlunaMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_botonlunaMousePressed
        file = uti.BuscarImagen();
        if (file != null) {
            ImageIcon ico = new ImageIcon(file.getAbsolutePath());
            foto.setImagen(ico);
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_botonlunaMousePressed

    private void btnGuardarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnGuardarKeyReleased
        if (evt.getKeyCode()==KeyEvent.VK_ENTER) {
            guardar();
        }
// TODO add your handling code here:
    }//GEN-LAST:event_btnGuardarKeyReleased

//vista en desarrollo
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel botonluna;
    private javax.swing.JLabel btnGuardar;
    private javax.swing.JComboBox<String> cbEstadoCivil;
    private rojerusan.RSPanelCircleImage foto;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lblDireccion;
    private javax.swing.JLabel lblEmailValid;
    private javax.swing.JLabel lblTelefono;
    private javax.swing.JTable tablaPersonas;
    private javax.swing.JTextField txtBucarCedula;
    private javax.swing.JTextField txtBuscarNombre;
    private javax.swing.JTextField txtDireccion;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtTelefono;
    // End of variables declaration//GEN-END:variables

}
