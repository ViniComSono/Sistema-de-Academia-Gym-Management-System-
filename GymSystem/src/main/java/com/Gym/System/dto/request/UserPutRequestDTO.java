package com.Gym.System.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserPutRequestDTO {

    private Long userId;
    //String email;
    //String password;
    private String name;
    private BigDecimal weight;
    private BigDecimal height;
    private Set<Long> assessementIdList = new HashSet<>();
    private Set<Long> workOutIdList = new HashSet<>();

}
