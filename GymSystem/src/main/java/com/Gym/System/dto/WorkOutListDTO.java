package com.Gym.System.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WorkOutListDTO {

    @NotNull
    private Long workOutId;

    @NotNull
    private List<Long> exercisesId = new ArrayList<>();
}
