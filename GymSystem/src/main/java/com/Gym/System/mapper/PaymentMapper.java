package com.Gym.System.mapper;

import com.Gym.System.dto.response.PaymentResponseDTO;
import com.Gym.System.entity.PaymentEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PaymentMapper {

    PaymentResponseDTO paymentResponse (PaymentEntity payment);
}
