package com.Gym.System.mapper;

import com.Gym.System.dto.response.ExerciseResponseDTO;
import com.Gym.System.entity.ExerciseEntity;
import org.mapstruct.Mapper;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface ExerciseMapper {
    ExerciseResponseDTO exerciseResponse(ExerciseEntity exercise);
    Set<ExerciseResponseDTO> exerciseListResponse(Set<ExerciseEntity> exerciseList);
}
