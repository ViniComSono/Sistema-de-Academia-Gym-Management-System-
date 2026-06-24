package com.Gym.System.service;

import com.Gym.System.dto.WorkOutDTO;
import com.Gym.System.dto.WorkOutListDTO;
import com.Gym.System.dto.WorkOutPutDTO;
import com.Gym.System.entity.ExerciseEntity;
import com.Gym.System.entity.UserEntity;
import com.Gym.System.entity.WorkOutEntity;
import com.Gym.System.exception.NotFoundException;
import com.Gym.System.repository.WorkOutRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Getter
@Setter
@AllArgsConstructor
public class WorkOutService {

    private final WorkOutRepository workOutRepository;
    private final ExerciseService exerciseService;
    private final UserService userService;

    public List<WorkOutEntity> findByAll() throws NotFoundException {
        if(workOutRepository.findAll() != null){
            return workOutRepository.findAll();
        }else{
            throw new NotFoundException("There isn´t any exercise on the system");
        }
    }

    public WorkOutEntity findById(Long id) throws NotFoundException{
        return workOutRepository.findById(id).orElseThrow(() -> new NotFoundException("Not Found this workOut"));
    }

    public List<WorkOutEntity> findByUserId(Long userId) throws NotFoundException{
        UserEntity user = userService.findById(userId);
        if(user != null){
            return workOutRepository.findByUserId(user);
        }else{
            throw new NotFoundException("Not found this exercise Id");
        }
    }

    public WorkOutEntity createWorkOut(WorkOutDTO workOutDTO) throws NotFoundException {
        UserEntity user = userService.findById(workOutDTO.getUserId());
        Map<Long, ExerciseEntity> workOut = new HashMap<>();

        if(workOutDTO.getExercisesId().size() <= 20){
            for(int i = 0; i < workOutDTO.getExercisesId().size(); i++){
                ExerciseEntity exercise = exerciseService.findByExerciseID(workOutDTO.getExercisesId().get(i));
                workOut.put(exercise.getExerciseId(), exercise);
            }
            WorkOutEntity newWorkOut = WorkOutEntity.builder()
                    .WorkOutName(workOutDTO.getWorkOutName())
                    .listaExercicios(workOut)
                    .userId(user)
                    .build();
            return workOutRepository.save(newWorkOut);
        }else{
            //criar mais exceptions depois para resolver isso
            throw new RuntimeException("You put a lot exercises on your list");
        }
    }

    public WorkOutEntity ediWorkOut(WorkOutPutDTO workOutPutDTO) throws NotFoundException{
        WorkOutEntity workOut = findById(workOutPutDTO.getWorkOutId());
        UserEntity user = userService.findById(workOutPutDTO.getUserId());

        return workOutRepository.save(workOut = WorkOutEntity.builder()
                .treinoId(workOutPutDTO.getWorkOutId())
                .WorkOutName(workOutPutDTO.getWorkOutName())
                .userId(user)
                .build());
    }

    //adicionar exception que passou de 20 exercicios na lista
    public WorkOutEntity addExerciseOnTheWorkOut(WorkOutListDTO workOutListDTO) throws NotFoundException{
        WorkOutEntity workOut = findById(workOutListDTO.getWorkOutId());

        for (int i = 0; i < workOutListDTO.getExercisesId().size(); i++) {
            ExerciseEntity exercise = exerciseService.findByExerciseID(workOutListDTO.getExercisesId().get(i));
            if (workOutListDTO.getExercisesId().contains(exercise)) {
                throw new NotFoundException("The exercise Id " + workOutListDTO.getExercisesId().get(i) + " ever exist on the list");
            } else {
                workOut.getListaExercicios().put(exercise.getExerciseId(), exercise);
            }
        }

        return workOutRepository.save(workOut);
    }

    public WorkOutEntity removeExerciseOnTheWorkOut(WorkOutListDTO workOutListDTO) throws NotFoundException {
        WorkOutEntity workOut = findById(workOutListDTO.getWorkOutId());

        for (int i = 0; i < workOutListDTO.getExercisesId().size(); i++) {
            ExerciseEntity exercise = exerciseService.findByExerciseID(workOutListDTO.getExercisesId().get(i));
            if (workOutListDTO.getExercisesId().contains(exercise)) {
                workOut.getListaExercicios().remove(workOutListDTO.getExercisesId().get(i));
            } else {
                throw new NotFoundException("Not found the Id " + workOutListDTO.getExercisesId().get(i) + " on the list");
            }
        }

        return workOutRepository.save(workOut);
    }

}
