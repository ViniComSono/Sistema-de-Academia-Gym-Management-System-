package com.Gym.System.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Getter
@Setter
@Table(name = "treinos")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class WorkOutEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "treino_id")
    private Long treinoId;

    @Column(name = "work_out_name")
    private String WorkOutName;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity userId;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "treinos_exercise",
            joinColumns = @JoinColumn(name = "treino_id"),
            inverseJoinColumns = @JoinColumn(name =  "id_exercise")
    )
    @Builder.Default
    private Map<Long ,ExerciseEntity> listaExercicios = new HashMap<>();

}
