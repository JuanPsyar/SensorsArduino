/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sensorsarduino;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Instant;
import java.util.Date;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Juan Pablo
 */
public class logs {
    /*Método para crear nuevos logs de registro en el disco duro del equipo
     los mismos se crean en base a la fecha y la hora 
     */

    Properties props = new Properties();

    public void cargarPropiedades() {
        try {
            FileInputStream descriptions = new FileInputStream("C:\\Users\\Juan Pablo\\Documents\\MDS\\TFM\\CODE\\GSR\\SensorsToExcel\\Java\\SensorsToExcel\\src\\sensorstoexcel\\definitions.properties");
            props.load(descriptions);
            props.list(System.out);

        } catch (FileNotFoundException ex) {
            Logger.getLogger(logs.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(logs.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void crearlogs() {
        String ruta = props.getProperty("RutaLogs");
//        String cabecera = Voluntario.getSelectedItem().toString();
//        String numLog = encabezadoArchivo();
//        fecha = Date.from(Instant.now());
//        String extension = props.getProperty("extension");
//        String hora = String.valueOf(fecha.getHours()) + String.valueOf(fecha.getMinutes()) + String.valueOf(fecha.getSeconds());
//        archivoGSR = new File(ruta + "expGSR" + "_" + numLog + "_" + hora + extension);
//        archivoEMG = new File(ruta + "expEMG" + "_" + numLog + "_" + hora + extension);
//        escribirGSR = new FileWriter(archivoGSR, true);
//        escribirEMG = new FileWriter(archivoEMG, true);
    }
}
