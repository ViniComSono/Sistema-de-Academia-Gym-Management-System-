package com.Gym.System.dto.response;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AssessmentResponseSummaryDTO {
    private Long id;
    private LocalDate date;
    private AssessmentResponseDTO assessmentResponse;
}
