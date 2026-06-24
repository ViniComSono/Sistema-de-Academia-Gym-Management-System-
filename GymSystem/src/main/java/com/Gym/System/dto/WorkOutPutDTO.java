package com.Gym.System.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WorkOutPutDTO {

    @NotNull
    private Long workOutId;

    @NotBlank
    private String workOutName;

    @NotNull
    private Long userId;

}
