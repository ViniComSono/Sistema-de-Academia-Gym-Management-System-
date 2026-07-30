package com.Gym.System.service;

import com.Gym.System.dto.request.*;
import com.Gym.System.dto.response.UserResponseDTO;
import com.Gym.System.entity.PhysicalAssessmentEntity;
import com.Gym.System.entity.UserEntity;
import com.Gym.System.entity.WorkOutEntity;
import com.Gym.System.exception.NotFoundException;
import com.Gym.System.mapper.UserMapper;
import com.Gym.System.repository.PhysicalAssessmentRepository;
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

    public List<UserEntity> findAll() throws NotFoundException{
         List<UserEntity> users = userRepository.findAll();

         if(users.isEmpty())
             throw new NotFoundException("Don´t exist users on the system");
         else
             return users;
    }

    public UserEntity findByUserName(String name) throws NotFoundException{
        UserEntity user = userRepository.findByName(name);

        if(user != null)
            return user;
        else
            throw new NotFoundException("Not found this user");
    }

    public UserEntity findById(Long id) throws NotFoundException{
        return userRepository.findById(id).orElseThrow(() -> new NotFoundException("Not found this User"));
    }

    public List<UserEntity> findByBirthday(LocalDate birthday) throws NotFoundException{
        List<UserEntity> usersList = userRepository.findByBirthday(birthday);

        if(usersList.isEmpty()){
            throw new NotFoundException("Any user with this birthday data");
        }else{
            return usersList;
        }
    }

    public List<UserEntity> findByBirthdayAfter(UserDateOfBirthRequestDTO userRequest) throws NotFoundException{
        List<UserEntity> users = userRepository.findByBirthdayAfter(userRequest.getDateOfBirth());

        if(users.isEmpty()){
            throw new NotFoundException("Don't exist any user with the birthday after this data");
        }else{
            return users;
        }
    }

    public List<UserEntity> findByBirthdayBefore(UserDateOfBirthRequestDTO userRequest) throws NotFoundException{
        List<UserEntity> users = userRepository.findByBirthdayBefore(userRequest.getDateOfBirth());

        if(users.isEmpty()){
            throw new NotFoundException("Don't exist any user with the birthday before this data");
        }else{
            return users;
        }
    }

    public List<UserEntity> findByBirthdayBetween(UserBirthdayBetweenRequestDTO userRequest) throws NotFoundException{
        List<UserEntity> users = userRepository.findByBirthdayBetween(userRequest.getDateOne(), userRequest.getDateTwo());

        if(users.isEmpty()){
            throw new NotFoundException("Don't exist any user with the birthday between those dates");
        }else{
            return users;
        }
    }

    public List<UserEntity> findyByBirthdayYear(UserDateOfBirthRequestDTO userRequest) throws NotFoundException{
        LocalDate firstDate = LocalDate.of(userRequest.getDateOfBirth().getYear(), 1, 1);
        LocalDate lastDate = LocalDate.of(userRequest.getDateOfBirth().getYear(), 12, 31);
        List<UserEntity> users = userRepository.findByBirthdayBetween(firstDate, lastDate);

        if(users.isEmpty()){
            throw new NotFoundException("Don't exist any user with the birthday between those dates");
        }else{
            return users;
        }
    }

    public List<UserResponseDTO> findAllResponse() throws NotFoundException{
        List<UserEntity> users = new ArrayList<>(findAll());
        return userMapper.userResponseSet(users);
    }

    public UserResponseDTO findByIdResponse(Long id) throws NotFoundException{
        return userMapper.userResponseDTO(findById(id));
    }

    public UserResponseDTO findByUserNameResponse(String name) throws NotFoundException{
        return userMapper.userResponseDTO(findByUserName(name));
    }

    public List<UserResponseDTO> findByBirthdayResponse(LocalDate birthday) throws NotFoundException{
        List<UserEntity> users = new ArrayList<>(findByBirthday(birthday));

        return userMapper.userResponseSet(users);
    }

    public List<UserResponseDTO> findByBirthdayAfterResponse(UserDateOfBirthRequestDTO userRequest) throws NotFoundException{
        List<UserEntity> users = new ArrayList<>(findByBirthdayAfter(userRequest));

        return userMapper.userResponseSet(users);
    }

    public List<UserResponseDTO> findByBirthdayBeforeResponse(UserDateOfBirthRequestDTO userRequest) throws NotFoundException{
        List<UserEntity> users = new ArrayList<>(findByBirthdayBefore(userRequest));

        return userMapper.userResponseSet(users);
    }

    public List<UserResponseDTO> findByBirthdayBetweenResponse(UserBirthdayBetweenRequestDTO userRequest) throws NotFoundException{
        List<UserEntity> users = new ArrayList<>(findByBirthdayBetween(userRequest));

        return userMapper.userResponseSet(users);
    }

    public List<UserResponseDTO> findByBirthdayYearResponse(UserDateOfBirthRequestDTO userRequest) throws NotFoundException{
        List<UserEntity> users = new ArrayList<>(findyByBirthdayYear(userRequest));

        return userMapper.userResponseSet(users);
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
        UserEntity user = findById(userRequest.getUserId());
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

        userRepository.save(user);return
                userMapper.userResponseDTO(user);
    }

    public UserResponseDTO addWorkOut(UserWorkOutsRequestDTO userRequest) throws NotFoundException{
        UserEntity user = findById(userRequest.getUserId());

        for(Long workOutId : userRequest.getWorkOutIdList()){
            WorkOutEntity workOut = workOutRepository.findById(workOutId).orElseThrow(() -> new NotFoundException("This workout don't exist"));
            user.getWorkOutList().add(workOut);
        }

        userRepository.save(user);
        return userMapper.userResponseDTO(user);
    }

    public UserResponseDTO removeWorkOut(UserWorkOutsRequestDTO userRequest) throws NotFoundException{
        UserEntity user = findById(userRequest.getUserId());

        for(Long workOutId : userRequest.getWorkOutIdList()){
            WorkOutEntity workOut = workOutRepository.findById(workOutId).orElseThrow(() -> new NotFoundException("This workout don't exist"));
            user.getWorkOutList().remove(workOut);
        }

        userRepository.save(user);
        return userMapper.userResponseDTO(user);
    }

    public UserResponseDTO editNameUser(UserNameRequestDTO userRequest) throws NotFoundException{
        UserEntity user = findById(userRequest.getUserId());
        user.setName(userRequest.getName());

        userRepository.save(user);
        return userMapper.userResponseDTO(user);
    }

    public void deleteUser(Long userId) throws NotFoundException{
        UserEntity user = findById(userId);

        for(WorkOutEntity workOut : user.getWorkOutList()){
            workOut.getUserList().remove(user);
        }

        userRepository.delete(user);
    }
}
