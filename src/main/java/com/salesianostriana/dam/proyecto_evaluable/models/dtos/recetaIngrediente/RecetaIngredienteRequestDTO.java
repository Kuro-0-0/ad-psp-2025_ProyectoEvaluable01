package com.salesianostriana.dam.proyecto_evaluable.models.dtos.recetaIngrediente;

import com.salesianostriana.dam.proyecto_evaluable.models.dtos.ingrediente.extras.RecetasIngredienteResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor @NoArgsConstructor
@Data
public class RecetaIngredienteRequestDTO {

    private Long ingredienteId;
    private Double cantidad;
    private String unidad;

}
