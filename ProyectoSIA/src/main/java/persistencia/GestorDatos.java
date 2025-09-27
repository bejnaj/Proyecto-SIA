package com.mycompany.proyectosia.persistencia;

import com.mycompany.proyectosia.modelo.Estudiante;
import com.mycompany.proyectosia.modelo.Recurso;
import java.io.*;
import java.util.*;

public class GestorDatos {
    private static final Map<Integer, Recurso> recursos = new HashMap<>();
    private static final List<Estudiante> estudiantes = new ArrayList<>();

    private static final String FILE_ESTUDIANTES = "CSV/alumnos/alumnos.csv";
    private static final String FILE_RECURSOS   = "CSV/recursos/recursos.csv";

    public static Map<Integer, Recurso> getRecursosMap() { return recursos; }
    public static List<Estudiante> getEstudiantes() { return estudiantes; }

    public static void guardarEstudiantes() {
        try {
            File f = new File(FILE_ESTUDIANTES);
            if (f.getParentFile() != null) f.getParentFile().mkdirs();
            try (PrintWriter pw = new PrintWriter(new FileWriter(f))) {
                pw.println("nombre,rut,curso");
                for (Estudiante e : estudiantes) pw.println(e.toCSV());
            }
        } catch (IOException e) { e.printStackTrace(); }
    }

    public static void cargarEstudiantes() {
        File f = new File(FILE_ESTUDIANTES);
        if (!f.exists()) {
            // Datos iniciales si no existe el archivo
            estudiantes.add(new Estudiante("Juan Pérez","12345678-9","3A"));
            estudiantes.add(new Estudiante("María Gómez","98765432-1","4B"));
            guardarEstudiantes();
            return;
        }
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                Estudiante e = Estudiante.fromCSV(linea);
                if (e != null) estudiantes.add(e);
            }
        } catch (IOException e) { e.printStackTrace(); }
    }

    public static void guardarRecursos() {
        try {
            File f = new File(FILE_RECURSOS);
            if (f.getParentFile() != null) f.getParentFile().mkdirs();
            try (PrintWriter pw = new PrintWriter(new FileWriter(f))) {
                pw.println("id,titulo,materia,tipoUso,cupos,poseedores");
                for (Recurso r : recursos.values()) pw.println(r.toCSV());
            }
        } catch (IOException e) { e.printStackTrace(); }
    }

    public static void cargarRecursos() {
        File f = new File(FILE_RECURSOS);
        if (!f.exists()) {
            // Datos iniciales de ejemplo
            Recurso r1 = new Recurso();
            r1.setId(1); r1.setTitulo("Calculadora científica"); r1.setMateria("Matemáticas"); r1.setTipoUso("Prestamo"); r1.setCupos(2);
            Recurso r2 = new Recurso();
            r2.setId(2); r2.setTitulo("Kit electrónica"); r2.setMateria("Física"); r2.setTipoUso("Prestamo"); r2.setCupos(3);
            recursos.put(r1.getId(), r1); recursos.put(r2.getId(), r2);
            guardarRecursos();
            return;
        }
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                Recurso r = Recurso.fromCSV(linea);
                if (r != null) recursos.put(r.getId(), r);
            }
        } catch (IOException e) { e.printStackTrace(); }
    }

    public static Estudiante buscarEstudiantePorRut(String rut) {
        if (rut == null) return null;
        return estudiantes.stream()
                .filter(e -> e.getRut().equalsIgnoreCase(rut))
                .findFirst().orElse(null);
    }

    public static void agregarEstudiante(Estudiante e) {
        // evitar duplicados por RUT
        Estudiante ex = buscarEstudiantePorRut(e.getRut());
        if (ex == null) {
            estudiantes.add(e);
            guardarEstudiantes();
        }
    }

    public static void agregarRecurso(Recurso r) {
        recursos.put(r.getId(), r);
        guardarRecursos();
    }

    public static void eliminarRecurso(int id) {
        recursos.remove(id);
        guardarRecursos();
    }

    public static void actualizarRecurso(Recurso r) {
        recursos.put(r.getId(), r);
        guardarRecursos();
    }

    public static List<Recurso> filtrarRecursosPorMateria(String materia) {
        List<Recurso> res = new ArrayList<>();
        for (Recurso r : recursos.values()) if (r.getMateria().equalsIgnoreCase(materia)) res.add(r);
        return res;
    }

    public static void eliminarEstudiantePorRut(String rut) {
        estudiantes.removeIf(e -> e.getRut().equalsIgnoreCase(rut));
        // además quitar de poseedores en recursos
        for (Recurso r : recursos.values()) {
            r.getListaPoseedores().removeIf(pe -> pe.getRut().equalsIgnoreCase(rut));
        }
        guardarEstudiantes();
        guardarRecursos();
    }
}
