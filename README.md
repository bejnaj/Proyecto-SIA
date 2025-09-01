# Gestor de Recursos Estudiantiles 

## Descripción 

Nuestro Proyecto SIA es un gestor de recursos estudiantiles para usuarios de un colegio. Los usuarios pueden pedir por medio nuestro gestor un listado del registro de alumnos, profesores, asignaturas y recursos digitales (pdfs, videos, etc). Además de poder agregar cualquier tipo de recurso digital a la base de datos. Está diseñado para ser eficiente y fácil de usar. 

### Datos a utilizar 

- Alumno: nombre, rut, correo, lista de asignaturas.
- Profesor: nombre, rut, correo, lista de asignaturas impartidas.
- Asignatura: nombre, código, profesor responsable, lista de recursos digitales.
- RecursoDigital: título, tipo (video, PDF, enlace), URL o ruta, asignatura asociada.
  
### Principales funcionalidades

- Leer desde la base de datos alumnos, profesores, asignaturas y recursos digitales.
- Relacionar alumnos con asignaturas.
- Relacionar profesores con asignaturas.
- Asociar recursos digitales a cada asignatura (aula virtual).
- Mostrar listados de recursos digitales, según asignatura.
- Agregar reserva de recurso digital.

### Datos iniciales

Para este avance del proyecto se han agregado algunos recursos digitales de prueba en el código, con el fin de verificar la correcta implementación de las colecciones, los métodos y el menú en consola. Más adelante, se implementará la lectura desde archivos CSV, lo que permitirá mostrar y administrar los recursos digitales de manera más eficiente.
