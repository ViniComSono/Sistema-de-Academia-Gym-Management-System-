package com.Gym.System.dto.request;


import lombok.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WorkOutPutRequestDTO {

    private Long workOutId;
    private String workOutName;
    private Set<Long> exerciseList = new HashSet<>();
    private Set<Long> userList = new HashSet<>();

}
