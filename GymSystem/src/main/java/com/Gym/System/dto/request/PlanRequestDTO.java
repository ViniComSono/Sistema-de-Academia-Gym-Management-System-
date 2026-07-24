package com.Gym.System.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlanRequestDTO {

    @NotBlank
    private String planName;
    @NotNull
    private BigDecimal PlanPrice;
    @NotNull
    private int planDurationInMonths;
}
