package com.salesianostriana.dam.proyecto_evaluable.services;

import com.salesianostriana.dam.proyecto_evaluable.errors.exceptions.IngredienteYaAnadidoException;
import com.salesianostriana.dam.proyecto_evaluable.errors.exceptions.NombreDuplicadoException;
import com.salesianostriana.dam.proyecto_evaluable.errors.exceptions.TiempoInvalidoException;
import com.salesianostriana.dam.proyecto_evaluable.errors.exceptions.badArguments.CategoriaBadArgumentsException;
import com.salesianostriana.dam.proyecto_evaluable.errors.exceptions.notFound.CategoriaNotFoundException;
import com.salesianostriana.dam.proyecto_evaluable.errors.exceptions.notFound.RecetaNotFoundException;
import com.salesianostriana.dam.proyecto_evaluable.models.Categoria;
import com.salesianostriana.dam.proyecto_evaluable.models.Ingrediente;
import com.salesianostriana.dam.proyecto_evaluable.models.Receta;
import com.salesianostriana.dam.proyecto_evaluable.models.RecetaIngrediente;
import com.salesianostriana.dam.proyecto_evaluable.models.dtos.receta.RecetaRequestDTO;
import com.salesianostriana.dam.proyecto_evaluable.models.dtos.recetaIngrediente.RecetaIngredienteRequestDTO;
import com.salesianostriana.dam.proyecto_evaluable.models.extras.Dificultad;
import com.salesianostriana.dam.proyecto_evaluable.repositories.RecetaIngredienteRepository;
import com.salesianostriana.dam.proyecto_evaluable.repositories.RecetaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RecetaService {

    private final RecetaRepository repository;
    private final CategoriaService categoriaService;
    private final IngredienteService ingredienteService;
    private final RecetaIngredienteRepository recetaIngredienteRepository;

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

    public RecetaIngrediente fromDTO(Long idReceta, RecetaIngredienteRequestDTO recetaIngredienteDTO) {

        Receta receta = this.checkIfExist(idReceta);
        Ingrediente ingrediente = ingredienteService.checkIfExist(recetaIngredienteDTO.getIngredienteId());

        return RecetaIngrediente.builder()
                .cantidad(recetaIngredienteDTO.getCantidad())
                .unidad(recetaIngredienteDTO.getUnidad())
                .ingrediente(ingrediente)
                .receta(receta)
                .build();
    }

    public Receta create(Receta receta) {

        if (repository.findByNombre(receta.getNombre()).isPresent()) {
            throw new NombreDuplicadoException("receta", receta.getNombre());
        }

        if (receta.getTiempoPreparacionMin() <= 0) {
            throw new TiempoInvalidoException("No se puede crear una receta con un tiempo de preparación menor o igual a 0 minutos.");
        }

        return repository.save(receta);
    }

    public List<Receta> list() {
        List<Receta> recetas = repository.findAll();

        if (recetas.isEmpty()) {
            throw new RecetaNotFoundException();
        }

        return recetas;
    }

    public Receta read(Long id) {
        return checkIfExist(id);
    }

    public Receta update(Long id, Receta receta) {
        Receta recetaOriginal = checkIfExist(id);

        if (
                !recetaOriginal.getNombre().equalsIgnoreCase(receta.getNombre()) &&
                repository.findByNombre(receta.getNombre()).isPresent()
        ) {
            throw new NombreDuplicadoException("Ya existe una receta con el nombre: " + receta.getNombre());
        }

        if (receta.getTiempoPreparacionMin() <= 0) {
            throw new TiempoInvalidoException("No se puede crear una receta con un tiempo de preparación menor o igual a 0 minutos.");
        }

        return repository.save(recetaOriginal.modify(receta));
    }

    public void delete(Long id) {
        Receta recetaOriginal = checkIfExist(id);
        repository.delete(recetaOriginal);
    }

    public RecetaIngrediente addIngrediente(RecetaIngrediente recetaIngrediente) {
            Receta r = recetaIngrediente.getReceta();

            if (r.checkIngrediente(recetaIngrediente.getIngrediente().getId())) {
                throw new IngredienteYaAnadidoException("El ingrediente con ID " + recetaIngrediente.getIngrediente().getId() + " ya ha sido añadido a la receta con ID " + r.getId());
            }

            return anadirIngrediente(recetaIngrediente);


    }

    private RecetaIngrediente anadirIngrediente(RecetaIngrediente recetaIngrediente) {

        recetaIngrediente.getReceta().getIngredientes().add(recetaIngrediente);
        recetaIngrediente.getIngrediente().getRecetas().add(recetaIngrediente);
        recetaIngredienteRepository.save(recetaIngrediente);
        return recetaIngrediente;

    }

    private Receta checkIfExist(Long id) {
        return repository.findById(id).orElseThrow(() -> new RecetaNotFoundException(id));
    }


}
