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

    private LocalDate birthday;


    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "user")
    @Builder.Default
    private Set<PhysicalAssessmentEntity> assessmentList = new HashSet<>();

    @ManyToMany(cascade = CascadeType.MERGE, mappedBy = "userList")
    @Builder.Default
    private Set<WorkOutEntity> workOutList = new HashSet<>();
}
