package com.Gym.System.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.util.*;

@Getter
@Setter
public class WorkOutUsersRequestDTO {

    @NotNull
    private Long workOutId;
    @NotNull
    private Set<Long> usersId;
}
