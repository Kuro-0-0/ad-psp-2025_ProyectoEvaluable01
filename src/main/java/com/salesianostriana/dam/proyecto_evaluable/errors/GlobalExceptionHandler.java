package com.salesianostriana.dam.proyecto_evaluable.errors;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.salesianostriana.dam.proyecto_evaluable.errors.exceptions.IngredienteYaAnadidoException;
import com.salesianostriana.dam.proyecto_evaluable.errors.exceptions.NombreDuplicadoException;
import com.salesianostriana.dam.proyecto_evaluable.errors.exceptions.badArguments.CategoriaBadArgumentsException;
import com.salesianostriana.dam.proyecto_evaluable.errors.exceptions.notFound.EntidadNotFoundException;
import com.salesianostriana.dam.proyecto_evaluable.errors.exceptions.TiempoInvalidoException;
import lombok.Builder;
import org.springframework.beans.factory.parsing.Problem;
import org.springframework.http.*;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.net.URI;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(CategoriaBadArgumentsException.class)
    public ProblemDetail handleCategoriaBadArgumentsException(CategoriaBadArgumentsException ex) {
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());

        pd.setTitle("Los argumentos proporcionados para la categoría no son válidos.");
        pd.setType(URI.create("about:blank"));

        return pd;
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        ProblemDetail result = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "Error de validacion.");

        List<ApiValidationSubError> subErrors = ex.getAllErrors().stream().map(ApiValidationSubError::from).toList();
        result.setProperty("invalid-params", subErrors);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
    }

    @Builder
    record ApiValidationSubError(
            String object,
            String message,
            @JsonInclude(JsonInclude.Include.NON_NULL)
            String field,
            @JsonInclude(JsonInclude.Include.NON_NULL)
            String rejectValue
    ) {
        public ApiValidationSubError(String object, String message) {
            this(object, message, null, null);
        }
        public static ApiValidationSubError from(ObjectError error) {
            ApiValidationSubError result = null;

            if (error instanceof FieldError fieldError) {
                result = ApiValidationSubError.builder()
                        .object(error.getObjectName())
                        .message(error.getDefaultMessage())
                        .field(fieldError.getField())
                        .rejectValue(String.valueOf(fieldError.getRejectedValue()))
                        .build();
            } else {
                result = ApiValidationSubError.builder()
                        .object(error.getObjectName())
                        .message(error.getDefaultMessage())
                        .build();
            }
            return result;
        }
    };

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
