package com.Gym.System.dto.response;

import lombok.*;
import java.time.LocalDate;


@Getter
@Setter
public class AssessmentResponseSummaryDTO {
    private Long id;
    private LocalDate date;
    private AssessmentResponseDTO assessmentResponse;
}
