package com.mycompany.proyectosia.persistencia;

import com.mycompany.proyectosia.modelo.Recurso;
import com.mycompany.proyectosia.modelo.Estudiante;
import java.io.*;

public class ReporteGenerator {
    public static void generarReporteTxt(String filename) {
        try {
            File f = new File(filename);
            if (f.getParentFile() != null) f.getParentFile().mkdirs();
            try (PrintWriter pw = new PrintWriter(new FileWriter(f))) {
                pw.println("REPORTE DE RECURSOS");
                pw.println("-------------------");
                for (Recurso r : GestorDatos.getRecursosMap().values()) {
                    pw.println(r.mostrarResumen());
                    pw.println("Poseedores:");
                    if (r.getListaPoseedores().isEmpty()) {
                        pw.println("  (ninguno)"); 
                    } else {
                        for (Estudiante e : r.getListaPoseedores()) pw.println("  - " + e.mostrarResumen());
                    }
                    pw.println();
                }
            }
        } catch (IOException ex) { ex.printStackTrace(); }
    }
}
