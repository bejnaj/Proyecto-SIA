package com.mycompany.proyectosia.vista;

import javax.swing.*;
import java.awt.*;
import com.mycompany.proyectosia.modelo.Estudiante;
import com.mycompany.proyectosia.persistencia.GestorDatos;
import com.mycompany.proyectosia.vista.MenuPrincipalUI;

public class LoginUI extends JFrame {
    public LoginUI() {
        super("Login Estudiante");
        setSize(420, 220);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(4,2,5,5));
        setLocationRelativeTo(null);

        JLabel lblRut = new JLabel("RUT:");
        JTextField txtRut = new JTextField();
        JLabel lblNombre = new JLabel("Nombre:");
        JTextField txtNombre = new JTextField();

        JButton btnLogin = new JButton("Iniciar Sesión");
        JButton btnRegistrar = new JButton("Registrar Usuario");

        // ---- LOGIN ----
        btnLogin.addActionListener(e -> {
            String rut = txtRut.getText().trim();
            String nombre = txtNombre.getText().trim();
            if (rut.isEmpty() || nombre.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Ingrese RUT y Nombre.");
                return;
            }

            // Caso administrador
            if (rut.equalsIgnoreCase("admin") && nombre.equalsIgnoreCase("admin")) {
                Estudiante adminUser = new Estudiante("admin", "admin", "ADMIN");
                new MenuPrincipalUI(adminUser).setVisible(true);
                dispose();
                return;
            }

            Estudiante encontrado = GestorDatos.buscarEstudiantePorRut(rut);
            if (encontrado != null && encontrado.getNombre().equalsIgnoreCase(nombre)) {
                new MenuPrincipalUI(encontrado).setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Usuario no encontrado o nombre no coincide.");
            }
        });

        // ---- REGISTRO + AUTO LOGIN ----
        btnRegistrar.addActionListener(e -> {
            String rut = txtRut.getText().trim();
            String nombre = txtNombre.getText().trim();
            String curso = JOptionPane.showInputDialog(this, "Ingrese curso:"); 
            if (rut.isEmpty() || nombre.isEmpty() || curso == null || curso.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Datos incompletos.");
                return;
            }
            Estudiante nuevo = new Estudiante(nombre, rut, curso.trim());
            GestorDatos.agregarEstudiante(nuevo);
            JOptionPane.showMessageDialog(this, "Usuario registrado correctamente. Iniciando sesión...");
            new MenuPrincipalUI(nuevo).setVisible(true);
            dispose();
        });

        add(lblRut); add(txtRut);
        add(lblNombre); add(txtNombre);
        add(btnLogin); add(btnRegistrar);
    }
}
