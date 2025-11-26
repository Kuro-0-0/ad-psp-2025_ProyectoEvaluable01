package com.salesianostriana.dam.proyecto_evaluable.services;

import com.salesianostriana.dam.proyecto_evaluable.errors.exceptions.NombreDuplicadoException;
import com.salesianostriana.dam.proyecto_evaluable.errors.exceptions.TiempoInvalidoException;
import com.salesianostriana.dam.proyecto_evaluable.models.Categoria;
import com.salesianostriana.dam.proyecto_evaluable.models.Receta;
import com.salesianostriana.dam.proyecto_evaluable.models.dtos.receta.RecetaRequestDTO;
import com.salesianostriana.dam.proyecto_evaluable.models.dtos.receta.RecetaResponseDTO;
import com.salesianostriana.dam.proyecto_evaluable.models.extras.Dificultad;
import com.salesianostriana.dam.proyecto_evaluable.repositories.RecetaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RecetaService {

    private final RecetaRepository repository;
    private final CategoriaService categoriaService;

    public Receta fromDTO(RecetaRequestDTO recetaDTO) {

        Categoria c = categoriaService.checkIfExist(recetaDTO.getCategoriaID());

        return Receta.builder()
                .nombre(recetaDTO.getNombre())
                .dificultad(Dificultad.valueOf(recetaDTO.getDificultad()))
                .tiempoPreparacionMin(recetaDTO.getTiempoPreparacionMin())
                .categoria(c)
                .build();
    }

    public RecetaResponseDTO create(RecetaRequestDTO recetaDTO) {
        Receta receta = fromDTO(recetaDTO);

        if (repository.findByNombre(receta.getNombre()).isPresent()) {
            throw new NombreDuplicadoException("receta", recetaDTO.getNombre());
        }

        if (receta.getTiempoPreparacionMin() <= 0) {
            throw new TiempoInvalidoException("No se puede crear una receta con un tiempo de preparación menor o igual a 0 minutos.");
        }

        return RecetaResponseDTO.toDTO(repository.save(receta));
    }



}
