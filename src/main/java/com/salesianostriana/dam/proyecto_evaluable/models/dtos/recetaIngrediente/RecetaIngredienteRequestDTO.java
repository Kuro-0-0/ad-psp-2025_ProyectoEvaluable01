package com.salesianostriana.dam.proyecto_evaluable.models.dtos.recetaIngrediente;

import com.salesianostriana.dam.proyecto_evaluable.models.dtos.ingrediente.extras.RecetasIngredienteResponseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor @NoArgsConstructor
@Data
public class RecetaIngredienteRequestDTO {

    @Schema(description = "ID del ingrediente", example = "1")
    private Long ingredienteId;
    @Schema(description = "Cantidad del ingrediente", example = "200")
    private Double cantidad;
    @Schema(description = "Unidad de medida del ingrediente", example = "gramos")
    private String unidad;

}
