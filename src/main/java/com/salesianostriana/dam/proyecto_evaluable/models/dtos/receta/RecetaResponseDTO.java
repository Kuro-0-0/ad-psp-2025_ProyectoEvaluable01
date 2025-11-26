package com.salesianostriana.dam.proyecto_evaluable.models.dtos.receta;

import com.salesianostriana.dam.proyecto_evaluable.models.Receta;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecetaResponseDTO {

    private Long id;
    private String nombre;
    private Integer tiempoPreparacionMin;
    private String dificultad;
    private String categoria;

    private static RecetaResponseDTO toDTO(Receta r) {
        return RecetaResponseDTO.builder()
                .id(r.getId())
                .nombre(r.getNombre())
                .tiempoPreparacionMin(r.getTiempoPreparacionMin())
                .dificultad(r.getDificultad().name())
                .categoria(r.getCategoria().getNombre())
                .build();
    }

}
