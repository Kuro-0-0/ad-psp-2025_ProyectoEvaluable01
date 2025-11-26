package com.salesianostriana.dam.proyecto_evaluable.errors.exceptions.notFound;

public class CategoriaNotFoundException extends EntidadNotFoundException {

    public CategoriaNotFoundException(String message) {
        super(message);
    }

    public CategoriaNotFoundException() {
        super("No se ha encontrado ninguna categoria");
    }

    public CategoriaNotFoundException(Long id) {
        super("No se ha encontrado ninguna categoria con id: " + id);
    }



}
