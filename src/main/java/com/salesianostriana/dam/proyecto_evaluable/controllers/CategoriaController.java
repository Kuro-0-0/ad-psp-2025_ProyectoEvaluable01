package com.salesianostriana.dam.proyecto_evaluable.controllers;

import com.salesianostriana.dam.proyecto_evaluable.models.dtos.categoria.CategoriaResponseDTO;
import com.salesianostriana.dam.proyecto_evaluable.services.CategoriaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Tag(name = "Categorías", description = "Controlador para gestionar las categorías de las recetas.")
@RequestMapping("/api/v1/categorias")
public class CategoriaController {

    private final CategoriaService service;

    @GetMapping()
    @Operation(summary = "Listar categorías", description = "Obtiene una lista de todas las categorías disponibles.")
    @ApiResponse(
            responseCode = "404",
            description = "No se encontraron categorías.",
            content = @Content()
    )
    public ResponseEntity<List<CategoriaResponseDTO>> listarCategorias() {
        return ResponseEntity.ok(service.list());
    }




}
