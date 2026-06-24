package com.Gym.System.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.util.*;

@Getter
@Setter
@Table(name = "user")
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private Long userId;

    //String email;
    //String password;

    @Column(nullable = false)
    private String nome;

    private BigDecimal peso;
    private BigDecimal altura;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "user")
    @Builder.Default
    private Map<Long, PhysicalAssessmentEntity> assessementList = new HashMap<>();
}
