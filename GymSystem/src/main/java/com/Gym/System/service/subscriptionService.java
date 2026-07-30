package com.Gym.System.service;

import com.Gym.System.dto.request.PaymentRequestDTO;
import com.Gym.System.dto.request.SubscriptionRequestDTO;
import com.Gym.System.dto.response.SubscriptionSummaryResponseDTO;
import com.Gym.System.entity.PaymentEntity;
import com.Gym.System.entity.SubscriptionEntity;
import com.Gym.System.enums.PaymentStatus;
import com.Gym.System.enums.SubscriptionStatus;
import com.Gym.System.exception.BadRequestException;
import com.Gym.System.exception.NotFoundException;
import com.Gym.System.mapper.SubscriptionMapper;
import com.Gym.System.repository.PaymentRepository;
import com.Gym.System.repository.SubscriptionRepository;
import com.Gym.System.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
@Getter
public class subscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final UserRepository userRepository;
    private final SubscriptionMapper subscriptionMapper;
    private final PaymentService paymentService;
    private final PaymentRepository paymentRepository;


    //do the validation of payment with for using the start date to create the others payments
    //create a function will be response to notice how many delayd payments exist on the subscription to define the status.

    public List<SubscriptionSummaryResponseDTO> findAll(){
        return subscriptionMapper.subscriptionListResponse(subscriptionRepository.findAll());
    }

    public SubscriptionEntity findById(Long id) throws NotFoundException{
        return subscriptionRepository.findById(id).orElseThrow(() ->  new NotFoundException("not found this subscription"));
    }

    public List<SubscriptionSummaryResponseDTO> findByStratDate(LocalDate date) throws NotFoundException{
        List<SubscriptionEntity> subscriptionEntities = subscriptionRepository.findByStratDate(date);

        if(subscriptionEntities.isEmpty())
            throw new NotFoundException("Don't exist subscriptions that start on this date");
        else
            return subscriptionMapper.subscriptionListResponse(subscriptionEntities);
    }

    public List<SubscriptionSummaryResponseDTO> findByExpirationDate(LocalDate date) throws NotFoundException{
        List<SubscriptionEntity> subscriptionEntities = subscriptionRepository.findByExpirationDate(date);

        if(subscriptionEntities.isEmpty())
            throw new NotFoundException("Don't exist subscriptions that expired on this date");
        else
            return subscriptionMapper.subscriptionListResponse(subscriptionEntities);
    }

    public List<SubscriptionSummaryResponseDTO> findByPlanId(Long planId) throws NotFoundException{
        List<SubscriptionEntity> subscriptionEntities = subscriptionRepository.findByPlan_PlanId(planId);

        if(subscriptionEntities.isEmpty())
            throw new NotFoundException("Don't exist subscriptions with this plan");
        else
            return subscriptionMapper.subscriptionListResponse(subscriptionEntities);
    }

    public List<SubscriptionSummaryResponseDTO> findByPlanName(String planName) throws NotFoundException{
        List<SubscriptionEntity> subscriptionEntities = subscriptionRepository.findByPlan_PlanName(planName);

        if(subscriptionEntities.isEmpty())
            throw new NotFoundException("Don't exist subscriptions with this plan");
        else
            return subscriptionMapper.subscriptionListResponse(subscriptionEntities);
    }

    public List<SubscriptionSummaryResponseDTO> findByStatus(String status) throws NotFoundException{
        List<SubscriptionEntity> subscriptionEntities = subscriptionRepository.findByStatus(status);

        if(subscriptionEntities.isEmpty())
            throw new NotFoundException("Don't exist subscriptions with this status");
        else
            return subscriptionMapper.subscriptionListResponse(subscriptionEntities);
    }

    public SubscriptionSummaryResponseDTO findByUserId(Long userId) throws NotFoundException{
        SubscriptionEntity subscriptionEntities = subscriptionRepository.findByUser_UserId(userId);

        if(subscriptionEntities == null)
            throw new NotFoundException("Don't exist subscriptions with this user");
        else
            return subscriptionMapper.subscriptionResponse(subscriptionEntities);
    }

    public SubscriptionSummaryResponseDTO findByUserName(String userName) throws NotFoundException{
        SubscriptionEntity subscriptionEntities = subscriptionRepository.findByUser_UserName(userName);

        if(subscriptionEntities == null)
            throw new NotFoundException("Don't exist subscriptions with this user");
        else
            return subscriptionMapper.subscriptionResponse(subscriptionEntities);
    }

    public SubscriptionSummaryResponseDTO createSubscription(SubscriptionRequestDTO subscriptionRequest) throws BadRequestException{
        try{
            SubscriptionEntity subscription = SubscriptionEntity.builder()
                    .startDate(subscriptionRequest.getStartDate())
                    .expirationDate(subscriptionRequest.getExpirationDate())
                    .user(userRepository.findById(subscriptionRequest.getUserId()).orElseThrow(() -> new NotFoundException("Not found this user")))
                    .status(SubscriptionStatus.ACTIVE)
                    .build();

            subscriptionRepository.save(subscription);
            return subscriptionMapper.subscriptionResponse(subscription);
        } catch (Exception e) {
            throw new BadRequestException("bad request");
        }
    }

    public SubscriptionSummaryResponseDTO updateStatus(Long subscriptionId) throws BadRequestException{
        try{
            SubscriptionEntity subscription = findById(subscriptionId);
            long paymentStatus = subscription.getPaymentEntityList().stream().map(PaymentEntity::getPaymentStatus).filter(status -> status.equals(PaymentStatus.DELAYED)).count();

            if(paymentStatus > 6){
                subscription.setStatus(SubscriptionStatus.BLOCKED);
            }else if(paymentStatus > 3){
                subscription.setStatus(SubscriptionStatus.EXPIRED);
            }else{
                subscription.setStatus(SubscriptionStatus.ACTIVE);
            }

            subscriptionRepository.save(subscription);
            return subscriptionMapper.subscriptionResponse(subscription);
        }catch (Exception e){
            throw new BadRequestException("bad request");
        }
    }

    @Transactional
    public SubscriptionSummaryResponseDTO updatePayments(Long subscriptionId) throws BadRequestException{
        try{
            SubscriptionEntity subscription = findById(subscriptionId);
            LocalDate lastPayment = subscription.getPaymentEntityList().stream().map(PaymentEntity::getCorrectDate).max(LocalDate::compareTo).orElseThrow();

            while(lastPayment.isBefore(LocalDate.now())) {
                lastPayment = lastPayment.plusMonths(subscription.getPlan().getPlanDurationInMonths());

                PaymentRequestDTO paymentRequest = PaymentRequestDTO.builder()
                        .correctDate(lastPayment)
                        .amount(subscription.getPlan().getPlanPrice())
                        .subscriptionId(subscriptionId)
                        .build();

                paymentService.createPayment(paymentRequest);
            }
            updateStatus(subscriptionId);
            return subscriptionMapper.subscriptionResponse(subscription);
        }catch (Exception e){
            throw new BadRequestException("bad request");
        }
    }
}
