package com.Gym.System.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class UserBirthdayBetweenRequestDTO {

    @NotBlank
    @JsonFormat(pattern = "dd/MM/YYYY")
    private LocalDate dateOne;

    @NotBlank
    @JsonFormat(pattern = "dd/MM/YYYY")
    private LocalDate dateTwo;
}
