package com.Gym.System.dto.request;

import com.Gym.System.enums.PaymentStatus;

import java.math.BigDecimal;
import java.time.LocalDate;

public class PaymentRequestDTO {

    private BigDecimal amount;
    private LocalDate correctDate;
    private LocalDate dataOfPayment;
    private PaymentStatus paymentStatus;
    private Long SubscriptionId;
}
