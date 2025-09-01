/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.proyectosia;

/**
 *
 * @author benjo
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
        // GETTERS de estudiante
        public String getNombre(){return nombre;}
        public String getRut(){return rut;}
        public String getCurso(){return curso;}
        public int getCuposDisponibles(){return cuposDisponibles;}
        public int getCuposUtilizados(){return cuposUtilizados;}
        // SETTERS de estudiante
        public void setNombre(String nombre){
            this.nombre = nombre;
        }
        public void setRut(String rut){
            this.rut = rut;
        }
        public void setCurso(String curso){
            this.curso = curso;
        }
        public void setCuposDisponibles (int valor){
            this.cuposDisponibles = valor;
        }
        public void setCuposUtilizados (int valor){
            this.cuposUtilizados = valor;
        }
        // SERGIO BOLAINAS CODOCIA PARADA VA A AHACER LA SOBRECARGA DE METODOS AQUI !!!!!1
        
        
    }
    public class Recurso{
        private int id;
        private String tipoUso;
        private String titulo;
        private String materia;
        private boolean disponibilidad;
        private int cupos;
        private List<estudiante> listaEspera;
        public Recurso() {                     //Constructor que lo arregla  Leandro Raul bravo guerrero 22006725-4
            this.id = 0;
            this.tipoUso = "No definido";
            this.titulo = "No definido";
            this.materia = "No definida";
            this.disponibilidad = false;
            this.cupos = 0;
            this.listaEspera = new ArrayList<>();
        }
        //GETTERS de recurso
        public int getId(){return id;}
        public String getTipoUso(){return tipoUso;}
        public String getTitulo(){return titulo;}
        public String getMateria(){return materia;}
        public int getCupos(){return cupos;}
        //SETTERS de recuro
        public void asignarId(int id){
            this.id = id;
        }
        public void asignarTipoUso (String tipoUso){
            this.tipoUso = tipoUso;
        }
        public void asignarTitulo (String titulo){
            this.titulo = titulo;
        }
        public void asignarMateria (String materia){
            this.materia = materia;
        }
        public void asignarCupos (int cupos){
            this.cupos = cupos;
        }
    } 
    private static void mostrarMenu() {
        System.out.println("=== Menú de Recursos Educativos ===");
        System.out.println("1. Agregar recurso");
        System.out.println("2. Listar recursos por materia");
        System.out.println("3. Prestar recurso");
        System.out.println("4. Devolver recurso");
        System.out.println("5. Salir");
        System.out.println("===================================");
        System.out.println("Selecciona un numero");
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
