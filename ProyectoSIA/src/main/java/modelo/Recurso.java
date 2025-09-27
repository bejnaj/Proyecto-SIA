package com.mycompany.proyectosia.modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import com.mycompany.proyectosia.persistencia.GestorDatos;

public class Recurso extends Registro {
    private int id;
    private String tipoUso;
    private String titulo;
    private String materia;
    private int cupos;
    private List<Estudiante> listaPoseedores = new ArrayList<>();

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTipoUso() { return tipoUso; }
    public void setTipoUso(String tipoUso) { this.tipoUso = tipoUso; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getMateria() { return materia; }
    public void setMateria(String materia) { this.materia = materia; }

    public int getCupos() { return cupos; }
    public void setCupos(int cupos) { this.cupos = cupos; }

    public List<Estudiante> getListaPoseedores() { return listaPoseedores; }

    @Override
    public String mostrarResumen() {
        return id + ": " + titulo + " [" + materia + "] Cupos: " + cupos;
    }

    // Sobrecarga de métodos
    public String mostrarInfo() { return mostrarResumen(); }
    public String mostrarInfo(boolean conPoseedores) {
        String base = mostrarResumen();
        if (!conPoseedores) return base;
        if (listaPoseedores.isEmpty()) return base + "\nPoseedores: (ninguno)";
        String posee = listaPoseedores.stream().map(Estudiante::mostrarResumen).collect(Collectors.joining("\n  - "));
        return base + "\nPoseedores:\n  - " + posee;
    }

    // CSV serialización
    public String toCSV() {
        String poseedores = listaPoseedores.stream()
                .map(Estudiante::getRut)
                .collect(Collectors.joining(";"));
        return id + "," + titulo + "," + materia + "," + tipoUso + "," + cupos + "," + poseedores;
    }

    public static Recurso fromCSV(String linea) {
        if (linea == null || linea.trim().isEmpty()) return null;
        String[] parts = linea.split(",", -1);
        if (parts.length < 5) return null;
        if (parts[0].trim().equalsIgnoreCase("id")) return null;
        Recurso r = new Recurso();
        try {
            r.setId(Integer.parseInt(parts[0].trim()));
            r.setTitulo(parts[1].trim());
            r.setMateria(parts[2].trim());
            r.setTipoUso(parts[3].trim());
            r.setCupos(Integer.parseInt(parts[4].trim()));
        } catch (NumberFormatException e) {
            System.err.println("Error parseando línea CSV: " + linea);
            return null;
        }
        if (parts.length >= 6) {
            String poseedoresStr = parts[5].trim();
            if (!poseedoresStr.isEmpty()) {
                String[] ruts = poseedoresStr.split(";"); 
                for (String rut : ruts) {
                    Estudiante est = GestorDatos.buscarEstudiantePorRut(rut.trim());
                    if (est != null) r.listaPoseedores.add(est);
                }
            }
        }
        return r;
    }
}
