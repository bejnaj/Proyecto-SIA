package com.mycompany.proyectosia;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.*;

public class ProyectoSIA {
    // === CLASES ===
    public static class Estudiante {
        private String nombre;
        private String rut;
        private String curso;

        public Estudiante(String nombre, String rut, String curso) {
            this.nombre = nombre;
            this.rut = rut;
            this.curso = curso;
        }

        public String getNombre() { return nombre; }
        public String getRut() { return rut; }
        public String getCurso() { return curso; }

        // Para CSV
        public String toCSV() {
            return nombre + "," + rut + "," + curso;
        }

        public static Estudiante fromCSV(String linea) {
            if (linea == null || linea.trim().isEmpty()) return null;
            String[] parts = linea.split(",");
            if (parts.length < 3) return null;

            // Ignorar encabezado
            if (parts[0].trim().equalsIgnoreCase("nombre")) return null;

            String nombre = parts[0].trim();
            String rut = parts[1].trim();
            String curso = parts[2].trim();

            if (nombre.isEmpty() || rut.isEmpty() || curso.isEmpty()) return null;

            return new Estudiante(nombre, rut, curso);
        }
    }

    public static class Recurso {
        private int id;
        private String tipoUso;
        private String titulo;
        private String materia;
        private int cupos;
        private java.util.List<Estudiante> listaPoseedores;

        public Recurso() {
            this.listaPoseedores = new ArrayList<>();
        }

        public int getId() { return id; }
        public String getTipoUso() { return tipoUso; }
        public String getTitulo() { return titulo; }
        public String getMateria() { return materia; }
        public int getCupos() { return cupos; }
        public java.util.List<Estudiante> getListaPoseedores() { return listaPoseedores; }

        public void setId(int id) { this.id = id; }
        public void setTipoUso(String tipoUso) { this.tipoUso = tipoUso; }
        public void setTitulo(String titulo) { this.titulo = titulo; }
        public void setMateria(String materia) { this.materia = materia; }
        public void setCupos(int cupos) { this.cupos = cupos; }

        // Para CSV (no guardamos listaPoseedores aún, se puede extender)
        public String toCSV() {
            return id + "," + titulo + "," + materia + "," + tipoUso + "," + cupos;
        }

        public static Recurso fromCSV(String linea) {
            if (linea == null || linea.trim().isEmpty()) return null;
            String[] parts = linea.split(",");
            if (parts.length < 5) return null;

            // Ignorar encabezado
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

            return r;
        }
    }

    // === LISTAS GLOBALES ===
    private static final Map<Integer, Recurso> recursos = new HashMap<>();
    private static final java.util.List<Estudiante> estudiantes = new ArrayList<>();

    // === ARCHIVOS CSV ===
    private static final String FILE_ESTUDIANTES = "CSV/alumnos/alumnos.csv";
    private static final String FILE_RECURSOS   = "CSV/recursos/recursos.csv";

    // === MÉTODOS PARA CSV ===
    private static void guardarEstudiantes() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(FILE_ESTUDIANTES))) {
            for (Estudiante e : estudiantes) {
                pw.println(e.toCSV());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void cargarEstudiantes() {
        File f = new File(FILE_ESTUDIANTES);
        if (!f.exists()) return;
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                Estudiante e = Estudiante.fromCSV(linea);
                if (e != null) estudiantes.add(e);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void guardarRecursos() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(FILE_RECURSOS))) {
            for (Recurso r : recursos.values()) {
                pw.println(r.toCSV());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void cargarRecursos() {
        File f = new File(FILE_RECURSOS);
        if (!f.exists()) return;
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                Recurso r = Recurso.fromCSV(linea);
                if (r != null) recursos.put(r.getId(), r);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // === LOGIN ===
    private static void mostrarLogin() {
        JFrame loginFrame = new JFrame("Login Estudiante");
        loginFrame.setSize(400, 200);
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.setLayout(new GridLayout(4, 2, 5, 5));

        JLabel lblRut = new JLabel("RUT:");
        JTextField txtRut = new JTextField();
        JLabel lblNombre = new JLabel("Nombre:");
        JTextField txtNombre = new JTextField();

        JButton btnLogin = new JButton("Iniciar Sesión");
        JButton btnRegistrar = new JButton("Registrar Usuario");

        // Acción Login
        btnLogin.addActionListener(e -> {
            String rut = txtRut.getText().trim();
            String nombre = txtNombre.getText().trim();

            Optional<Estudiante> encontrado = estudiantes.stream()
                    .filter(est -> est.getRut().equalsIgnoreCase(rut) && est.getNombre().equalsIgnoreCase(nombre))
                    .findFirst();

            if (encontrado.isPresent()) {
                JOptionPane.showMessageDialog(loginFrame, "Bienvenido " + encontrado.get().getNombre());
                loginFrame.dispose();
                mostrarMenuPrincipal(encontrado.get()); // abre ventana principal
            } else {
                JOptionPane.showMessageDialog(loginFrame, "Usuario no encontrado. Regístrese primero.");
            }
        });

        // Acción Registrar
        btnRegistrar.addActionListener(e -> {
            String rut = txtRut.getText().trim();
            String nombre = txtNombre.getText().trim();
            String curso = JOptionPane.showInputDialog("Ingrese curso:");

            if (rut.isEmpty() || nombre.isEmpty() || curso == null) {
                JOptionPane.showMessageDialog(loginFrame, "Datos incompletos.");
                return;
            }

            Estudiante nuevo = new Estudiante(nombre, rut, curso);
            estudiantes.add(nuevo);
            guardarEstudiantes(); // Guardar en CSV
            JOptionPane.showMessageDialog(loginFrame, "Usuario registrado correctamente.");
        });

        loginFrame.add(lblRut);
        loginFrame.add(txtRut);
        loginFrame.add(lblNombre);
        loginFrame.add(txtNombre);
        loginFrame.add(btnLogin);
        loginFrame.add(btnRegistrar);

        loginFrame.setVisible(true);
    }

    // === MENU PRINCIPAL ===
    private static void mostrarMenuPrincipal(Estudiante usuario) {
        JFrame frame = new JFrame("Gestión de Recursos - Usuario: " + usuario.getNombre());
        frame.setSize(500, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JTextArea areaTexto = new JTextArea();
        areaTexto.setEditable(false);
        JScrollPane scroll = new JScrollPane(areaTexto);

        JPanel panelBotones = new JPanel(new GridLayout(5, 1, 5, 5));

        JButton btnAgregar = new JButton("Agregar recurso");
        JButton btnListar = new JButton("Listar por materia");
        JButton btnPrestar = new JButton("Prestar recurso");
        JButton btnDevolver = new JButton("Devolver recurso");
        JButton btnSalir = new JButton("Salir");

        panelBotones.add(btnAgregar);
        panelBotones.add(btnListar);
        panelBotones.add(btnPrestar);
        panelBotones.add(btnDevolver);
        panelBotones.add(btnSalir);

        // Botones
        btnAgregar.addActionListener(e -> {
            try {
                int id = Integer.parseInt(JOptionPane.showInputDialog("Ingrese ID del recurso:"));
                if (recursos.containsKey(id)) {
                    JOptionPane.showMessageDialog(frame, "Ya existe un recurso con ese ID.");
                    return;
                }
                String titulo = JOptionPane.showInputDialog("Título:");
                String materia = JOptionPane.showInputDialog("Materia:");
                String tipoUso = JOptionPane.showInputDialog("Tipo de uso:");
                int cupos = Integer.parseInt(JOptionPane.showInputDialog("Cantidad de cupos:"));

                Recurso nuevo = new Recurso();
                nuevo.setId(id);
                nuevo.setTitulo(titulo);
                nuevo.setMateria(materia);
                nuevo.setTipoUso(tipoUso);
                nuevo.setCupos(cupos);

                recursos.put(nuevo.getId(), nuevo);
                guardarRecursos(); // Guardar en CSV
                areaTexto.append("Recurso agregado: " + titulo + "\n");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Error: entrada inválida.");
            }
        });

        btnListar.addActionListener(e -> {
            String materiaBuscada = JOptionPane.showInputDialog("Ingrese la materia a listar:");
            boolean encontrado = false;
            for (Recurso rec : recursos.values()) {
                if (rec.getMateria().equalsIgnoreCase(materiaBuscada)) {
                    areaTexto.append("ID: " + rec.getId() + " | " + rec.getTitulo() +
                            " (" + rec.getTipoUso() + "), Cupos: " + rec.getCupos() + "\n");
                    encontrado = true;
                }
            }
            if (!encontrado) {
                areaTexto.append("No se encontraron recursos para " + materiaBuscada + "\n");
            }
        });

        btnPrestar.addActionListener(e -> {
            try {
                int idPrestamo = Integer.parseInt(JOptionPane.showInputDialog("Ingrese ID del recurso a prestar:"));
                Recurso recurso = recursos.get(idPrestamo);
                if (recurso == null) {
                    JOptionPane.showMessageDialog(frame, "Recurso no encontrado.");
                    return;
                }
                if (recurso.getCupos() <= 0) {
                    JOptionPane.showMessageDialog(frame, "No hay cupos disponibles.");
                    return;
                }

                recurso.getListaPoseedores().add(usuario);
                recurso.setCupos(recurso.getCupos() - 1);
                guardarRecursos(); // Guardar cambios
                areaTexto.append("Recurso prestado a " + usuario.getNombre() + "\n");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Error en el préstamo.");
            }
        });

        btnDevolver.addActionListener(e -> {
            try {
                int idDevolver = Integer.parseInt(JOptionPane.showInputDialog("Ingrese ID del recurso a devolver:"));
                Recurso recurso = recursos.get(idDevolver);
                if (recurso == null) {
                    JOptionPane.showMessageDialog(frame, "Recurso no encontrado.");
                    return;
                }

                boolean encontrado = recurso.getListaPoseedores().removeIf(est -> est.getRut().equals(usuario.getRut()));
                if (encontrado) {
                    recurso.setCupos(recurso.getCupos() + 1);
                    guardarRecursos(); // Guardar cambios
                    areaTexto.append("Recurso devuelto por " + usuario.getNombre() + "\n");
                } else {
                    JOptionPane.showMessageDialog(frame, "Usted no tenía este recurso.");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Error en la devolución.");
            }
        });

        btnSalir.addActionListener(e -> frame.dispose());

        frame.add(panelBotones, BorderLayout.WEST);
        frame.add(scroll, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        // Cargar datos al iniciar
        cargarEstudiantes();
        cargarRecursos();
        SwingUtilities.invokeLater(ProyectoSIA::mostrarLogin);
    }
}
