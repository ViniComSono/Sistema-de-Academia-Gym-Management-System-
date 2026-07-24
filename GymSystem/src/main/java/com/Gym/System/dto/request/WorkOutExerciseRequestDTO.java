package com.Gym.System.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class WorkOutExerciseRequestDTO {

    @NotNull
    private Long workOutId;
    @NotNull
    private Set<Long> exerciseList;
}
