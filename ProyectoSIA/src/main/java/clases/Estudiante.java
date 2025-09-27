/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clases;

/**
 *
 * @author benjo
 */
public class Estudiante extends Registro {
    private String nombre;
    private String rut;
    private String curso;

    public Estudiante(String nombre, String rut, String curso) {
        this.nombre = nombre;
        this.rut = rut;
        this.curso = curso;
    }
    
    // getters estudiantes
    public String getNombre() { return nombre; }
    public String getRut() { return rut; }
    public String getCurso() { return curso; }
    
    // setters estudiantes
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setRut(String rut) { this.rut = rut; }
    public void setCurso(String curso) { this.curso = curso; }

    // Para CSV
    public String toCSV() {
        return nombre + "," + rut + "," + curso;
    }
    
    // funcion tipo Estudiante para
    public static Estudiante fromCSV(String linea) {
        if (linea == null || linea.trim().isEmpty()) return null;
        String[] partes = linea.split(",");
        if (partes.length < 3) return null;

        // Ignorar encabezado
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
    }

