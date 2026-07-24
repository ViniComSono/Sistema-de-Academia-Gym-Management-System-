package com.Gym.System.dto.request;

import lombok.Getter;
import lombok.Setter;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class UserWorkOutsRequestDTO {

    private Long userId;
    private Set<Long> workOutIdList = new HashSet<>();
}
