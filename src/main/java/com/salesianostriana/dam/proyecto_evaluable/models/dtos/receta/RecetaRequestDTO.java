package com.salesianostriana.dam.proyecto_evaluable.models.dtos.receta;

import com.salesianostriana.dam.proyecto_evaluable.models.Categoria;
import com.salesianostriana.dam.proyecto_evaluable.models.Receta;
import com.salesianostriana.dam.proyecto_evaluable.models.extras.Dificultad;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecetaRequestDTO {

    @Schema(description = "Nombre de la receta", example = "Paella Valenciana")
    private String nombre;
    @Schema(description = "Tiempo de preparación en minutos", example = "60")
    private Integer tiempoPreparacionMin;
    @Schema(description = "Dificultad de la receta", example = "MEDIA")
    private String dificultad;
    @Schema(description = "ID de la categoría a la que pertenece la receta", example = "1")
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
