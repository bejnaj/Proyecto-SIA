package com.mycompany.proyectosia.modelo;

public class Estudiante extends Registro {
    private String nombre;
    private String rut;
    private String curso;

    public Estudiante(String nombre, String rut, String curso) {
        this.nombre = nombre;
        this.rut = rut;
        this.curso = curso;
    }

    // getters
    public String getNombre() { return nombre; }
    public String getRut() { return rut; }
    public String getCurso() { return curso; }

    // setters
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setRut(String rut) { this.rut = rut; }
    public void setCurso(String curso) { this.curso = curso; }

    // CSV
    public String toCSV() {
        return nombre + "," + rut + "," + curso;
    }

    public static Estudiante fromCSV(String linea) {
        if (linea == null || linea.trim().isEmpty()) return null;
        String[] partes = linea.split(",");
        if (partes.length < 3) return null;
        if (partes[0].trim().equalsIgnoreCase("nombre")) return null;
        String nombre = partes[0].trim();
        String rut = partes[1].trim();
        String curso = partes[2].trim();
        if (nombre.isEmpty() || rut.isEmpty() || curso.isEmpty()) return null;
        return new Estudiante(nombre, rut, curso);
    }

    @Override
    public String mostrarResumen() {
        return nombre + " (" + rut + ") - " + curso;
    }

    // Sobrecarga de métodos
    public String mostrarInfo() { return mostrarResumen(); }
    public String mostrarInfo(boolean detallado) {
        if (!detallado) return mostrarResumen();
        return "Nombre: " + nombre + "\nRUT: " + rut + "\nCurso: " + curso;
    }
}
