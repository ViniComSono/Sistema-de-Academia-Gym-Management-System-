package com.Gym.System.mapper;

import com.Gym.System.dto.response.SubscriptionSummaryPlanResponseDTO;
import com.Gym.System.entity.SubscriptionEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SubscriptionDTO {

    SubscriptionSummaryPlanResponseDTO subscriptionSummaryPlan(SubscriptionEntity subscription);
}
