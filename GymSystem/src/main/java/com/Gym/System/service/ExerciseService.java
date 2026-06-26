package com.Gym.System.service;

import com.Gym.System.dto.request.ExerciseDTO;
import com.Gym.System.entity.ExerciseEntity;
import com.Gym.System.exception.NotFoundException;
import com.Gym.System.repository.ExerciseRepository;
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

public class ExerciseService {

    private final ExerciseRepository exercicioRepository;

    public List<ExerciseEntity> findAll() throws NotFoundException {
        try {
            return exercicioRepository.findAll();
        } catch (Exception e) {
            throw new NotFoundException("Dont exist any exercise");
        }
    }

    public ExerciseEntity findByExerciseID(Long id) throws NotFoundException{
        return exercicioRepository.findById(id).orElseThrow(() -> new NotFoundException("Exercise not found"));
    }

    public ExerciseEntity findByExerciseNameIgnoreCase(String name) throws NotFoundException{
        try{
            return exercicioRepository.findByExerciseNameIgnoreCase(name);
        } catch (Exception e) {
            throw new NotFoundException("Dont exist this exercise");
        }
    }

    public List<ExerciseEntity> findByGrupoMuscularIgnoreCase(String grupoMuscular) throws NotFoundException{
        try{
            return exercicioRepository.findByMuscleGroupIgnoreCase(grupoMuscular);
        } catch (RuntimeException e) {
            throw new NotFoundException("Dont exist this exercise");
        }
    }

    public ExerciseEntity cadastrarExercicio(ExerciseDTO exerciseDTO){
        ExerciseEntity verification = exercicioRepository.findByExerciseNameIgnoreCase(exerciseDTO.getExerciseName());

        if(verification == null){
            ExerciseEntity exercise = ExerciseEntity.builder()
                    .exerciseName(exerciseDTO.getExerciseName())
                    .muscleGroup(exerciseDTO.getMuscleGroup())
                    .build();
            return exercicioRepository.save(exercise);
        }else{
            throw new RuntimeException("This Exercise exist on the System");
        }
    }

    public void removeExercise(Long id) throws NotFoundException{
        ExerciseEntity exercise = findByExerciseID(id);
        exercicioRepository.delete(exercise);
    }
}
