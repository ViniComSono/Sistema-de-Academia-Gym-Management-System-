package com.Gym.System.dto.response;

import com.Gym.System.enums.PaymentStatus;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

public class PaymentResponseDTO {

    private Long paymentId;
    private BigDecimal amount;
    private LocalDate correctDate;
    private LocalDate dateOfPayment;

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    private SubscriptionSummaryResponseDTO subscription;
}
