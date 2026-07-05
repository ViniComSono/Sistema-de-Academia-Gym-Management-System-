package com.Gym.System.exception;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErrorResponse {
    private String message;
    private Integer status;
}
