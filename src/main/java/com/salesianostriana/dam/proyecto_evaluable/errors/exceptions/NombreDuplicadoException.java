package com.salesianostriana.dam.proyecto_evaluable.errors.exceptions;

public class NombreDuplicadoException extends RuntimeException {
    public NombreDuplicadoException(String message) {
        super(message);
    }

    public NombreDuplicadoException(String kind, String nombre) {
        super("Ya existe una entidad de tipo " + kind + " con el nombre: " + nombre);
    }
}
