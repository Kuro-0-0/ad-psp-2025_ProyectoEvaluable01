package com.salesianostriana.dam.proyecto_evaluable.models;

import com.salesianostriana.dam.proyecto_evaluable.models.extras.Dificultad;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Receta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String nombre;
    private Integer tiempoPreparacionMin;
    private Dificultad dificultad;

    @ManyToMany
    private List<Ingrediente> ingredientes;

    @ManyToOne
    private Categoria categoria;


}
