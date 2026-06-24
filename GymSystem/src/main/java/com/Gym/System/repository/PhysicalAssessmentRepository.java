package com.Gym.System.repository;

import com.Gym.System.entity.PhysicalAssessmentEntity;
import com.Gym.System.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhysicalAssessmentRepository extends JpaRepository<PhysicalAssessmentEntity, Long> {
    public PhysicalAssessmentEntity findByUser(UserEntity user);
}
