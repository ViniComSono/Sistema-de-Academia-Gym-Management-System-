package com.Gym.System.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.Gym.System.entity.PlanEntity;

import java.util.List;

public interface PlanRepository extends JpaRepository <PlanEntity, Long>{
    PlanEntity findByPlanNameIgnoreCase(String name);
    List<PlanEntity> findByPlanDurationInMonths(int duration);
    List<PlanEntity> findBySubscription_SubscriptionId(Long id);

}
