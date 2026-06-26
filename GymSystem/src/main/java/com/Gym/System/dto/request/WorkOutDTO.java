package com.Gym.System.dto.request;

import com.Gym.System.entity.ExerciseEntity;
import com.Gym.System.entity.UserEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WorkOutDTO {

    @NotNull
    private String workOutName;

    private Set<Long> usersId;

    private Set<Long> exercisesId;

}
