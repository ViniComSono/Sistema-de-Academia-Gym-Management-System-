package com.Gym.System.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class PaymentAddDateRequestDTO {

    @NotNull
    private Long paymentId;
    @NotNull
    @JsonFormat(pattern = "dd/MM/YYYY")
    private LocalDate paymentDate;
}
