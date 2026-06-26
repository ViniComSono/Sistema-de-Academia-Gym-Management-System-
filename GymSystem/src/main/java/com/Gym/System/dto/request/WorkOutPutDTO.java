package com.Gym.System.dto.request;

import com.Gym.System.entity.UserEntity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WorkOutPutDTO {

    @NotNull
    private Long workOutId;

    private String workOutName;

    private Set<Long> usersId;

}
