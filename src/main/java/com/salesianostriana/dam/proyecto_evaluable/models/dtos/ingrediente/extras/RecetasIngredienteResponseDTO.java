package com.salesianostriana.dam.proyecto_evaluable.models.dtos.ingrediente.extras;


import com.salesianostriana.dam.proyecto_evaluable.models.Receta;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecetasIngredienteResponseDTO {

    private Long id;
    private String nombre;

    public static RecetasIngredienteResponseDTO toDTO(Receta receta) {
        return RecetasIngredienteResponseDTO.builder()
                .id(receta.getId())
                .nombre(receta.getNombre())
                .build();
    }

}

