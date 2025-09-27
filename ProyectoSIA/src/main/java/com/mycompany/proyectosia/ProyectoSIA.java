package com.mycompany.proyectosia;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class ProyectoSIA {
    
    public static class RecursoNoEncontradoException extends Exception {
        public RecursoNoEncontradoException(String msg) { super(msg); }
    }

    public static class UsuarioNoEncontradoException extends Exception {
        public UsuarioNoEncontradoException(String msg) { super(msg); }
    }

    public static class CuposInsuficientesException extends Exception {
        public CuposInsuficientesException(String msg) { super(msg); }
    }
    public static abstract class Registro {
        public abstract String mostrarResumen();
    }
    // === CLASES ===
    public static class Estudiante extends Registro {
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

    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setRut(String rut) { this.rut = rut; }
    public void setCurso(String curso) { this.curso = curso; }

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

    @Override
    public String mostrarResumen() {
        return nombre + " (" + rut + ") - " + curso;
    }
    }

    public static class Recurso extends Registro {
        private int id;
        private String tipoUso;
        private String titulo;
        private String materia;
        private int cupos;
        private java.util.List<Estudiante> listaPoseedores = new ArrayList<>();

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

        public java.util.List<Estudiante> getListaPoseedores() { return listaPoseedores; }

        @Override
        public String mostrarResumen() {
            return id + ": " + titulo + " [" + materia + "] Cupos: " + cupos;
    }
    // Para CSV (ahora guardamos poseedores como ruts separados por ;)
    public String toCSV() {
        String poseedores = listaPoseedores.stream()
                .map(Estudiante::getRut)
                .collect(Collectors.joining(";"));
        return id + "," + titulo + "," + materia + "," + tipoUso + "," + cupos + "," + poseedores;
    }
            // Buscar estudiante por RUT en la lista global
    private static Estudiante buscarEstudiantePorRut(String rut) {
        if (rut == null) return null;
        return estudiantes.stream()
                .filter(e -> e.getRut().equalsIgnoreCase(rut))
                .findFirst()
                .orElse(null);
    }
    public static Recurso fromCSV(String linea) {
        if (linea == null || linea.trim().isEmpty()) return null;
        String[] parts = linea.split(",", -1); // -1 para conservar campos vacíos
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

        // Si hay campo de poseedores (parts[5]) lo enlazamos a objetos Estudiante cargados.
        if (parts.length >= 6) {
            String poseedoresStr = parts[5].trim();
            if (!poseedoresStr.isEmpty()) {
                String[] ruts = poseedoresStr.split(";");
                for (String rut : ruts) {
                    Estudiante est = buscarEstudiantePorRut(rut.trim());;
                    if (est != null) r.listaPoseedores.add(est);
                }
            }
        }

        return r;
    }


    // === LISTAS GLOBALES ===
    private static final Map<Integer, Recurso> recursos = new HashMap<>();
    private static final java.util.List<Estudiante> estudiantes = new ArrayList<>();

    // === ARCHIVOS CSV ===
    private static final String FILE_ESTUDIANTES = "CSV/alumnos/alumnos.csv";
    private static final String FILE_RECURSOS   = "CSV/recursos/recursos.csv";

    // === MÉTODOS PARA CSV ===
    private static void guardarEstudiantes() {
        try {
            File f = new File(FILE_ESTUDIANTES);
            if (f.getParentFile() != null) f.getParentFile().mkdirs();
            try (PrintWriter pw = new PrintWriter(new FileWriter(f))) {
                pw.println("nombre,rut,curso"); // encabezado
                for (Estudiante e : estudiantes) {
                    pw.println(e.toCSV());
                }
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
        try {
            File f = new File(FILE_RECURSOS);
            if (f.getParentFile() != null) f.getParentFile().mkdirs();
            try (PrintWriter pw = new PrintWriter(new FileWriter(f))) {
                pw.println("id,titulo,materia,tipoUso,cupos,poseedores"); // encabezado
                for (Recurso r : recursos.values()) {
                    pw.println(r.toCSV());
                }
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

    // Generar reporte txt con resumen de recursos y poseedores
    private static void generarReporteTxt(String filename) {
        try {
            File f = new File(filename);
            if (f.getParentFile() != null) f.getParentFile().mkdirs();
            try (PrintWriter pw = new PrintWriter(new FileWriter(f))) {
                pw.println("REPORTE DE RECURSOS");
                pw.println("-------------------");
                for (Recurso r : recursos.values()) {
                    pw.println(r.mostrarResumen());
                    pw.println("Poseedores:");
                    if (r.getListaPoseedores().isEmpty()) {
                        pw.println("  (ninguno)");
                    } else {
                        for (Estudiante e : r.getListaPoseedores()) {
                            pw.println("  - " + e.mostrarResumen());
                        }
                    }
                    pw.println();
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
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

        JPanel panelBotones = new JPanel(new GridLayout(10, 1, 5, 5));

        JButton btnAgregar = new JButton("Agregar recurso");
        JButton btnListar = new JButton("Listar por materia");
        JButton btnPrestar = new JButton("Prestar recurso");
        JButton btnDevolver = new JButton("Devolver recurso");
        JButton btnEditarRecurso = new JButton("Editar recurso");
        JButton btnEliminarRecurso = new JButton("Eliminar recurso");
        JButton btnListarPoseedores = new JButton("Listar poseedores");
        JButton btnEditarEstudiante = new JButton("Editar estudiante (global)");
        JButton btnBuscarPorEstudiante = new JButton("Buscar recursos por estudiante");
        JButton btnGenerarReporte = new JButton("Generar reporte TXT");
        JButton btnSalir = new JButton("Salir");
        
        panelBotones.add(btnAgregar);
        panelBotones.add(btnListar);
        panelBotones.add(btnPrestar);
        panelBotones.add(btnDevolver);
        panelBotones.add(btnEditarRecurso);
        panelBotones.add(btnEliminarRecurso);
        panelBotones.add(btnListarPoseedores);
        panelBotones.add(btnEditarEstudiante);
        panelBotones.add(btnBuscarPorEstudiante);
        panelBotones.add(btnGenerarReporte);
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
        btnEditarRecurso.addActionListener(e -> {
            try {
                String s = JOptionPane.showInputDialog("Ingrese ID del recurso a editar:");
                if (s == null) return;
                int id = Integer.parseInt(s.trim());
                Recurso r = recursos.get(id);
                if (r == null) {
                    JOptionPane.showMessageDialog(frame, "Recurso no encontrado.");
                    return;
                }
                String nuevoTitulo = (String) JOptionPane.showInputDialog(frame, "Título:", "Editar", JOptionPane.PLAIN_MESSAGE, null, null, r.getTitulo());
                String nuevaMateria = (String) JOptionPane.showInputDialog(frame, "Materia:", "Editar", JOptionPane.PLAIN_MESSAGE, null, null, r.getMateria());
                String nuevoTipoUso = (String) JOptionPane.showInputDialog(frame, "Tipo de uso:", "Editar", JOptionPane.PLAIN_MESSAGE, null, null, r.getTipoUso());
                String strCupos = (String) JOptionPane.showInputDialog(frame, "Cupos:", "Editar", JOptionPane.PLAIN_MESSAGE, null, null, String.valueOf(r.getCupos()));
                if (nuevoTitulo != null && nuevaMateria != null && nuevoTipoUso != null && strCupos != null) {
                    r.setTitulo(nuevoTitulo);
                    r.setMateria(nuevaMateria);
                    r.setTipoUso(nuevoTipoUso);
                    r.setCupos(Integer.parseInt(strCupos));
                    guardarRecursos();
                    areaTexto.append("Recurso editado: " + r.getTitulo() + "\n");
                }
            } catch (NumberFormatException nfe) {
                JOptionPane.showMessageDialog(frame, "Cupos inválido.");
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(frame, "Error editando recurso.");
            }
        });

        // Eliminar recurso
        btnEliminarRecurso.addActionListener(e -> {
            try {
                String s = JOptionPane.showInputDialog("Ingrese ID del recurso a eliminar:");
                if (s == null) return;
                int id = Integer.parseInt(s.trim());
                Recurso r = recursos.remove(id);
                if (r == null) {
                    JOptionPane.showMessageDialog(frame, "Recurso no encontrado.");
                    return;
                }
                guardarRecursos();
                areaTexto.append("Recurso eliminado: " + r.getTitulo() + "\n");
            } catch (NumberFormatException nfe) {
                JOptionPane.showMessageDialog(frame, "ID inválido.");
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(frame, "Error eliminando recurso.");
            }
        });

        // Listar poseedores de un recurso
        btnListarPoseedores.addActionListener(e -> {
            try {
                String s = JOptionPane.showInputDialog("Ingrese ID del recurso:");
                if (s == null) return;
                int id = Integer.parseInt(s.trim());
                Recurso r = recursos.get(id);
                if (r == null) {
                    JOptionPane.showMessageDialog(frame, "Recurso no encontrado.");
                    return;
                }
                areaTexto.append("Poseedores de " + r.getTitulo() + ":\n");
                if (r.getListaPoseedores().isEmpty()) areaTexto.append("  (ninguno)\n");
                for (Estudiante est : r.getListaPoseedores()) {
                    areaTexto.append("  - " + est.mostrarResumen() + "\n");
                }
            } catch (NumberFormatException nfe) {
                JOptionPane.showMessageDialog(frame, "ID inválido.");
            }
        });

        // Editar estudiante global (SIA2.12 sobre la 1ª colección también)
        btnEditarEstudiante.addActionListener(e -> {
            String rut = JOptionPane.showInputDialog("Ingrese RUT del estudiante:");
            if (rut == null) return;
            Estudiante est = buscarEstudiantePorRut(rut.trim());
            if (est == null) {
                JOptionPane.showMessageDialog(frame, "Estudiante no encontrado.");
                return;
            }
            String nuevoNombre = (String) JOptionPane.showInputDialog(frame, "Nombre:", "Editar", JOptionPane.PLAIN_MESSAGE, null, null, est.getNombre());
            String nuevoCurso = (String) JOptionPane.showInputDialog(frame, "Curso:", "Editar", JOptionPane.PLAIN_MESSAGE, null, null, est.getCurso());
            if (nuevoNombre != null && nuevoCurso != null) {
                est.setNombre(nuevoNombre);
                est.setCurso(nuevoCurso);
                guardarEstudiantes();
                areaTexto.append("Estudiante editado: " + est.mostrarResumen() + "\n");
            }
        });

        // Generar reporte
        btnGenerarReporte.addActionListener(e -> {
            generarReporteTxt("CSV/reportes/reporte_recursos.txt");
            JOptionPane.showMessageDialog(frame, "Reporte generado en CSV/reportes/reporte_recursos.txt");
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
                String idStr = JOptionPane.showInputDialog("Ingrese ID del recurso a prestar:");
                if (idStr == null) return;
                int idPrestamo = Integer.parseInt(idStr.trim());

                Recurso recurso = recursos.get(idPrestamo);
                if (recurso == null) throw new RecursoNoEncontradoException("Recurso no encontrado.");

                if (recurso.getCupos() <= 0) throw new CuposInsuficientesException("No hay cupos disponibles.");

                // Añadir si no está ya en la lista
                boolean ya = recurso.getListaPoseedores().stream()
                        .anyMatch(est -> est.getRut().equalsIgnoreCase(usuario.getRut()));
                if (!ya) {
                    recurso.getListaPoseedores().add(usuario);
                    recurso.setCupos(recurso.getCupos() - 1);
                    guardarRecursos();
                    areaTexto.append("Recurso prestado a " + usuario.getNombre() + "\n");
                } else {
                    JOptionPane.showMessageDialog(frame, "Usted ya posee ese recurso.");
                }
            } catch (NumberFormatException nfe) {
                JOptionPane.showMessageDialog(frame, "ID inválido.");
            } catch (RecursoNoEncontradoException | CuposInsuficientesException excep) {
                JOptionPane.showMessageDialog(frame, excep.getMessage());
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(frame, "Error en el préstamo.");
            }
        });
        btnBuscarPorEstudiante.addActionListener(e -> {
            String rut = JOptionPane.showInputDialog("Ingrese RUT del estudiante a buscar:");
            if (rut == null) return;
            Estudiante est = buscarEstudiantePorRut(rut.trim());
            if (est == null) {
                JOptionPane.showMessageDialog(frame, "Estudiante no encontrado.");
                return;
            }
            areaTexto.append("Recursos poseídos por " + est.getNombre() + ":\n");
            boolean any = false;
            for (Recurso r : recursos.values()) {
                if (r.getListaPoseedores().stream().anyMatch(pe -> pe.getRut().equalsIgnoreCase(est.getRut()))) {
                    areaTexto.append("  - " + r.mostrarResumen() + "\n");
                    any = true;
                }
            }
            if (!any) areaTexto.append("  (ninguno)\n");
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
        cargarEstudiantes();
        cargarRecursos();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            guardarEstudiantes();
            guardarRecursos();
        }));

        javax.swing.SwingUtilities.invokeLater(() -> mostrarLogin());
    }
}
}