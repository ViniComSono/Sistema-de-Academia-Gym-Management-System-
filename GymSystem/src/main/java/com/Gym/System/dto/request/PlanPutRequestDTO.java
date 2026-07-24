package com.Gym.System.dto.request;


import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
@Getter
@Setter
public class PlanPutRequestDTO {

    private Long planId;
    private String planName;
    private BigDecimal planPrice;
    private int planDurationInMonths;
}
