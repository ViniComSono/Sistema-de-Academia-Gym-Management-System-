package com.Gym.System.dto.response;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AssessmentResponseDTO {

    private Long Id;
    private BigDecimal weight;
    private BigDecimal height;
    private Double bodyFatPercentage;
    private UserSummaryResponseDTO user;
}