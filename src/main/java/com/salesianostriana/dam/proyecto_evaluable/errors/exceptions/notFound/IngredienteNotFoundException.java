package com.salesianostriana.dam.proyecto_evaluable.errors.exceptions.notFound;

public class IngredienteNotFoundException extends EntidadNotFoundException {

    public IngredienteNotFoundException(String message) {
        super(message);
    }

    public IngredienteNotFoundException() {
        super("No se ha encontrado ningún ingrediente");
    }

    public IngredienteNotFoundException(Long id) {
        super("No se ha encontrado ningún ingrediente con id: " + id);
    }
}
