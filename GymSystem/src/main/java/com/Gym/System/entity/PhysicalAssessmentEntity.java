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

    @Column(name = "percentual_gordura")
    private BigDecimal percentualGordura;

    @Column(name = "percentual_massa_magra")
    private BigDecimal percentualMasseMagra;

    @Column(name = "calorias_diarias")
    private BigDecimal caloriasDiarias;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private UserEntity user;
}
