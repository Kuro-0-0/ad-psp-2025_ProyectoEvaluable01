package com.salesianostriana.dam.proyecto_evaluable.services;

import com.salesianostriana.dam.proyecto_evaluable.errors.exceptions.notFound.CategoriaNotFoundException;
import com.salesianostriana.dam.proyecto_evaluable.errors.exceptions.NombreDuplicadoException;
import com.salesianostriana.dam.proyecto_evaluable.models.Categoria;
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

    public Categoria create(Categoria categoria) {

        if (repository.findByNombre(categoria.getNombre()).isPresent()) {
            throw new NombreDuplicadoException("categoria", categoria.getNombre());
        }

        return repository.save(categoria);
    }

    public List<Categoria> list() {
        List<Categoria> listadoCategorias = repository.findAll();

        if (listadoCategorias.isEmpty()) {
            throw new CategoriaNotFoundException();
        }

        return listadoCategorias;
    }

    public Categoria read(Long id) {
        return checkIfExist(id);
    }


    public Categoria update(Long id, Categoria categoria) {
        Categoria categoriaOriginal = checkIfExist(id);

        if (
                !categoriaOriginal.getNombre().equalsIgnoreCase(categoria.getNombre()) &&
                repository.findByNombre(categoria.getNombre()).isPresent()
        ) {
            throw new NombreDuplicadoException("Ya existe una categoría con el nombre: " + categoria.getNombre());
        }

        return repository.save(categoriaOriginal.modify(categoria));

    }

    public void delete(Long id) {
        Categoria categoriaOriginal = checkIfExist(id);
        repository.delete(categoriaOriginal);
    }

}

