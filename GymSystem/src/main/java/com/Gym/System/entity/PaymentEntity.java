package com.Gym.System.entity;

import com.Gym.System.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Table(name = "payment")
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private BigDecimal amount;

    @Column(name = "correct_date")
    private LocalDate correctDate;

    @Column(name = "date_of_payment")
    private LocalDate dateOfPayment;

    @Column(name = "payment_status")
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;
}
