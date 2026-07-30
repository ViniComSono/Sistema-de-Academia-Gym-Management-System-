package com.Gym.System.mapper;

import com.Gym.System.dto.response.PaymentResponseDTO;
import com.Gym.System.entity.PaymentEntity;
import org.mapstruct.Mapper;
import java.util.List;

@Mapper(componentModel = "spring")
public interface PaymentMapper {

    PaymentResponseDTO paymentResponse (PaymentEntity payment);
    List<PaymentResponseDTO> paymentListResponse (List<PaymentEntity> payments);
}
