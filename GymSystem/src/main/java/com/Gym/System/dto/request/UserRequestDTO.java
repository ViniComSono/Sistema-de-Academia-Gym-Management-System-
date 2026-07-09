package com.Gym.System.dto.request;

import com.Gym.System.enums.SexUser;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDTO {

    @NotBlank
    private String name;

    @NotBlank
    private SexUser sexUser;

    @NotBlank
    @JsonFormat(pattern = "dd/MM/YYYY")
    private LocalDate birthday;


}
