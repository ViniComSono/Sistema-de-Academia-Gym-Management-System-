package com.Gym.System.service;

import com.Gym.System.dto.request.UserRequestDTO;
import com.Gym.System.entity.UserEntity;
import com.Gym.System.exception.NotFoundException;
import com.Gym.System.repository.UserRepository;
import lombok.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Builder
@Getter
@Setter

public class UserService {

    private final UserRepository userRepository;

    public List<UserEntity> findAlll() throws NotFoundException{
         return userRepository.findAll();
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

    public UserEntity createUser(UserRequestDTO user) throws RuntimeException{
        UserEntity verification = userRepository.findByName(user.getName());
        if(verification == null){
            UserEntity newUser = UserEntity.builder()
                    .name(user.getName())
                    .weight(user.getWeight())
                    .height(user.getHeight())
                    .build();
            return userRepository.save(newUser);
        }else{
            throw new RuntimeException("This user exist on the System");
        }
    }


}
