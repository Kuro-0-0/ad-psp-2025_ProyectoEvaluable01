package com.salesianostriana.dam.proyecto_evaluable.services;

import com.salesianostriana.dam.proyecto_evaluable.errors.exceptions.NombreDuplicadoException;
import com.salesianostriana.dam.proyecto_evaluable.errors.exceptions.TiempoInvalidoException;
import com.salesianostriana.dam.proyecto_evaluable.errors.exceptions.badArguments.CategoriaBadArgumentsException;
import com.salesianostriana.dam.proyecto_evaluable.errors.exceptions.notFound.CategoriaNotFoundException;
import com.salesianostriana.dam.proyecto_evaluable.errors.exceptions.notFound.RecetaNotFoundException;
import com.salesianostriana.dam.proyecto_evaluable.models.Categoria;
import com.salesianostriana.dam.proyecto_evaluable.models.Receta;
import com.salesianostriana.dam.proyecto_evaluable.models.dtos.receta.RecetaRequestDTO;
import com.salesianostriana.dam.proyecto_evaluable.models.dtos.receta.RecetaResponseDTO;
import com.salesianostriana.dam.proyecto_evaluable.models.extras.Dificultad;
import com.salesianostriana.dam.proyecto_evaluable.repositories.RecetaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RecetaService {

    private final RecetaRepository repository;
    private final CategoriaService categoriaService;

    public Receta fromDTO(RecetaRequestDTO recetaDTO) {

        Categoria c = null;
        try {
            c = categoriaService.checkIfExist(recetaDTO.getCategoriaID());
        } catch (CategoriaNotFoundException e) {
            throw new CategoriaBadArgumentsException("El ID de categoría ("+recetaDTO.getCategoriaID()+") proporcionado no existe.");
        }

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

    public List<RecetaResponseDTO> list() {
        List<Receta> recetas = repository.findAll();

        if (recetas.isEmpty()) {
            throw new RecetaNotFoundException();
        }

        return recetas.stream().map(RecetaResponseDTO::toDTO).toList();
    }

    public RecetaResponseDTO read(Long id) {
        return RecetaResponseDTO.toDTO(checkIfExist(id));
    }

    public RecetaResponseDTO update(Long id, RecetaRequestDTO recetaDTO) {
        Receta recetaOriginal = checkIfExist(id);

        if (
                !recetaOriginal.getNombre().equalsIgnoreCase(recetaDTO.getNombre()) &&
                repository.findByNombre(recetaDTO.getNombre()).isPresent()
        ) {
            throw new NombreDuplicadoException("Ya existe una receta con el nombre: " + recetaDTO.getNombre());
        }

        if (recetaDTO.getTiempoPreparacionMin() <= 0) {
            throw new TiempoInvalidoException("No se puede crear una receta con un tiempo de preparación menor o igual a 0 minutos.");
        }

        recetaOriginal = recetaOriginal.modify(fromDTO(recetaDTO));

        return RecetaResponseDTO.toDTO(recetaOriginal);
    }

    public void delete(Long id) {
        Receta recetaOriginal = checkIfExist(id);
        repository.delete(recetaOriginal);
    }

    private Receta checkIfExist(Long id) {
        return repository.findById(id).orElseThrow(() -> new RecetaNotFoundException(id));
    }


}
