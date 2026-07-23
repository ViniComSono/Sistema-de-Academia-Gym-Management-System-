package com.Gym.System.entity;


import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Table(name = "plan")
@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
public class PlanEntity {

    @Column(name = "plan_id")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long planId;

    @Column(name = "plan_name")
    private String planName;

    @Column(name = "plan_price")
    private BigDecimal planPrice;

    @Column(name = "plan_duration")
    private LocalDate planDuration;

    @OneToMany(cascade =  {CascadeType.PERSIST, CascadeType.REFRESH})
    private List<SubscriptionEntity> subscriptions;
}
