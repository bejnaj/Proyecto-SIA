import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;
/**
 *
 * @authors Sergio Codoceo Leandro Bravo Bastian Contreras
 */
public class Sistema {
    
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
