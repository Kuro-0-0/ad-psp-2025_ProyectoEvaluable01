package com.salesianostriana.dam.proyecto_evaluable.models;

import jakarta.persistence.*;
import lombok.*;

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

    @OneToMany(mappedBy = "categoria",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<Receta> recetas;
}
