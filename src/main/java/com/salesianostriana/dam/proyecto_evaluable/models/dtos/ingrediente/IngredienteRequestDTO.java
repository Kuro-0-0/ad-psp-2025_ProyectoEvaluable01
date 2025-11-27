package com.salesianostriana.dam.proyecto_evaluable.models.dtos.ingrediente;


import com.salesianostriana.dam.proyecto_evaluable.models.Ingrediente;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class IngredienteRequestDTO {

    @Schema(description = "Nombre del ingrediente", example = "Azúcar")
    private String nombre;

    public static IngredienteRequestDTO toDTO(String nombre) {
        return IngredienteRequestDTO.builder()
                .nombre(nombre)
                .build();
    }

    public Ingrediente fromDTO() {
        return Ingrediente.builder()
                .nombre(this.getNombre())
                .build();
    }



}
