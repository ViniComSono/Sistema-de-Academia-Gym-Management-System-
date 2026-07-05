package com.Gym.System.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserCharacteristicsRequestDTO{

    private Long userId;
    private BigDecimal weight;
    private BigDecimal height;
}


