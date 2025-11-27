package com.salesianostriana.dam.proyecto_evaluable.controllers;

import com.salesianostriana.dam.proyecto_evaluable.models.dtos.ingrediente.IngredienteRequestDTO;
import com.salesianostriana.dam.proyecto_evaluable.models.dtos.ingrediente.IngredienteResponseDTO;
import com.salesianostriana.dam.proyecto_evaluable.services.IngredienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "Ingrediente", description = "Controlador de Ingredientes")
@RequiredArgsConstructor
@RequestMapping("/api/v1/ingredientes")
public class IngredienteController {

    private final IngredienteService service;

    @PostMapping()
    @Operation(
            summary = "Crear Ingrediente",
            description = "Crea un nuevo ingrediente"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Ingrediente creado correctamente",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = IngredienteResponseDTO.class),
                    examples = {
                            @ExampleObject(
                                    value = """
                                            {
                                              "id": 1,
                                              "nombre": "Tomate",
                                              "recetas": []
                                            }
                                            """
                            )
                    }
            )
    )
    @ApiResponse(
            responseCode = "409",
            description = "El nombre del ingrediente ya existe",
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
                                              "detail": "Ya existe una entidad de tipo ingrediente con el nombre: Tomate",
                                              "instance": "/"
                                            }
                                            """
                            )
                    }
            )
    )
    public ResponseEntity<IngredienteResponseDTO> create(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    description = "DTO con la información del ingrediente a crear",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = IngredienteRequestDTO.class),
                            examples = {
                                    @ExampleObject(
                                            value = """
                                                    {
                                                      "nombre": "Tomate"
                                                    }
                                                    """
                                    )
                            }
                    )
            )
            @RequestBody IngredienteRequestDTO ingrediente
            ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(IngredienteResponseDTO.toDTO(service.create(ingrediente.fromDTO())));
    }

    @GetMapping()
    @Operation(
            summary = "Listar Ingredientes",
            description = "Obtiene el listado de todos los ingredientes"
    )
    @ApiResponse(
            responseCode = "404",
            description = "No se han encontrado ingredientes",
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
                                              "detail": "No se ha encontrado ningún ingrediente",
                                              "instance": "/"
                                            }
                                            """
                            )
                    }
            )
    )
    @ApiResponse(
            responseCode = "200",
            description = "Listado de ingredientes obtenido correctamente",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = IngredienteResponseDTO.class),
                    examples = {
                            @ExampleObject(
                                    value = """
                                            [
                                              {
                                                "id": 1,
                                                "nombre": "Tomate",
                                                "recetas": []
                                              }
                                            ]
                                            """
                            )
                    }
            )
    )
    public ResponseEntity<List<IngredienteResponseDTO>> listar() {
        return ResponseEntity.ok(service.list().stream().map(IngredienteResponseDTO::toDTO).toList());
    }

}
