package com.Gym.System.mapper;
import com.Gym.System.dto.response.WorkOutResponseDTO;
import com.Gym.System.entity.WorkOutEntity;
import org.mapstruct.Mapper;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface WorkOutMapper {

    WorkOutResponseDTO workOutResponse(WorkOutEntity workOut);
    Set<WorkOutResponseDTO> workOutResponseSet(Set<WorkOutEntity> workOutList);
}
