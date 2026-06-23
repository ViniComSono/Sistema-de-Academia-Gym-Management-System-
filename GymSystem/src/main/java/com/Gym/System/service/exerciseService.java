package com.Gym.System.service;

import com.Gym.System.dto.exerciseDTO;
import com.Gym.System.entity.exerciseEntity;
import com.Gym.System.repository.exerciseRepository;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Builder
@Getter
@Setter

public class exerciseService {

    private final exerciseRepository exercicioRepository;

    public List<exerciseEntity> findAll() throws RuntimeException{
        try {
            return exercicioRepository.findAll();
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    public exerciseEntity findByExerciseID(Long id) throws RuntimeException{
        return exercicioRepository.findById(id).orElseThrow(() -> new RuntimeException("Exercise not found"));
    }

    public exerciseEntity fingByExerciseNameIgnoreCase(String name){
        try{
            return exercicioRepository.findByExercicioIgnoreCase(name);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    public List<exerciseEntity> findByGrupoMuscularIgnoreCase(String grupoMuscular){
        try{
            return exercicioRepository.findByGrupoMuscularIgnoreCase(grupoMuscular);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    public exerciseEntity cadastrarExercicio(exerciseDTO exerciseDTO){
        exerciseEntity verification = exercicioRepository.findByExercicioIgnoreCase(exerciseDTO.getExercicio());

        if(verification == null){
            exerciseEntity exercise = exerciseEntity.builder()
                    .exercicio(exerciseDTO.getExercicio())
                    .grupoMuscular(exerciseDTO.getGrupoMuscular())
                    .build();
            return exercicioRepository.save(exercise);
        }else{
            throw new RuntimeException("This Exercise exist on the System");
        }
    }
}
