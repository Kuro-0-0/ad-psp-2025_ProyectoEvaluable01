package com.salesianostriana.dam.proyecto_evaluable.errors;

import com.salesianostriana.dam.proyecto_evaluable.errors.exceptions.IngredienteYaAnadidoException;
import com.salesianostriana.dam.proyecto_evaluable.errors.exceptions.NombreDuplicadoException;
import com.salesianostriana.dam.proyecto_evaluable.errors.exceptions.notFound.EntidadNotFoundException;
import com.salesianostriana.dam.proyecto_evaluable.errors.exceptions.TiempoInvalidoException;
import com.salesianostriana.dam.proyecto_evaluable.errors.exceptions.notFound.IngredienteNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.net.URI;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IngredienteYaAnadidoException.class)
    public ProblemDetail handleIngredienteYaAnadidoException(IngredienteYaAnadidoException ex) {
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, ex.getMessage());

        pd.setTitle("El ingrediente ya se encuentra en la receta.");
        pd.setType(URI.create("about:blank"));

        return pd;
    }

    @ExceptionHandler(NombreDuplicadoException.class)
    public ProblemDetail handleNombreDuplicadoExceptionException(NombreDuplicadoException ex) {
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, ex.getMessage());

        pd.setTitle("El nombre insertado ya se encuentra en la BBDD.");
        pd.setType(URI.create("about:blank"));

        return pd;
    }

    @ExceptionHandler(EntidadNotFoundException.class)
    public ProblemDetail handleEntidadNoEncontradaExceptionException(EntidadNotFoundException ex) {
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());

        pd.setTitle("La entidad solicitada no se encuentra en la BBDD.");
        pd.setType(URI.create("about:blank"));

        return pd;
    }

    @ExceptionHandler(TiempoInvalidoException.class)
    public ProblemDetail handleTiempoInvalidoExceptionException(TiempoInvalidoException ex) {
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());

        pd.setTitle("El tiempo de creación de una receta no debe ser 0 o menor.");
        pd.setType(URI.create("about:blank"));

        return pd;
    }

}
