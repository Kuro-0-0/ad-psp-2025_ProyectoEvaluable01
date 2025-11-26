package com.salesianostriana.dam.proyecto_evaluable.errors.exceptions;

public class EntidadNotFoundException extends RuntimeException {

    public EntidadNotFoundException(String kind) {
        super("No se ha encontrado ninguna entidad de tipo " + kind);
    }

    public EntidadNotFoundException(Long id) {
        super("No se ha encontrado ninguna entidad con id: " + id);
    }

    public EntidadNotFoundException(String kind, Long id) {
        super("No se ha encontrado ninguna entidad de tipo " + kind + " con id: " + id);
    }

}
