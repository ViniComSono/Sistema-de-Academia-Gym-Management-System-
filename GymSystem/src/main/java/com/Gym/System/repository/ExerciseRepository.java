package com.Gym.System.repository;

import com.Gym.System.entity.ExerciseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ExerciseRepository extends JpaRepository<ExerciseEntity, Long> {

    ExerciseEntity findByExerciseNameIgnoreCase(String exerciseName);
    List<ExerciseEntity> findByMuscleGroupIgnoreCase(String GroupMuscular);

}
