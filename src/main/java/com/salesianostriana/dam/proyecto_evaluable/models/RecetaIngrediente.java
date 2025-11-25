package com.salesianostriana.dam.proyecto_evaluable.models;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RecetaIngrediente {

    @Id
    private Long id;
    private Integer cantidad;
    private String unidad;

    @ManyToOne
    private Receta receta;

    @ManyToOne
    private Ingrediente ingrediente;



}
