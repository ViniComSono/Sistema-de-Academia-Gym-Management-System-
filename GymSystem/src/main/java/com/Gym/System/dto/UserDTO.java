package com.Gym.System.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
//
//    //String email;
//    //String password;
    @NotBlank
    private String nome;
    @NotNull
    private BigDecimal peso;
    @NotNull
    private BigDecimal altura;
}
