package com.Gym.System.mapper;

import com.Gym.System.dto.response.ExerciseResponseDTO;
import com.Gym.System.entity.ExerciseEntity;
import org.mapstruct.Mapper;
import java.util.List;

@Mapper(componentModel = "spring")
public interface ExerciseMapper {
    ExerciseResponseDTO exerciseResponse(ExerciseEntity exercise);
    List<ExerciseResponseDTO> exerciseListResponse(List<ExerciseEntity> exerciseList);
}
