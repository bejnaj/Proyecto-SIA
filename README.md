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

Perfecto, ya encontré el `README.md` y los CSV de ejemplo. Te propongo agregar al README una nueva sección con las credenciales de administrador y ejemplos de los CSV. Aquí está el texto que podemos añadir al final del archivo:

---

## Acceso como Administrador

Para acceder a **todas las funcionalidades del sistema**, debes iniciar sesión con las siguientes credenciales:

* **RUT:** `admin`
* **Nombre:** `admin`

Esto te permitirá gestionar estudiantes, recursos y asignaturas sin restricciones.

---

## Ejemplos de CSV incluidos

El proyecto trae consigo archivos CSV de ejemplo para que puedas probar la aplicación.

### 📂 `alumnos.csv`

Ejemplo de estudiantes cargados en el sistema:

| nombre      | rut        | curso |
| ----------- | ---------- | ----- |
| Juan Pérez  | 12345678-9 | 3A    |
| María Gómez | 98765432-1 | 4B    |

### 📂 `recursos.CSV`

Archivo con recursos disponibles (PDFs, enlaces, videos). Puedes editarlo para añadir tus propios materiales.

---

## Ejemplo de uso

1. **Iniciar sesión y abrir el menú principal**

   * **Acción:** Ejecutar la aplicación y autenticarse en la ventana de **Login**.
   * **Resultado esperado:** Acceso al **Menú Principal**.

   ```
   Ventana: Login
   Usuario: admin
   Estado: Sesión iniciada → Menú Principal
   ```

2. **Registrar un nuevo recurso digital**

   * **Acción:** En el menú, ir a **Recursos → Agregar** y completar los datos.
   * **Resultado esperado:** Recurso creado y disponible para asociar.

   ```
   Opción: Recursos → Agregar
   Título: Guía de Física I
   Tipo: PDF
   URL: https://ejemplo.edu/guias/fisica1.pdf
   Descripción: Material de apoyo para la unidad 1
   ```

3. **Asociar el recurso a una asignatura**

   * **Acción:** En **Recursos → Asociar a asignatura**, seleccionar el recurso y la asignatura.
   * **Resultado esperado:** Recurso asociado correctamente a la asignatura.

   ```
   Opción: Recursos → Asociar a asignatura
   Recurso: Guía de Física I
   Asignatura: Física I (1º Medio A)
   Resultado: Asociación realizada correctamente
   ```

4. **Ver la lista de recursos por asignatura**

   * **Acción:** En **Recursos → Listar por asignatura**, elegir la asignatura.
   * **Resultado esperado:** Listado con el recurso agregado.

   ```
   Opción: Recursos → Listar por asignatura
   Asignatura: Física I (1º Medio A)
   Listado:
    - Guía de Física I (PDF) — https://ejemplo.edu/guias/fisica1.pdf
   ```

5. **Cerrar la aplicación (guardado automático)**

   * **Acción:** Salir del sistema desde el menú correspondiente o cerrando la ventana.
   * **Resultado esperado:** Persistencia de los cambios (guardado automático).

   ```
   Cierre de sesión → Guardando datos...
   Estado: Cambios persistidos. Aplicación finalizada.
   ```
