package com.Gym.System.service;

import com.Gym.System.dto.request.*;
import com.Gym.System.dto.response.UserResponseDTO;
import com.Gym.System.entity.UserEntity;
import com.Gym.System.entity.WorkOutEntity;
import com.Gym.System.exception.NotFoundException;
import com.Gym.System.mapper.UserMapper;
import com.Gym.System.repository.UserRepository;
import lombok.*;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
@Builder
@Getter
@Setter

public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final WorkOutService workOutService;

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

    public Set<UserResponseDTO> findAllResponse(){
        Set<UserEntity> users = new HashSet<>(userRepository.findAll());
        return userMapper.UserResponseSet(users);
    }

    public UserResponseDTO findByIdResponse(Long id) throws NotFoundException{
        return userMapper.userResponseDTO(findById(id));
    }

    public UserResponseDTO findByUserNameResponse(String name) throws NotFoundException{
        return userMapper.userResponseDTO(userRepository.findByName(name));
    }

    public UserResponseDTO createUser(UserRequestDTO userRequest){
        UserEntity verification = userRepository.findByName(userRequest.getName());

        UserEntity newUser = UserEntity.builder()
                .name(userRequest.getName())
                .weight(userRequest.getWeight())
                .height(userRequest.getHeight())
                .build();

        userRepository.save(newUser);
        return userMapper.userResponseDTO(newUser);
    }

    public UserResponseDTO addWorkOut(UserWorkOutsRequestDTO userRequest) throws NotFoundException{
        UserEntity user = findById(userRequest.getUserId());

        for(Long workOutId : userRequest.getWorkOutIdList()){
            WorkOutEntity workOut = workOutService.findById(workOutId);
            user.getWorkOutList().add(workOut);
        }

        userRepository.save(user);
        return userMapper.userResponseDTO(user);
    }

    public UserResponseDTO removeWorkOut(UserWorkOutsRequestDTO userRequest) throws NotFoundException{
        UserEntity user = findById(userRequest.getUserId());

        for(Long workOutId : userRequest.getWorkOutIdList()){
            WorkOutEntity workOut = workOutService.findById(workOutId);
            user.getWorkOutList().remove(workOut);
        }

        userRepository.save(user);
        return userMapper.userResponseDTO(user);
    }

    public UserResponseDTO editAll(UserPutRequestDTO userRequest) throws NotFoundException{
        UserEntity user = findById(userRequest.getUserId());
        Set<WorkOutEntity> workOutSet = new HashSet<>();

        for(Long workOutId : userRequest.getWorkOutIdList()){
            WorkOutEntity workOut = workOutService.findById(workOutId);
            workOutSet.add(workOut);
        }
            /*
            FAZER A DE ASSESSMENT DEPOIS, NAO FIZ AGORA POIS NAO ESTA PRONTO NO MOMENNTO
             */

        user.setName(userRequest.getName());
        user.setWeight(userRequest.getWeight());
        user.setHeight(userRequest.getHeight());
        user.setWorkOutList(workOutSet);

        userRepository.save(user);return
        userMapper.userResponseDTO(user);
    }

    public UserResponseDTO editNameUser(UserNameRequestDTO userRequest) throws NotFoundException{
        UserEntity user = findById(userRequest.getUserId());
        user.setName(userRequest.getName());

        userRepository.save(user);
        return userMapper.userResponseDTO(user);
    }

    public UserResponseDTO editUserCharacteristics(UserCharacteristicsRequestDTO userRequest) throws NotFoundException{
        UserEntity user = findById(userRequest.getUserId());

        user.setWeight(userRequest.getWeight());
        user.setHeight(userRequest.getHeight());

        userRepository.save(user);
        return userMapper.userResponseDTO(user);
    }
}
