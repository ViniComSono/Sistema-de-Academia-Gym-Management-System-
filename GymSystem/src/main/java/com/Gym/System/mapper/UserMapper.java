package com.Gym.System.mapper;

import com.Gym.System.dto.response.UserResponseDTO;
import com.Gym.System.dto.response.WorkOutResponseDTO;
import com.Gym.System.entity.UserEntity;
import com.Gym.System.entity.WorkOutEntity;
import org.mapstruct.Mapper;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserResponseDTO userResponseDTO(UserEntity user);
    Set<UserResponseDTO> UserResponseSet(Set<UserEntity> userList);

}
