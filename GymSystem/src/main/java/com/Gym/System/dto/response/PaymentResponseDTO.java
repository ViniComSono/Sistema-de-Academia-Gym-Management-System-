package com.Gym.System.dto.response;


import com.Gym.System.enums.PaymentStatus;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import com.Gym.System.entity.SubscriptionEntity;
import com.Gym.System.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentResponseDTO {

    private Long paymentId;
    private BigDecimal amount;
    private LocalDate correctDate;
    private LocalDate dateOfPayment;
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;
    private SubscriptionSummaryPlanResponseDTO subscription;
}
