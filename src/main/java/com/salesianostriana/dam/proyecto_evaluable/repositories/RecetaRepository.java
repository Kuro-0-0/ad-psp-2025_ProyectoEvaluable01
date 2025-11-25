package com.salesianostriana.dam.proyecto_evaluable.repositories;

import com.salesianostriana.dam.proyecto_evaluable.models.Ingrediente;
import com.salesianostriana.dam.proyecto_evaluable.models.Receta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RecetaRepository extends JpaRepository<Receta,Long> {

    Optional<Receta> findByNombre();

}
