# Sistema de Gestión de Recursos Educativos (Proyecto SIA)

## Descripción

Este sistema permite a los usuarios gestionar información académica en un entorno escolar: **estudiantes, docentes, asignaturas y recursos digitales** (PDFs, videos, enlaces). Los usuarios pueden registrar y consultar recursos, asociarlos a asignaturas y navegar mediante una **interfaz gráfica (Swing)**. Esta herramienta está diseñada para mejorar el acceso y la organización del material educativo, asegurando que la comunidad pueda encontrar rápidamente los recursos pertinentes.

## Cómo compilar y ejecutar

Este sistema ha sido desarrollado en **Java (JDK 17+)** con **Maven**, y puede ejecutarse fácilmente desde **NetBeans** o desde la **línea de comandos**. Para comenzar a trabajar con el sistema en tu equipo local, sigue estos pasos:

### Requisitos previos:

- Tener instalado **Java JDK 17+**.  
- Tener instalado **Apache Maven 3.8+**.  
- (Opcional) **NetBeans** u otro IDE Java compatible con Maven.

### Pasos para compilar y ejecutar:

1. **Descarga o clona el proyecto**
   - Opción ZIP: descarga el repositorio como `.zip` desde GitHub y descomprímelo.  
   - Opción Git:
     ```
     git clone https://github.com/bejnaj/Proyecto-SIA.git
     ```
2. **Abre el proyecto en NetBeans o ubícate en la carpeta del módulo**
   - En NetBeans: `Archivo > Abrir carpeta...` y selecciona `Proyecto-SIA/proyectoSIA`.  
   - Por terminal: ve a la carpeta del módulo:
     ```
     cd Proyecto-SIA/proyectoSIA
     ```
3. **Compila el código**
   - En NetBeans: `Run > Clean and Build Project`.  
   - Por terminal:
     ```
     mvn -DskipTests clean compile
     ```
4. **Ejecuta el programa**
   - En NetBeans: `Run` (si se solicita, establece `com.mycompany.proyectosia.ProyectoSIA` como clase principal).  
   - Por terminal:
     ```bash
     mvn -Dexec.mainClass=com.mycompany.proyectosia.ProyectoSIA \
         org.codehaus.mojo:exec-maven-plugin:3.1.0:exec
     ```

## Funcionalidades

### Funcionando correctamente:

- Registrar y consultar **estudiantes, docentes, asignaturas y recursos digitales** mediante la interfaz de usuario.
- **Asociar** recursos digitales a **asignaturas** y **listar/filtrar** recursos por curso o criterio.
- **Persistencia básica**: carga de datos al iniciar y guardado automático al salir (shutdown hook).
- Inicio mediante **UI de Login** y navegación por el menú principal (Swing).

### Problemas conocidos:

- La **exportación/importación** de datos (por ejemplo, a/desde CSV) está en desarrollo.

### A mejorar:

- Optimizar la **interfaz de usuario** (flujo y diseño) y añadir más validaciones.
- Implementar **búsqueda/filtrado avanzado** y **roles/permiso por usuario**.

## Ejemplo de uso

**Paso 1: Iniciar sesión y abrir el menú principal**

Al ejecutar la aplicación se muestra la ventana de **Login**. Tras autenticarse, se accede al menú principal.

Ventana: Login
Usuario: admin
Estado: Sesión iniciada → Menú Principal

**Paso 2: Registrar un nuevo recurso digital**

Desde el menú, el usuario agrega un recurso (ej. un PDF o enlace) y completa los datos.

Opción seleccionada: Recursos → Agregar
Título: Guía de Física I
Tipo: PDF
URL: https://ejemplo.edu/guias/fisica1.pdf
Descripción: Material de apoyo para la unidad 1

**Paso 3: Asociar el recurso a una asignatura**

El usuario vincula el recurso recién creado con la asignatura correspondiente.

Opción seleccionada: Recursos → Asociar a asignatura
Recurso: Guía de Física I
Asignatura: Física I (1º Medio A)
Resultado: Asociación realizada correctamente

**Paso 4: Ver la lista de recursos por asignatura**

Se verifica que el recurso figure en el listado de la asignatura.

Opción seleccionada: Recursos → Listar por asignatura
Asignatura: Física I (1º Medio A)
Listado:

Guía de Física I (PDF) — https://ejemplo.edu/guias/fisica1.pdf

**Paso 5: Cerrar la aplicación (guardado automático)**

Al cerrar la app, el sistema guarda los cambios automáticamente.

Cierre de sesión → Guardando datos...
Estado: Cambios persistidos. Aplicación finalizada.

## Contribuciones

Leandro Bravo:

Bastián Contreras:

Sergio Codoceo:
