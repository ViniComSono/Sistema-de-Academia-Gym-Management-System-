package com.Gym.System.repository;

import com.Gym.System.entity.SubscriptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface SubscriptionRepository extends JpaRepository <SubscriptionEntity, Long> {
    List<SubscriptionEntity> findByStratDate(LocalDate date);
    List<SubscriptionEntity> findByExpirationDate(LocalDate date);
    List<SubscriptionEntity> findByPlan_PlanId(Long planName);
    List<SubscriptionEntity> findByPlan_PlanName(String planName);
    List<SubscriptionEntity> findByStatus(String status);
    SubscriptionEntity findByUser_UserId(Long id);
    SubscriptionEntity findByUser_UserName(String name);
}
