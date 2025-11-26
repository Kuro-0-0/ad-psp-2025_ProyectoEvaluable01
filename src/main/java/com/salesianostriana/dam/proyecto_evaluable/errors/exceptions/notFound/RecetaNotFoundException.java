package com.salesianostriana.dam.proyecto_evaluable.errors.exceptions.notFound;

public class RecetaNotFoundException extends EntidadNotFoundException {

    public RecetaNotFoundException(String message) {
        super(message);
    }

    public RecetaNotFoundException() {
        super("No se ha encontrado ninguna receta");
    }

    public RecetaNotFoundException(Long id) {
        super("No se ha encontrado ninguna receta con id: " + id);
    }}
