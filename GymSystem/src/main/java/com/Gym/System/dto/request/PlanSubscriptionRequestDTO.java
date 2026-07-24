package com.Gym.System.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PlanSubscriptionRequestDTO {

    Long planId;
    List<Long> subscriptionsId;
}
