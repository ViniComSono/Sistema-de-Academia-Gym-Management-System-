package com.Gym.System.dto.request;

import com.Gym.System.enums.SexUser;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
public class UserRequestDTO {

    @NotBlank
    private String name;

    @NotBlank
    private SexUser sexUser;

    @NotBlank
    @JsonFormat(pattern = "dd/MM/YYYY")
    private LocalDate dateOfBirth;


}
