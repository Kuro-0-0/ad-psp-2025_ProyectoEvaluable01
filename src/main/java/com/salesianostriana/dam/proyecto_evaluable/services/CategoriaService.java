package com.salesianostriana.dam.proyecto_evaluable.services;

import com.salesianostriana.dam.proyecto_evaluable.errors.exceptions.EntidadNoEncontradaException;
import com.salesianostriana.dam.proyecto_evaluable.errors.exceptions.NombreDuplicadoException;
import com.salesianostriana.dam.proyecto_evaluable.models.Categoria;
import com.salesianostriana.dam.proyecto_evaluable.models.dtos.categoria.CategoriaRequestDTO;
import com.salesianostriana.dam.proyecto_evaluable.models.dtos.categoria.CategoriaResponseDTO;
import com.salesianostriana.dam.proyecto_evaluable.repositories.CategoriaRepository;
import com.salesianostriana.dam.proyecto_evaluable.services.base.BaseServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoriaService extends BaseServiceImpl<Categoria, Long, CategoriaRepository> {

    private final CategoriaRepository repository;

    private Categoria checkIfExist(Long id) {
        return repository.findById(id).orElseThrow(() ->
                new EntidadNoEncontradaException("No se ha encontrado ninguna categoría con la ID: " + id)
        );
    }

    public CategoriaResponseDTO create(CategoriaRequestDTO categoriaDTO) {

        if (repository.findByNombre(categoriaDTO.getNombre()).isPresent()) {
            throw new NombreDuplicadoException("Ya existe una categoría con el nombre: " + categoriaDTO.getNombre());
        }

        return CategoriaResponseDTO.toDTO(save(categoriaDTO.fromDTO()));
    }

    public List<CategoriaResponseDTO> list() {
        List<Categoria> listadoCategorias = findAll();

        if (listadoCategorias.isEmpty()) {
            throw new EntidadNoEncontradaException("No se han encontrado categorías en la BBDD.");
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

    public void delete(Long id) {
        Categoria categoriaOriginal = checkIfExist(id);
        delete(categoriaOriginal);
    }

}

