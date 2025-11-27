package com.salesianostriana.dam.proyecto_evaluable.models.dtos.receta;

import com.salesianostriana.dam.proyecto_evaluable.models.Receta;
import com.salesianostriana.dam.proyecto_evaluable.models.dtos.recetaIngrediente.RecetaIngredienteResponseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.stream.Collectors;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecetaResponseDTO {

    @Schema(description = "ID de la receta", example = "1")
    private Long id;
    @Schema(description = "Nombre de la receta", example = "Paella Valenciana")
    private String nombre;
    @Schema(description = "Tiempo de preparación en minutos", example = "60")
    private Integer tiempoPreparacionMin;
    @Schema(description = "Dificultad de la receta", example = "MEDIA")
    private String dificultad;
    @Schema(description = "Ingredientes de la receta")
    private Set<RecetaIngredienteResponseDTO> ingredientes;
    @Schema(description = "Categoría de la receta", example = "Postre")
    private String categoria;

    public static RecetaResponseDTO toDTO(Receta r) {

        Set<RecetaIngredienteResponseDTO> ingredientes = r.getIngredientes().stream().map(RecetaIngredienteResponseDTO::toDTO).collect(Collectors.toSet());

        return RecetaResponseDTO.builder()
                .id(r.getId())
                .nombre(r.getNombre())
                .tiempoPreparacionMin(r.getTiempoPreparacionMin())
                .dificultad(r.getDificultad().name())
                .categoria(r.getCategoria().getNombre())
                .ingredientes(ingredientes)
                .build();
    }

}
