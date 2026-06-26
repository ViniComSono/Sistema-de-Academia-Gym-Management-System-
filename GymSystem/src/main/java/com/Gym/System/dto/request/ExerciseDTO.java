package com.Gym.System.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExerciseDTO {
    @NotBlank
    private String exerciseName;
    @NotBlank
    private String muscleGroup;
}
