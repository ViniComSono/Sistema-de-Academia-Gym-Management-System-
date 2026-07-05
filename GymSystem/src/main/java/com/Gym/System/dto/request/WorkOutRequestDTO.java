package com.Gym.System.dto.request;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WorkOutRequestDTO {

    @NotNull
    private String workOutName;

    private Set<Long> usersId;

    private Set<Long> exercisesId;

}
