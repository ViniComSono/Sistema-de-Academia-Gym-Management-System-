package com.Gym.System.entity;

import com.Gym.System.enums.Imc;
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

    @Column(name = "body_mass_index")
    private BigDecimal imc;

    @Column(name = "imc_type")
    @Enumerated(EnumType.STRING)
    private Imc imcType;

    @ManyToOne()
    @JoinColumn(name = "user_id")
    private UserEntity user;
}
