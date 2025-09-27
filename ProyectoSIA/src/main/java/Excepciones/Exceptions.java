/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Excepciones;

/**
 *
 * @author benjo
 */
public class Exceptions {
    public static class RecursoNoEncontradoException extends Exception {
        public RecursoNoEncontradoException(String msg) { super(msg); }
    }

    public static class UsuarioNoEncontradoException extends Exception {
        public UsuarioNoEncontradoException(String msg) { super(msg); }
    }

    public static class CuposInsuficientesException extends Exception {
        public CuposInsuficientesException(String msg) { super(msg); }
    }
}
