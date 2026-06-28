package com.Gym.System.mapper;

import com.Gym.System.dto.response.UserResponseDTO;
import com.Gym.System.entity.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserResponseDTO userResponseDTO(UserEntity user);

}
