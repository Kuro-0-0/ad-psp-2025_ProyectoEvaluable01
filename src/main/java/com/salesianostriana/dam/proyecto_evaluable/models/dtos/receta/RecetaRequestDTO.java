package com.salesianostriana.dam.proyecto_evaluable.models.dtos.receta;

import com.salesianostriana.dam.proyecto_evaluable.models.Categoria;
import com.salesianostriana.dam.proyecto_evaluable.models.Receta;
import com.salesianostriana.dam.proyecto_evaluable.models.extras.Dificultad;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecetaRequestDTO {

    private String nombre;
    private Integer tiempoPreparacionMin;
    private String dificultad;
    private Long categoriaID;

    public static RecetaRequestDTO toDTO(Receta r) {
        return RecetaRequestDTO.builder()
                .nombre(r.getNombre())
                .tiempoPreparacionMin(r.getTiempoPreparacionMin())
                .dificultad(r.getDificultad().name())
                .categoriaID(r.getCategoria().getId())
                .build();
    }

}
