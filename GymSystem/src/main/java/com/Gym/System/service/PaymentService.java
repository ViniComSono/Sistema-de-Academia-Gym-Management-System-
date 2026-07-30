package com.Gym.System.service;

import com.Gym.System.dto.request.PaymentAddDateRequestDTO;
import com.Gym.System.dto.request.PaymentDateRequestDTO;
import com.Gym.System.dto.request.PaymentRequestDTO;
import com.Gym.System.dto.response.PaymentResponseDTO;
import com.Gym.System.entity.PaymentEntity;
import com.Gym.System.entity.SubscriptionEntity;
import com.Gym.System.enums.PaymentStatus;
import com.Gym.System.exception.BadRequestException;
import com.Gym.System.exception.NotFoundException;
import com.Gym.System.mapper.PaymentMapper;
import com.Gym.System.repository.PaymentRepository;
import com.Gym.System.repository.SubscriptionRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
@Getter
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final PaymentMapper paymentMapper;

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

    public List<PaymentResponseDTO> findAllResponse() throws NotFoundException {
        return paymentMapper.paymentListResponse(findAll());
    }

    public PaymentResponseDTO findByIdResponse(Long id) throws NotFoundException{
        return paymentMapper.paymentResponse(findById(id));
    }

    public List<PaymentResponseDTO> findByDateOfPaymentResponse(PaymentDateRequestDTO dateOfPayment)  throws NotFoundException{
        return paymentMapper.paymentListResponse(findByDateOfPayment(dateOfPayment));
    }

    public List<PaymentResponseDTO> findByDateAfterOfPaymentResponse(PaymentDateRequestDTO dateOfPayment)  throws NotFoundException{
        return paymentMapper.paymentListResponse(findByDateAfterOfPayment(dateOfPayment));
    }

    public List<PaymentResponseDTO> findByDateBeforeOfPaymentResponse(PaymentDateRequestDTO dateOfPayment)  throws NotFoundException{
        return paymentMapper.paymentListResponse(findByDateBeforeOfPayment(dateOfPayment));
    }

    public List<PaymentResponseDTO> findByDateBetweenOfPaymentResponse(PaymentDateRequestDTO dateOfPaymentOne, PaymentDateRequestDTO dateOfPaymentTwo)  throws NotFoundException{
        return paymentMapper.paymentListResponse(findByDateBetweenOfPayment(dateOfPaymentOne, dateOfPaymentTwo));
    }

    public List<PaymentResponseDTO> findByPaymentStatusResponse(String status) throws BadRequestException {
        return paymentMapper.paymentListResponse(findByPaymentStatus(status));
    }

    public List<PaymentResponseDTO> findBySubscriptionIdResponse(Long id) throws NotFoundException{
        return paymentMapper.paymentListResponse(findBySubscriptionId(id));
    }

    public PaymentStatus paymentStatus(PaymentEntity payment) throws NotFoundException{

        if(payment.getDateOfPayment() != null)
            return PaymentStatus.PAID;
        else if(LocalDate.now().isBefore(payment.getCorrectDate()))
            return PaymentStatus.OPEN;
        else
            return PaymentStatus.DELAYED;
    }


    public PaymentResponseDTO createPayment(PaymentRequestDTO paymentRequest) throws NotFoundException, BadRequestException{
        try {
            SubscriptionEntity subscription = subscriptionRepository.findById(paymentRequest.getSubscriptionId()).orElseThrow(() -> new NotFoundException("This subscription don't exist"));

            PaymentEntity payment = PaymentEntity.builder()
                    .amount(paymentRequest.getAmount())
                    .correctDate(paymentRequest.getCorrectDate())
                    .dateOfPayment(paymentRequest.getCorrectDate())
                    .subscription(subscription)
                    .build();

            payment.setPaymentStatus(paymentStatus(payment));
            subscription.getPaymentEntityList().add(payment);
            paymentRepository.save(payment);
            return paymentMapper.paymentResponse(payment);
        } catch (Exception e) {
            throw new BadRequestException("bad request");
        }
    }

    public PaymentResponseDTO addPaymentDate(PaymentAddDateRequestDTO paymentRequest) throws NotFoundException, BadRequestException{
        try{
            PaymentEntity payment = paymentRepository.findById(paymentRequest.getPaymentId()).orElseThrow(() -> new NotFoundException("This payment don't exist"));
            if(payment.getDateOfPayment() != null) {
                throw new BadRequestException("already exist a date of payment");
            }else{
                payment.setDateOfPayment(paymentRequest.getPaymentDate());
                payment.setPaymentStatus(paymentStatus(payment));
            }

            paymentRepository.save(payment);
            return paymentMapper.paymentResponse(payment);
        } catch (Exception e) {
            throw new BadRequestException("bad request");
        }
    }

    public void deletePayment(Long paymentId) throws NotFoundException{
        PaymentEntity payment = paymentRepository.findById(paymentId).orElseThrow(() -> new NotFoundException("This payment don't exist"));
    }
}
