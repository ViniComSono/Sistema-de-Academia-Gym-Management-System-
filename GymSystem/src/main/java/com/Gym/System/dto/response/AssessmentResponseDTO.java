package com.Gym.System.dto.response;
import com.Gym.System.enums.Imc;
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
    private BigDecimal bodyFatPercentage;
    private BigDecimal imc;
    private Imc imcType;
    private UserSummaryResponseDTO user;
}