package com.Gym.System.repository;

import com.Gym.System.entity.PaymentEntity;
import com.Gym.System.entity.SubscriptionEntity;
import com.Gym.System.enums.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;

public interface PaymentRepository extends JpaRepository <PaymentEntity, Long>{

    List<PaymentEntity> findByDateOfPayment(LocalDate birthday);
    List<PaymentEntity> findByDateOfPaymentBefore(LocalDate birthday);
    List<PaymentEntity> findByDateOfPaymentAfter(LocalDate birthday);
    List<PaymentEntity> findByDateOfPaymentBetween(LocalDate birthdayOne, LocalDate birthdayTwo);

    List<PaymentEntity> findByPaymentStatus(PaymentStatus status);

    PaymentEntity findBySubscription_paymentId(SubscriptionEntity subscription);
}
