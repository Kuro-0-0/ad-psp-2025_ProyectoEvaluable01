# Gestión de Recetas — API REST (Tipo B)
Proyecto: Desarrollo de una API REST con Spring Boot  
Asignatura: Acceso a Datos / Programación de Servicios y Procesos — Curso 2025-26

Fecha de entrega estipulada: 2025-11-28 14:30

---

## Resumen
Esta aplicación implementa una API REST para gestionar recetas, categorías e ingredientes. Está diseñada siguiendo las directrices del enunciado de la práctica (Tipo B). Proporciona:

- CRUD completo para Categoría y Receta.
- CRUD básico para Ingrediente (crear y listar).
- Relación Muchos-a-Muchos entre Receta e Ingrediente con una tabla de enlace que almacena cantidad y unidad.
- Validaciones y excepciones mapeadas a ProblemDetail (RFC 7807).
- Documentación generada con OpenAPI 3.0 + Swagger.
- Colección de Postman exportada para probar la API.
- Documentación en Markdown (este README).

---

## Tecnologías
- Java 17 (o superior)
- Spring Boot (versión indicada en pom/gradle)
- Spring Data JPA
- H2 (por defecto para desarrollo) / Posible configuración para PostgreSQL/MySQL
- springdoc-openapi (Swagger UI)
- Maven o Gradle según configuración del proyecto

---

## Modelo de datos

Entidades modeladas:

- Categoria
    - id: Long (PK)
    - nombre: String (único)
    - descripcion: String

- Receta
    - id: Long (PK)
    - nombre: String (único)
    - tiempoPreparacionMin: Integer
    - dificultad: Enum { FACIL, MEDIA, DIFICIL }
    - categoria: Categoria (ManyToOne)

- Ingrediente
    - id: Long (PK)
    - nombre: String (único)

Relación adicional:
- Receta <-> Ingrediente: Muchos-a-Muchos con tabla de enlace que contiene:
    - receta_id
    - ingrediente_id
    - cantidad (Double)
    - unidad (String, ej. "gramos", "ml", "unidades")

Nota: En respuesta a cliente, la Receta incluirá una lista de DTOs que representan esa tabla de enlace (ingrediente + cantidad/unidad).

---

## Requisitos funcionales implementados

1. Categoría
    - GET /api/v1/categorias
    - GET /api/v1/categorias/{id}
    - POST /api/v1/categorias
    - PUT /api/v1/categorias/{id}
    - DELETE /api/v1/categorias/{id}

2. Receta
    - GET /api/v1/recetas
    - GET /api/v1/recetas/{id}  -> incluye lista de ingredientes con cantidad y unidad
    - POST /api/v1/recetas  -> debe incluir `categoriaId` existente
    - PUT /api/v1/recetas/{id}
    - DELETE /api/v1/recetas/{id}

3. Ingrediente
    - GET /api/v1/ingredientes
    - GET /api/v1/ingredientes/{id}
    - POST /api/v1/ingredientes

4. Gestión de ingredientes en recetas (M:M)
    - POST /api/v1/recetas/{recetaId}/ingredientes
        - Añade un ingrediente existente a una receta (tabla de enlace).
        - El cuerpo (DTO) debe incluir: ingredienteId, cantidad, unidad
    - GET /api/v1/recetas/{recetaId} devolverá, entre otros campos, la lista de ingredientes asignados con cantidad y unidad.

---

## DTOs (resumen)

Ejemplos de DTOs usados por la API:

- CreateCategoriaDTO
    - nombre: String
    - descripcion: String

- CategoriaResponseDTO
    - id: Long
    - nombre: String
    - descripcion: String

- RecetaRequestDTO
    - nombre: String
    - tiempoPreparacionMin: Integer
    - dificultad: String (FACIL | MEDIA | DIFICIL)
    - categoriaId: Long

- RecetaResponseDTO
    - id: Long
    - nombre: String
    - tiempoPreparacionMin: Integer
    - dificultad: String
    - categoria: CategoriaResponseDTO
    - ingredientes: List<IngredienteAsignadoDTO>

- IngredienteRequestDTO
    - nombre: String

- IngredienteResponseDTO
    - id: Long
    - nombre: String

- IngredienteRecetaResponseDTO (tabla de enlace)
    - ingredienteId: Long
    - nombre: String
    - cantidad: String (p. ej. "200")
    - unidad: String (p. ej. "gramos")
    - (opcional) nota: String

- IngredienteRecetaRequestDTO (petición POST /recetas/{recetaId}/ingredientes)
    - ingredienteId: Long (obligatorio)
    - cantidad: String (obligatorio)
    - unidad: String (obligatorio)

---

## Validaciones y manejo de errores

Las excepciones se mapean a ProblemDetail (RFC 7807). Excepciones relevantes implementadas:

- EntidadNoEncontradaException -> 404 Not Found  
  Ejemplo ProblemDetail:
  {
  "type": "about:blank",
  "title": "Not Found",
  "status": 404,
  "detail": "Receta con id 10 no encontrada",
  "instance": "/api/v1/recetas/10"
  }

- NombreDuplicadoException -> 409 Conflict
    - Al crear Categoría o Receta con un nombre ya existente.

- IngredienteYaAnadidoException -> 409 Conflict
    - Si se intenta añadir un ingrediente a una receta y ya está asignado.

- TiempoInvalidoException -> 400 Bad Request
    - Si `tiempoPreparacionMin` <= 0 al crear/actualizar receta.

Además: validaciones de campos (not null, tamaño mínimo/máximo) devuelven ProblemDetail con status 400.

---

## Endpoints — ejemplos de uso

1) Crear categoría
   POST /api/v1/categorias
   Body:
   {
   "nombre": "Postres",
   "descripcion": "Recetas dulces"
   }
   Respuesta: 201 Created con CategoriaResponseDTO

2) Crear receta (requiere categoriaId existente)
   POST /api/v1/recetas
   Body:
   {
   "nombre": "Tarta de manzana",
   "tiempoPreparacionMin": 60,
   "dificultad": "MEDIA",
   "categoriaId": 1
   }
   Respuesta: 201 Created con RecetaResponseDTO (ingredientes vacío)

Errores esperados:
- 409 si nombre duplicado
- 400 si tiempoPreparacionMin <= 0
- 404 si categoriaId no existe

3) Crear ingrediente
   POST /api/v1/ingredientes
   Body:
   {
   "nombre": "Manzana"
   }
   Respuesta: 201 Created con IngredienteResponseDTO

4) Añadir ingrediente a receta (tabla de enlace con cantidad/unidad)
   POST /api/v1/recetas/{recetaId}/ingredientes
   Body (AddIngredienteToRecetaDTO):
   {
   "ingredienteId": 3,
   "cantidad": "200",
   "unidad": "gramos"
   }
   Respuestas:
- 201 Created -> asignación creada
- 404 Not Found -> receta o ingrediente no encontrado
- 409 Conflict -> ingrediente ya asignado a la receta

5) Obtener receta con ingredientes
   GET /api/v1/recetas/{recetaId}
   Respuesta: 200 OK con RecetaResponseDTO, ejemplo snippet:
   {
   "id": 5,
   "nombre": "Tarta de manzana",
   "tiempoPreparacionMin": 60,
   "dificultad": "MEDIA",
   "categoria": { "id": 1, "nombre": "Postres", "descripcion": "..." },
   "ingredientes": [
   { "ingredienteId": 3, "nombre": "Manzana", "cantidad": "200", "unidad": "gramos" },
   { "ingredienteId": 7, "nombre": "Azúcar", "cantidad": "100", "unidad": "gramos" }
   ]
   }

---

## Documentación automática (Swagger / OpenAPI)

- OpenAPI JSON: GET /v3/api-docs
- Swagger UI: GET /swagger-ui.html  (o /swagger-ui/index.html dependiendo de la versión de springdoc)

La documentación incluye los modelos DTO, ejemplos y descripción de parámetros. Asegúrate de arrancar la aplicación y acceder a la URL para ver y probar directamente desde la UI.

---

## Colección Postman
En el repositorio se incluye la colección de Postman exportada (`GarciaMariaPabloPostman.json`) con las peticiones para:
- gestión de categorías
- gestión de recetas
- gestión de ingredientes
- asignación de ingredientes a recetas
  Utiliza variables de entorno para `baseUrl` (por ejemplo `http://localhost:9000/api/v1`).

---

## Arrancar la aplicación (desarrollo)

Con Maven:
1. mvn clean package
2. mvn spring-boot:run

Por defecto la aplicación arranca en http://localhost:9000

Configuración H2 (por defecto):
- Consola H2 disponible en /h2-console
- URL JDBC en application.properties o application.yml

---

## Consideraciones de diseño y notas técnicas
- Uso de DTOs para evitar exponer entidades JPA directamente.
- Relaciones ManyToMany modeladas con entidad intermedia (tabla de enlace) para almacenar cantidad y unidad.
- Manejo centralizado de excepciones con controlador de errores que transforma excepciones a ProblemDetail.
- Nombre de categoría/receta/ingrediente definido como único a nivel de base de datos y comprobado en servicio para lanzar NombreDuplicadoException.

---

## Autoría
Autor del repositorio: Kuro-0-0  
