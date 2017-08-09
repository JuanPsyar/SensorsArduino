package sensorsarduino;

import com.panamahitek.PanamaHitek_Arduino;
import com.panamahitek.ArduinoException;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import jssc.SerialPortException;
import java.awt.Rectangle;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Window extends javax.swing.JFrame {

    PanamaHitek_Arduino Arduino = new PanamaHitek_Arduino();
    int Slot = 1;
    String[] parts;
    DefaultTableModel dataGSR;
    DefaultTableModel dataEMG;
    DefaultTableModel PhyData;
    Rectangle r;
    int Lecturas = 0;
    Date fecha = new Date();
    File archivologGSR;
    File archivologEMG;
    FileWriter escribirlogGSR;
    FileWriter escribirlogEMG;
    int numeroprueba = 0;
    String datosArduino = "";
    boolean State = false;
    Calendar Calendario = Calendar.getInstance();
    Integer indiceGSR = 0;
    Integer timStampGSR = 0;
    Integer indiceEMG = 0;
    Integer timStampEMG = 0;
    Integer indicePhyData = 0;
    Integer timStampPhyData = 0;
    Properties props = new Properties();
    File fichero;
    int ensayo = 1;
    boolean cambioVol = false;
    Integer timeInicioGSR = 0;
    Integer timeInicioEMG = 0;
    File archivoGSRdir;
    File archivoEMGdir;
    File archivoPhyDatadir;
    FileWriter escribirdirGSR;
    FileWriter escribirdirEMG;
    FileWriter escribirdirPhyData;
    String ensayoAnterior = "";

    final static String MSSQL_DRIVER = "org.apache.derby.jdbc.EmbeddedDriver";
    final static String MSSQL_JDBC_URL = "jdbc:derby://localhost:1527/sensors";
    final static String MSSQL_USERNAME = "jp";
    final static String MSSQL_PASSWORD = "admin";
    final static String MSSQL_SCHEMA = "JP";
    Conexion conDB = new Conexion();

    //Método que constamente escucha el puerto del Arduino para capturar los datos del sensor GSR
    //una vez que los obtiene los envia a almacenar en una tabla
    SerialPortEventListener evento = new SerialPortEventListener() {

        public void serialEvent(SerialPortEvent spe) {
            try {
                if (Arduino.isMessageAvailable()) {
                    if (!datosArduino.isEmpty()) {
                        actualizarTableLogs();
                    }
                    datosArduino = Arduino.printMessage();
                }
            } catch (ArduinoException ex) {
                Logger.getLogger(Window.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SerialPortException ex) {
                Logger.getLogger(Window.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    };

    //Método para inicializar el archivo de propiedades y textos en botones y combo-box
    private void inicializarValores() {
        try {
            //Cargar el archivo de propiedades
            FileInputStream descriptions = new FileInputStream("C:\\Users\\Juan Pablo\\Documents\\MDS\\TFM\\CODE\\GSR\\SensorsToExcel\\Java\\SensorsArduino\\src\\sensorsarduino\\definitions.properties");
            props.load(descriptions);
            props.list(System.out);
            descriptions.close();

            dataGSR = (DefaultTableModel) gsrTable.getModel();
            dataEMG = (DefaultTableModel) emgTable.getModel();
            PhyData = (DefaultTableModel) phyDataTable.getModel();
            //Definimos el texto de los botones y ocultamos ciertos elementos de la aplicación
            StartStop.setText(props.getProperty("textoBoton") + String.valueOf(ensayo));
            ensayoAnterior = StartStop.getText().replace("Ensayo #", "e");
            phyDataTable.setVisible(false);
            physDatScrollPane.setVisible(false);
            PhyDataComboBox.setVisible(false);
            typePhyDatalabel.setVisible(false);
            //crearlogs();
            //Definimos la ruta del archivo de propiedades
            OrdenEmociones.setText(props.getProperty(PruebaTipo.getSelectedItem().toString()).replace("%", "-"));
            cargarUsuarios();

        } catch (Exception ex) {
            Logger.getLogger(Window.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //al inicialiar la clase se establece la conexión con Arduino y se crean los logs para almacenar los datos
    public Window() {

        try {
            initComponents();
            inicializarValores();
//            en esta línea se hace la conexión a Arduino
            Arduino.arduinoRXTX("COM4", 9600, evento);
        } catch (Exception ex) {
            Logger.getLogger(Window.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        StartStop = new javax.swing.JButton();
        gsrScrollPane = new javax.swing.JScrollPane();
        gsrTable = new javax.swing.JTable();
        emgScrollPane = new javax.swing.JScrollPane();
        emgTable = new javax.swing.JTable();
        TableClean = new javax.swing.JButton();
        CreateChunksAuBT = new javax.swing.JButton();
        PruebaTipo = new javax.swing.JComboBox();
        OrdenEmociones = new javax.swing.JLabel();
        Voluntario = new javax.swing.JComboBox();
        CreateFileVol = new javax.swing.JButton();
        newUser = new javax.swing.JButton();
        physDatScrollPane = new javax.swing.JScrollPane();
        phyDataTable = new javax.swing.JTable();
        loadCSV = new javax.swing.JButton();
        PhyDataComboBox = new javax.swing.JComboBox();
        typePhyDatalabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        StartStop.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        StartStop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                StartStopActionPerformed(evt);
            }
        });

        gsrTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Timestamp", "GSR"
            }
        ));
        gsrTable.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        gsrTable.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        gsrScrollPane.setViewportView(gsrTable);

        emgTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Timestamp", "EMG"
            }
        ));
        emgTable.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        emgScrollPane.setViewportView(emgTable);

        TableClean.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        TableClean.setText("Limpiar Tablas");
        TableClean.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TableCleanActionPerformed(evt);
            }
        });

        CreateChunksAuBT.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        CreateChunksAuBT.setText("CrearFicheroAuBT");
        CreateChunksAuBT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CreateChunksAuBTActionPerformed(evt);
            }
        });

        PruebaTipo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Prueba1", "Prueba2", "Prueba3" }));
        PruebaTipo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PruebaTipoActionPerformed(evt);
            }
        });

        Voluntario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                VoluntarioActionPerformed(evt);
            }
        });

        CreateFileVol.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        CreateFileVol.setText("CrearFicheroVol");
        CreateFileVol.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CreateFileVolActionPerformed(evt);
            }
        });

        newUser.setText("Nuevo Voluntario");
        newUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newUserActionPerformed(evt);
            }
        });

        phyDataTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Timestamp", "PhysioData"
            }
        ));
        physDatScrollPane.setViewportView(phyDataTable);

        loadCSV.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        loadCSV.setText("CargarCSV");
        loadCSV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loadCSVActionPerformed(evt);
            }
        });

        PhyDataComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "BP", "SPO2", "HRV" }));
        PhyDataComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PhyDataComboBoxActionPerformed(evt);
            }
        });

        typePhyDatalabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        typePhyDatalabel.setText("Dato Fisiológico");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                            .addComponent(PruebaTipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(OrdenEmociones, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                            .addComponent(gsrScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 254, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(emgScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(Voluntario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(newUser))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(TableClean, javax.swing.GroupLayout.DEFAULT_SIZE, 181, Short.MAX_VALUE)
                            .addComponent(StartStop, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(70, 70, 70)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(loadCSV, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(CreateFileVol, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(CreateChunksAuBT, javax.swing.GroupLayout.DEFAULT_SIZE, 193, Short.MAX_VALUE))))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(physDatScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(typePhyDatalabel, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(PhyDataComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 23, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Voluntario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(newUser))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(PruebaTipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(emgScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                    .addComponent(gsrScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                    .addComponent(physDatScrollPane, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(OrdenEmociones, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(CreateFileVol)
                                    .addComponent(StartStop, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(TableClean)
                                    .addComponent(CreateChunksAuBT)))
                            .addComponent(PhyDataComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(333, 333, 333)
                        .addComponent(typePhyDatalabel, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(47, 47, 47)))
                .addComponent(loadCSV, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        OrdenEmociones.getAccessibleContext().setAccessibleName("OrdenEmotions");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    //Esta función envia una señal al Arduino para que se dejen de leer los datos de los sensores
    private void EnviarSenal(String dato) {
        try {
            Arduino.sendData(dato);
        } catch (Exception ex) {
            Logger.getLogger(Window.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void crearlogs() {
        try {
            //se crea el fichero del paciente 
            String carpetaVol = Voluntario.getSelectedItem().toString();
            fichero = new File(props.getProperty("RutaLogs") + "/" + carpetaVol);
            fichero.mkdirs();
            //String ruta = "C:/Users/Juan Pablo/Documents/MDS/TFM/Pruebas/DATOS/arduino/";
            //String aniomesdia = String.valueOf(fecha.getYear()) + String.valueOf(fecha.getMonth()) + String.valueOf(fecha.getDay());
            String idArchivo = carpetaVol.replace("voluntario", props.getProperty("inicialArchivo"));
            String tipoPrueba = PruebaTipo.getSelectedItem().toString().replace("Prueba", "p");
            String inicNomArchivo = "/" + idArchivo + "_" + aniomesdia() + "_" + tipoPrueba + "_";
            String finNomArchivo = ensayoAnterior + props.getProperty("extension");
//            fecha = Date.from(Instant.now());
//            String hora = String.valueOf(fecha.getHours()) + String.valueOf(fecha.getMinutes()) + String.valueOf(fecha.getSeconds());
            archivologGSR = new File(fichero.getAbsolutePath() + inicNomArchivo + "GSR" + finNomArchivo);
            archivologEMG = new File(fichero.getAbsolutePath() + inicNomArchivo + "EMG" + finNomArchivo);
            escribirlogGSR = new FileWriter(archivologGSR, true);
            escribirlogEMG = new FileWriter(archivologEMG, true);
        } catch (IOException ex) {
            Logger.getLogger(Window.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /*
     Método para almacenar los valores obtenidos del sensor en una tabla que posteriormente será 
     almacenada en un excel, al mismo tiempo escribe en un log dentro de una ubicación del disco duro. 
     */

    public void actualizarTableLogs() {
        try {
            String identificador = "";
            System.out.println("data: " + datosArduino);
            String tokenCSV = props.getProperty("TokenData");
            parts = datosArduino.split(props.getProperty("TokenID"));
            if (parts.length > 0) {
                identificador = parts[0];               //la parte inicial de la trama es capturada con el identificador de datos
                //en esta parte se desglosa la trama según el token y se almacena según el identificador en la tabla seleccionada
                String[] celdas = parts[1].split(tokenCSV);
                if (celdas.length > 0) {
                    if (!(dataGSR.getRowCount() > 0)) {
                        timeInicioGSR = Integer.parseInt(celdas[0]);
                    }
                    if (!(dataEMG.getRowCount() > 0)) {
                        timeInicioEMG = Integer.parseInt(celdas[0]);
                    }
                    switch (identificador) {
                        case "GSR":
                            dataGSR.addRow(new Object[]{Integer.parseInt(celdas[0]) - timeInicioGSR, celdas[1]});
                            escribirlogGSR.write(Integer.parseInt(celdas[0]) - timeInicioGSR + tokenCSV + celdas[1] + "\r\n");           //en esta sección se escribe en los logs
                            break;
                        case "EMG":
                            dataEMG.addRow(new Object[]{Integer.parseInt(celdas[0]) - timeInicioEMG, celdas[1]});
                            escribirlogEMG.write(Integer.parseInt(celdas[0]) - timeInicioEMG + tokenCSV + celdas[1] + "\r\n");           //en esta sección se escribe en los logs
                            break;
                        default:
                            break;
                    }
                }

                //mover el cursor de la tabla de datos automáticamente 
                switch (identificador) {
                    case "GSR":
                        r = gsrTable.getCellRect(gsrTable.getRowCount() - 1, 0, true);
                        gsrScrollPane.getViewport().scrollRectToVisible(r);
                        break;
                    case "EMG":
                        r = emgTable.getCellRect(emgTable.getRowCount() - 1, 0, true);
                        emgScrollPane.getViewport().scrollRectToVisible(r);
                        break;
                    default:
                        break;
                }
            }

        } catch (Exception ex) {
            Logger.getLogger(Window.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //Método que borra todos los datos de las tablas dentro de la aplicación y de pantalla 
    private void LimpiarTablas(DefaultTableModel tablalimpiar) {
        if (tablalimpiar.getRowCount() > 0) {
            for (int i = tablalimpiar.getRowCount() - 1; i >= 0; i--) {
                tablalimpiar.removeRow(i);
            }
        }
    }

    //Esta función comienza o detiene la recolección de datos en la pantalla de la aplicación
    //para ello llama a la función EnviarSenal()
    private void FinalizarEnsayo() {
        try {
            if (State == true) {
                State = false;
                EnviarSenal("0");           //se desactiva ya la recolección de la data                
                escribirlogGSR.close();
                escribirlogEMG.close();
                ensayo++;
                //se inicializa el texto con el número de ensayo
                StartStop.setText(props.getProperty("textoBoton") + String.valueOf(ensayo));
                Voluntario.setEnabled(true);
                datosArduino = "";
            } else {
                State = true;
                //se toma el nombre del ensayo
                ensayoAnterior = StartStop.getText().replace("Ensayo #", "e");
                //se crean los logs para almacenar los valores 
                crearlogs();
                StartStop.setText("Finalizar Ensayo #" + ensayo);
                EnviarSenal("1");           //se activa ya la recolección de la data para sacar la media comparativa para los valores del GSR                                
                Voluntario.setEnabled(false);
                if (!CreateChunksAuBT.isEnabled()) {
                    CreateChunksAuBT.setEnabled(true);
                }
            }

        } catch (Exception ex) {
            Logger.getLogger(Window.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    private void StartStopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_StartStopActionPerformed
        //Llamamos al método finalizar ensayo con la variable true para que cierre los logs de Ensayo 
        FinalizarEnsayo();

    }//GEN-LAST:event_StartStopActionPerformed

    //Función del botón Limpiar Tablas
    private void TableCleanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TableCleanActionPerformed
        LimpiarTablas(dataGSR);
        LimpiarTablas(dataEMG);
    }//GEN-LAST:event_TableCleanActionPerformed

    //Método para obtener el número de Log a almacenarse en el disco, en base a la fecha y la hora
    private String aniomesdia() {
        String anio = Integer.toString(Calendario.get(Calendar.YEAR));
        String mes = Integer.toString(Calendario.get(Calendar.MONTH));
        String dia = Integer.toString(Calendario.get(Calendar.DAY_OF_MONTH));
        String encabezado = anio + mes + dia;
        return encabezado;
    }

    //Método para crear los directorios donde se almacenarán los valores obtenidos de los sensores en el Arduino
    //y que posteriromente se usarán para el programa AuBT
    private void CreateChunksAuBTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CreateChunksAuBTActionPerformed
        // TODO add your handling code here:java 
        CrearDirectorio("aubt");
//        try {
//            if (dataEMG.getRowCount() > 0 && dataGSR.getRowCount() > 0) {
//                EnviarSenal("0");          //se deshabilita la recolección de datos y el botón para recolectarlos            
//                //se definen variables para realizar los cortes en la tabla con los datos GSR y EMG
//                timStampEMG = Double.parseDouble(dataEMG.getValueAt(0, 0).toString());
//                timStampGSR = Double.parseDouble(dataGSR.getValueAt(0, 0).toString());
//                String pruebaNum = PruebaTipo.getSelectedItem().toString();
//                //props.list(System.out);
//                String directorio = props.getProperty(pruebaNum);
//                String tipoArchivo = props.getProperty("extension");
//                //desglosa la propiedad alegria%miedo%ira%sorpresa%tristeza%asco según la selección en el comboBox
//                String[] sentimentalDir = directorio.split("%");
//
//                String aniomesdia = aniomesdia();
//                String ruta = props.getProperty("RutaFicheros");
//                //String ruta = "C:/Users/Juan Pablo/Documents/MDS/TFM/Pruebas/DATOS/datosaubt/";
//                Double emotionTime = Double.parseDouble(props.getProperty("tiempoemocion"));
//                //para prueba se utilizará la siguiente matriz
//                //DefaultTableModel Pruebamatriz = matrizprueba();
//                //for (int i = 0; i < sentimentalDir.length; i++) {         
//                for (String sentimentalDir1 : sentimentalDir) {       //sugerencia de Java 
//                    String emotionDir = props.getProperty(sentimentalDir1);
//                    //    String emotionDir = props.getProperty(sentimentalDir[i]);
//                    fichero = new File(ruta + aniomesdia + "/" + emotionDir);
//                    fichero.mkdirs();
//                    archivologGSR = new File(fichero.getAbsolutePath() + "/GSR" + tipoArchivo);
//                    archivologEMG = new File(fichero.getAbsolutePath() + "/EMG" + tipoArchivo);
//                    escribirLogsDir(emotionTime + timStampGSR, emotionTime + timStampEMG, tipoArchivo);
//                    //escribirLogs(Pruebamatriz, emotionTime + timStampGSR);                    
//                }
//                CreateChunksAuBT.setEnabled(false);         //deshabilitamos el botón de Creación de Fichero
//                State = true;                             //Con esto se cambia el valor del fichero
//                LimpiarTablas(dataGSR);
//                LimpiarTablas(dataEMG);
//                indiceGSR = 0;
//                indiceEMG = 0;
//            } else {
//                JOptionPane.showMessageDialog(null, "No existen datos para crear ficheros");
//            }
//
//        } catch (Exception ex) {
//            Logger.getLogger(Window.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }//GEN-LAST:event_CreateChunksAuBTActionPerformed
    private void CrearDirectorio(String tipoDirectorio) {
        try {
            if ((dataEMG.getRowCount() > 0 && dataGSR.getRowCount() > 0) || PhyData.getRowCount() > 0) {
                String ruta = "";
                File archivoEMGAuBT = new File("");
                File archivoGSRAuBT = new File("");
                String datosEscribir = "";
                File archivoAuBTPhyData = new File("");
                Boolean ficheroAubt = false;
                File ficheroPadre = new File("");
                String aniomesdia = aniomesdia();
                String carpetaVol = Voluntario.getSelectedItem().toString();
                String tipoPrueba = PruebaTipo.getSelectedItem().toString().replace("Prueba", "p");
                String extension = props.getProperty("extension");
                String PhyDataType = PhyDataComboBox.getSelectedItem().toString();
                //obtengo las emociones que se contemplaron en la prueba 
                String[] sentimentalDir = OrdenEmociones.getText().split("-");
                Integer emotionTime = Integer.parseInt(props.getProperty("tiempoemocion"));

                //se definen variables para realizar los cortes en la tabla con los datos GSR y EMG
                if (dataEMG.getRowCount() > 0 && dataGSR.getRowCount() > 0) {
                    timStampEMG = Integer.parseInt(dataEMG.getValueAt(0, 0).toString());
                    timStampGSR = Integer.parseInt(dataGSR.getValueAt(0, 0).toString());
                }
                if (PhyData.getRowCount() > 0) {
                    timStampPhyData = Integer.parseInt(PhyData.getValueAt(0, 0).toString());
                }
                //según el tipo de directorio se asignará la ruta para almacenar los logs
                switch (tipoDirectorio) {
                    case "emociones":
                        ruta = props.getProperty("RutaLogs");
                        ficheroPadre = new File(ruta + carpetaVol + "/" + aniomesdia + "/" + ensayoAnterior + "/" + tipoPrueba);
                        ficheroPadre.mkdirs();
                        break;
                    case "aubt":
                        ruta = props.getProperty("RutaAubt");
                        //en este punto se crean o actualizan los archivos con las rutas de los ficheros 
                        //de las distintas señales fisiológicas, en otras palabras se escribe el log general
                        if (dataEMG.getRowCount() > 0 && dataGSR.getRowCount() > 0) {
                            archivoGSRAuBT = new File(ruta + "/GSR" + extension);
                            archivoEMGAuBT = new File(ruta + "/EMG" + extension);
                        } else if (PhyData.getRowCount() > 0) {
                            archivoAuBTPhyData = new File(ruta + "/" + PhyDataType + extension);
                        }
                        ficheroAubt = true;
                        break;

                }
                //for (String sentimentalDir1 : sentimentalDir) {
                for (int j = 0; j < sentimentalDir.length; j++) {
                    switch (tipoDirectorio) {
                        case "emociones":
                            fichero = new File(ficheroPadre.getAbsolutePath() + "/" + sentimentalDir[j]);
                            break;
                        case "aubt":
                            fichero = new File(ruta + aniomesdia + "/" + props.getProperty(sentimentalDir[j]));
                            break;
                    }
                    fichero.mkdirs();
                    if (dataEMG.getRowCount() > 0 && dataGSR.getRowCount() > 0) {
                        archivoGSRdir = new File(fichero.getAbsolutePath() + "/GSR" + extension);
                        archivoEMGdir = new File(fichero.getAbsolutePath() + "/EMG" + extension);
                    }
                    if (PhyData.getRowCount() > 0) {
                        archivoPhyDatadir = new File(fichero.getAbsolutePath() + "/" + PhyDataType + extension);
                    }
                    //con esta función escribimos los archivos CSV correspondientes a las disintas señales
                    //sean para señales EMG, GSR, PB, HRV, SPO2
                    escribirLogsDir(emotionTime + timStampGSR, emotionTime + timStampEMG, emotionTime + timStampPhyData, ficheroAubt);
                    //en el caso de ficheros para aubt se escriben el log General 
                    if (ficheroAubt) {
                        //comprobamos que se hallan creado los archivos de Log General, en otras palabras
                        //que hallan datos en las tablas GSR y EMG
                        if (archivoGSRAuBT.exists() && archivoEMGAuBT.exists()) {
                            //primero los de datos GSR
                            datosEscribir = aniomesdia + "/s" + String.valueOf(j + 1) + "/GSR" + extension + "\r\n";
                            escribirLogGeneral(archivoGSRdir, archivoGSRAuBT, datosEscribir);
                            datosEscribir = aniomesdia + "/" + String.valueOf(j + 1) + "/EMG" + extension + "\r\n";
                            escribirLogGeneral(archivoEMGdir, archivoEMGAuBT, datosEscribir);
                        }
                        if (archivoAuBTPhyData.exists()) {
                            datosEscribir = aniomesdia + "/" + String.valueOf(j + 1) + "/" + PhyDataType + extension + "\r\n";
                            escribirLogGeneral(archivoPhyDatadir, archivoAuBTPhyData, datosEscribir);
                        }
                    }
                }
//                LimpiarTablas(dataGSR);
//                LimpiarTablas(dataEMG);
                indiceGSR = 0;
                indiceEMG = 0;
                timStampEMG = 0;
                timStampGSR = 0;
                indicePhyData = 0;
                timStampPhyData = 0;

            } else {
                JOptionPane.showMessageDialog(null, "No existen datos para crear ficheros");
            }

        } catch (Exception ex) {
            Logger.getLogger(Window.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //Función para actualizar o crear el log general de las señales fisiologicas en directorios AuBT
    private void escribirLogGeneral(File archivoEscrito, File archivoGeneral, String rutaArchivoEscrito) {
        try {
            if (archivoEscrito.length() > 0) {
                FileWriter escribirLogG;
                escribirLogG = new FileWriter(archivoGeneral, true);
                escribirLogG.write(rutaArchivoEscrito);
                escribirLogG.close();
            }
        } catch (IOException ex) {
            Logger.getLogger(Window.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //Función que escribe los logs en los directorios
    private void escribirLogsDir(Integer intervaloGSR, Integer intervaloEMG, Integer intervaloPhyData, Boolean aubt) {
        //private void escribirLogsDir(DefaultTableModel matriz, Double intervaloEmocion) {
        try {
            //este será el token con el que separaremos los archivos CSV
            String tokenFile = props.getProperty("TokenData");
            if (dataEMG.getRowCount() > 0 && dataGSR.getRowCount() > 0) {
                escribirdirGSR = new FileWriter(archivoGSRdir, true);
                escribirdirEMG = new FileWriter(archivoEMGdir, true);

                //Empezamos a ingresar los valores en el log de datos GSR
                for (int i = indiceGSR; i < dataGSR.getRowCount(); i++) {
                    if (Integer.parseInt(dataGSR.getValueAt(i, 0).toString()) <= intervaloGSR) {
                        if (aubt) {
                            escribirdirGSR.write(dataGSR.getValueAt(i, 1) + "\r\n");
                        } else {
                            escribirdirGSR.write(dataGSR.getValueAt(i, 0) + tokenFile + dataGSR.getValueAt(i, 1) + "\r\n"); //en esta sección se escribe en los logs                        
                        }
                    } else {
                        indiceGSR = i;
                        timStampGSR = Integer.parseInt(dataGSR.getValueAt(i, 0).toString());
                        i = dataGSR.getRowCount();
                    }
                }
                //Empezamos a ingresar los valores en el log de datos EMG
                for (int i = indiceEMG; i < dataEMG.getRowCount(); i++) {
                    if (Integer.parseInt(dataEMG.getValueAt(i, 0).toString()) <= intervaloEMG) {
                        if (aubt) {
                            escribirdirEMG.write(dataEMG.getValueAt(i, 1) + "\r\n");
                        } else {
                            escribirdirEMG.write(dataEMG.getValueAt(i, 0) + tokenFile + dataEMG.getValueAt(i, 1) + "\r\n");           //en esta sección se escribe en los logs                        
                        }
                    } else {
                        indiceEMG = i;
                        timStampEMG = Integer.parseInt(dataEMG.getValueAt(i, 0).toString());
                        i = dataEMG.getRowCount();
                    }
                }
                escribirdirGSR.close();
                escribirdirEMG.close();
            }
            if (PhyData.getRowCount() > 0) {
                escribirdirPhyData = new FileWriter(archivoPhyDatadir, true);

                //Empezamos a ingresar los valores en el log de datos fisiólogicos
                for (int i = indicePhyData; i < PhyData.getRowCount(); i++) {
                    if (Integer.parseInt(PhyData.getValueAt(i, 0).toString()) <= intervaloPhyData) {
                        if (aubt) {
                            escribirdirPhyData.write(PhyData.getValueAt(i, 1) + "\r\n");
                        } else {
                            escribirdirPhyData.write(PhyData.getValueAt(i, 0) + tokenFile + PhyData.getValueAt(i, 1) + "\r\n"); //en esta sección se escribe en los logs                        
                        }
                    } else {
                        indicePhyData = i;
                        timStampPhyData = Integer.parseInt(PhyData.getValueAt(i, 0).toString());
                        i = PhyData.getRowCount();
                    }
                }
                escribirdirPhyData.close();
            }
        } catch (IOException ex) {
            Logger.getLogger(Window.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //Función que crea el archivo con las cabeceras para los ficheros AuBT
    private void creararchivoRutas() {

    }


    private void PruebaTipoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PruebaTipoActionPerformed
        // TODO add your handling code here:
        OrdenEmociones.setText(props.getProperty(PruebaTipo.getSelectedItem().toString()).replace("%", "-"));
    }//GEN-LAST:event_PruebaTipoActionPerformed


    private void CreateFileVolActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CreateFileVolActionPerformed
        // TODO add your handling code here:
        CrearDirectorio("emociones");
    }//GEN-LAST:event_CreateFileVolActionPerformed

    private void cargarUsuarios() throws Exception {
        Connection dbCon = conDB.dbConexion(MSSQL_DRIVER, MSSQL_JDBC_URL, MSSQL_USERNAME,
                MSSQL_PASSWORD, MSSQL_SCHEMA);
        ArrayList<String> listaVol = conDB.listaNombres(dbCon);
        Voluntario.setModel(new DefaultComboBoxModel(listaVol.toArray()));
    }

    private void newUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newUserActionPerformed
        try {
            //se crea un nuevo usuario dentro de la base de datos
            int ingresoVol = 0;
            Connection dbCon = conDB.dbConexion(MSSQL_DRIVER, MSSQL_JDBC_URL, MSSQL_USERNAME,
                    MSSQL_PASSWORD, MSSQL_SCHEMA);
            ingresoVol = conDB.insertarUsuario(dbCon);
            if (ingresoVol > 0) {
                cargarUsuarios();
            } else {
                JOptionPane.showMessageDialog(null, "No se ha insertado un nuevo usuario");
            }
        } catch (Exception ex) {
            Logger.getLogger(Window.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_newUserActionPerformed

    private void VoluntarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_VoluntarioActionPerformed
        // TODO add your handling code here:
        cambioVol = true;
        ensayo = 1;
        StartStop.setText(props.getProperty("textoBoton") + String.valueOf(ensayo));
    }//GEN-LAST:event_VoluntarioActionPerformed

    private File obtenerArchivo() {
        JFileChooser fileChooser = new JFileChooser(props.getProperty("RutaCargaCSV"));

        FileNameExtensionFilter filtro = new FileNameExtensionFilter(null, props.getProperty("extension").replace(".", ""));
        fileChooser.setFileFilter(filtro);
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int resultado = fileChooser.showOpenDialog(this);
        if (resultado == JFileChooser.CANCEL_OPTION) {
            return null;
        } else {
            File archivoSeleccionado = fileChooser.getSelectedFile();
            return archivoSeleccionado;
        }
    }

    // método para cargar un archivo CSV dentro de una tabla en Java
    //tomado de https://balusoft.wordpress.com/2012/06/25/abrir-un-archivo-de-excel-csv-en-java/
    private void procesarArchivo() {
        File archivoCSV = obtenerArchivo();
        if (archivoCSV.exists()) {
            BufferedReader input = null;
            try {
                input = new BufferedReader(new FileReader(archivoCSV));
                String linea;
                while ((linea = input.readLine()) != null) {
                    String[] filaCSV = linea.split(props.getProperty("TokenCSV"));
                    PhyData.addRow(new Object[]{Integer.parseInt(filaCSV[0]), Integer.parseInt(filaCSV[1])});
                }
                input.close();
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Window.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(Window.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    input.close();
                } catch (IOException ex) {
                    Logger.getLogger(Window.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }
    }


    private void loadCSVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loadCSVActionPerformed
        // TODO add your handling code here:
        //hacemos visibles los elementos
        phyDataTable.setVisible(true);
        physDatScrollPane.setVisible(true);
        PhyDataComboBox.setVisible(true);
        typePhyDatalabel.setVisible(true);
        LimpiarTablas(PhyData);
        procesarArchivo();
    }//GEN-LAST:event_loadCSVActionPerformed

    private void PhyDataComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PhyDataComboBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_PhyDataComboBoxActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Window.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Window.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Window.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Window.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Window().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton CreateChunksAuBT;
    private javax.swing.JButton CreateFileVol;
    private javax.swing.JLabel OrdenEmociones;
    private javax.swing.JComboBox PhyDataComboBox;
    private javax.swing.JComboBox PruebaTipo;
    private javax.swing.JButton StartStop;
    private javax.swing.JButton TableClean;
    private javax.swing.JComboBox Voluntario;
    private javax.swing.JScrollPane emgScrollPane;
    private javax.swing.JTable emgTable;
    private javax.swing.JScrollPane gsrScrollPane;
    private javax.swing.JTable gsrTable;
    private javax.swing.JButton loadCSV;
    private javax.swing.JButton newUser;
    private javax.swing.JTable phyDataTable;
    private javax.swing.JScrollPane physDatScrollPane;
    private javax.swing.JLabel typePhyDatalabel;
    // End of variables declaration//GEN-END:variables
}
