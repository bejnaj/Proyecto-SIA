/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clases;
import java.util.ArrayList;

/**
 *
 * @author benjo
 */
public class Recurso extends Registro {
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
}