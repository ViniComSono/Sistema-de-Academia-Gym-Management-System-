package com.Gym.System.mapper;

import com.Gym.System.dto.response.UserResponseDTO;
import com.Gym.System.entity.UserEntity;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserResponseDTO userResponseDTO(UserEntity user);
    List<UserResponseDTO> userResponseSet(List<UserEntity> userList);
}
