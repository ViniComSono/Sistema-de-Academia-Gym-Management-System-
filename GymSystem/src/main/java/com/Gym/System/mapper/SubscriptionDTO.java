package com.Gym.System.mapper;

import com.Gym.System.dto.response.SubscriptionSummaryResponseDTO;
import com.Gym.System.entity.SubscriptionEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SubscriptionDTO {

    SubscriptionSummaryResponseDTO subscriptionSummaryPlan(SubscriptionEntity subscription);
}
