package com.Gym.System.repository;

import com.Gym.System.entity.PhysicalAssessmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface PhysicalAssessmentRepository extends JpaRepository<PhysicalAssessmentEntity, Long>{

    List<PhysicalAssessmentEntity> findByUser_UserId(Long user);
    List<PhysicalAssessmentEntity> findByDate(LocalDate date);
}
