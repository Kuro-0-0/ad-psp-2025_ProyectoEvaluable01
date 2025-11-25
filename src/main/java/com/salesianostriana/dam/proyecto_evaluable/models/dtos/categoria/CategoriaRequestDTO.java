package com.salesianostriana.dam.proyecto_evaluable.models.dtos.categoria;

import com.salesianostriana.dam.proyecto_evaluable.models.Categoria;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data @AllArgsConstructor @NoArgsConstructor
public class CategoriaRequestDTO {

    private String nombre;
    private String descripcion;

    private static CategoriaRequestDTO toDTO(Categoria c) {
        return CategoriaRequestDTO.builder()
                .nombre(c.getNombre())
                .descripcion(c.getDescripcion())
                .build();
    }

    private Categoria fromDTO() {
        return Categoria.builder()
                .nombre(this.getNombre())
                .descripcion(this.getDescripcion())
                .build();
    }

}
