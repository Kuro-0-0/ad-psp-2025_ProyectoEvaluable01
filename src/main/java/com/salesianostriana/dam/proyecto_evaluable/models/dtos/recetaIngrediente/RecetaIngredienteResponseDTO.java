package com.salesianostriana.dam.proyecto_evaluable.models.dtos.recetaIngrediente;

import com.salesianostriana.dam.proyecto_evaluable.models.RecetaIngrediente;
import com.salesianostriana.dam.proyecto_evaluable.models.dtos.ingrediente.extras.RecetasIngredienteResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class RecetaIngredienteResponseDTO {

    private String nombreIngrediente;
    private Double cantidad;
    private String unidad;

    public static RecetaIngredienteResponseDTO toDTO(RecetaIngrediente recetaIngrediente){
        return RecetaIngredienteResponseDTO.builder()
                .nombreIngrediente(recetaIngrediente.getIngrediente().getNombre())
                .cantidad(recetaIngrediente.getCantidad())
                .unidad(recetaIngrediente.getUnidad())
                .build();


    }

}
