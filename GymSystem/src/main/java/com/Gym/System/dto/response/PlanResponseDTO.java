package com.Gym.System.dto.response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PlanResponseDTO {

    private Long planId;
    private String planName;
    private int planDurationInMonths;
    private List<SubscriptionSummaryPlanResponseDTO> subscriptions;
}
