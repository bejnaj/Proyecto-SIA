package com.mycompany.proyectosia.vista;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import com.mycompany.proyectosia.modelo.Estudiante;
import com.mycompany.proyectosia.modelo.Recurso;
import com.mycompany.proyectosia.persistencia.GestorDatos;
import com.mycompany.proyectosia.persistencia.ReporteGenerator;
import com.mycompany.proyectosia.excepciones.CuposInsuficientesException;
import com.mycompany.proyectosia.excepciones.RecursoNoEncontradoException;

public class MenuPrincipalUI extends JFrame {
    private Estudiante usuario;
    public MenuPrincipalUI(Estudiante usuario) {
        super("Gestión de Recursos - Usuario: " + usuario.getNombre());
        this.usuario = usuario;
        setSize(700,500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);

        JTextArea areaTexto = new JTextArea(); areaTexto.setEditable(false);
        JScrollPane scroll = new JScrollPane(areaTexto);

        JPanel panelBotones = new JPanel(new GridLayout(11,1,5,5));
        JButton btnAgregar = new JButton("Agregar recurso");
        JButton btnListar = new JButton("Listar por materia");
        JButton btnPrestar = new JButton("Prestar recurso");
        JButton btnDevolver = new JButton("Devolver recurso");
        JButton btnEditarRecurso = new JButton("Editar recurso");
        JButton btnEliminarRecurso = new JButton("Eliminar recurso");
        JButton btnListarPoseedores = new JButton("Listar poseedores");
        JButton btnEditarEstudiante = new JButton("Editar estudiante (global)"); 
        JButton btnEliminarEstudiante = new JButton("Eliminar estudiante (global)"); 
        JButton btnBuscarPorEstudiante = new JButton("Buscar recursos por estudiante");
        JButton btnGenerarReporte = new JButton("Generar reporte TXT");
        JButton btnSalir = new JButton("Salir");

        panelBotones.add(btnAgregar); panelBotones.add(btnListar); panelBotones.add(btnPrestar);
        panelBotones.add(btnDevolver); panelBotones.add(btnEditarRecurso); panelBotones.add(btnEliminarRecurso);
        panelBotones.add(btnListarPoseedores); panelBotones.add(btnEditarEstudiante); panelBotones.add(btnEliminarEstudiante);
        panelBotones.add(btnBuscarPorEstudiante); panelBotones.add(btnGenerarReporte); panelBotones.add(btnSalir);

        // Acciones
        btnAgregar.addActionListener(e -> {
            try {
                String idStr = JOptionPane.showInputDialog(this, "Ingrese ID del recurso (entero):");
                if (idStr == null) return;
                int id = Integer.parseInt(idStr.trim());
                if (GestorDatos.getRecursosMap().containsKey(id)) {
                    JOptionPane.showMessageDialog(this, "Ya existe un recurso con ese ID.");
                    return;
                }
                String titulo = JOptionPane.showInputDialog(this, "Título:"); if (titulo==null) return;
                String materia = JOptionPane.showInputDialog(this, "Materia:"); if (materia==null) return;
                String tipoUso = JOptionPane.showInputDialog(this, "Tipo de uso:"); if (tipoUso==null) return;
                String cuposStr = JOptionPane.showInputDialog(this, "Cantidad de cupos:"); if (cuposStr==null) return;
                int cupos = Integer.parseInt(cuposStr.trim());
                Recurso nuevo = new Recurso(); nuevo.setId(id); nuevo.setTitulo(titulo); nuevo.setMateria(materia);
                nuevo.setTipoUso(tipoUso); nuevo.setCupos(cupos);
                GestorDatos.agregarRecurso(nuevo);
                areaTexto.append("Recurso agregado: " + titulo + "\n");
            } catch (NumberFormatException ex) { JOptionPane.showMessageDialog(this, "Entrada inválida (número).");
            } catch (Exception ex) { ex.printStackTrace(); JOptionPane.showMessageDialog(this, "Error agregando recurso."); }
        });

        btnListar.addActionListener(e -> {
            String materiaBuscada = JOptionPane.showInputDialog(this, "Ingrese la materia a listar:"); if (materiaBuscada==null) return;
            List<Recurso> encontrados = GestorDatos.filtrarRecursosPorMateria(materiaBuscada);
            if (encontrados.isEmpty()) areaTexto.append("No se encontraron recursos para " + materiaBuscada + "\n");
            else {
                for (Recurso rec : encontrados) areaTexto.append("ID: " + rec.getId() + " | " + rec.getTitulo() + " (" + rec.getTipoUso() + "), Cupos: " + rec.getCupos() + "\n");
            }
        });

        btnPrestar.addActionListener(e -> {
            try {
                String idStr = JOptionPane.showInputDialog(this, "Ingrese ID del recurso a prestar:"); if (idStr==null) return;
                int idPrestamo = Integer.parseInt(idStr.trim());
                Recurso recurso = GestorDatos.getRecursosMap().get(idPrestamo);
                if (recurso == null) throw new RecursoNoEncontradoException("Recurso no encontrado.");
                if (recurso.getCupos() <= 0) throw new CuposInsuficientesException("No hay cupos disponibles.");
                boolean ya = recurso.getListaPoseedores().stream().anyMatch(est -> est.getRut().equalsIgnoreCase(usuario.getRut()));
                if (!ya) {
                    recurso.getListaPoseedores().add(usuario);
                    recurso.setCupos(recurso.getCupos() - 1);
                    GestorDatos.actualizarRecurso(recurso);
                    areaTexto.append("Recurso prestado a " + usuario.getNombre() + "\n");
                } else JOptionPane.showMessageDialog(this, "Usted ya posee ese recurso.");
            } catch (NumberFormatException nfe) { JOptionPane.showMessageDialog(this, "ID inválido."); }
            catch (CuposInsuficientesException | RecursoNoEncontradoException ex) { JOptionPane.showMessageDialog(this, ex.getMessage()); }
            catch (Exception ex) { ex.printStackTrace(); JOptionPane.showMessageDialog(this, "Error en el préstamo."); }
        });

        btnDevolver.addActionListener(e -> {
            try {
                String idStr = JOptionPane.showInputDialog(this, "Ingrese ID del recurso a devolver:"); if (idStr==null) return;
                int idDevolver = Integer.parseInt(idStr.trim());
                Recurso recurso = GestorDatos.getRecursosMap().get(idDevolver);
                if (recurso == null) { JOptionPane.showMessageDialog(this, "Recurso no encontrado."); return; }
                boolean encontrado = recurso.getListaPoseedores().removeIf(est -> est.getRut().equalsIgnoreCase(usuario.getRut()));
                if (encontrado) {
                    recurso.setCupos(recurso.getCupos() + 1);
                    GestorDatos.actualizarRecurso(recurso);
                    areaTexto.append("Recurso devuelto por " + usuario.getNombre() + "\n");
                } else JOptionPane.showMessageDialog(this, "Usted no tenía este recurso.");
            } catch (NumberFormatException nfe) { JOptionPane.showMessageDialog(this, "ID inválido."); }
            catch (Exception ex) { ex.printStackTrace(); JOptionPane.showMessageDialog(this, "Error en la devolución."); }
        });

        btnEditarRecurso.addActionListener(e -> {
            try {
                String s = JOptionPane.showInputDialog(this, "Ingrese ID del recurso a editar:"); if (s==null) return;
                int id = Integer.parseInt(s.trim()); Recurso r = GestorDatos.getRecursosMap().get(id);
                if (r == null) { JOptionPane.showMessageDialog(this, "Recurso no encontrado."); return; }
                String nuevoTitulo = (String) JOptionPane.showInputDialog(this, "Título:", "Editar", JOptionPane.PLAIN_MESSAGE, null, null, r.getTitulo());
                String nuevaMateria = (String) JOptionPane.showInputDialog(this, "Materia:", "Editar", JOptionPane.PLAIN_MESSAGE, null, null, r.getMateria());
                String nuevoTipoUso = (String) JOptionPane.showInputDialog(this, "Tipo de uso:", "Editar", JOptionPane.PLAIN_MESSAGE, null, null, r.getTipoUso());
                String strCupos = (String) JOptionPane.showInputDialog(this, "Cupos:", "Editar", JOptionPane.PLAIN_MESSAGE, null, null, String.valueOf(r.getCupos()));
                if (nuevoTitulo != null && nuevaMateria != null && nuevoTipoUso != null && strCupos != null) {
                    r.setTitulo(nuevoTitulo); r.setMateria(nuevaMateria); r.setTipoUso(nuevoTipoUso); r.setCupos(Integer.parseInt(strCupos));
                    GestorDatos.actualizarRecurso(r);
                    areaTexto.append("Recurso editado: " + r.getTitulo() + "\n");
                }
            } catch (NumberFormatException nfe) { JOptionPane.showMessageDialog(this, "Cupos inválido."); }
            catch (Exception ex) { ex.printStackTrace(); JOptionPane.showMessageDialog(this, "Error editando recurso."); }
        });

        btnEliminarRecurso.addActionListener(e -> {
            try {
                String s = JOptionPane.showInputDialog(this, "Ingrese ID del recurso a eliminar:"); if (s==null) return;
                int id = Integer.parseInt(s.trim()); Recurso r = GestorDatos.getRecursosMap().get(id);
                if (r == null) { JOptionPane.showMessageDialog(this, "Recurso no encontrado."); return; }
                GestorDatos.eliminarRecurso(id);
                areaTexto.append("Recurso eliminado: " + r.getTitulo() + "\n");
            } catch (NumberFormatException nfe) { JOptionPane.showMessageDialog(this, "ID inválido."); }
            catch (Exception ex) { ex.printStackTrace(); JOptionPane.showMessageDialog(this, "Error eliminando recurso."); }
        });

        btnListarPoseedores.addActionListener(e -> {
            try {
                String s = JOptionPane.showInputDialog(this, "Ingrese ID del recurso:"); if (s==null) return;
                int id = Integer.parseInt(s.trim()); Recurso r = GestorDatos.getRecursosMap().get(id);
                if (r == null) { JOptionPane.showMessageDialog(this, "Recurso no encontrado."); return; }
                areaTexto.append("Poseedores de " + r.getTitulo() + ":\n");
                if (r.getListaPoseedores().isEmpty()) areaTexto.append("  (ninguno)\n");
                for (Estudiante est : r.getListaPoseedores()) areaTexto.append("  - " + est.mostrarResumen() + "\n");
            } catch (NumberFormatException nfe) { JOptionPane.showMessageDialog(this, "ID inválido."); }
        });

        btnEditarEstudiante.addActionListener(e -> {
            String rut = JOptionPane.showInputDialog(this, "Ingrese RUT del estudiante:"); if (rut==null) return;
            Estudiante est = GestorDatos.buscarEstudiantePorRut(rut.trim()); if (est==null) { JOptionPane.showMessageDialog(this, "Estudiante no encontrado."); return; }
            String nuevoNombre = (String) JOptionPane.showInputDialog(this, "Nombre:", "Editar", JOptionPane.PLAIN_MESSAGE, null, null, est.getNombre());
            String nuevoCurso = (String) JOptionPane.showInputDialog(this, "Curso:", "Editar", JOptionPane.PLAIN_MESSAGE, null, null, est.getCurso());
            if (nuevoNombre != null && nuevoCurso != null) { est.setNombre(nuevoNombre); est.setCurso(nuevoCurso); GestorDatos.guardarEstudiantes(); areaTexto.append("Estudiante editado: " + est.mostrarResumen() + "\n"); }
        });

        btnEliminarEstudiante.addActionListener(e -> {
            String rut = JOptionPane.showInputDialog(this, "Ingrese RUT del estudiante a eliminar:"); if (rut==null) return;
            Estudiante est = GestorDatos.buscarEstudiantePorRut(rut.trim()); if (est==null) { JOptionPane.showMessageDialog(this, "Estudiante no encontrado."); return; }
            int confirm = JOptionPane.showConfirmDialog(this, "¿Eliminar al estudiante " + est.getNombre() + "?","Confirmar", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                GestorDatos.eliminarEstudiantePorRut(est.getRut());
                areaTexto.append("Estudiante eliminado: " + est.mostrarResumen() + "\n");
            }
        });

        btnBuscarPorEstudiante.addActionListener(e -> {
            String rut = JOptionPane.showInputDialog(this, "Ingrese RUT del estudiante a buscar:"); if (rut==null) return;
            Estudiante est = GestorDatos.buscarEstudiantePorRut(rut.trim()); if (est==null) { JOptionPane.showMessageDialog(this, "Estudiante no encontrado."); return; }
            areaTexto.append("Recursos poseídos por " + est.getNombre() + ":\n");
            boolean any = false;
            for (Recurso r : GestorDatos.getRecursosMap().values()) {
                if (r.getListaPoseedores().stream().anyMatch(pe -> pe.getRut().equalsIgnoreCase(est.getRut()))) {
                    areaTexto.append("  - " + r.mostrarResumen() + "\n"); any = true;
                }
            }
            if (!any) areaTexto.append("  (ninguno)\n");
        });

        btnGenerarReporte.addActionListener(e -> {
            ReporteGenerator.generarReporteTxt("CSV/reportes/reporte_recursos.txt");
            JOptionPane.showMessageDialog(this, "Reporte generado en CSV/reportes/reporte_recursos.txt");
        });

        btnSalir.addActionListener(e -> { dispose(); });

        add(panelBotones, BorderLayout.WEST);
        add(scroll, BorderLayout.CENTER);
    }
}
