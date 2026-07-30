package com.Gym.System.mapper;

import com.Gym.System.dto.response.SubscriptionSummaryResponseDTO;
import com.Gym.System.entity.SubscriptionEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SubscriptionMapper {

    SubscriptionSummaryResponseDTO subscriptionResponse(SubscriptionEntity subscription);
    List<SubscriptionSummaryResponseDTO> subscriptionListResponse(List<SubscriptionEntity> subscription);
}
