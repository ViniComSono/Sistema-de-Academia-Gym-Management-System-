package com.Gym.System.dto.request;

import com.Gym.System.enums.SexUser;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class UserPutRequestDTO {

    private Long userId;
    private String name;

    @NotBlank
    private SexUser sexUser;

    @JsonFormat(pattern = "dd/MM/YYYY")
    private LocalDate dateOfBirth;

    private List<Long> assessmentIdList = new ArrayList<>();
    private List<Long> workOutIdList = new ArrayList<>();
    private Long subscriptionId;

}
