package com.salesianostriana.dam.proyecto_evaluable.models.dtos.categoria;

import com.salesianostriana.dam.proyecto_evaluable.models.Categoria;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data @AllArgsConstructor @NoArgsConstructor
public class CategoriaResponseDTO {

    @Schema(description = "ID de la categoría", example = "1")
    private Long id;
    @Schema(description = "Nombre de la categoría", example = "Postre")
    private String nombre;
    @Schema(description = "Descripción de la categoría", example = "Categoría para postres dulces")
    private String descripcion;

    public static CategoriaResponseDTO toDTO(Categoria c) {
        return CategoriaResponseDTO.builder()
                .id(c.getId())
                .nombre(c.getNombre())
                .descripcion(c.getDescripcion())
                .build();
    }

}
