package com.salesianostriana.dam.proyecto_evaluable.models;

import com.salesianostriana.dam.proyecto_evaluable.models.extras.Dificultad;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    @Enumerated(EnumType.STRING)
    private Dificultad dificultad;

    @ManyToOne
    private Categoria categoria;

    @OneToMany(mappedBy = "receta", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    @ToString.Exclude
    private Set<RecetaIngrediente> ingredientes = new HashSet<>();


}
