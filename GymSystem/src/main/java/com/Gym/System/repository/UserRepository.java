package com.Gym.System.repository;

import com.Gym.System.entity.PlanEntity;
import com.Gym.System.entity.UserEntity;
import com.Gym.System.enums.PaymentStatus;
import com.Gym.System.enums.SubscriptionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {



    UserEntity findByName(String nome);

    List<UserEntity> findBySexUser(String sexUser);

    @Query("""
            select u
            from UserEntity
            join u.subscription s
            where s.plan = :plan
            """)
    List<UserEntity> findBySubscriptionPlan(@Param("plan")PlanEntity plan);

    @Query("""
            select u
            from UserEntity
            join u.subscription s
            where s.status = :subscriptionStatus
            """)
    List<UserEntity> findBySubscriptionStatus(@Param("subscriptionStatus")SubscriptionStatus subscriptionStatus);

    @Query("""
            select u
            from UserEntity
            join u.subscription s
            join s.payments p
            where p.paymentStatus = :paymentStatus
            """)
    List<UserEntity> findByPaymentStatus(@Param("paymentStatus")PaymentStatus paymentStatus);

    @Query("""
            select COUNT(u) > 0
            from UserEntity u
            join u.subscription s
            join s.payments p
            where p.paymentStatus = 'DELAYED'
            """)
    boolean existUserWithDelayPayment(UserEntity user);
}
