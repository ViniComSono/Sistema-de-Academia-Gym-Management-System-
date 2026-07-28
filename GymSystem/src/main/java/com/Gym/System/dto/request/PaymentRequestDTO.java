package com.Gym.System.dto.request;


import com.Gym.System.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
public class PaymentRequestDTO {

    private BigDecimal amount;
    private LocalDate correctDate;
    private LocalDate dateOfPayment;

    private Long subscriptionId;
}
