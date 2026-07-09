package com.Gym.System.service;

import com.Gym.System.dto.request.ExerciseRequestDTO;
import com.Gym.System.dto.response.ExerciseResponseDTO;
import com.Gym.System.entity.ExerciseEntity;
import com.Gym.System.exception.BadRequestException;
import com.Gym.System.exception.NotFoundException;
import com.Gym.System.mapper.ExerciseMapper;
import com.Gym.System.repository.ExerciseRepository;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;
import java.util.HashSet;
import java.util.Set;

@Service
@AllArgsConstructor
@Builder
@Getter
@Setter
public class ExerciseService {

    private final ExerciseRepository exerciseRepository;
    private final ExerciseMapper exerciseMapper;

    public Set<ExerciseEntity> findAll() throws NotFoundException {
        Set<ExerciseEntity> list = new HashSet<>(exerciseRepository.findAll());

        if(list.isEmpty())
            throw new NotFoundException("Those aren´t any exercise on the system");
        else
            return list;
    }

    public ExerciseEntity findByExerciseID(Long id) throws NotFoundException{
        return exerciseRepository.findById(id).orElseThrow(() -> new NotFoundException("Exercise not found"));
    }

    public ExerciseEntity findByExerciseNameIgnoreCase(String name) throws NotFoundException{
        ExerciseEntity exercise = exerciseRepository.findByExerciseNameIgnoreCase(name);

       if(exercise != null)
           return exercise;
       else
           throw new NotFoundException("This user don´t exist on the system");
    }

    public Set<ExerciseEntity> findByMuscleGroupIgnoreCase(String muscleGroup) throws NotFoundException{
        Set<ExerciseEntity> exerciseList = new HashSet<>(exerciseRepository.findByMuscleGroupIgnoreCase(muscleGroup));

        if(exerciseList.isEmpty())
            throw new NotFoundException("This user don´t exist on the system");
        else
            return exerciseList;
    }

    public Set<ExerciseResponseDTO> findAllResponse() throws NotFoundException{
        return exerciseMapper.exerciseListResponse(findAll());
    }

    public ExerciseResponseDTO findByExerciseIdResponse(Long id) throws NotFoundException{
        return exerciseMapper.exerciseResponse(findByExerciseID(id));
    }

    public ExerciseResponseDTO findByExerciseNameIgnoreCaseResponse(String name) throws NotFoundException{
        return exerciseMapper.exerciseResponse(findByExerciseNameIgnoreCase(name));
    }

    public Set<ExerciseResponseDTO> findByMuscleGroupIgnoreCaseResponse(String muscleGroup) throws NotFoundException{
        return exerciseMapper.exerciseListResponse(findByMuscleGroupIgnoreCase(muscleGroup));
    }

    public ExerciseResponseDTO createExercicio(ExerciseRequestDTO exerciseRequest) throws BadRequestException {
        ExerciseEntity verification = exerciseRepository.findByExerciseNameIgnoreCase(exerciseRequest.getExerciseName());

        if(verification == null){
            ExerciseEntity exercise = ExerciseEntity.builder()
                    .exerciseName(exerciseRequest.getExerciseName())
                    .muscleGroup(exerciseRequest.getMuscleGroup())
                    .build();
            exerciseRepository.save(exercise);
            return exerciseMapper.exerciseResponse(exercise);
        }else{
            throw new BadRequestException("This Exercise exist on the System");
        }
    }

    public void removeExercise(Long id) throws NotFoundException{
        ExerciseEntity exercise = findByExerciseID(id);
        exerciseRepository.delete(exercise);
    }
}
