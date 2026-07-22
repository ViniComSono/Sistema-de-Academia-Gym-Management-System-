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
public class ExerciseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "exercise_id")
    private Long exerciseId;

    @Column(nullable = false, unique = true, name = "exercise_name")
    private String exerciseName;

    @Column(nullable = false, name = "muscle_group")
    private String muscleGroup;
}
