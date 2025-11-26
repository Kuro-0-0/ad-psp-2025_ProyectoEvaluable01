package com.salesianostriana.dam.proyecto_evaluable.errors.exceptions.badArguments;

public class CategoriaBadArgumentsException extends RuntimeException {

    public CategoriaBadArgumentsException(String message) {
        super("Has introducido argumentos inválidos para la categoría: " + message);
    }

}

