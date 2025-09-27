package com.mycompany.proyectosia;

import com.mycompany.proyectosia.persistencia.GestorDatos;

public class ProyectoSIA {
    public static void main(String[] args) {
        // Cargar datos (batch)
        GestorDatos.cargarEstudiantes();
        GestorDatos.cargarRecursos();

        // Agregar shutdown hook para guardar datos al salir
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            GestorDatos.guardarEstudiantes();
            GestorDatos.guardarRecursos();
        }));

        // Lanzar la interfaz
        javax.swing.SwingUtilities.invokeLater(() -> {
            com.mycompany.proyectosia.vista.LoginUI login = new com.mycompany.proyectosia.vista.LoginUI();
            login.setVisible(true);
        });
    }
}
