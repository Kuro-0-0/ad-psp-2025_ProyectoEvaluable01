package com.salesianostriana.dam.proyecto_evaluable.models;

import jakarta.persistence.ManyToOne;

public class RecetaIngrediente {

    @ManyToOne
    private Receta receta;
    @ManyToOne
    private Ingrediente ingrediente;
    private Integer cantidad;

}
