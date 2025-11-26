package com.salesianostriana.dam.proyecto_evaluable.controllers;

import com.salesianostriana.dam.proyecto_evaluable.models.dtos.categoria.CategoriaRequestDTO;
import com.salesianostriana.dam.proyecto_evaluable.models.dtos.categoria.CategoriaResponseDTO;
import com.salesianostriana.dam.proyecto_evaluable.services.CategoriaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Tag(name = "Categorías", description = "Controlador para gestionar las categorías de las recetas.")
@RequestMapping("/api/v1/categorias")
public class CategoriaController {

    private final CategoriaService service;

    @Operation(
            summary = "Crear una nueva categoría",
            description = "Permite crear una nueva categoría proporcionando los datos necesarios."
    )
    @ApiResponse(
            responseCode = "201",
            description = "Categoría creada correctamente.",
            content = @Content(
                    mediaType = "application/JSON",
                    schema = @Schema(implementation = CategoriaResponseDTO.class),
                    examples = {
                            @ExampleObject(
                                    value = """
                                            {
                                              "id": 1,
                                              "nombre": "Postres",
                                              "descripcion": "Categoría para recetas de postres y dulces."
                                            }
                                            """
                            )
                    }
            )
    )
    @ApiResponse(
            responseCode = "400",
            description = "Solicitud inválida. Los datos proporcionados no son correctos.",
            content = @Content(
                    mediaType = "application/JSON",
                    schema = @Schema(implementation = ProblemDetail.class),
                    examples = {
                            @ExampleObject(
                                    value = """
                                            {
                                              "timestamp": "2025-11-26T16:05:09.575+00:00",
                                              "status": 400,
                                              "error": "Bad Request",
                                              "path": "/api/v1/categorias"
                                            }
                                            """
                            )
                    }
            )
    )
    @ApiResponse(
            responseCode = "409",
            description = "Has introducido un nombre de categoría que ya existe.",
            content = @Content(
                    mediaType = "application/JSON",
                    schema = @Schema(implementation = ProblemDetail.class),
                    examples = {
                            @ExampleObject(
                                    value = """
                                            {
                                              "type": "about:blank",
                                              "title": "El nombre insertado ya se encuentra en la BBDD.",
                                              "status": 409,
                                              "detail": "Ya existe una entidad de tipo categoria con el nombre: Postres",
                                              "instance": "/api/v1/categorias"
                                            }
                                            """
                            )
                    }
            )
    )
    @PostMapping()
    public ResponseEntity<CategoriaResponseDTO> crearCategoria(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    description = "Datos de la nueva categoría a crear.",
                    content = @Content(
                            mediaType = "application/JSON",
                            schema = @Schema(implementation = CategoriaRequestDTO.class),
                            examples = {
                                    @ExampleObject(
                                            value = """
                                                    {
                                                      "nombre": "Postres",
                                                      "descripcion": "Categoría para recetas de postres y dulces."
                                                    }
                                                    """
                                                    )
                                    }
                    )
            )
            @RequestBody CategoriaRequestDTO categoriaDTO
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(categoriaDTO));
    }

    @GetMapping()
    @Operation(summary = "Listar categorías", description = "Obtiene una lista de todas las categorías disponibles.")
    @ApiResponse(
            responseCode = "404",
            description = "No se encontraron categorías.",
            content = @Content(
                    mediaType = "application/JSON",
                    schema = @Schema(implementation = ProblemDetail.class),
                    examples = {
                            @ExampleObject(
                                    value = """
                                            {
                                              "type": "about:blank",
                                              "title": "La entidad solicitada no se encuentra en la BBDD.",
                                              "status": 404,
                                              "detail": "No se ha encontrado ninguna categoria",
                                              "instance": "/api/v1/categorias"
                                            }
                                            """
                            )
                    }
            )
    )
    @ApiResponse(
            responseCode = "200",
            description = "El listado de categorías ha cargado correctamente.",
            content = @Content(
                    mediaType = "application/JSON",
                    schema = @Schema(implementation = CategoriaResponseDTO.class),
                    examples = {
                            @ExampleObject(
                                    value = """
                                            [
                                              {
                                                "id": 1,
                                                "nombre": "Postres",
                                                "descripcion": "Categoría para recetas de postres y dulces."
                                              }
                                            ]
                                            """
                            )
                    }
            )
    )
    public ResponseEntity<List<CategoriaResponseDTO>> listarCategorias() {
        return ResponseEntity.ok(service.list());
    }

    public ResponseEntity<CategoriaResponseDTO> verCategoria() {
        return null;
    }

    public ResponseEntity<CategoriaResponseDTO> editarCategoria(CategoriaRequestDTO categoriaDTO) {
        return null;
    }

    public ResponseEntity<String> eliminarCategoria() {
        return null;
    }






}
