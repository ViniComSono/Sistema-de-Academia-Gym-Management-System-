package com.Gym.System.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class PaymentDateRequestDTO {

    @NotBlank
    @JsonFormat(pattern = "dd/MM/YYYY")
    private LocalDate dateOfPayment;
}
