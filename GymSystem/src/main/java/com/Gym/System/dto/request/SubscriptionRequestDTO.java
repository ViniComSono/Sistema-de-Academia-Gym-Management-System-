package com.Gym.System.dto.request;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class SubscriptionRequestDTO {

    private LocalDate startDate;
    private LocalDate expirationDate;
    private String plan;
    private Long userId;
}
