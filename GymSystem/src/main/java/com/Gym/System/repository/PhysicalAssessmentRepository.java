package com.Gym.System.repository;

import com.Gym.System.entity.PhysicalAssessmentEntity;
import com.Gym.System.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PhysicalAssessmentRepository extends JpaRepository<PhysicalAssessmentEntity, Long> {
    public List<PhysicalAssessmentEntity> findByUser_UserId(Long user);
}
