package com.salesianostriana.dam.proyecto_evaluable.services;

import com.salesianostriana.dam.proyecto_evaluable.errors.exceptions.NombreDuplicadoException;
import com.salesianostriana.dam.proyecto_evaluable.errors.exceptions.notFound.IngredienteNotFoundException;
import com.salesianostriana.dam.proyecto_evaluable.models.Ingrediente;
import com.salesianostriana.dam.proyecto_evaluable.models.RecetaIngrediente;
import com.salesianostriana.dam.proyecto_evaluable.repositories.IngredienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class IngredienteService {

    private final IngredienteRepository repository;

    public Ingrediente create(Ingrediente ingrediente) {
        System.out.println(ingrediente.getNombre());

        if (repository.findByNombre(ingrediente.getNombre()).isPresent()) {
            throw new NombreDuplicadoException("ingrediente",ingrediente.getNombre());
        }

        return repository.save(ingrediente);
    }

    public List<Ingrediente> list() {
        List<Ingrediente> listadoIngredientes = repository.findAll();

        if (listadoIngredientes.isEmpty()) {
            throw new IngredienteNotFoundException();
        }

        return listadoIngredientes;
    }

    public Ingrediente checkIfExist(Long id) {
        return repository.findById(id).orElseThrow(() -> new IngredienteNotFoundException(id));


    }
}
