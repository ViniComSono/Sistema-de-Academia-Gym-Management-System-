package com.Gym.System.service;

import com.Gym.System.dto.userDTO;
import com.Gym.System.entity.userEntity;
import com.Gym.System.repository.userRepository;
import lombok.*;
import org.hibernate.sql.exec.ExecutionException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Builder
@Getter
@Setter

public class userService {

    private final userRepository userRepository;

    public List<userEntity> findAlll(){
         try{
             return userRepository.findAll();
         } catch (RuntimeException e) {
             throw new RuntimeException(e);
         }
    }

    public userEntity findByUserName(String name) throws RuntimeException{
        userEntity user = userRepository.findByNome(name);

        if(user != null)
            return user;
        else
            throw new ExecutionException("Not found this user");
    }

    public userEntity findById(Long id) throws RuntimeException{
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("Not found this User"));
    }

    public userEntity cadastrarUsuario(userDTO user) throws RuntimeException{
        userEntity verification = userRepository.findByNome(user.getNome());
        if(verification == null){
            userEntity newUser = userEntity.builder()
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
