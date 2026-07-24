package com.Gym.System.dto.response;

import com.Gym.System.enums.SubscriptionStatus;
import lombok.*;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SubscriptionSummaryPlanResponseDTO {

    private Long subscriptionId;
    private LocalDate startDate;
    private LocalDate expirationDate;
    private SubscriptionStatus status;
}
