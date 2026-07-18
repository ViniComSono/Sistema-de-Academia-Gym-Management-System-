package com.Gym.System.dto.request;

import com.Gym.System.enums.PhysicalActivityFrequency;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.math.BigDecimal;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AssessmentRequestDTO {

    @NotNull
    private Long userId;
    @NotNull
    private BigDecimal weight;
    @NotNull
    private BigDecimal height;
    @NotNull
    private PhysicalActivityFrequency physicalActivityFrequency;
}
