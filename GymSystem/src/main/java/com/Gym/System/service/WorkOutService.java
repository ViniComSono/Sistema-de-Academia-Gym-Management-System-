package com.Gym.System.service;

import com.Gym.System.dto.request.*;
import com.Gym.System.dto.response.WorkOutResponseDTO;
import com.Gym.System.entity.ExerciseEntity;
import com.Gym.System.entity.UserEntity;
import com.Gym.System.entity.WorkOutEntity;
import com.Gym.System.exception.BadRequestException;
import com.Gym.System.exception.NotFoundException;
import com.Gym.System.mapper.WorkOutMapper;
import com.Gym.System.repository.ExerciseRepository;
import com.Gym.System.repository.PhysicalAssessmentRepository;
import com.Gym.System.repository.UserRepository;
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
    private final ExerciseRepository exerciseRepository;
    private final UserRepository userRepository;
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
        userRepository.findById(userId).orElseThrow(() -> new NotFoundException("Not found this user Id"));

        return workOutRepository.findByUserList_UserId(userId);
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
        return workOutMapper.workOutResponseSet(findByUserId(userId));
    }

    public WorkOutResponseDTO createWorkOut(WorkOutRequestDTO workOutRequest) throws NotFoundException, BadRequestException {
        Set<UserEntity> users = new HashSet<>();
        Set<ExerciseEntity> exercises = new HashSet<>();

        if(workOutRequest.getExercisesId().size() < 20){

            for(Long id : workOutRequest.getUsersId()){
                UserEntity user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("Not found this user Id"));
                users.add(user);
            }

            for(Long exerciseId : workOutRequest.getExercisesId()){
                ExerciseEntity exercise = exerciseRepository.findById(exerciseId).orElseThrow(() -> new NotFoundException("This exercise Id don´t exist"));
                exercises.add(exercise);
            }

            WorkOutEntity workOut = WorkOutEntity.builder()
                    .workOutName(workOutRequest.getWorkOutName())
                    .userList(users)
                    .exerciseList(exercises)
                    .build();

            workOutRepository.save(workOut);
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
                UserEntity users = userRepository.findById(userList).orElseThrow(() -> new NotFoundException("Not found this user Id"));
                usersList.add(users);
            }

            for(Long exerciseList : workOutRequest.getExerciseList()){
                ExerciseEntity exercise = exerciseRepository.findById(exerciseList).orElseThrow(() -> new NotFoundException("This exercise Id don´t exist"));
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
            UserEntity user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("Not found this user Id"));
            workOut.getUserList().add(user);
        }

        workOutRepository.save(workOut);
        return workOutMapper.workOutResponse(workOut);
    }

    public WorkOutResponseDTO removeWorkOutUsers(WorkOutUsersRequestDTO workOutRequest) throws NotFoundException{
        WorkOutEntity workOut = findById(workOutRequest.getWorkOutId());

        for(Long id : workOutRequest.getUsersId()){
            UserEntity user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("Not found this user Id"));
            if(workOut.getUserList().contains(user)) {
                workOut.getUserList().remove(user);
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
            workOut.getExerciseList().add(exerciseRepository.findById(exerciseId).orElseThrow(() -> new NotFoundException("This exercise Id don´t exist")));
        }

        workOutRepository.save(workOut);
        return workOutMapper.workOutResponse(workOut);
    }

    public WorkOutResponseDTO removeWorkOutExercise(WorkOutExerciseRequestDTO workOutRequest) throws NotFoundException {
        WorkOutEntity workOut = findById(workOutRequest.getWorkOutId());

        for(Long exerciseId : workOutRequest.getExerciseList()){
            ExerciseEntity exercise = exerciseRepository.findById(exerciseId).orElseThrow(() -> new NotFoundException("This exercise Id don´t exist"));
            if(workOut.getExerciseList().contains(exercise)) {
                workOut.getExerciseList().remove(exercise);
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
