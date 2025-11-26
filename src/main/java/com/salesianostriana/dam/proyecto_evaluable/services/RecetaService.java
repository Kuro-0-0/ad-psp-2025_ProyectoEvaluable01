package com.salesianostriana.dam.proyecto_evaluable.services;

import com.salesianostriana.dam.proyecto_evaluable.models.Categoria;
import com.salesianostriana.dam.proyecto_evaluable.models.Receta;
import com.salesianostriana.dam.proyecto_evaluable.models.dtos.receta.RecetaRequestDTO;
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



}
