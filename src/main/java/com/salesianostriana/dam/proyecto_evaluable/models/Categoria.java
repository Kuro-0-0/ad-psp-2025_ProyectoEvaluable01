package com.salesianostriana.dam.proyecto_evaluable.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor @Builder
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String nombre;
    private String descripcion;

    @OneToMany
    @ToString.Exclude
    @Builder.Default
    private Set<Receta> recetas = new HashSet<>();

    public Categoria modify(Categoria categoria) {
        return Categoria.builder()
                .id(this.getId())
                .nombre(categoria.getNombre())
                .descripcion(categoria.getDescripcion())
                .build();
    }
}
