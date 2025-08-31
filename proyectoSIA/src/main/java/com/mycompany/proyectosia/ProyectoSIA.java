/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.proyectosia;

/**
 *
 * @author benjo
 */
import java.util.*;
import java.io.*;
public class ProyectoSIA {
    public class Estudiante{
        private String nombre;
        private String rut;
        private String curso;
        private int cuposDisponibles;
        private int cuposUtilizados;
        public Estudiante(String nombre, String rut, String curso){
        this.nombre = nombre;
        this.rut = rut;
        this.curso = curso;
        this.cuposDisponibles = 5;
        this.cuposUtilizados = 0;
        }
    }
    public class Recurso{
        private int id;
        private String tipoUso;
        private String titulo;
        private String materia;
        private boolean disponibilidad;
        private int cupos;
        private List<Estudiante> listaEspera;
        public Recurso(int id, String tipoUso, String titulo, String materia, int cupos) {
            this.id = id;
            this.tipoUso = tipoUso;
            this.titulo = titulo;
            this.materia = materia;
            this.disponibilidad = true;
            this.cupos = cupos;
            this.listaEspera = new ArrayList<>();
        }
        
    }
    private static void mostrarMenu() {
        System.out.println("=== Menú de Recursos Educativos ===");
        System.out.println("1. Agregar recurso");
        System.out.println("2. Listar recursos por materia");
        System.out.println("3. Prestar recurso");
        System.out.println("4. Devolver recurso");
        System.out.println("5. Salir");
    }

    public static void main(String[] args) throws IOException {

        BufferedReader lector = new BufferedReader(new InputStreamReader(System.in));
        int opcion;
        String ingresado;
        do {
            mostrarMenu();
            ingresado =lector.readLine();
            opcion   = Integer.parseInt(ingresado.trim()); 
            switch (opcion) {
                case 1 -> {
                }
                case 2 -> {
                }
                case 3 -> {
                }
                case 4 -> {
                }
                case 5 -> System.out.println("Saliendo del sistema.");
                default -> System.out.println("Opción inválida. Intente nuevamente.");
            }

            System.out.println();
        } while (opcion != 5);
    }
}
