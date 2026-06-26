package com.Gym.System.service;

import com.Gym.System.dto.request.WorkOutDTO;
import com.Gym.System.dto.request.WorkOutExercisesDTO;
import com.Gym.System.dto.request.WorkOutPutDTO;
import com.Gym.System.entity.ExerciseEntity;
import com.Gym.System.entity.UserEntity;
import com.Gym.System.entity.WorkOutEntity;
import com.Gym.System.exception.NotFoundException;
import com.Gym.System.repository.WorkOutRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Getter
@Setter
@AllArgsConstructor
public class WorkOutService {

    private final WorkOutRepository workOutRepository;
    private final ExerciseService exerciseService;
    private final UserService userService;

    public List<WorkOutEntity> findByAll(){
        return workOutRepository.findAll();
    }

    public WorkOutEntity findById(Long id) throws NotFoundException{
        return workOutRepository.findById(id).orElseThrow(() -> new NotFoundException("Not Found this workOut"));
    }

    public Set<WorkOutEntity> findByUserId(Long userId) throws NotFoundException{
        UserEntity user = userService.findById(userId);
        if(user != null){
            return workOutRepository.findByUserList_UserId(userId);
        }else{
            throw new NotFoundException("Not found this exercise Id");
        }
    }

    //arrumar com nova exception depois
    public WorkOutEntity createWorkOut(WorkOutDTO workOutDTO) throws NotFoundException {
        Set<UserEntity> users = new HashSet<>();
        Set<ExerciseEntity> exercises = new HashSet<>();

        if(workOutDTO.getExercisesId().size() <= 20){

            for(Long id : workOutDTO.getUsersId()){
                UserEntity user = userService.findById(id);
                users.add(user);
            }

            for(Long exerciseId : workOutDTO.getExercisesId()){
                ExerciseEntity exercise = exerciseService.findByExerciseID(exerciseId);
                exercises.add(exercise);
            }

            WorkOutEntity workOut = WorkOutEntity.builder()
                    .workOutName(workOutDTO.getWorkOutName())
                    .userList(users)
                    .exerciseList(exercises)
                    .build();

            return workOutRepository.save(workOut);
        }else{
            throw new RuntimeException("You put a lot exercises on your list");
        }
    }

    public WorkOutEntity editWorkOut(WorkOutPutDTO workOutPutDTO) throws NotFoundException{
        WorkOutEntity workOut = findById(workOutPutDTO.getWorkOutId());

        Set<UserEntity> users = new HashSet<>();

        for(Long id : workOutPutDTO.getUsersId()){
            UserEntity user = userService.findById(id);
            users.add(user);
        }

        workOut.setWorkOutName(workOutPutDTO.getWorkOutName());
        workOut.setUserList(users);
        return workOutRepository.save(workOut);
    }

    public WorkOutEntity editWorkOutName(WorkOutPutDTO workOutPutDTO) throws NotFoundException{
        WorkOutEntity workOut = findById(workOutPutDTO.getWorkOutId());

        workOut.setWorkOutName(workOutPutDTO.getWorkOutName());
        return workOutRepository.save(workOut);
    }

    public WorkOutEntity addWorkOutUsers(WorkOutPutDTO workOutPutDTO) throws NotFoundException{
        WorkOutEntity workOut = findById(workOutPutDTO.getWorkOutId());

        for(Long id : workOutPutDTO.getUsersId()){
            if(!workOut.getUserList().contains(userService.findById(id))) {
                UserEntity user = userService.findById(id);
                workOut.getUserList().add(user);
            }else{
                throw new NotFoundException("The id " + id + " user has been on the list");
            }
        }

        return workOutRepository.save(workOut);
    }

    public WorkOutEntity removeWorkOutUsers(WorkOutPutDTO workOutPutDTO) throws NotFoundException{
        WorkOutEntity workOut = findById(workOutPutDTO.getWorkOutId());

        for(Long id : workOutPutDTO.getUsersId()){
            if(workOut.getUserList().contains(userService.findById(id))) {
                workOut.getUserList().remove(id);
            }else{
                throw new NotFoundException("The id " + id + " don´t exist on the list");
            }
        }

        return workOutRepository.save(workOut);
    }

    public WorkOutEntity addWorkOutExercise(WorkOutExercisesDTO workOutExercisesDTO) throws NotFoundException{
        WorkOutEntity workOut = findById(workOutExercisesDTO.getWorkOutId());

        for(Long id : workOutExercisesDTO.getExercisesId()){
            if(!workOut.getUserList().contains(findById(id))) {
                ExerciseEntity exercise = exerciseService.findByExerciseID(id);
                workOut.getExerciseList().add(exercise);
            }else{
                throw new NotFoundException("The id " + id + " exercise has been on the list");
            }
        }

        return workOutRepository.save(workOut);
    }

    public WorkOutEntity removeWorkOutExercise(WorkOutExercisesDTO workOutExercisesDTO) throws NotFoundException {
        WorkOutEntity workOut = findById(workOutExercisesDTO.getWorkOutId());

        for(Long id : workOutExercisesDTO.getExercisesId()){
            if(workOut.getUserList().contains(findById(id))) {
                workOut.getExerciseList().remove(id);
            }else{
                throw new NotFoundException("The id " + id + " don´t exist on the list");
            }
        }

        return workOutRepository.save(workOut);
    }

    public void deleteWorkOut(Long workOutId) throws NotFoundException{
        WorkOutEntity workOut = findById(workOutId);
        workOutRepository.delete(workOut);
    }
}
