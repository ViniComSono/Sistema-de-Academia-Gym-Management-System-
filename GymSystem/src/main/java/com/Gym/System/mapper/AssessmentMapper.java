package com.Gym.System.mapper;

import com.Gym.System.dto.response.AssessmentResponseDTO;
import com.Gym.System.entity.PhysicalAssessmentEntity;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface AssessmentMapper {
    AssessmentResponseDTO assessmentResponse(PhysicalAssessmentEntity assessment);
    List<AssessmentResponseDTO> assessmentResponse(List<PhysicalAssessmentEntity> assessment);
}
