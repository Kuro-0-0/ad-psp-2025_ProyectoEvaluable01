package com.salesianostriana.dam.proyecto_evaluable.repositories;

import com.salesianostriana.dam.proyecto_evaluable.models.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

    Optional<Categoria> findByNombre(String nombre);
}
