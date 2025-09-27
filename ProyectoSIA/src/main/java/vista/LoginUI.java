package com.mycompany.proyectosia.vista;

import javax.swing.*;
import java.awt.*;
import com.mycompany.proyectosia.modelo.Estudiante;
import com.mycompany.proyectosia.persistencia.GestorDatos;

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

        btnLogin.addActionListener(e -> {
            String rut = txtRut.getText().trim();
            String nombre = txtNombre.getText().trim();
            Estudiante encontrado = GestorDatos.buscarEstudiantePorRut(rut);
            if (encontrado != null && encontrado.getNombre().equalsIgnoreCase(nombre)) {
                JOptionPane.showMessageDialog(this, "Bienvenido " + encontrado.getNombre());
                dispose();
                MenuPrincipalUI menu = new MenuPrincipalUI(encontrado);
                menu.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Usuario no encontrado. Regístrese primero.");
            }
        });

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
            JOptionPane.showMessageDialog(this, "Usuario registrado correctamente.");
        });

        add(lblRut); add(txtRut);
        add(lblNombre); add(txtNombre);
        add(btnLogin); add(btnRegistrar);
    }
}
