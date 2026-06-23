package com.Gym.System.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;

@Getter
@Setter
@Table(name = "user")
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class userEntity {

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


}
