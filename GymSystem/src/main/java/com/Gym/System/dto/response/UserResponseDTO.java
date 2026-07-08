package com.Gym.System.dto.response;
import com.Gym.System.entity.PhysicalAssessmentEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
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
    private LocalDate birthday;
    private BigDecimal weight;
    private BigDecimal height;
    private Set<PhysicalAssessmentEntity> assessementList = new HashSet<>();
    private Set<WorkOutSummaryResponseDTO> workOutList = new HashSet<>();
}
