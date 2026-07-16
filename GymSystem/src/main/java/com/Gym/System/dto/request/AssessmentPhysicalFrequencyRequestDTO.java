package com.Gym.System.dto.request;

import com.Gym.System.enums.PhysicalActivityFrequency;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class AssessmentPhysicalFrequencyRequestDTO {
    @NotNull
    private Long userId;
    @NotNull
    private BigDecimal weight;
    @NotNull
    private BigDecimal height;
    @NotNull
    private PhysicalActivityFrequency physicalActivityFrequency;
}
