/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

import ControlAdminDatos.PersonaDao;
import Controlador.CondenaDao;
import Controlador.DelitoDao;
import Controlador.JuzgadoDao;
import Controlador.ProcesoDao;
import Controlador.Utilidades.UtilAgreGesAnt;
import Modelo.Condena;
import Modelo.Delito;
import Modelo.Juzgado;
import Modelo.Persona;
import Modelo.Proceso;
import Vista.Utiles.GestionCeldas;
import Vista.Utiles.GestionEncabezadoTabla;
import Vista.Utiles.TablaAntecedentes.UtilidadesTablaAntecedentes;
import Vista.Utiles.ModeloTabla;
import Vista.Utiles.UtilesFecha;
import java.awt.Image;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.JTableHeader;
import necesario.RSFileChooser;

/**
 *
 * @author hp
 */
public class GestionarAntecedentes extends javax.swing.JPanel {

    /**
     * Creates new form GestionarAntecedentes
     */
    ModeloTabla modelo;
    private int filasTabla;
    private int columnasTabla;
    File fichero = null;
    String rutaArchivo;
    ArrayList<Delito> listaDelito;
    DelitoDao dd = new DelitoDao();
    ArrayList<Juzgado> listaJuzgado;
    JuzgadoDao jd = new JuzgadoDao();
    ArrayList<Persona> listaPersonas;
    PersonaDao pd = new PersonaDao();
    ArrayList<Proceso> listaProcesos = new ArrayList<>();
    ProcesoDao prcd = new ProcesoDao();
    ArrayList<Condena> listaCondena;
    CondenaDao cd = new CondenaDao();
    Persona auxPer = null;
    Proceso auxPro = null;
    Condena auxCon = null;
    String estadoProceso;
    String estadoVictimario;
    UtilesFecha fecha = new UtilesFecha();
    byte[] auxByte = null;

    public GestionarAntecedentes() {
        initComponents();
        IconoBorrarArchivo.setVisible(false);
        lbIconoArchivo.setVisible(false);
        lbNombreArchivo.setVisible(false);
        rbEnProceso.setEnabled(false);
        rbEnProceso.setSelected(true);
        construirTabla();
    }

    /**
     * Limpia todos los campos
     */
    public void limpiarCampos() {
        Proceso auxPro = null;
        Condena auxCon = null;
        dcFechaFinalizacionAudiencia.setDate(null);
        txtIntancia.setText("");
        txtNrAudiencia.setText("");
        txtSentencia.setText("");
        borrarArchivo();
    }

    /**
     * Borra el archivo temporal que se muestra al usuario
     */
    public void borrarArchivo() {
        IconoBorrarArchivo.setVisible(false);
        lbIconoArchivo.setVisible(false);
        lbNombreArchivo.setVisible(false);
        lbNombreArchivo.setText("");
        fichero = null;
        auxByte = null;
    }

    /**
     * Carga las listas necesarias para llenar la tabla
     */
    public void cargarListas() {
        listaProcesos = prcd.listaProcesoPersona(auxPer.getIdPersona(), true);
        listaDelito = UtilAgreGesAnt.listaDelito(listaProcesos, dd);
        listaJuzgado = UtilAgreGesAnt.listaJuzgado(listaProcesos, jd);
        listaCondena = UtilAgreGesAnt.listaCondena(listaProcesos, cd);
        construirTabla();
    }

    /**
     * Construye la tabla segun los datos y titulos
     */
    private void construirTabla() {

        ArrayList<String> titulosList = new ArrayList<>();

        titulosList.add("Delito");
        titulosList.add("Art.");
        titulosList.add("Condena");
        titulosList.add("Fecha de Inicio");
        titulosList.add("Fecha de Finalización");
        titulosList.add("Juzgado");
        titulosList.add("Estado Victimario");
        titulosList.add("Estado Demanda");
        titulosList.add("Estado Proceso");
        titulosList.add(" ");
        titulosList.add(" ");

        //se asignan las columnas al arreglo para enviarse al momento de construir la tabla
        String titulos[] = new String[titulosList.size()];
        for (int i = 0; i < titulos.length; i++) {
            titulos[i] = titulosList.get(i);
        }
        /*obtenemos los datos de la lista y los guardamos en la matriz
		 * que luego se manda a construir la tabla
         */
        Object[][] data = UtilAgreGesAnt.obtenerMatrizDatos(titulosList, listaProcesos, listaJuzgado, listaDelito, listaCondena);
        construirTabla(titulos, data);

    }

    /**
     * Construye la tabla segun los datos y botones
     *
     * @param titulos
     * @param data
     */
    private void construirTabla(String[] titulos, Object[][] data) {
        modelo = new ModeloTabla(data, titulos);
        modelo.setRowCount(0);
        modelo = new ModeloTabla(data, titulos);
        //se asigna el modelo a la tabla
        tablaAntecedentes.setModel(modelo);

        //se asigna el tipo de dato que tendrón las celdas de cada columna definida respectivamente para validar su personalización;
        tablaAntecedentes.getColumnModel().getColumn(UtilidadesTablaAntecedentes.BORRAR).setCellRenderer(new GestionCeldas("icono"));
        tablaAntecedentes.getColumnModel().getColumn(UtilidadesTablaAntecedentes.EDITAR).setCellRenderer(new GestionCeldas("icono"));

        //se recorre y asigna el resto de celdas que serian las que almacenen datos de tipo texto
        for (int i = 0; i < titulos.length - 2; i++) {//se resta 1 porque las ultimas 1 columnas se definen arriba
            tablaAntecedentes.getColumnModel().getColumn(i).setCellRenderer(new GestionCeldas("texto"));
        }

        tablaAntecedentes.getTableHeader().setReorderingAllowed(false);
        tablaAntecedentes.setRowHeight(25);//tamano de las celdas
        tablaAntecedentes.setGridColor(new java.awt.Color(0, 0, 0));
        //Se define el tamaño de largo para cada columna y su contenido
        tablaAntecedentes.getColumnModel().getColumn(UtilidadesTablaAntecedentes.DELITO).setPreferredWidth(150);
        tablaAntecedentes.getColumnModel().getColumn(UtilidadesTablaAntecedentes.ART).setPreferredWidth(100);
        tablaAntecedentes.getColumnModel().getColumn(UtilidadesTablaAntecedentes.CONDENA).setPreferredWidth(450);
        tablaAntecedentes.getColumnModel().getColumn(UtilidadesTablaAntecedentes.FECHAINICIO).setPreferredWidth(100);
        tablaAntecedentes.getColumnModel().getColumn(UtilidadesTablaAntecedentes.FECHAFINALIZACION).setPreferredWidth(100);
        tablaAntecedentes.getColumnModel().getColumn(UtilidadesTablaAntecedentes.JUZGADO).setPreferredWidth(180);
        tablaAntecedentes.getColumnModel().getColumn(UtilidadesTablaAntecedentes.ESTADOVICTIMARIO).setPreferredWidth(180);
        tablaAntecedentes.getColumnModel().getColumn(UtilidadesTablaAntecedentes.ESTADODEMANDA).setPreferredWidth(160);
        tablaAntecedentes.getColumnModel().getColumn(UtilidadesTablaAntecedentes.ESTADOPROCESO).setPreferredWidth(160);
        tablaAntecedentes.getColumnModel().getColumn(UtilidadesTablaAntecedentes.BORRAR).setPreferredWidth(35);
        tablaAntecedentes.getColumnModel().getColumn(UtilidadesTablaAntecedentes.EDITAR).setPreferredWidth(35);

        //personaliza el encabezado
        JTableHeader jtableHeader = tablaAntecedentes.getTableHeader();
        jtableHeader.setDefaultRenderer(new GestionEncabezadoTabla());
        tablaAntecedentes.setTableHeader(jtableHeader);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane4 = new javax.swing.JScrollPane();
        PanelComponentes = new javax.swing.JPanel();
        LabelTitulo = new javax.swing.JLabel();
        txtCedula = new javax.swing.JTextField();
        txtNombreApellido = new javax.swing.JTextField();
        lbLinea = new javax.swing.JLabel();
        lbNombreApellido = new javax.swing.JLabel();
        lbCedula = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        lbFechaInicioAudiencia1 = new javax.swing.JLabel();
        dcFechaFinalizacionAudiencia = new com.toedter.calendar.JDateChooser();
        lbIntancia = new javax.swing.JLabel();
        txtIntancia = new javax.swing.JTextField();
        lbNrAudiencia = new javax.swing.JLabel();
        txtNrAudiencia = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        rbEnProceso = new javax.swing.JRadioButton();
        rbFinalizado = new javax.swing.JRadioButton();
        lbEstadoProceso = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        lbSentencia = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtSentencia = new javax.swing.JTextArea();
        jPanel3 = new javax.swing.JPanel();
        lbNombreArchivo = new javax.swing.JLabel();
        lbIconoArchivo = new javax.swing.JLabel();
        IconoBorrarArchivo = new javax.swing.JLabel();
        botonSubir = new javax.swing.JPanel();
        lbIconoSubir = new javax.swing.JLabel();
        lbSubirArchivo = new javax.swing.JLabel();
        botonBuscar = new javax.swing.JPanel();
        IconoBuscar = new javax.swing.JLabel();
        lbBuscar = new javax.swing.JLabel();
        botonGuardar = new javax.swing.JPanel();
        lbIconoGuardar = new javax.swing.JLabel();
        lbGuardar = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tablaAntecedentes = new javax.swing.JTable();
        jLabel23 = new javax.swing.JLabel();
        foto = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        lbEstadoProceso1 = new javax.swing.JLabel();
        rbCulpable = new javax.swing.JRadioButton();
        rbPresuntoCulpable = new javax.swing.JRadioButton();
        rbInocente = new javax.swing.JRadioButton();

        setMinimumSize(new java.awt.Dimension(1000, 610));
        setPreferredSize(new java.awt.Dimension(1000, 610));
        setLayout(null);

        jScrollPane4.setPreferredSize(new java.awt.Dimension(1072, 600));

        PanelComponentes.setBackground(new java.awt.Color(255, 255, 255));
        PanelComponentes.setPreferredSize(new java.awt.Dimension(600, 1090));
        PanelComponentes.setVerifyInputWhenFocusTarget(false);
        PanelComponentes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                PanelComponentesMouseExited(evt);
            }
        });
        PanelComponentes.setLayout(null);

        LabelTitulo.setFont(new java.awt.Font("Nirmala UI Semilight", 0, 24)); // NOI18N
        LabelTitulo.setText("Gestionar ANTECEDENTES");
        LabelTitulo.setToolTipText("");
        PanelComponentes.add(LabelTitulo);
        LabelTitulo.setBounds(233, 11, 270, 32);

        txtCedula.setBackground(new java.awt.Color(240, 240, 240));
        txtCedula.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCedulaActionPerformed(evt);
            }
        });
        txtCedula.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCedulaKeyTyped(evt);
            }
        });
        PanelComponentes.add(txtCedula);
        txtCedula.setBounds(50, 90, 219, 30);

        txtNombreApellido.setBackground(new java.awt.Color(240, 240, 240));
        txtNombreApellido.setEnabled(false);
        txtNombreApellido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNombreApellidoActionPerformed(evt);
            }
        });
        PanelComponentes.add(txtNombreApellido);
        txtNombreApellido.setBounds(340, 90, 319, 30);

        lbLinea.setText("_________________________________________________________________________________________________________________________________________________________________");
        lbLinea.setMaximumSize(new java.awt.Dimension(600, 14));
        lbLinea.setPreferredSize(new java.awt.Dimension(600, 14));
        PanelComponentes.add(lbLinea);
        lbLinea.setBounds(0, 190, 971, 20);

        lbNombreApellido.setFont(new java.awt.Font("Nirmala UI Semilight", 0, 14)); // NOI18N
        lbNombreApellido.setText("Nombre y Apellido");
        lbNombreApellido.setToolTipText("");
        PanelComponentes.add(lbNombreApellido);
        lbNombreApellido.setBounds(340, 50, 114, 20);

        lbCedula.setFont(new java.awt.Font("Nirmala UI Semilight", 0, 14)); // NOI18N
        lbCedula.setText("Cédula");
        lbCedula.setToolTipText("");
        PanelComponentes.add(lbCedula);
        lbCedula.setBounds(50, 50, 42, 20);

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(204, 0, 0));
        jLabel11.setText("*");
        PanelComponentes.add(jLabel11);
        jLabel11.setBounds(40, 50, 10, 17);

        lbFechaInicioAudiencia1.setFont(new java.awt.Font("Nirmala UI Semilight", 0, 14)); // NOI18N
        lbFechaInicioAudiencia1.setText("Fecha de Finalización de la Audiencia:");
        lbFechaInicioAudiencia1.setToolTipText("");
        PanelComponentes.add(lbFechaInicioAudiencia1);
        lbFechaInicioAudiencia1.setBounds(76, 534, 226, 20);

        dcFechaFinalizacionAudiencia.setDateFormatString("dd/MM/yyyy");
        dcFechaFinalizacionAudiencia.setIcon(new ImageIcon(getClass().getResource("/Vista/Iconos/IconoCalendario2.png")));
        PanelComponentes.add(dcFechaFinalizacionAudiencia);
        dcFechaFinalizacionAudiencia.setBounds(320, 524, 130, 30);

        lbIntancia.setFont(new java.awt.Font("Nirmala UI Semilight", 0, 14)); // NOI18N
        lbIntancia.setText("Intancia:");
        lbIntancia.setToolTipText("");
        PanelComponentes.add(lbIntancia);
        lbIntancia.setBounds(76, 575, 49, 20);

        txtIntancia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIntanciaActionPerformed(evt);
            }
        });
        txtIntancia.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtIntanciaKeyTyped(evt);
            }
        });
        PanelComponentes.add(txtIntancia);
        txtIntancia.setBounds(143, 572, 66, 30);

        lbNrAudiencia.setFont(new java.awt.Font("Nirmala UI Semilight", 0, 14)); // NOI18N
        lbNrAudiencia.setText("Nr Audiencia:");
        lbNrAudiencia.setToolTipText("");
        PanelComponentes.add(lbNrAudiencia);
        lbNrAudiencia.setBounds(320, 575, 81, 20);

        txtNrAudiencia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNrAudienciaActionPerformed(evt);
            }
        });
        txtNrAudiencia.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNrAudienciaKeyTyped(evt);
            }
        });
        PanelComponentes.add(txtNrAudiencia);
        txtNrAudiencia.setBounds(419, 572, 66, 30);

        jLabel21.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(204, 0, 0));
        jLabel21.setText("*");
        PanelComponentes.add(jLabel21);
        jLabel21.setBounds(60, 577, 10, 17);

        rbEnProceso.setBackground(new java.awt.Color(255, 255, 255));
        rbEnProceso.setSelected(true);
        rbEnProceso.setText("En Proceso");
        rbEnProceso.setFocusPainted(false);
        rbEnProceso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbEnProcesoActionPerformed(evt);
            }
        });
        PanelComponentes.add(rbEnProceso);
        rbEnProceso.setBounds(220, 660, 78, 22);

        rbFinalizado.setBackground(new java.awt.Color(255, 255, 255));
        rbFinalizado.setText("Finalizado");
        rbFinalizado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbFinalizadoActionPerformed(evt);
            }
        });
        PanelComponentes.add(rbFinalizado);
        rbFinalizado.setBounds(320, 660, 73, 23);

        lbEstadoProceso.setFont(new java.awt.Font("Nirmala UI Semilight", 0, 14)); // NOI18N
        lbEstadoProceso.setText("Estado del Proceso:");
        lbEstadoProceso.setToolTipText("");
        PanelComponentes.add(lbEstadoProceso);
        lbEstadoProceso.setBounds(70, 660, 117, 20);

        jLabel22.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(204, 0, 0));
        jLabel22.setText("*");
        PanelComponentes.add(jLabel22);
        jLabel22.setBounds(200, 830, 10, 17);

        lbSentencia.setFont(new java.awt.Font("Nirmala UI Semilight", 0, 14)); // NOI18N
        lbSentencia.setText("Sentencia:");
        lbSentencia.setToolTipText("");
        PanelComponentes.add(lbSentencia);
        lbSentencia.setBounds(70, 700, 60, 20);

        txtSentencia.setColumns(20);
        txtSentencia.setRows(5);
        jScrollPane3.setViewportView(txtSentencia);

        PanelComponentes.add(jScrollPane3);
        jScrollPane3.setBounds(70, 730, 335, 76);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lbNombreArchivo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPanel3.add(lbNombreArchivo, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 110, 317, 21));

        lbIconoArchivo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Vista/Iconos/PDF file icon_page-0001.png"))); // NOI18N
        lbIconoArchivo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbIconoArchivoMouseClicked(evt);
            }
        });
        jPanel3.add(lbIconoArchivo, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 20, -1, -1));

        IconoBorrarArchivo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Vista/Iconos/IconoCerrar.png"))); // NOI18N
        IconoBorrarArchivo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                IconoBorrarArchivoMouseClicked(evt);
            }
        });
        jPanel3.add(IconoBorrarArchivo, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 10, 20, -1));

        PanelComponentes.add(jPanel3);
        jPanel3.setBounds(220, 830, 331, 131);

        botonSubir.setBackground(new java.awt.Color(18, 44, 82));
        botonSubir.setPreferredSize(new java.awt.Dimension(140, 46));
        botonSubir.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                botonSubirMouseClicked(evt);
            }
        });

        lbIconoSubir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Vista/Iconos/IconoSubir.png"))); // NOI18N

        lbSubirArchivo.setFont(new java.awt.Font("Nirmala UI Semilight", 1, 16)); // NOI18N
        lbSubirArchivo.setForeground(new java.awt.Color(204, 206, 223));
        lbSubirArchivo.setText("Subir Archivo");

        javax.swing.GroupLayout botonSubirLayout = new javax.swing.GroupLayout(botonSubir);
        botonSubir.setLayout(botonSubirLayout);
        botonSubirLayout.setHorizontalGroup(
            botonSubirLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(botonSubirLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbSubirArchivo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 16, Short.MAX_VALUE)
                .addComponent(lbIconoSubir)
                .addContainerGap())
        );
        botonSubirLayout.setVerticalGroup(
            botonSubirLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, botonSubirLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(botonSubirLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lbIconoSubir)
                    .addComponent(lbSubirArchivo))
                .addGap(0, 0, 0))
        );

        PanelComponentes.add(botonSubir);
        botonSubir.setBounds(310, 990, 147, 22);

        botonBuscar.setBackground(new java.awt.Color(18, 44, 82));
        botonBuscar.setPreferredSize(new java.awt.Dimension(140, 46));
        botonBuscar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                botonBuscarMouseClicked(evt);
            }
        });

        IconoBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Vista/Iconos/IconoBuscar1.png"))); // NOI18N

        lbBuscar.setFont(new java.awt.Font("Nirmala UI Semilight", 1, 16)); // NOI18N
        lbBuscar.setForeground(new java.awt.Color(204, 206, 223));
        lbBuscar.setText("Buscar");

        javax.swing.GroupLayout botonBuscarLayout = new javax.swing.GroupLayout(botonBuscar);
        botonBuscar.setLayout(botonBuscarLayout);
        botonBuscarLayout.setHorizontalGroup(
            botonBuscarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(botonBuscarLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbBuscar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 17, Short.MAX_VALUE)
                .addComponent(IconoBuscar)
                .addContainerGap())
        );
        botonBuscarLayout.setVerticalGroup(
            botonBuscarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, botonBuscarLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(botonBuscarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(IconoBuscar)
                    .addComponent(lbBuscar))
                .addGap(0, 0, 0))
        );

        PanelComponentes.add(botonBuscar);
        botonBuscar.setBounds(327, 163, 105, 22);

        botonGuardar.setBackground(new java.awt.Color(18, 44, 82));
        botonGuardar.setPreferredSize(new java.awt.Dimension(140, 46));
        botonGuardar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                botonGuardarMouseClicked(evt);
            }
        });

        lbIconoGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Vista/Iconos/IconoGuardar.png"))); // NOI18N
        lbIconoGuardar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbIconoGuardarMouseClicked(evt);
            }
        });

        lbGuardar.setFont(new java.awt.Font("Nirmala UI Semilight", 1, 16)); // NOI18N
        lbGuardar.setForeground(new java.awt.Color(204, 206, 223));
        lbGuardar.setText("Guardar");

        javax.swing.GroupLayout botonGuardarLayout = new javax.swing.GroupLayout(botonGuardar);
        botonGuardar.setLayout(botonGuardarLayout);
        botonGuardarLayout.setHorizontalGroup(
            botonGuardarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(botonGuardarLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbGuardar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 15, Short.MAX_VALUE)
                .addComponent(lbIconoGuardar)
                .addContainerGap())
        );
        botonGuardarLayout.setVerticalGroup(
            botonGuardarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, botonGuardarLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(botonGuardarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lbIconoGuardar)
                    .addComponent(lbGuardar))
                .addGap(0, 0, 0))
        );

        PanelComponentes.add(botonGuardar);
        botonGuardar.setBounds(330, 1060, 114, 22);

        tablaAntecedentes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tablaAntecedentes.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        tablaAntecedentes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaAntecedentesMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(tablaAntecedentes);

        PanelComponentes.add(jScrollPane5);
        jScrollPane5.setBounds(10, 220, 960, 280);

        jLabel23.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(204, 0, 0));
        jLabel23.setText("*");
        PanelComponentes.add(jLabel23);
        jLabel23.setBounds(60, 670, 10, 17);

        foto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Vista/Acces/Imagenes/UsuarioImg.png"))); // NOI18N
        PanelComponentes.add(foto);
        foto.setBounds(720, 30, 140, 140);

        jLabel24.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(204, 0, 0));
        jLabel24.setText("*");
        PanelComponentes.add(jLabel24);
        jLabel24.setBounds(50, 620, 10, 17);

        lbEstadoProceso1.setFont(new java.awt.Font("Nirmala UI Semilight", 0, 14)); // NOI18N
        lbEstadoProceso1.setText("Estado del Victimario:");
        lbEstadoProceso1.setToolTipText("");
        PanelComponentes.add(lbEstadoProceso1);
        lbEstadoProceso1.setBounds(70, 620, 130, 20);

        rbCulpable.setBackground(new java.awt.Color(255, 255, 255));
        rbCulpable.setText("Culpable");
        rbCulpable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbCulpableActionPerformed(evt);
            }
        });
        PanelComponentes.add(rbCulpable);
        rbCulpable.setBounds(220, 620, 67, 23);

        rbPresuntoCulpable.setBackground(new java.awt.Color(255, 255, 255));
        rbPresuntoCulpable.setText("Presunto Culpable");
        rbPresuntoCulpable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbPresuntoCulpableActionPerformed(evt);
            }
        });
        PanelComponentes.add(rbPresuntoCulpable);
        rbPresuntoCulpable.setBounds(320, 620, 120, 23);

        rbInocente.setBackground(new java.awt.Color(255, 255, 255));
        rbInocente.setText("Inocente");
        rbInocente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbInocenteActionPerformed(evt);
            }
        });
        PanelComponentes.add(rbInocente);
        rbInocente.setBounds(470, 620, 70, 23);

        jScrollPane4.setViewportView(PanelComponentes);

        add(jScrollPane4);
        jScrollPane4.setBounds(0, 0, 1000, 610);
    }// </editor-fold>//GEN-END:initComponents

    private void PanelComponentesMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PanelComponentesMouseExited
        txtNombreApellido.setFocusable(true);        // TODO add your handling code here:
    }//GEN-LAST:event_PanelComponentesMouseExited
    /**
     * Guarda los cambios que se le hizo a un determinado Proceso/Antecedente
     *
     * @param evt
     */
    private void botonGuardarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_botonGuardarMouseClicked
        if (txtIntancia.getText().length() > 0 && (auxByte != null || fichero != null)) {
            auxPro.setFechaFinal(fecha.getFecha(dcFechaFinalizacionAudiencia));
            auxPro.setInstancia(Integer.parseInt(txtIntancia.getText()));
            auxPro.setNrAudiencias(Integer.parseInt(txtNrAudiencia.getText()));
            auxPro.setEstadoVictimario(estadoVictimario);
            auxPro.setEstadoDemanda((dcFechaFinalizacionAudiencia != null) ? "Finalizado" : estadoProceso);
            auxCon.setSentencia(txtSentencia.getText());
            auxCon.setEstadoCondena((!txtSentencia.getText().equalsIgnoreCase("") ? "Dictada" : "Sin Dictar"));
            if (auxByte == null) {
                try {
                    byte[] pdf = new byte[(int) fichero.length()];
                    InputStream input = new FileInputStream(fichero);
                    input.read(pdf);
                    auxPro.setText(pdf);
                    auxPro.setNombreDocumento(fichero.getName());
                } catch (Exception e) {
                }
            }
            if (!UtilAgreGesAnt.datoRepetido(listaProcesos, auxPro)) {
                cd.edit(auxCon);
                prcd.edit(auxPro);
                if (cd.isSeGuardo() && prcd.isSeGuardo()) {
                    JOptionPane.showMessageDialog(null, "Se ha modificado con exito");
                    limpiarCampos();
                    cargarListas();
                } else {
                    JOptionPane.showMessageDialog(null, "Error al modificar");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Este proceso ya se encuentra registrado");
            }

        } else {
            JOptionPane.showMessageDialog(null, "Por favor llene todos los campos");
        }
    }//GEN-LAST:event_botonGuardarMouseClicked
    /**
     * Busca los datos de la persona ingresada
     *
     * @param evt
     */
    private void botonBuscarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_botonBuscarMouseClicked
        try {
            auxPer = pd.obtenerPersona(txtCedula.getText(), "cedula");
        } catch (SQLException ex) {
            Logger.getLogger(GestionarAntecedentes.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Apellido" + auxPer.getApellido());
        if (auxPer != null) {
            cargarListas();
            txtNombreApellido.setText(auxPer.getNombre() + " " + auxPer.getApellido());
            ImageIcon icon = new ImageIcon("Perfiles/" + auxPer.getFile().getPath());
            Image imgEscalada = icon.getImage().getScaledInstance(140, 140, Image.SCALE_SMOOTH);
            Icon imgIcon = new ImageIcon(imgEscalada);
            foto.setIcon(imgIcon);
        } else {
            JOptionPane.showMessageDialog(null, "No existe registro de esa persona");
        }
    }//GEN-LAST:event_botonBuscarMouseClicked
    /**
     * Permite subir un archivo
     *
     * @param evt
     */
    private void botonSubirMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_botonSubirMouseClicked
        necesario.RSFileChooser fc = new necesario.RSFileChooser();//Creamos el objeto JFileChooser
        int seleccion = fc.showOpenDialog(this);//Abrimos la ventana, guardamos la opcion seleccionada por el usuario
        if (seleccion == JFileChooser.APPROVE_OPTION) { //Si el usuario selecciona aceptar
            IconoBorrarArchivo.setVisible(true);
            lbIconoArchivo.setVisible(true);
            fichero = fc.getSelectedFile();//Seleccionamos el fichero
            lbNombreArchivo.setVisible(true);
            lbNombreArchivo.setText(fichero.getName());//Escribimos el nombre del archivo
            auxByte = null;
        }
    }//GEN-LAST:event_botonSubirMouseClicked
    /**
     * Permite borrar el archivo subido
     *
     * @param evt
     */
    private void IconoBorrarArchivoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_IconoBorrarArchivoMouseClicked
        borrarArchivo();
    }//GEN-LAST:event_IconoBorrarArchivoMouseClicked
    /**
     * Seleciona el estado Finalizado del Proceso o Jucio
     *
     * @param evt
     */
    private void rbFinalizadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbFinalizadoActionPerformed
        if (rbFinalizado.isSelected()) {
            rbFinalizado.setEnabled(false);
            rbEnProceso.setEnabled(true);
            rbEnProceso.setSelected(false);
            this.estadoProceso = "Finalizado";
        }
    }//GEN-LAST:event_rbFinalizadoActionPerformed
    /**
     * Seleciona el estado En proceso del Proceso o Jucio
     *
     * @param evt
     */
    private void rbEnProcesoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbEnProcesoActionPerformed
        if (rbEnProceso.isSelected()) {
            rbEnProceso.setEnabled(false);
            rbFinalizado.setEnabled(true);
            rbFinalizado.setSelected(false);
            this.estadoProceso = "En proceso";
        }
    }//GEN-LAST:event_rbEnProcesoActionPerformed

    private void txtNrAudienciaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNrAudienciaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNrAudienciaActionPerformed

    private void txtIntanciaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIntanciaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIntanciaActionPerformed

    private void txtNombreApellidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombreApellidoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombreApellidoActionPerformed

    private void txtCedulaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCedulaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCedulaActionPerformed

    private void lbIconoGuardarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbIconoGuardarMouseClicked
        // TODO add your handling code here:

    }//GEN-LAST:event_lbIconoGuardarMouseClicked
    /**
     * Permite realizar las acciones de editar y dar de baja seleccionando los
     * determinados iconos de la tabla
     *
     * @param evt
     */
    private void tablaAntecedentesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaAntecedentesMouseClicked
        int fila = tablaAntecedentes.rowAtPoint(evt.getPoint());
        int columna = tablaAntecedentes.columnAtPoint(evt.getPoint());

        /*uso la columna para valiar si corresponde a la columna de perfil garantizando
        * que solo se produzca algo si selecciono una fila de esa columna
         */
        if (columna == UtilidadesTablaAntecedentes.BORRAR) {
            Proceso aux = listaProcesos.get(fila);
            prcd.destroy(aux.getIdProceso());
            if (prcd.isSeGuardo()) {
                JOptionPane.showMessageDialog(null, "Se ha modificado el estado correctamente");
            } else {
                JOptionPane.showMessageDialog(null, "Error al modificar el estado correctamente");
            }
        } else if (columna == UtilidadesTablaAntecedentes.EDITAR) {//se valida que sea la columna del otro evento
            auxPro = listaProcesos.get(fila);
            auxCon = listaCondena.get(fila);
            SimpleDateFormat formato = new SimpleDateFormat("yyy-MM-dd");
            Date d = null;
            if (auxPro.getFechaFinal() != null) {
                try {
                    d = formato.parse(auxPro.getFechaFinal());
                } catch (ParseException ex) {
                    Logger.getLogger(GestionarAntecedentes.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            dcFechaFinalizacionAudiencia.setDate(d);
            txtIntancia.setText(auxPro.getInstancia() + "");
            txtNrAudiencia.setText(auxPro.getNrAudiencias() + "");

            if (auxPro.getEstadoVictimario().equalsIgnoreCase("Culpable")) {
                rbCulpable.setEnabled(false);
                rbCulpable.setSelected(true);
                rbPresuntoCulpable.setEnabled(true);
                rbPresuntoCulpable.setSelected(false);
                rbInocente.setEnabled(true);
                rbInocente.setSelected(false);
                this.estadoVictimario = "Culpable";
            } else if (auxPro.getEstadoVictimario().equalsIgnoreCase("Presunto Culpable")) {
                rbCulpable.setEnabled(true);
                rbCulpable.setSelected(false);
                rbPresuntoCulpable.setEnabled(false);
                rbPresuntoCulpable.setSelected(true);
                rbInocente.setEnabled(true);
                rbInocente.setSelected(false);
                this.estadoVictimario = "Presunto Culpable";
            } else if (auxPro.getEstadoVictimario().equalsIgnoreCase("Inocente")) {
                rbCulpable.setEnabled(true);
                rbCulpable.setSelected(false);
                rbPresuntoCulpable.setEnabled(true);
                rbPresuntoCulpable.setSelected(false);
                rbInocente.setEnabled(false);
                rbInocente.setSelected(true);
                this.estadoVictimario = "Inocente";
            }

            if (auxPro.getEstadoDemanda().equalsIgnoreCase("Finalizado")) {
                rbFinalizado.setEnabled(false);
                rbFinalizado.setSelected(true);
                rbEnProceso.setEnabled(true);
                rbEnProceso.setSelected(false);
                this.estadoProceso = "Finalizado";
            } else if (auxPro.getEstadoDemanda().equalsIgnoreCase("En proceso")) {
                rbFinalizado.setEnabled(true);
                rbFinalizado.setSelected(false);
                rbEnProceso.setEnabled(false);
                rbEnProceso.setSelected(true);
                this.estadoProceso = "En proceso";
            }

            txtSentencia.setText(auxCon.getSentencia());
            IconoBorrarArchivo.setVisible(true);
            lbIconoArchivo.setVisible(true);
            lbNombreArchivo.setVisible(true);
            lbNombreArchivo.setText(auxPro.getNombreDocumento());//Escribimos el nombre del archivo
            auxByte = auxPro.getText();
        }
        cargarListas();
    }//GEN-LAST:event_tablaAntecedentesMouseClicked
    /**
     * Seleciona el estado Culpable del Victimario
     *
     * @param evt
     */
    private void rbCulpableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbCulpableActionPerformed
        if (rbCulpable.isSelected()) {
            rbCulpable.setEnabled(false);
            rbPresuntoCulpable.setEnabled(true);
            rbPresuntoCulpable.setSelected(false);
            rbInocente.setEnabled(true);
            rbInocente.setSelected(false);
            this.estadoVictimario = "Culpable";
        }
    }//GEN-LAST:event_rbCulpableActionPerformed
    /**
     * Seleciona el estado Presunto Culpable del Victimario
     *
     * @param evt
     */
    private void rbPresuntoCulpableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbPresuntoCulpableActionPerformed
        if (rbPresuntoCulpable.isSelected()) {
            rbPresuntoCulpable.setEnabled(false);
            rbCulpable.setEnabled(true);
            rbCulpable.setSelected(false);
            rbInocente.setEnabled(true);
            rbInocente.setSelected(false);
            this.estadoVictimario = "Presunto Culpable";
        }
    }//GEN-LAST:event_rbPresuntoCulpableActionPerformed
    /**
     * Seleciona el estado Inocente del Victimario
     *
     * @param evt
     */
    private void rbInocenteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbInocenteActionPerformed
        if (rbInocente.isSelected()) {
            rbInocente.setEnabled(false);
            rbCulpable.setEnabled(true);
            rbCulpable.setSelected(false);
            rbPresuntoCulpable.setEnabled(true);
            rbPresuntoCulpable.setSelected(false);
            this.estadoVictimario = "Inocente";
        }
    }//GEN-LAST:event_rbInocenteActionPerformed
    /**
     * Permite descargar el archivo
     *
     * @param evt
     */
    private void lbIconoArchivoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbIconoArchivoMouseClicked
        if (evt.getClickCount() == 2) {
            if (auxByte != null) {
                necesario.RSFileChooser guardar = new RSFileChooser();
                guardar.showSaveDialog(null);
                guardar.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
                File archivo = guardar.getSelectedFile();
                UtilAgreGesAnt.descargarArchivo(auxByte, archivo);
            } else {
                try {
                    byte[] pdf = new byte[(int) fichero.length()];
                    InputStream input = new FileInputStream(fichero);
                    input.read(pdf);
                    necesario.RSFileChooser guardar = new RSFileChooser();
                    guardar.showSaveDialog(null);
                    guardar.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
                    File archivo = guardar.getSelectedFile();
                    UtilAgreGesAnt.descargarArchivo(pdf, archivo);
                } catch (IOException e) {
                }
            }
        }
    }//GEN-LAST:event_lbIconoArchivoMouseClicked
    /**
     * Solo permite numeros en la cedula
     *
     * @param evt
     */
    private void txtCedulaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCedulaKeyTyped
        char caracter = evt.getKeyChar();
        if (((caracter < '0')
                || (caracter > '9'))
                && (caracter != '\b')) {
            evt.consume();
        }    }//GEN-LAST:event_txtCedulaKeyTyped
    /**
     * Solo permite numeros en la instancia
     *
     * @param evt
     */
    private void txtIntanciaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtIntanciaKeyTyped
        char caracter = evt.getKeyChar();
        if (((caracter < '0')
                || (caracter > '9'))
                && (caracter != '\b')) {
            evt.consume();
        }    }//GEN-LAST:event_txtIntanciaKeyTyped
    /**
     * Solo permite numeros en el número de audiencias
     *
     * @param evt
     */
    private void txtNrAudienciaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNrAudienciaKeyTyped
        char caracter = evt.getKeyChar();
        if (((caracter < '0')
                || (caracter > '9'))
                && (caracter != '\b')) {
            evt.consume();
        }    }//GEN-LAST:event_txtNrAudienciaKeyTyped


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel IconoBorrarArchivo;
    private javax.swing.JLabel IconoBuscar;
    private javax.swing.JLabel LabelTitulo;
    private javax.swing.JPanel PanelComponentes;
    private javax.swing.JPanel botonBuscar;
    private javax.swing.JPanel botonGuardar;
    private javax.swing.JPanel botonSubir;
    private com.toedter.calendar.JDateChooser dcFechaFinalizacionAudiencia;
    private javax.swing.JLabel foto;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JLabel lbBuscar;
    private javax.swing.JLabel lbCedula;
    private javax.swing.JLabel lbEstadoProceso;
    private javax.swing.JLabel lbEstadoProceso1;
    private javax.swing.JLabel lbFechaInicioAudiencia1;
    private javax.swing.JLabel lbGuardar;
    private javax.swing.JLabel lbIconoArchivo;
    private javax.swing.JLabel lbIconoGuardar;
    private javax.swing.JLabel lbIconoSubir;
    private javax.swing.JLabel lbIntancia;
    private javax.swing.JLabel lbLinea;
    private javax.swing.JLabel lbNombreApellido;
    private javax.swing.JLabel lbNombreArchivo;
    private javax.swing.JLabel lbNrAudiencia;
    private javax.swing.JLabel lbSentencia;
    private javax.swing.JLabel lbSubirArchivo;
    private javax.swing.JRadioButton rbCulpable;
    private javax.swing.JRadioButton rbEnProceso;
    private javax.swing.JRadioButton rbFinalizado;
    private javax.swing.JRadioButton rbInocente;
    private javax.swing.JRadioButton rbPresuntoCulpable;
    private javax.swing.JTable tablaAntecedentes;
    private javax.swing.JTextField txtCedula;
    private javax.swing.JTextField txtIntancia;
    private javax.swing.JTextField txtNombreApellido;
    private javax.swing.JTextField txtNrAudiencia;
    private javax.swing.JTextArea txtSentencia;
    // End of variables declaration//GEN-END:variables
}
