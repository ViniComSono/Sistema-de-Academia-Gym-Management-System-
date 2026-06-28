package com.Gym.System.dto.response;
import com.Gym.System.entity.ExerciseEntity;
import lombok.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WorkOutResponseDTO {

    private Long workOutId;
    private String workOutName;
    private Set<ExerciseEntity> exerciseList = new HashSet<>();
    private Set<UserSummaryResponseDTO> userList = new HashSet<>();
}
