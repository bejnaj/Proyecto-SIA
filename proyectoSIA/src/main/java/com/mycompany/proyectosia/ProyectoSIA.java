package com.mycompany.proyectosia;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class ProyectoSIA {
    public static class Estudiante {
        private String nombre;
        private String rut;
        private String curso;
        private int cuposDisponibles;
        private int cuposUtilizados;

        public Estudiante(String nombre, String rut, String curso) {
            this.nombre = nombre;
            this.rut = rut;
            this.curso = curso;
            this.cuposDisponibles = 5;
            this.cuposUtilizados = 0;
        }

        // GETTERS
        public String getNombre() { return nombre; }
        public String getRut() { return rut; }
        public String getCurso() { return curso; }
        public int getCuposDisponibles() { return cuposDisponibles; }
        public int getCuposUtilizados() { return cuposUtilizados; }

        // SETTERS
        public void setNombre(String nombre) { this.nombre = nombre; }
        public void setRut(String rut) { this.rut = rut; }
        public void setCurso(String curso) { this.curso = curso; }
        public void setCuposDisponibles(int valor) { this.cuposDisponibles = valor; }
        public void setCuposUtilizados(int valor) { this.cuposUtilizados = valor; }
    }

    public static class Recurso {
        private int id;
        private String tipoUso;
        private String titulo;
        private String materia;
        private boolean disponibilidad;
        private int cupos;
        private List<Estudiante> listaPoseedores;

        public Recurso() {
            this.id = 0;
            this.tipoUso = "No definido";
            this.titulo = "No definido";
            this.materia = "No definida";
            this.disponibilidad = false;
            this.cupos = 0;
            this.listaPoseedores = new ArrayList<>();
        }

        // GETTERS
        public int getId() { return id; }
        public String getTipoUso() { return tipoUso; }
        public String getTitulo() { return titulo; }
        public String getMateria() { return materia; }
        public int getCupos() { return cupos; }
        public List<Estudiante> getListaPoseedores() { return listaPoseedores; }

        // SETTERS
        public void setId(int id) { this.id = id; }
        public void setTipoUso(String tipoUso) { this.tipoUso = tipoUso; }
        public void setTitulo(String titulo) { this.titulo = titulo; }
        public void setMateria(String materia) { this.materia = materia; }
        public void setCupos(int cupos) { this.cupos = cupos; }
    }

    private static void mostrarMenu() {
        System.out.println("=== Menú de Recursos Educativos ===");
        System.out.println("1. Agregar recurso");
        System.out.println("2. Listar recursos por materia");
        System.out.println("3. Prestar recurso");
        System.out.println("4. Devolver recurso");
        System.out.println("5. Salir");
        System.out.println("===================================");
        System.out.print("Selecciona un número: ");
    }

    public static void main(String[] args) throws IOException {
        // Usamos un Mapa con el id como clave
        Map<Integer, Recurso> recursos = new HashMap<>();
        BufferedReader lector = new BufferedReader(new InputStreamReader(System.in));

        // --- Recursos de prueba ---
        Recurso r1 = new Recurso();
        r1.setId(1); r1.setTitulo("Álgebra Lineal"); r1.setMateria("Matemáticas"); r1.setTipoUso("Libro"); r1.setCupos(3);
        Recurso r2 = new Recurso();
        r2.setId(2); r2.setTitulo("Electricidad Básica"); r2.setMateria("Física"); r2.setTipoUso("Video"); r2.setCupos(5);
        Recurso r3 = new Recurso();
        r3.setId(3); r3.setTitulo("Cálculo Diferencial"); r3.setMateria("Matemáticas"); r3.setTipoUso("Libro"); r3.setCupos(2);

        recursos.put(r1.getId(), r1);
        recursos.put(r2.getId(), r2);
        recursos.put(r3.getId(), r3);

        int opcion;
        String ingresado;

        do {
            mostrarMenu();
            ingresado = lector.readLine();
            opcion = Integer.parseInt(ingresado.trim());

            switch (opcion) {
                case 1 -> {
                    System.out.print("Ingrese ID del recurso: ");
                    int id = Integer.parseInt(lector.readLine());
                    if (recursos.containsKey(id)) {
                        System.out.println("Ya existe un recurso con ese ID.");
                        break;
                    }
                    Recurso nuevo = new Recurso();
                    nuevo.setId(id);
                    System.out.print("Título: ");
                    nuevo.setTitulo(lector.readLine());
                    System.out.print("Materia: ");
                    nuevo.setMateria(lector.readLine());
                    System.out.print("Tipo de uso: ");
                    nuevo.setTipoUso(lector.readLine());
                    System.out.print("Cantidad de cupos: ");
                    nuevo.setCupos(Integer.parseInt(lector.readLine()));

                    recursos.put(nuevo.getId(), nuevo);
                    System.out.println("Recurso agregado correctamente.");
                }
                case 2 -> {
                    System.out.print("Ingrese la materia a listar: ");
                    String materiaBuscada = lector.readLine();
                    boolean encontrado = false;
                    for (Recurso rec : recursos.values()) {
                        if (rec.getMateria().equalsIgnoreCase(materiaBuscada)) {
                            System.out.println("ID: " + rec.getId() + " | " + rec.getTitulo() +
                                               " (" + rec.getTipoUso() + "), Cupos: " + rec.getCupos());
                            encontrado = true;
                        }
                    }
                    if (!encontrado) {
                        System.out.println("No se encontraron recursos para la materia " + materiaBuscada);
                    }
                }
                case 3 -> {
                    System.out.print("Ingrese ID del recurso a prestar: ");
                    int idPrestamo = Integer.parseInt(lector.readLine());
                    Recurso recurso = recursos.get(idPrestamo);
                    if (recurso == null) {
                        System.out.println("Recurso no encontrado.");
                        break;
                    }
                    if (recurso.getCupos() <= 0) {
                        System.out.println("No hay cupos disponibles para este recurso.");
                        break;
                    }
                    System.out.print("Nombre estudiante: ");
                    String nombre = lector.readLine();
                    System.out.print("RUT estudiante: ");
                    String rut = lector.readLine();
                    System.out.print("Curso estudiante: ");
                    String curso = lector.readLine();

                    Estudiante e = new Estudiante(nombre, rut, curso);
                    recurso.getListaPoseedores().add(e);
                    recurso.setCupos(recurso.getCupos() - 1);
                    System.out.println("Recurso prestado correctamente a " + e.getNombre());
                }
                case 4 -> {
                    System.out.print("Ingrese ID del recurso a devolver: ");
                    int idDevolver = Integer.parseInt(lector.readLine());
                    Recurso recurso = recursos.get(idDevolver);
                    if (recurso == null) {
                        System.out.println("Recurso no encontrado.");
                        break;
                    }
                    System.out.print("Ingrese RUT del estudiante: ");
                    String rutDevolver = lector.readLine();

                    boolean encontrado = false;
                    Iterator<Estudiante> it = recurso.getListaPoseedores().iterator();
                    while (it.hasNext()) {
                        Estudiante e = it.next();
                        if (e.getRut().equalsIgnoreCase(rutDevolver)) {
                            it.remove();
                            recurso.setCupos(recurso.getCupos() + 1);
                            System.out.println("Recurso devuelto correctamente por " + e.getNombre());
                            encontrado = true;
                            break;
                        }
                    }
                    if (!encontrado) {
                        System.out.println("Ese estudiante no tenía prestado este recurso.");
                    }
                }
                case 5 -> System.out.println("Saliendo del sistema.");
                default -> System.out.println("Opción inválida. Intente nuevamente.");
            }
            System.out.println();
        } while (opcion != 5);
    }
}
