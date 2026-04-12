# 📝 To-Do List App - Desarrollo Ágil

![Java](https://img.shields.io/badge/Java-17-orange?style=flat-square&logo=java)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.x-green?style=flat-square&logo=spring)
![Docker](https://img.shields.io/badge/Docker-Pro-blue?style=flat-square&logo=docker)

Esta aplicación de gestión de tareas ha sido desarrollada siguiendo una metodología **Agile**, utilizando un ciclo de vida basado en **Scrum** y herramientas de integración continua.

---

## 🚀 Guía de Ejecución Rápida

### Requisitos del Sistema
* **Java 17 SDK** o superior.
* **Maven** para la gestión de dependencias.

### Comandos de Terminal
| Acción | Comando |
| :--- | :--- |
| **Ejecutar App** | `./mvn spring-boot:run` |
| **Generar JAR** | `mvn package` |
| **Limpiar Proyecto** | `mvn clean` |

🌐 **Acceso Local:** [http://localhost:8080/login](http://localhost:8080/login)

---

## ⚙️ Gestión y Despliegue (Práctica 2)

### Enlaces de Control
* 📋 **Tablero Trello (Backlog):** [Trello de Jaume](https://trello.com/invite/b/69c17dd42a091f9f6b5da618/ATTI9af45cad8452f31af5004f2c5bd3213b9EA8A9C4/p2-to-do-list-app)
* 🐳 **Docker Hub (Producción):** [clim17/p2-todolistapp:1.1.0](https://hub.docker.com/repository/docker/clim17/p2-todolistapp/general)
* 💻 **Repositorio GitHub:** [PEGA_AQUÍ_TU_LINK_DE_GITHUB]

---

## 📘 Documentación Técnica (v1.1.0)

### 🏗️ Arquitectura del Sistema
La aplicación se basa en una arquitectura de **tres capas** (Persistencia, Servicio y Controlador):
* **DTOs (`UsuarioData`, `TareaData`):** Se utilizan para transferir datos entre capas sin exponer las entidades JPA directamente. Esto evita errores de carga perezosa (**Lazy**) fuera del contexto transaccional y mejora la seguridad.

### 🛠️ Funcionalidades Implementadas
* **Barra de Menú (Navbar):** Centralizada mediante fragmentos de Thymeleaf. Adapta las opciones dinámicamente: muestra login/registro para invitados y tareas/logout para usuarios autenticados.
* **Listado de Usuarios:** Página en `/registered` que muestra todos los usuarios del sistema (ID y Email).
* **Descripción de Usuario:** Vista protegida en `/registered/{id}` que muestra el perfil completo del usuario seleccionado.

### 🧪 Estrategia de Testing
Se ha seguido un enfoque de **Integración Continua** para asegurar la calidad:
* **Tests de Repositorio:** Comprueban la persistencia en base de datos H2 en memoria.
* **Tests de Servicio:** Validan la lógica de negocio y el control de excepciones personalizadas.
* **Tests Web (`MockMvc`):** Aseguran que los controladores devuelven el HTML correcto y protegen las rutas mediante el manejo de sesiones.

### 💻 Ejemplo de Código Relevante
Este método de la capa de servicio ilustra cómo manejamos la seguridad y la transformación de datos mediante `ModelMapper`:

```java
@Transactional(readOnly = true)
public UsuarioData findById(Long usuarioId) {
    // Recuperamos la entidad desde el repositorio JPA
    Usuario usuario = usuarioRepository.findById(usuarioId).orElse(null);
    
    if (usuario == null) {
        // Lanzamos excepción si el usuario no existe
        throw new UsuarioServiceException("Usuario no encontrado con ID: " + usuarioId);
    }
    
    // Mapeamos a DTO para la capa de presentación
    return modelMapper.map(usuario, UsuarioData.class);
}

```
---

## 🔄 Metodología Ágil Aplicada
El desarrollo de esta aplicación se ha gestionado siguiendo una metodología **Agile** basada en el flujo de trabajo de **Feature Branch**:

* **GitHub Issues & Projects:** Cada funcionalidad técnica y requerimiento de usuario se registró como un *Issue*, permitiendo un seguimiento detallado en el tablero de gestión de GitHub.
* **Flujo de Ramas (Git Flow):** Para cada nueva característica (como la barra de menú o el listado de usuarios), se creó una rama independiente (ej. `feature/user-list`), manteniendo la rama `main` siempre estable y lista para producción.
* **Pull Requests (PR):** La integración del código se realizó mediante PRs, donde se revisaron los cambios antes de fusionarlos. Se utilizaron palabras clave (ej. `Closes #1`) para automatizar el cierre de las tareas vinculadas.
* **Trello Backlog:** Se utilizó un tablero de Trello público para gestionar las *User Stories* desde un punto de vista funcional, facilitando la comunicación sobre el estado de las entregas.

---

## 🐳 Despliegue en Producción (Docker)
La aplicación ha sido contenedorizada para garantizar su portabilidad y facilitar su ejecución en cualquier entorno sin dependencias locales.

**Pasos realizados para el despliegue de la versión 1.1.0:**

1.  **Limpiar y empaquetar:** Generación del binario ejecutable (.jar) omitiendo archivos temporales.
    ```bash
    mvn clean package
    ```
2.  **Construir imagen:** Creación de la imagen Docker utilizando el `Dockerfile` incluido en el proyecto.
    ```bash
    docker build -t clim17/p2-todolistapp:1.1.0 .
    ```
3.  **Publicación:** Subida de la imagen al registro oficial de Docker Hub para su distribución[cite: 2730].
    ```bash
    docker push clim17/p2-todolistapp:1.1.0
    ```

**Comando para ejecución inmediata:**
```bash
docker run --rm -p 8080:8080 clim17/p2-todolistapp:1.1.0


