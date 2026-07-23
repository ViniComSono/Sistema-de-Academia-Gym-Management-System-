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
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
@Getter
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

    public List<UserEntity> findByBirthday(LocalDate birthday) throws NotFoundException{
        List<UserEntity> usersList = userRepository.findByBirthday(birthday);

        if(usersList.isEmpty()){
            throw new NotFoundException("Any user with this birthday data");
        }else{
            return usersList;
        }
    }

    public List<UserEntity> findByBirthdayAfter(UserBirthdayRequestDTO userRequest) throws NotFoundException{
        List<UserEntity> users = userRepository.findByBirthdayAfter(userRequest.getBirthday());

        if(users.isEmpty()){
            throw new NotFoundException("Don't exist any user with the birthday after this data");
        }else{
            return users;
        }
    }

    public List<UserEntity> findByBirthdayBefore(UserBirthdayRequestDTO userRequest) throws NotFoundException{
        List<UserEntity> users = userRepository.findByBirthdayBefore(userRequest.getBirthday());

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

    public List<UserEntity> findyByBirthdayYear(UserBirthdayRequestDTO userRequest) throws NotFoundException{
        LocalDate firstDate = LocalDate.of(userRequest.getBirthday().getYear(), 1, 1);
        LocalDate lastDate = LocalDate.of(userRequest.getBirthday().getYear(), 12, 31);
        List<UserEntity> users = userRepository.findByBirthdayBetween(firstDate, lastDate);

        if(users.isEmpty()){
            throw new NotFoundException("Don't exist any user with the birthday between those dates");
        }else{
            return users;
        }
    }

    public Set<UserResponseDTO> findAllResponse() throws NotFoundException{
        Set<UserEntity> users = new HashSet<>(findAll());
        return userMapper.userResponseSet(users);
    }

    public UserResponseDTO findByIdResponse(Long id) throws NotFoundException{
        return userMapper.userResponseDTO(findById(id));
    }

    public UserResponseDTO findByUserNameResponse(String name) throws NotFoundException{
        return userMapper.userResponseDTO(findByUserName(name));
    }

    public Set<UserResponseDTO> findByBirthdayResponse(LocalDate birthday) throws NotFoundException{
        Set<UserEntity> users = new HashSet<>(findByBirthday(birthday));

        return userMapper.userResponseSet(users);
    }

    public Set<UserResponseDTO> findByBirthdayAfterResponse(UserBirthdayRequestDTO userRequest) throws NotFoundException{
        Set<UserEntity> users = new HashSet<>(findByBirthdayAfter(userRequest));

        return userMapper.userResponseSet(users);
    }

    public Set<UserResponseDTO> findByBirthdayBeforeResponse(UserBirthdayRequestDTO userRequest) throws NotFoundException{
        Set<UserEntity> users = new HashSet<>(findByBirthdayBefore(userRequest));

        return userMapper.userResponseSet(users);
    }

    public Set<UserResponseDTO> findByBirthdayBetweenResponse(UserBirthdayBetweenRequestDTO userRequest) throws NotFoundException{
        Set<UserEntity> users = new HashSet<>(findByBirthdayBetween(userRequest));

        return userMapper.userResponseSet(users);
    }

    public Set<UserResponseDTO> findByBirthdayYearResponse(UserBirthdayRequestDTO userRequest) throws NotFoundException{
        Set<UserEntity> users = new HashSet<>(findyByBirthdayYear(userRequest));

        return userMapper.userResponseSet(users);
    }

    public UserResponseDTO createUser(UserRequestDTO userRequest){

        UserEntity newUser = UserEntity.builder()
                .name(userRequest.getName())
                .sexUser(userRequest.getSexUser())
                .birthday(userRequest.getBirthday())
                .build();

        userRepository.save(newUser);
        return userMapper.userResponseDTO(newUser);
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
        user.setSexUser(userRequest.getSexUser());
        user.setBirthday(userRequest.getBirthday());
        user.setWorkOutList(workOutSet);

        userRepository.save(user);return
                userMapper.userResponseDTO(user);
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
