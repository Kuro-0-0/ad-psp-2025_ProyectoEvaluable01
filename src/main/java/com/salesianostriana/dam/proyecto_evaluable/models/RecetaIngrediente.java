package com.salesianostriana.dam.proyecto_evaluable.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RecetaIngrediente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double cantidad;
    private String unidad;

    @ManyToOne
    private Receta receta;

    @ManyToOne
    private Ingrediente ingrediente;



}
