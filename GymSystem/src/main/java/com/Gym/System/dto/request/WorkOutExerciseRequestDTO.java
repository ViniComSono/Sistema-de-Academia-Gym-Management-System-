package com.Gym.System.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WorkOutExerciseRequestDTO {

    @NotNull
    private Long workOutId;
    @NotNull
    private Set<Long> exerciseList;
}
