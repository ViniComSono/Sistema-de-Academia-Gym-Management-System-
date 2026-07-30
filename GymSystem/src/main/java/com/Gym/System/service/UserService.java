package com.Gym.System.service;

import com.Gym.System.dto.request.*;
import com.Gym.System.dto.response.UserResponseDTO;
import com.Gym.System.entity.PhysicalAssessmentEntity;
import com.Gym.System.entity.UserEntity;
import com.Gym.System.entity.WorkOutEntity;
import com.Gym.System.exception.NotFoundException;
import com.Gym.System.mapper.UserMapper;
import com.Gym.System.repository.PhysicalAssessmentRepository;
import com.Gym.System.repository.SubscriptionRepository;
import com.Gym.System.repository.UserRepository;
import com.Gym.System.repository.WorkOutRepository;
import lombok.*;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
@Getter
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PhysicalAssessmentRepository assessmentRepository;
    private final WorkOutRepository workOutRepository;
    private final SubscriptionRepository subscriptionRepository;

    public List<UserResponseDTO> findAllResponse(){
        return userMapper.userResponseSet(userRepository.findAll());
    }

    public UserResponseDTO findByIdResponse(Long id) throws NotFoundException{
        return userMapper.userResponseDTO(userRepository.findById(id).orElseThrow(() -> new NotFoundException("Not found this User")));
    }

    public UserResponseDTO findByUserNameResponse(String name) throws NotFoundException{
        UserEntity user = userRepository.findByName(name);

        if(user != null)
            return userMapper.userResponseDTO(user);
        else
            throw new NotFoundException("Not found this user");
    }

    public List<UserResponseDTO> findByBirthdayResponse(LocalDate birthday) throws NotFoundException{
        List<UserEntity> usersList = userRepository.findByBirthday(birthday);

        if(usersList.isEmpty()){
            throw new NotFoundException("Any user with this birthday data");
        }else{
            return userMapper.userResponseSet(usersList);
        }
    }

    public List<UserResponseDTO> findByBirthdayAfterResponse(UserDateOfBirthRequestDTO userRequest) throws NotFoundException{
        List<UserEntity> users = userRepository.findByBirthdayAfter(userRequest.getDateOfBirth());

        if(users.isEmpty()){
            throw new NotFoundException("Don't exist any user with the birthday after this data");
        }else{
            return userMapper.userResponseSet(users);
        }
    }

    public List<UserResponseDTO> findByBirthdayBeforeResponse(UserDateOfBirthRequestDTO userRequest) throws NotFoundException{
        List<UserEntity> users = userRepository.findByBirthdayBefore(userRequest.getDateOfBirth());

        if(users.isEmpty()){
            throw new NotFoundException("Don't exist any user with the birthday before this data");
        }else{
            return userMapper.userResponseSet(users);
        }
    }

    public List<UserResponseDTO> findByBirthdayBetweenResponse(UserBirthdayBetweenRequestDTO userRequest) throws NotFoundException{
        List<UserEntity> users = userRepository.findByBirthdayBetween(userRequest.getDateOne(), userRequest.getDateTwo());

        if(users.isEmpty()){
            throw new NotFoundException("Don't exist any user with the birthday between those dates");
        }else{
            return userMapper.userResponseSet(users);
        }
    }

    public List<UserResponseDTO> findByBirthdayYearResponse(UserDateOfBirthRequestDTO userRequest) throws NotFoundException{
        LocalDate firstDate = LocalDate.of(userRequest.getDateOfBirth().getYear(), 1, 1);
        LocalDate lastDate = LocalDate.of(userRequest.getDateOfBirth().getYear(), 12, 31);
        List<UserEntity> users = userRepository.findByBirthdayBetween(firstDate, lastDate);

        if(users.isEmpty()){
            throw new NotFoundException("Don't exist any user with the birthday between those dates");
        }else{
            return userMapper.userResponseSet(users);
        }
    }

    public UserResponseDTO createUser(UserRequestDTO userRequest){

        UserEntity newUser = UserEntity.builder()
                .name(userRequest.getName())
                .sexUser(userRequest.getSexUser())
                .dateOfBirth(userRequest.getDateOfBirth())
                .build();

        userRepository.save(newUser);
        return userMapper.userResponseDTO(newUser);
    }

    public UserResponseDTO editAll(UserPutRequestDTO userRequest) throws NotFoundException{
        UserEntity user = userRepository.findById(userRequest.getUserId()).orElseThrow(() -> new NotFoundException("Not found this User"));
        List<WorkOutEntity> workOutList = new ArrayList<>();
        List<PhysicalAssessmentEntity> assessmentList = new ArrayList<>();


        for(Long workOutId : userRequest.getWorkOutIdList()){
            WorkOutEntity workOut = workOutRepository.findById(workOutId).orElseThrow(() -> new NotFoundException("This workout don't exist"));
            workOutList.add(workOut);
        }

        for(Long assessmentId : userRequest.getAssessmentIdList()){
            PhysicalAssessmentEntity assessment = assessmentRepository.findById(assessmentId).orElseThrow(() -> new NotFoundException("This assessment don't exist"));
            assessmentList.add(assessment);
        }

        user.setName(userRequest.getName());
        user.setSexUser(userRequest.getSexUser());
        user.setDateOfBirth(userRequest.getDateOfBirth());
        user.setWorkOutList(workOutList);
        user.setAssessmentList(assessmentList);
        user.setSubscription(subscriptionRepository.findById(userRequest.getSubscriptionId()).orElseThrow(() -> new NotFoundException("This subscription don't exist")));

        userRepository.save(user);return
                userMapper.userResponseDTO(user);
    }

    public UserResponseDTO addWorkOut(UserWorkOutsRequestDTO userRequest) throws NotFoundException{
        UserEntity user = userRepository.findById(userRequest.getUserId()).orElseThrow(() -> new NotFoundException("Not found this User"));

        for(Long workOutId : userRequest.getWorkOutIdList()){
            WorkOutEntity workOut = workOutRepository.findById(workOutId).orElseThrow(() -> new NotFoundException("This workout don't exist"));
            user.getWorkOutList().add(workOut);
        }

        userRepository.save(user);
        return userMapper.userResponseDTO(user);
    }

    public UserResponseDTO removeWorkOut(UserWorkOutsRequestDTO userRequest) throws NotFoundException{
        UserEntity user = userRepository.findById(userRequest.getUserId()).orElseThrow(() -> new NotFoundException("Not found this User"));

        for(Long workOutId : userRequest.getWorkOutIdList()){
            WorkOutEntity workOut = workOutRepository.findById(workOutId).orElseThrow(() -> new NotFoundException("This workout don't exist"));
            user.getWorkOutList().remove(workOut);
        }

        userRepository.save(user);
        return userMapper.userResponseDTO(user);
    }

    public UserResponseDTO editNameUser(UserNameRequestDTO userRequest) throws NotFoundException{
        UserEntity user = userRepository.findById(userRequest.getUserId()).orElseThrow(() -> new NotFoundException("Not found this User"));
        user.setName(userRequest.getName());

        userRepository.save(user);
        return userMapper.userResponseDTO(user);
    }

    public void deleteUser(Long userId) throws NotFoundException{
        UserEntity user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("Not found this User"));

        for(WorkOutEntity workOut : user.getWorkOutList()){
            workOut.getUserList().remove(user);
        }

        userRepository.delete(user);
    }
}
