package com.Gym.System.entity;


import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Table(name = "exercise")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class exerciseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "exercise_id")
    private Long exerciseId;

    @Column(nullable = false, unique = true)
    private String exercicio;
    @Column(nullable = false, name = "grupo_muscular")
    private String grupoMuscular;
}
