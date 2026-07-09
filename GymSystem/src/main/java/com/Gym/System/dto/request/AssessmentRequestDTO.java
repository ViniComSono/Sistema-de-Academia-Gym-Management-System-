package com.Gym.System.dto.request;

import jakarta.persistence.Entity;
import lombok.*;
import java.math.BigDecimal;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class AssessmentRequestDTO {

    private Long userId;
    private BigDecimal weight;
    private BigDecimal height;
}
