package com.salesianostriana.dam.proyecto_evaluable.models.dtos.categoria;

import com.salesianostriana.dam.proyecto_evaluable.models.Categoria;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data @AllArgsConstructor @NoArgsConstructor
public class CategoriaRequestDTO {

    @Schema(description = "Nombre de la categoría", example = "Postre")
    private String nombre;
    @Schema(description = "Descripción de la categoría", example = "Categoría para postres dulces")
    private String descripcion;

    public static CategoriaRequestDTO toDTO(Categoria c) {
        return CategoriaRequestDTO.builder()
                .nombre(c.getNombre())
                .descripcion(c.getDescripcion())
                .build();
    }

    public Categoria fromDTO() {
        return Categoria.builder()
                .nombre(this.getNombre())
                .descripcion(this.getDescripcion())
                .build();
    }

}
