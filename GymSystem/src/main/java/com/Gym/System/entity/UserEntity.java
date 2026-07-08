package com.Gym.System.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
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
    private String name;

    private LocalDate birthday;
    private BigDecimal weight;
    private BigDecimal height;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "user")
    @Builder.Default
    private Set<PhysicalAssessmentEntity> assessementList = new HashSet<>();

    @ManyToMany(cascade = CascadeType.MERGE, mappedBy = "userList")
    @Builder.Default
    private Set<WorkOutEntity> workOutList = new HashSet<>();
}
