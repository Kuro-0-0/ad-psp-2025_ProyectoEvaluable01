package com.salesianostriana.dam.proyecto_evaluable.repositories;

import com.salesianostriana.dam.proyecto_evaluable.models.Receta;
import com.salesianostriana.dam.proyecto_evaluable.models.RecetaIngrediente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecetaIngredienteRepository extends JpaRepository<RecetaIngrediente, Long> {
}
