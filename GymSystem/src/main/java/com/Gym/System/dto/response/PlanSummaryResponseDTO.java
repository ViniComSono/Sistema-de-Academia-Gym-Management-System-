package com.Gym.System.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlanSummaryResponseDTO {

    private Long planId;
    private String planName;
    private int planDurationInMonths;

}
