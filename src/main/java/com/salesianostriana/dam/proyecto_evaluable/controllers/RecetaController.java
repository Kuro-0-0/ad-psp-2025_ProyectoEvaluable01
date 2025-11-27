package com.salesianostriana.dam.proyecto_evaluable.controllers;

import com.salesianostriana.dam.proyecto_evaluable.models.RecetaIngrediente;
import com.salesianostriana.dam.proyecto_evaluable.models.dtos.receta.RecetaRequestDTO;
import com.salesianostriana.dam.proyecto_evaluable.models.dtos.receta.RecetaResponseDTO;
import com.salesianostriana.dam.proyecto_evaluable.models.dtos.recetaIngrediente.RecetaIngredienteRequestDTO;
import com.salesianostriana.dam.proyecto_evaluable.models.dtos.recetaIngrediente.RecetaIngredienteResponseDTO;
import com.salesianostriana.dam.proyecto_evaluable.services.RecetaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "Receta", description = "Controlador de Recetas")
@RequiredArgsConstructor
@RequestMapping("/api/v1/recetas")
public class RecetaController {

    private final RecetaService service;

    @PostMapping()
    @Operation(summary = "Crear Receta", description = "Crea una nueva receta")
    @ApiResponse(
            responseCode = "400",
            description = "Solicitud inválida al crear la receta",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ProblemDetail.class),
                    examples = {
                            @ExampleObject(
                                    value = """
                                            {
                                              "type": "about:blank",
                                              "title": "Los argumentos proporcionados para la categoría no son válidos.",
                                              "status": 400,
                                              "detail": "Has introducido argumentos inválidos para la categoría: El ID de categoría (1) proporcionado no existe.",
                                              "instance": "/api/v1/recetas"
                                            }
                                            """
                            )
                    }
            )
    )
    @ApiResponse(
            responseCode =  "201",
            description = "Receta creada correctamente",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = RecetaResponseDTO.class),
                    examples = {
                            @ExampleObject(
                                    value = """
                                            {
                                              "id": 1,
                                              "nombre": "Paella",
                                              "dificultad": "MEDIA",
                                              "tiempoPreparacionMin": 60,
                                              "categoriaID": 1
                                            }
                                            """
                            )
                    }
            )
    )
    @ApiResponse(
            responseCode = "409",
            description = "El nombre de la receta ya existe",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ProblemDetail.class),
                    examples = {
                            @ExampleObject(
                                    value = """
                                            {
                                              "type": "about:blank",
                                              "title": "El nombre insertado ya se encuentra en la BBDD.",
                                              "status": 409,
                                              "detail": "Ya existe una entidad de tipo receta con el nombre: Paella",
                                              "instance": "/"
                                            }
                                            """
                            )
                    }
            )
    )
    @ApiResponse(
            responseCode = "400",
            description = "El tiempo de preparación no es válido",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ProblemDetail.class),
                    examples = {
                            @ExampleObject(
                                    value = """
                                            {
                                              "type": "about:blank",
                                              "title": "El tiempo de preparación de una receta no debe ser 0 o menor.",
                                              "status": 400,
                                              "detail": "No se puede crear una receta con un tiempo de preparación menor o igual a 0 minutos.",
                                              "instance": "/api/v1/recetas"
                                            }
                                            """
                            )
                    }
            )
    )
    public ResponseEntity<RecetaResponseDTO> create(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    description = "Datos de la receta a crear",
                    content = @Content(
                            mediaType = "application/JSON",
                            schema = @Schema(implementation = RecetaResponseDTO.class),
                            examples = {
                                    @ExampleObject(
                                            value = """
                                                    {
                                                      "nombre": "Paella",
                                                      "dificultad": "MEDIA",
                                                      "tiempoPreparacionMin": 60,
                                                      "categoriaID": 1
                                                    }
                                                    """
                                    )
                            }
                    )
            )
            @RequestBody RecetaRequestDTO receta
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(RecetaResponseDTO.toDTO(service.create(service.fromDTO(receta))));
    }

    @GetMapping()
    @Operation(summary = "Listar Recetas", description = "Obtiene la lista de todas las recetas")
    @ApiResponse(
            responseCode = "404",
            description = "No se encontraron recetas",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ProblemDetail.class),
                    examples = {
                            @ExampleObject(
                                    value = """
                                            {
                                              "type": "about:blank",
                                              "title": "No se han encontrado recetas en la BBDD.",
                                              "status": 404,
                                              "detail": "Actualmente no hay recetas registradas en la base de datos.",
                                              "instance": "/api/v1/recetas"
                                            }
                                            """
                            )
                    }
            )
    )
    @ApiResponse(
            responseCode = "200",
            description = "Lista de recetas obtenida correctamente",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = RecetaResponseDTO.class),
                    examples = {
                            @ExampleObject(
                                    value = """
                                            [
                                              {
                                                "id": 1,
                                                "nombre": "Paella",
                                                "tiempoPreparacionMin": 60,
                                                "dificultad": "MEDIA",
                                                "ingredientes": [],
                                                "categoria": "Postres"
                                              }
                                            ]
                                            """
                            )
                    }
            )
    )
    public ResponseEntity<List<RecetaResponseDTO>> list() {
        return ResponseEntity.ok().body(service.list().stream().map(RecetaResponseDTO::toDTO).toList());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Detalles de Receta", description = "Obtiene los detalles de una receta por su ID")
    @ApiResponse(
            responseCode = "404",
            description = "Receta no encontrada",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = RecetaResponseDTO.class),
                    examples = {
                            @ExampleObject(
                                    value = """
                                            {
                                              "type": "about:blank",
                                              "title": "La entidad solicitada no se encuentra en la BBDD.",
                                              "status": 404,
                                              "detail": "No se ha encontrado ninguna receta con id: 1",
                                              "instance": "/api/v1/recetas/1"
                                            }
                                            """
                            )
                    }
            )
    )
    @ApiResponse(
            responseCode = "200",
            description = "Detalles de la receta obtenidos correctamente",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = RecetaResponseDTO.class),
                    examples = {
                            @ExampleObject(
                                    value = """
                                            {
                                              "id": 1,
                                              "nombre": "Paella",
                                              "dificultad": "MEDIA",
                                              "tiempoPreparacionMin": 60,
                                              "categoriaID": 1
                                            }
                                            """
                            )
                    }
            )
    )
    public ResponseEntity<RecetaResponseDTO> details(
            @Parameter(required = true, description = "ID de la receta a obtener", example = "1")
            @PathVariable Long id
    ) {
        return ResponseEntity.ok().body(RecetaResponseDTO.toDTO(service.read(id)));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Editar Receta", description = "Edita una receta existente por su ID")
    @ApiResponse(
            responseCode = "400",
            description = "Solicitud inválida al crear la receta",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ProblemDetail.class),
                    examples = {
                            @ExampleObject(
                                    value = """
                                            {
                                              "type": "about:blank",
                                              "title": "Los argumentos proporcionados para la categoría no son válidos.",
                                              "status": 400,
                                              "detail": "Has introducido argumentos inválidos para la categoría: El ID de categoría (1) proporcionado no existe.",
                                              "instance": "/api/v1/recetas"
                                            }
                                            """
                            )
                    }
            )
    )
    @ApiResponse(
            responseCode =  "200",
            description = "Receta actualizada correctamente",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = RecetaResponseDTO.class),
                    examples = {
                            @ExampleObject(
                                    value = """
                                            {
                                              "id": 1,
                                              "nombre": "Paella",
                                              "dificultad": "MEDIA",
                                              "tiempoPreparacionMin": 60,
                                              "categoriaID": 1
                                            }
                                            """
                            )
                    }
            )
    )
    @ApiResponse(
            responseCode = "409",
            description = "El nombre de la receta ya existe",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ProblemDetail.class),
                    examples = {
                            @ExampleObject(
                                    value = """
                                            {
                                              "type": "about:blank",
                                              "title": "El nombre insertado ya se encuentra en la BBDD.",
                                              "status": 409,
                                              "detail": "Ya existe una entidad de tipo receta con el nombre: Paella",
                                              "instance": "/"
                                            }
                                            """
                            )
                    }
            )
    )
    @ApiResponse(
            responseCode = "400",
            description = "El tiempo de preparación no es válido",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ProblemDetail.class),
                    examples = {
                            @ExampleObject(
                                    value = """
                                            {
                                              "type": "about:blank",
                                              "title": "El tiempo de preparación de una receta no debe ser 0 o menor.",
                                              "status": 400,
                                              "detail": "No se puede editar una receta para poner un tiempo de preparación menor o igual a 0 minutos.",
                                              "instance": "/api/v1/recetas"
                                            }
                                            """
                            )
                    }
            )
    )
    public ResponseEntity<RecetaResponseDTO> edit(
            @Parameter(required = true, description = "ID de la receta a editar", example = "1")
            @PathVariable Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    description = "Datos de la receta a editar",
                    content = @Content(
                            mediaType = "application/JSON",
                            schema = @Schema(implementation = RecetaResponseDTO.class),
                            examples = {
                                    @ExampleObject(
                                            value = """
                                                    {
                                                      "nombre": "Paella Valenciana",
                                                      "dificultad": "MEDIA",
                                                      "tiempoPreparacionMin": 70,
                                                      "categoriaID": 1
                                                    }
                                                    """
                                    )
                            }
                    )
            )
            @RequestBody RecetaRequestDTO receta
    ) {
        return ResponseEntity.ok(RecetaResponseDTO.toDTO(service.update(id, service.fromDTO(receta))));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar Receta", description = "Elimina una receta existente por su ID")
    @ApiResponse(
            responseCode = "204",
            description = "Receta eliminada correctamente",
            content = @Content()
    )
    @ApiResponse(
            responseCode = "404",
            description = "Receta no encontrada",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ProblemDetail.class),
                    examples = {
                            @ExampleObject(
                                    value = """
                                            {
                                              "type": "about:blank",
                                              "title": "La entidad solicitada no se encuentra en la BBDD.",
                                              "status": 404,
                                              "detail": "No se ha encontrado ninguna receta con id: 1",
                                              "instance": "/api/v1/recetas/1"
                                            }
                                            """
                            )
                    }
            )
    )
    public ResponseEntity<?> delete(
            @Parameter(required = true, description = "ID de la receta a eliminar", example = "1")
            @PathVariable Long id
    ) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }


    @PostMapping("/{id}/ingredientes")
    @Operation(summary = "Añadir Ingrediente a Receta", description = "Añade un ingrediente a una receta existente por su ID")
    @ApiResponse(
            responseCode = "404",
            description = "Receta o Ingrediente no encontrado",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ProblemDetail.class),
                    examples = {
                            @ExampleObject(
                                    value = """
                                            {
                                              "type": "about:blank",
                                              "title": "La entidad solicitada no se encuentra en la BBDD.",
                                              "status": 404,
                                              "detail": "No se ha encontrado ninguna receta con id: 1",
                                              "instance": "/api/v1/recetas/1/ingredientes"
                                            }
                                            """
                            ),
                            @ExampleObject(
                                    value = """
                                            {
                                              "type": "about:blank",
                                              "title": "La entidad solicitada no se encuentra en la BBDD.",
                                              "status": 404,
                                              "detail": "No se ha encontrado ningún ingrediente con id: 2",
                                              "instance": "/api/v1/recetas/97/ingredientes"
                                            }
                                            """
                            )
                    }

            )
    )
    @ApiResponse(
            responseCode = "409",
            description = "El ingrediente ya ha sido añadido a la receta",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ProblemDetail.class),
                    examples = {
                            @ExampleObject(
                                    value = """
                                            {
                                              "type": "about:blank",
                                              "title": "El ingrediente ya se encuentra en la receta.",
                                              "status": 409,
                                              "detail": "El ingrediente con ID 1 ya ha sido añadido a la receta con ID 1",
                                              "instance": "/api/v1/recetas/1/ingredientes"
                                            }
                                            """
                            )
                    }
            )
    )
    @ApiResponse(
            responseCode = "200",
            description = "Ingrediente añadido correctamente a la receta",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = RecetaIngredienteResponseDTO.class),
                    examples = {
                            @ExampleObject(
                                    value = """
                                            {
                                              "nombreIngrediente": "Tomate",
                                              "cantidad": 200,
                                              "unidad": "gramos"
                                            }
                                            """
                            )
                    }
            )
    )
    public ResponseEntity<RecetaIngredienteResponseDTO> addIngrediente(
            @Parameter(required = true, description = "ID de la receta a la que se le va a añadir el ingrediente", example = "1")
            @PathVariable Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    description = "Datos del ingrediente a añadir",
                    content = @Content(
                            mediaType = "application/JSON",
                            schema = @Schema(implementation = RecetaIngredienteRequestDTO.class),
                            examples = {
                                    @ExampleObject(
                                            value = """
                                                    {
                                                      "ingredienteId": 2,
                                                      "cantidad": 200,
                                                      "unidad": "gramos"
                                                    }
                                                    """
                                    )
                            }
                    )
            )
            @RequestBody RecetaIngredienteRequestDTO recetaIngrediente
    ) {
        return ResponseEntity.ok().body(RecetaIngredienteResponseDTO.toDTO(service.addIngrediente(service.fromDTO(id,recetaIngrediente))));
    }


}
