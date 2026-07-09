package com.Gym.System.mapper;

import com.Gym.System.dto.response.AssessmentResponseDTO;
import com.Gym.System.entity.PhysicalAssessmentEntity;
import org.mapstruct.Mapper;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface AssessmentMapper {
    AssessmentResponseDTO assessmentResponse(PhysicalAssessmentEntity assessment);
    Set<AssessmentResponseDTO> assessmentResponse(Set<PhysicalAssessmentEntity> assessment);
}
