package com.salesianostriana.dam.proyecto_evaluable.repositories;

import com.salesianostriana.dam.proyecto_evaluable.models.Categoria;
import com.salesianostriana.dam.proyecto_evaluable.models.Ingrediente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IngredienteRepository extends JpaRepository<Ingrediente,Long> {

    Optional<Ingrediente> findByNombre();
}
