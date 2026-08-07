package com.Gym.System.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.*;

@Getter
@Setter
@Table(name = "workout")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class WorkOutEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "work_out_id")
    private Long workOutId;

    @Column(name = "work_out_name", unique = true)
    private String workOutName;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "workout_exercise",
            joinColumns = @JoinColumn(name = "work_out_id"),
            inverseJoinColumns = @JoinColumn(name =  "id_exercise")
    )
    @Builder.Default
    private List<ExerciseEntity> exercises = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "workout_user",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name =  "id_work_out")
    )
    @Builder.Default
    private List<UserEntity> users = new ArrayList<>();
}
