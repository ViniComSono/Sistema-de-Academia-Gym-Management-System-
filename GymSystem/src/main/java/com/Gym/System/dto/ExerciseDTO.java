package com.Gym.System.dto;

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
    private String exercicio;
    @NotBlank
    private String grupoMuscular;
}
