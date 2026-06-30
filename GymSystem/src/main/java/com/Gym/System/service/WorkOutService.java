package com.Gym.System.service;

import com.Gym.System.dto.request.*;
import com.Gym.System.dto.response.WorkOutResponseDTO;
import com.Gym.System.entity.ExerciseEntity;
import com.Gym.System.entity.UserEntity;
import com.Gym.System.entity.WorkOutEntity;
import com.Gym.System.exception.BadRequestException;
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

    public List<WorkOutEntity> findAll() throws NotFoundException {
        List<WorkOutEntity> workOuts = workOutRepository.findAll();

        if(workOuts.isEmpty())
            throw new NotFoundException("Don´t have any workOut on the system");
        else
            return workOuts;
    }

    public WorkOutEntity findById(Long id) throws NotFoundException{
        return workOutRepository.findById(id).orElseThrow(() -> new NotFoundException("Not Found this workOut"));
    }

    public Set<WorkOutEntity> findByUserId(Long userId) throws NotFoundException{
        UserEntity user = userService.findById(userId);
        if(user != null){
            return workOutRepository.findByUserList_UserId(userId);
        }else{
            throw new NotFoundException("Not found this user Id");
        }
    }

    public Set<WorkOutResponseDTO> findAllResponse() throws NotFoundException{
        Set<WorkOutEntity> workOut = new HashSet<>(findAll());
        return workOutMapper.workOutResponseSet(workOut);
    }

    public WorkOutResponseDTO findByIdResponse(Long id) throws NotFoundException{
        WorkOutEntity workOut = workOutRepository.findById(id).orElseThrow(() -> new NotFoundException("Not Found this workOut"));
        return workOutMapper.workOutResponse(workOut);
    }

    public Set<WorkOutResponseDTO> findByUserIdResponse(Long userId) throws NotFoundException{
        UserEntity user = userService.findById(userId);
        if(user != null){
            return workOutMapper.workOutResponseSet(findByUserId(userId));
        }else{
            throw new NotFoundException("Not found this user Id");
        }
    }

    public WorkOutResponseDTO createWorkOut(WorkOutRequestDTO workOutRequest) throws NotFoundException, BadRequestException {
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
            throw new BadRequestException("Your exercise list has more than 20 items");
        }
    }

    public WorkOutResponseDTO editAllWorkOut(WorkOutPutRequestDTO workOutRequest) throws NotFoundException{
        WorkOutEntity workOut = findById(workOutRequest.getWorkOutId());
        Set<UserEntity> usersList = new HashSet<>();
        Set<ExerciseEntity> exercisesList = new HashSet<>();

        if(workOut != null){

            for(Long userList : workOutRequest.getUserList()){
                UserEntity users = userService.findById(userList);
                usersList.add(users);
            }

            for(Long exerciseList : workOutRequest.getExerciseList()){
                ExerciseEntity exercise = exerciseService.findByExerciseID(exerciseList);
                exercisesList.add(exercise);
            }

            workOut.setWorkOutName(workOutRequest.getWorkOutName());
            workOut.setUserList(usersList);
            workOut.setExerciseList(exercisesList);

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
                workOut.getExerciseList().add(exerciseService.findByExerciseID(exerciseId));
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
