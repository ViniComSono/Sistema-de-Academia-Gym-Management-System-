package com.Gym.System.mapper;

import com.Gym.System.dto.request.PlanRequestDTO;
import com.Gym.System.dto.response.PlanResponseDTO;
import com.Gym.System.entity.PlanEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PlanMapper {

    PlanResponseDTO planResponse(PlanEntity plan);
    List<PlanResponseDTO> plansResponse(List<PlanEntity> planEntities);
}
