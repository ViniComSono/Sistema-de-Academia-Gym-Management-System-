package com.Gym.System.service;

import com.Gym.System.dto.request.WorkOutExerciseRequestDTO;
import com.Gym.System.dto.request.WorkOutNameRequestDTO;
import com.Gym.System.dto.request.WorkOutRequestDTO;
import com.Gym.System.dto.request.WorkOutUsersRequestDTO;
import com.Gym.System.dto.response.WorkOutResponseDTO;
import com.Gym.System.entity.ExerciseEntity;
import com.Gym.System.entity.UserEntity;
import com.Gym.System.entity.WorkOutEntity;
import com.Gym.System.exception.NotFoundException;
import com.Gym.System.mapper.WorkOutMapper;
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
    private final WorkOutMapper workOutMapper;

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
    public WorkOutResponseDTO createWorkOut(WorkOutRequestDTO workOutRequest) throws NotFoundException {
        Set<UserEntity> users = new HashSet<>();
        Set<ExerciseEntity> exercises = new HashSet<>();

        if(workOutRequest.getExercisesId().size() < 20){

            for(Long id : workOutRequest.getUsersId()){
                UserEntity user = userService.findById(id);
                users.add(user);
            }

            for(Long exerciseId : workOutRequest.getExercisesId()){
                ExerciseEntity exercise = exerciseService.findByExerciseID(exerciseId);
                exercises.add(exercise);
            }

            WorkOutEntity workOut = WorkOutEntity.builder()
                    .workOutName(workOutRequest.getWorkOutName())
                    .userList(users)
                    .exerciseList(exercises)
                    .build();

            return workOutMapper.workOutResponse(workOut);
        }else{
            throw new RuntimeException("You put a lot exercises on your list");
        }
    }

    public WorkOutResponseDTO editAllWorkOut(WorkOutEntity workOutRequest) throws NotFoundException{
        WorkOutEntity workOut = findById(workOutRequest.getWorkOutId());

        if(workOut != null){
            workOut.setWorkOutName(workOutRequest.getWorkOutName());
            workOut.setExerciseList(workOutRequest.getExerciseList());
            workOut.setUserList(workOutRequest.getUserList());

            return workOutMapper.workOutResponse(workOut);
        }else{
            throw new NotFoundException("This workOut Id don´t exist");
        }
    }

    public WorkOutResponseDTO editWorkOutName(WorkOutNameRequestDTO workOutRequest) throws NotFoundException{
        WorkOutEntity workOut = findById(workOutRequest.getWorkOutId());

        if(workOut != null){
            workOut.setWorkOutName(workOutRequest.getWorkOutName());

            workOutRepository.save(workOut);
            return workOutMapper.workOutResponse(workOut);
        }else{
            throw new NotFoundException("This workOut Id don´t exist");
        }
    }

    public WorkOutResponseDTO addWorkOutUsers(WorkOutUsersRequestDTO workOutRequest) throws NotFoundException{
        WorkOutEntity workOut = findById(workOutRequest.getWorkOutId());

        for(Long id : workOutRequest.getUsersId()){
            if(!workOut.getUserList().contains(userService.findById(id))) {
                UserEntity user = userService.findById(id);
                workOut.getUserList().add(user);
            }
        }

        workOutRepository.save(workOut);
        return workOutMapper.workOutResponse(workOut);
    }

    public WorkOutResponseDTO removeWorkOutUsers(WorkOutUsersRequestDTO workOutRequest) throws NotFoundException{
        WorkOutEntity workOut = findById(workOutRequest.getWorkOutId());

        for(Long id : workOutRequest.getUsersId()){
            if(workOut.getUserList().contains(userService.findById(id))) {
                workOut.getUserList().remove(userService.findById(id));
            }else{
                throw new NotFoundException("The id " + id + " don´t exist on this work out");
            }
        }

        workOutRepository.save(workOut);
        return workOutMapper.workOutResponse(workOut);
    }

    public WorkOutResponseDTO addWorkOutExercise(WorkOutExerciseRequestDTO workOutRequest) throws NotFoundException{
        WorkOutEntity workOut = findById(workOutRequest.getWorkOutId());

        for(Long exerciseId : workOutRequest.getExerciseList()){
            if(!workOut.getUserList().contains(userService.findById(exerciseId))) {
                ExerciseEntity exercise = exerciseService.findByExerciseID(exerciseId);
                workOut.getExerciseList().add(exercise);
            }
        }

        workOutRepository.save(workOut);
        return workOutMapper.workOutResponse(workOut);
    }

    public WorkOutResponseDTO removeWorkOutExercise(WorkOutExerciseRequestDTO workOutRequest) throws NotFoundException {
        WorkOutEntity workOut = findById(workOutRequest.getWorkOutId());

        for(Long exerciseId : workOutRequest.getExerciseList()){
            if(workOut.getExerciseList().contains(exerciseService.findByExerciseID(exerciseId))) {
                workOut.getExerciseList().remove(exerciseService.findByExerciseID(exerciseId));
            }else{
                throw new NotFoundException("The id " + exerciseId + " don´t exist on this work out");
            }
        }

        workOutRepository.save(workOut);
        return workOutMapper.workOutResponse(workOut);
    }

    public void deleteWorkOut(Long workOutId) throws NotFoundException{
        WorkOutEntity workOut = findById(workOutId);
        workOutRepository.delete(workOut);
    }
}
