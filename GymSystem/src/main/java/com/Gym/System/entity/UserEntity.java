package com.Gym.System.entity;

import com.Gym.System.enums.SexUser;
import jakarta.persistence.*;
import lombok.*;
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

    @Column(nullable = false, unique = true)
    private String name;

    @Column(name = "sex_user")
    @Enumerated(EnumType.STRING)
    private SexUser sexUser;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "user")
    @Builder.Default
    private List<PhysicalAssessmentEntity> assessmentList = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.MERGE, mappedBy = "userList")
    @Builder.Default
    private List<WorkOutEntity> workOutList = new ArrayList<>();

    @OneToOne(cascade = CascadeType.REFRESH, mappedBy = "user")
    private SubscriptionEntity subscription;
}
