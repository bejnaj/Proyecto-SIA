/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.proyectosia;

/**
 *
 * @author benjo
 */
import java.util.*;
public class ProyectoSIA {
    public class estudiante{
        private String nombre;
        private String rut;
        private String curso;
        private int cuposDisponibles;
        private int cuposUtilizados;
        public estudiante(String nombre, String rut, String curso){
        this.nombre = nombre;
        this.rut = rut;
        this.curso = curso;
        this.cuposDisponibles = 5;
        this.cuposUtilizados = 0;
    }
    }
    public class Recurso{
        private String id;
        private String tipoUso;
        private String titulo;
        private String materia;
        private boolean disponibilidad;
        private int cupos;
        private List<estudiante> listaEspera;
        public Recurso(String id, String tipoUso, String titulo, String materia, int cupos) {
            this.id = id;
            this.tipoUso = tipoUso;
            this.titulo = titulo;
            this.materia = "";
            this.disponibilidad = true;
            this.cupos = cupos;
            this.listaEspera = new ArrayList<>();
        }
        
    }
    public static void main(String[] args) {
        System.out.println("Esto es una prueba 2");
    }
}
