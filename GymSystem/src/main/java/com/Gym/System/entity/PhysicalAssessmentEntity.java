package com.Gym.System.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Getter
@Setter
@Table(name = "physical_assessment")
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PhysicalAssessmentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;

    private BigDecimal weight;
    private BigDecimal height;

    @Column(name = "body_fat_percentage")
    private BigDecimal bodyFatPercentage;

    @ManyToOne()
    @JoinColumn(name = "user_id")
    private UserEntity user;
}
