package com.salesianostriana.dam.proyecto_evaluable.models.dtos.categoria;

import com.salesianostriana.dam.proyecto_evaluable.models.Categoria;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data @AllArgsConstructor @NoArgsConstructor
public class CategoriaResponseDTO {

    private Long id;
    private String nombre;
    private String descripcion;

    private static CategoriaResponseDTO toDTO(Categoria c) {
        return CategoriaResponseDTO.builder()
                .id(c.getId())
                .nombre(c.getNombre())
                .descripcion(c.getDescripcion())
                .build();
    }

    private Categoria fromDTO() {
        return Categoria.builder()
                .id(this.getId())
                .nombre(this.getNombre())
                .descripcion(this.getDescripcion())
                .build();
    }

}
