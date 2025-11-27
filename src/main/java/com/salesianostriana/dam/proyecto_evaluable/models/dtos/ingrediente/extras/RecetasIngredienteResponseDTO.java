package com.salesianostriana.dam.proyecto_evaluable.models.dtos.ingrediente.extras;


import com.salesianostriana.dam.proyecto_evaluable.models.Receta;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecetasIngredienteResponseDTO {

    @Schema(description = "ID de la receta", example = "1")
    private Long id;
    @Schema(description = "Nombre de la receta", example = "Paella Valenciana")
    private String nombre;

    public static RecetasIngredienteResponseDTO toDTO(Receta receta) {
        return RecetasIngredienteResponseDTO.builder()
                .id(receta.getId())
                .nombre(receta.getNombre())
                .build();
    }

}

