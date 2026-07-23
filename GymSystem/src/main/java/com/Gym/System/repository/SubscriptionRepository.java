package com.Gym.System.repository;

import com.Gym.System.entity.SubscriptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionRepository extends JpaRepository <SubscriptionEntity, Long> {
}
