package com.Gym.System.dto.request;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class AssessmentCharacteristicsRequestDTO {
    @NotNull
    private BigDecimal weight;
    @NotNull
    private BigDecimal height;
}
