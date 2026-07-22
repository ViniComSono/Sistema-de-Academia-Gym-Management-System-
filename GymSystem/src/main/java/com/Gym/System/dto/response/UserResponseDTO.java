package com.Gym.System.dto.response;
import com.Gym.System.entity.PhysicalAssessmentEntity;
import com.Gym.System.enums.SexUser;
import lombok.*;
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
    private SexUser sexUser;
    private LocalDate birthday;
    private Set<PhysicalAssessmentEntity> assessmentList = new HashSet<>();
    private Set<WorkOutSummaryResponseDTO> workOutList = new HashSet<>();
}
