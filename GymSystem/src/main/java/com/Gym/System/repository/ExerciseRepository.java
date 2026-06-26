package com.Gym.System.repository;

import com.Gym.System.entity.ExerciseEntity;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;

public interface ExerciseRepository extends JpaRepository<ExerciseEntity, Long> {

    ExerciseEntity findByExerciseNameIgnoreCase(String exerciseName);
    List<ExerciseEntity> findByMuscleGroupIgnoreCase(String GrupoMuscular);

}
