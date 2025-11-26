package com.salesianostriana.dam.proyecto_evaluable.services;

import com.salesianostriana.dam.proyecto_evaluable.errors.exceptions.EntidadNotFoundException;
import com.salesianostriana.dam.proyecto_evaluable.errors.exceptions.NombreDuplicadoException;
import com.salesianostriana.dam.proyecto_evaluable.models.Ingrediente;
import com.salesianostriana.dam.proyecto_evaluable.models.dtos.ingrediente.IngredienteRequestDTO;
import com.salesianostriana.dam.proyecto_evaluable.models.dtos.ingrediente.IngredienteResponseDTO;
import com.salesianostriana.dam.proyecto_evaluable.repositories.IngredienteRepository;
import com.salesianostriana.dam.proyecto_evaluable.services.base.BaseServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class IngredienteService extends BaseServiceImpl<Ingrediente, Long, IngredienteRepository> {

    private void findByNombre(String nombre) {
        if (repository.findByNombre(nombre).isPresent()) {
            throw new NombreDuplicadoException("ingrediente",nombre);
        }
    }

    public IngredienteResponseDTO create(IngredienteRequestDTO ingredienteDTO) {
        Ingrediente ingrediente = ingredienteDTO.fromDTO();

        findByNombre(ingredienteDTO.getNombre());

        return IngredienteResponseDTO.toDTO(save(ingrediente));
    }

    public List<IngredienteResponseDTO> list() {
        List<Ingrediente> listadoIngredientes = findAll();

        if (listadoIngredientes.isEmpty()) {
            throw new EntidadNotFoundException("ingrediente");
        }

        return listadoIngredientes.stream().map(IngredienteResponseDTO::toDTO).toList();
    }


}
