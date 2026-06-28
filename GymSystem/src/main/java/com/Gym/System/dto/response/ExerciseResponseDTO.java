package com.Gym.System.dto.response;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExerciseResponseDTO {

    private Long exerciseId;
    private String exerciseName;
    private String muscleGroup;
}
