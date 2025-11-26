package com.salesianostriana.dam.proyecto_evaluable.services;

import com.salesianostriana.dam.proyecto_evaluable.errors.exceptions.NombreDuplicadoException;
import com.salesianostriana.dam.proyecto_evaluable.errors.exceptions.notFound.IngredienteNotFoundException;
import com.salesianostriana.dam.proyecto_evaluable.models.Ingrediente;
import com.salesianostriana.dam.proyecto_evaluable.models.dtos.ingrediente.IngredienteRequestDTO;
import com.salesianostriana.dam.proyecto_evaluable.models.dtos.ingrediente.IngredienteResponseDTO;
import com.salesianostriana.dam.proyecto_evaluable.repositories.IngredienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class IngredienteService {

    private final IngredienteRepository repository;

    public IngredienteResponseDTO create(IngredienteRequestDTO ingredienteDTO) {
        Ingrediente ingrediente = ingredienteDTO.fromDTO();

        if (repository.findByNombre(ingredienteDTO.getNombre()).isPresent()) {
            throw new NombreDuplicadoException("ingrediente",ingredienteDTO.getNombre());
        }

        return IngredienteResponseDTO.toDTO(repository.save(ingrediente));
    }

    public List<IngredienteResponseDTO> list() {
        List<Ingrediente> listadoIngredientes = repository.findAll();

        if (listadoIngredientes.isEmpty()) {
            throw new IngredienteNotFoundException();
        }

        return listadoIngredientes.stream().map(IngredienteResponseDTO::toDTO).toList();
    }


}
