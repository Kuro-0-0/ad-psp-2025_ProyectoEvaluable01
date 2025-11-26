package com.salesianostriana.dam.proyecto_evaluable.services;

import com.salesianostriana.dam.proyecto_evaluable.errors.exceptions.EntidadNotFoundException;
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

    private void findByNombre(String nombre) {
        if (repository.findByNombre(nombre).isPresent()) {
            throw new NombreDuplicadoException("categoria", nombre);
        }
    }

    public CategoriaResponseDTO create(CategoriaRequestDTO categoriaDTO) {

        findByNombre(categoriaDTO.getNombre());

        return CategoriaResponseDTO.toDTO(save(categoriaDTO.fromDTO()));
    }

    public List<CategoriaResponseDTO> list() {
        List<Categoria> listadoCategorias = findAll();

        if (listadoCategorias.isEmpty()) {
            throw new EntidadNotFoundException("categorías");
        }

        return listadoCategorias.stream().map(CategoriaResponseDTO::toDTO).toList();
    }

    public CategoriaResponseDTO read(Long id) {
        return CategoriaResponseDTO.toDTO(checkIfExist("categoria",id));
    }

    public CategoriaResponseDTO update(Long id, CategoriaRequestDTO categoriaDTO) {
        Categoria categoriaOriginal = checkIfExist("categoria",id);

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
        Categoria categoriaOriginal = checkIfExist("categoria",id);
        delete(categoriaOriginal);
    }

}

