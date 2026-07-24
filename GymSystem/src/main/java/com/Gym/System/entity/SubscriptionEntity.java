package com.Gym.System.entity;

import com.Gym.System.enums.SubscriptionStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Table(name = "subscription")
@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
public class SubscriptionEntity {

    @Id
    @Column(name = "subscription_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long subscriptionId;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "expiration_date")
    private LocalDate expirationDate;

    @Enumerated(EnumType.STRING)
    private SubscriptionStatus status;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.REFRESH})
    private PlanEntity plan;

    @Column(name = "payments")
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REFRESH}, mappedBy = "subscription")
    @Builder.Default
    private List<PaymentEntity> paymentEntityList = new ArrayList<>();
}
