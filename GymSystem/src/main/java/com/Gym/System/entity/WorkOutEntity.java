package com.Gym.System.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Table(name = "treinos")
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class WorkOutEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "treino_id")
    private Long treinoId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity userId;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "treinos_exercise",
            joinColumns = @JoinColumn(name = "treino_id"),
            inverseJoinColumns = @JoinColumn(name =  "id_exercise")

    )
    private Set<ExerciseEntity> listaExercicios = new HashSet<>();

}
