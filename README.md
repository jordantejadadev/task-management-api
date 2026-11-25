# Task Management API

REST API para la gestión de tareas, desarrollada con Java y Spring Boot. Permite crear, consultar, actualizar, cambiar el estado y eliminar tareas, aplicando reglas de negocio y validaciones.

## Tecnologías utilizadas

- Java 17
- Spring Boot
- Spring Data JPA
- PostgreSQL
- Supabase (base de datos en producción)
- Lombok
- Jakarta Validation
- Maven
- Docker
- Render (despliegue)
- Git y GitHub

## Funcionalidades

- Crear tareas con estado inicial `PENDING`.
- Consultar una tarea por su ID.
- Listar todas las tareas.
- Actualizar los datos de una tarea.
- Cambiar el estado de una tarea.
- Eliminar tareas según sus reglas de negocio.
- Validar los datos de entrada.
- Manejar excepciones mediante un controlador global.

## Reglas de negocio

Los estados disponibles son:

- `PENDING`
- `IN_PROGRESS`
- `COMPLETED`

Transiciones permitidas:

`PENDING` → `IN_PROGRESS` → `COMPLETED`

Otras reglas:

- No se puede pasar directamente de `PENDING` a `COMPLETED`.
- Una tarea `COMPLETED` no puede modificarse ni eliminarse.
- Solo se pueden eliminar tareas pendientes o en progreso.
- El título y el responsable son obligatorios.
- La fecha de creación se genera automáticamente.

## Endpoints

| Método | Endpoint | Descripción |
|---|---|---|
| POST | `/api/tasks` | Crear una tarea |
| GET | `/api/tasks` | Listar tareas |
| GET | `/api/tasks/{id}` | Consultar por ID |
| PUT | `/api/tasks/{id}` | Actualizar una tarea |
| PATCH | `/api/tasks/{id}/status?status=IN_PROGRESS` | Cambiar estado |
| DELETE | `/api/tasks/{id}` | Eliminar una tarea |

## Ejemplo de creación

**POST** `/api/tasks`

```json
{
  "title": "Desarrollar nueva funcionalidad",
  "description": "Implementar módulo de gestión",
  "responsible": "Jordan"
}
```

Respuesta esperada: `201 Created`.

## Validaciones

- `title`: obligatorio, máximo 100 caracteres.
- `description`: máximo 500 caracteres.
- `responsible`: obligatorio, máximo 100 caracteres.

## Códigos HTTP

| Código | Descripción |
|---|---|
| 200 | Operación exitosa |
| 201 | Recurso creado |
| 204 | Recurso eliminado |
| 400 | Error de validación o parámetros |
| 404 | Recurso no encontrado |
| 409 | Conflicto con una regla de negocio |

## Configuración local

### Requisitos

- Java 17
- Maven
- PostgreSQL

### Variables de entorno

Configurar las siguientes variables:

```properties
DB_URL=jdbc:postgresql://localhost:5432/task_management_db
DB_USERNAME=postgres
DB_PASSWORD=tu_contraseña
```

### Ejecución

Clonar el repositorio:

```bash
git clone https://github.com/jordantejadadev/task-management-api.git
```

Entrar al proyecto:

```bash
cd task-management-api
```

Ejecutar la aplicación:

```bash
mvn spring-boot:run
```

La API estará disponible en:

`http://localhost:8080`

## Docker

El proyecto utiliza un Dockerfile con construcción multi-stage:

1. Primera etapa: Maven compila y genera el archivo JAR.
2. Segunda etapa: utiliza una imagen JRE para ejecutar la aplicación.

Construir la imagen:

```bash
docker build -t task-management-api .
```

Ejecutar el contenedor:

```bash
docker run -p 8080:8080 \
-e DB_URL="tu_db_url" \
-e DB_USERNAME="tu_usuario" \
-e DB_PASSWORD="tu_contraseña" \
task-management-api
```

## Despliegue

La aplicación está desplegada en Render y utiliza PostgreSQL alojado en Supabase.

La configuración de producción se gestiona mediante variables de entorno, manteniendo las credenciales fuera del código fuente.

## Autor

Jordan Tejada

GitHub: https://github.com/jordantejadadev