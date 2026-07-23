package com.Gym.System.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.Gym.System.entity.PlanEntity;

public interface PlanRepository extends JpaRepository <PlanEntity, Long>{
}
