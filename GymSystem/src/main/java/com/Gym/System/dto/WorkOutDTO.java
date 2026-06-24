package com.Gym.System.dto;

import com.Gym.System.entity.ExerciseEntity;
import com.Gym.System.entity.UserEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WorkOutDTO {

    @NotNull
    private String workOutName;

    @NotNull
    private Long userId;

    private List<Long> exercisesId = new ArrayList<>();

}
