package com.Gym.System.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WorkOutNameRequestDTO {
    @NotNull
    private Long workOutId;
    @NotBlank
    private String workOutName;

}
