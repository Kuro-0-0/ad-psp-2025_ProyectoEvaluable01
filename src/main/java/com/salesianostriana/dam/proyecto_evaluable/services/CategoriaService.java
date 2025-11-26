package com.salesianostriana.dam.proyecto_evaluable.services;

import com.salesianostriana.dam.proyecto_evaluable.errors.exceptions.notFound.CategoriaNotFoundException;
import com.salesianostriana.dam.proyecto_evaluable.errors.exceptions.NombreDuplicadoException;
import com.salesianostriana.dam.proyecto_evaluable.models.Categoria;
import com.salesianostriana.dam.proyecto_evaluable.models.dtos.categoria.CategoriaRequestDTO;
import com.salesianostriana.dam.proyecto_evaluable.models.dtos.categoria.CategoriaResponseDTO;
import com.salesianostriana.dam.proyecto_evaluable.repositories.CategoriaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoriaService {

    private final CategoriaRepository repository;

    public Categoria checkIfExist(Long id) {
        return repository.findById(id).orElseThrow(() -> new CategoriaNotFoundException(id));
    }

    public CategoriaResponseDTO create(CategoriaRequestDTO categoriaDTO) {

        if (repository.findByNombre(categoriaDTO.getNombre()).isPresent()) {
            throw new NombreDuplicadoException("categoria", categoriaDTO.getNombre());
        }

        return CategoriaResponseDTO.toDTO(repository.save(categoriaDTO.fromDTO()));
    }

    public List<CategoriaResponseDTO> list() {
        List<Categoria> listadoCategorias = repository.findAll();

        if (listadoCategorias.isEmpty()) {
            throw new CategoriaNotFoundException();
        }

        return listadoCategorias.stream().map(CategoriaResponseDTO::toDTO).toList();
    }

    public CategoriaResponseDTO read(Long id) {
        return CategoriaResponseDTO.toDTO(checkIfExist(id));
    }


    public CategoriaResponseDTO update(Long id, CategoriaRequestDTO categoriaDTO) {
        Categoria categoriaOriginal = checkIfExist(id);

        if (
                !categoriaOriginal.getNombre().equalsIgnoreCase(categoriaDTO.getNombre()) &&
                repository.findByNombre(categoriaDTO.getNombre()).isPresent()
        ) {
            throw new NombreDuplicadoException("Ya existe una categoría con el nombre: " + categoriaDTO.getNombre());
        }

        categoriaOriginal = categoriaOriginal.modify(categoriaDTO.fromDTO());

        return CategoriaResponseDTO.toDTO(categoriaOriginal);
    }

    public String delete(Long id) {
        Categoria categoriaOriginal = checkIfExist(id);
        repository.delete(categoriaOriginal);
        return "Categoría con id " + id + " eliminada correctamente.";
    }

}

