package com.salesianostriana.dam.proyecto_evaluable.models.dtos.ingrediente;

import com.salesianostriana.dam.proyecto_evaluable.models.Ingrediente;
import com.salesianostriana.dam.proyecto_evaluable.models.RecetaIngrediente;
import com.salesianostriana.dam.proyecto_evaluable.models.dtos.ingrediente.extras.RecetasIngredienteResponseDTO;
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
public class IngredienteResponseDTO {

    private Long id;
    private String nombre;
    private Set<RecetasIngredienteResponseDTO> recetas;

    public static IngredienteResponseDTO toDTO(Ingrediente ingrediente) {
        Set<RecetasIngredienteResponseDTO> recetasDTO = ingrediente.getRecetas().stream()
                .map(RecetaIngrediente::getReceta)
                .map(RecetasIngredienteResponseDTO::toDTO)
                .collect(Collectors.toSet());

        return IngredienteResponseDTO.builder()
                .id(ingrediente.getId())
                .nombre(ingrediente.getNombre())
                .recetas(recetasDTO)
                .build();
    }




}
