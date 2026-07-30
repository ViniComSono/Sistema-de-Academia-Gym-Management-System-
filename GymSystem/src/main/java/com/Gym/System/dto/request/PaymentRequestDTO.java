package com.Gym.System.dto.request;

import com.Gym.System.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import com.Gym.System.enums.PaymentStatus;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Builder
public class PaymentRequestDTO {

    private BigDecimal amount;
    private LocalDate correctDate;
    private LocalDate dateOfPayment;
    private Long subscriptionId;
}
