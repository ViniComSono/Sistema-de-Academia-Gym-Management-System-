package com.Gym.System.dto.response;
import com.Gym.System.entity.PhysicalAssessmentEntity;
import lombok.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDTO {

    private Long userId;
    private String name;
    private BigDecimal weight;
    private BigDecimal height;
    private Set<PhysicalAssessmentEntity> assessementList = new HashSet<>();
    private Set<WorkOutSummaryResponseDTO> workOutList = new HashSet<>();
}
