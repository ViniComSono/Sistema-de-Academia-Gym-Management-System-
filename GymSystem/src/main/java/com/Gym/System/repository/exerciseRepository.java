package com.Gym.System.repository;

import com.Gym.System.entity.exerciseEntity;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;

public interface exerciseRepository extends JpaRepository<exerciseEntity, Long> {

    exerciseEntity findByExercicioIgnoreCase(String exerciseName);
    List<exerciseEntity> findByGrupoMuscularIgnoreCase(String GrupoMuscular);

}
