package com.Gym.System.service;

import com.Gym.System.dto.UserDTO;
import com.Gym.System.entity.UserEntity;
import com.Gym.System.exception.NotFoundException;
import com.Gym.System.repository.UserRepository;
import lombok.*;
import org.hibernate.sql.exec.ExecutionException;
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
         try{
             return userRepository.findAll();
         } catch (Exception e) {
             throw new NotFoundException("Not found this user");
         }
    }

    public UserEntity findByUserName(String name) throws NotFoundException{
        UserEntity user = userRepository.findByNome(name);

        if(user != null)
            return user;
        else
            throw new NotFoundException("Not found this user");
    }

    public UserEntity findById(Long id) throws NotFoundException{
        return userRepository.findById(id).orElseThrow(() -> new NotFoundException("Not found this User"));
    }

    public UserEntity cadastrarUsuario(UserDTO user) throws RuntimeException{
        UserEntity verification = userRepository.findByNome(user.getNome());
        if(verification == null){
            UserEntity newUser = UserEntity.builder()
                    .nome(user.getNome())
                    .peso(user.getPeso())
                    .altura(user.getAltura())
                    .build();
            return userRepository.save(newUser);
        }else{
            throw new RuntimeException("This user exist on the System");
        }
    }


}
