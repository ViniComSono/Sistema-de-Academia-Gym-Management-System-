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
import java.time.Month;
import java.time.Year;
import java.time.YearMonth;
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

    public List<UserEntity> findAll(){
        return userRepository.findAll();
    }

    public List<UserResponseDTO> findAllResponse(){
        return userMapper.userResponseSet(findAll());
    }

    public UserEntity findByUserId(Long userId) throws NotFoundException{
        return userRepository.findById(userId).orElseThrow(() -> new NotFoundException("this " + userId + "user id don't exist"));
    }

    public UserResponseDTO findByUserIdResponse(Long userId) throws NotFoundException{
        return userMapper.userResponseDTO(findByUserId(userId));
    }

    public UserEntity findByUserName(String userName) throws NotFoundException{
        UserEntity user = userRepository.findByName(userName);

        if(user == null)
            throw new NotFoundException("this '" + userName + "' user name don't exist");
        else
            return user;
    }

    public UserResponseDTO findByUserNameResponse(String userName) throws NotFoundException{
        return userMapper.userResponseDTO(findByUserName(userName));
    }

    public List<UserEntity> findByBirthday(UserBirthdayRequestDTO userBirthdayRequest) throws NotFoundException{
        List<UserEntity> userList = userRepository.findByBirthday(userBirthdayRequest.getDateOfBirth());

        if(userList.isEmpty())
            throw new NotFoundException("don´t exist any user with this birthday '" + userBirthdayRequest.getDateOfBirth() + "'");
        else
            return userList;
    }

    public List<UserResponseDTO> findByBirthdayResponse(UserBirthdayRequestDTO userBirthdayRequest) throws NotFoundException{
        return userMapper.userResponseSet(findByBirthday(userBirthdayRequest));
    }

    public List<UserEntity> findByBirthDayAfter(UserBirthdayRequestDTO userBirthdayRequest) throws NotFoundException{
        List<UserEntity> userList = userRepository.findByBirthdayAfter(userBirthdayRequest.getDateOfBirth());

        if(userList.isEmpty())
            throw new NotFoundException("don´t exist any user with this birthday after of this '" + userBirthdayRequest.getDateOfBirth() + "'");
        else
            return userList;
    }

    public List<UserResponseDTO> findByBirthdayAfterResponse(UserBirthdayRequestDTO userBirthdayRequest) throws NotFoundException{
        return userMapper.userResponseSet(findByBirthDayAfter(userBirthdayRequest));
    }

    public List<UserEntity> findByBirthdayBefore(UserBirthdayRequestDTO userBirthdayRequest) throws NotFoundException{
        List<UserEntity> userList = userRepository.findByBirthdayBefore(userBirthdayRequest.getDateOfBirth());

        if(userList.isEmpty()){
            throw new NotFoundException("don't exist any user with this birthday before of this '" + userBirthdayRequest.getDateOfBirth() + "'");
        }else{
            return userList;
        }
    }

    public List<UserResponseDTO> findByBirthdayBeforeResponse(UserBirthdayRequestDTO userBirthdayRequest) throws NotFoundException{
        return userMapper.userResponseSet(findByBirthdayBefore(userBirthdayRequest));
    }

    public List<UserEntity> findByBirthdayBetween(UserBirthdayBetweenRequestDTO userBirthdayRequest) throws NotFoundException{
        List<UserEntity> userList = userRepository.findByBirthdayBetween(userBirthdayRequest.getDateOne(), userBirthdayRequest.getDateTwo());

        if(userList.isEmpty()){
            throw new NotFoundException("don't exist any user with the birthday between those dates");
        }else{
            return userList;
        }
    }

    public List<UserResponseDTO> findByBirthdayBetweenResponse(UserBirthdayBetweenRequestDTO userBirthdayRequest) throws NotFoundException{
        return userMapper.userResponseSet(findByBirthdayBetween(userBirthdayRequest));
    }

    public List<UserEntity> findByBirthdayYear(Year year) throws NotFoundException{
        LocalDate fistDate = LocalDate.of(year.getValue(), Month.JANUARY, 1);
        YearMonth lastDay = YearMonth.of(year.getValue(), Month.DECEMBER);
        LocalDate secondDate = LocalDate.of(year.getValue(), Month.DECEMBER, lastDay.getMonthValue());
        List<UserEntity> userList = userRepository.findByBirthdayBetween(fistDate, secondDate);

        if(userList.isEmpty())
            throw new NotFoundException("don't exist any user with this birthday year '" + year + "'");
        else
            return userList;
    }

    public List<UserResponseDTO> findByBirthdayYearResponse(Year year) throws NotFoundException{
        return userMapper.userResponseSet(findByBirthdayYear(year));
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
