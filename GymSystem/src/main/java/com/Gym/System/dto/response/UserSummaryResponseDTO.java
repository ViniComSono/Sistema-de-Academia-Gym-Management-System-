package com.Gym.System.dto.response;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserSummaryResponseDTO {

    private Long userId;
    private String name;
}
