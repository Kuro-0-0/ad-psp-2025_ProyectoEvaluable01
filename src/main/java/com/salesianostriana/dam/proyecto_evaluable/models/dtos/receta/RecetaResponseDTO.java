package com.salesianostriana.dam.proyecto_evaluable.models.dtos.receta;

import com.salesianostriana.dam.proyecto_evaluable.models.Receta;
import com.salesianostriana.dam.proyecto_evaluable.models.dtos.recetaIngrediente.RecetaIngredienteResponseDTO;
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

    private Long id;
    private String nombre;
    private Integer tiempoPreparacionMin;
    private String dificultad;
    private Set<RecetaIngredienteResponseDTO> ingredientes;
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
