package com.Gym.System.service;

import com.Gym.System.dto.request.PaymentDateRequestDTO;
import com.Gym.System.entity.PaymentEntity;
import com.Gym.System.enums.PaymentStatus;
import com.Gym.System.exception.BadRequestException;
import com.Gym.System.exception.NotFoundException;
import com.Gym.System.repository.PaymentRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@AllArgsConstructor
@Getter
public class PaymentService {

    private final PaymentRepository paymentRepository;

    public List<PaymentEntity> findAll() throws NotFoundException {
        List<PaymentEntity> payments = paymentRepository.findAll();

        if(payments.isEmpty())
            throw new NotFoundException("Don't exist any payment on the system");
        else
            return payments;
    }

    public PaymentEntity findById(Long id) throws NotFoundException{
        return paymentRepository.findById(id).orElseThrow(() -> new NotFoundException("This payment id don't exist on the system"));
    }

    public List<PaymentEntity> findByDateOfPayment(PaymentDateRequestDTO dateOfPayment)  throws NotFoundException{
        List<PaymentEntity> payments = paymentRepository.findByDateOfPayment(dateOfPayment.getDateOfPayment());

        if(payments.isEmpty())
            throw new NotFoundException("Don't exist any payment on the system");
        else
            return payments;
    }

    public List<PaymentEntity> findByDateAfterOfPayment(PaymentDateRequestDTO dateOfPayment)  throws NotFoundException{
        List<PaymentEntity> payments = paymentRepository.findByDateOfPaymentAfter(dateOfPayment.getDateOfPayment());

        if(payments.isEmpty())
            throw new NotFoundException("Don't exist any payment on the system");
        else
            return payments;
    }

    public List<PaymentEntity> findByDateBeforeOfPayment(PaymentDateRequestDTO dateOfPayment)  throws NotFoundException{
        List<PaymentEntity> payments = paymentRepository.findByDateOfPaymentBefore(dateOfPayment.getDateOfPayment());

        if(payments.isEmpty())
            throw new NotFoundException("Don't exist any payment on the system");
        else
            return payments;
    }

    public List<PaymentEntity> findByDateBetweenOfPayment(PaymentDateRequestDTO dateOfPaymentOne, PaymentDateRequestDTO dateOfPaymentTwo)  throws NotFoundException{
        List<PaymentEntity> payments = paymentRepository.findByDateOfPaymentBetween(dateOfPaymentOne.getDateOfPayment(), dateOfPaymentTwo.getDateOfPayment());

        if(payments.isEmpty())
            throw new NotFoundException("Don't exist any payment on the system");
        else
            return payments;
    }

    public List<PaymentEntity> findByPaymentStatus(String status) throws BadRequestException {
        try {
            return paymentRepository.findByPaymentStatus(PaymentStatus.valueOf(status));
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("This value is incorrect :" + e);
        }
    }

    public List<PaymentEntity> findBySubscriptionId(Long id) throws NotFoundException{
        List<PaymentEntity> payments = paymentRepository.findBySubscription_SubscriptionId(id);

        if(payments.isEmpty())
            throw new NotFoundException("Don't exist any payment on the system");
        else
            return payments;
    }
}
