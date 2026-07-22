package com.Gym.System.entity;

import com.Gym.System.enums.SubscriptionStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Table(name = "plan")
@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlanEntity {

    @Column(name = "plan_id")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;

    @Column(name = "plan_name")
    private String planName;

    @Column(name = "plan_price")
    private BigDecimal planPrice;

    @Column(name = "plan_duration")
    private LocalDate planDuration;

    @ManyToOne(cascade =  {CascadeType.PERSIST, CascadeType.REFRESH})
    private SubscriptionEntity subscriptionEntity;
}
