package com.Gym.System.repository;

import com.Gym.System.entity.WorkOutEntity;
import com.Gym.System.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkOutRepository extends JpaRepository<WorkOutEntity, Long> {

    WorkOutEntity findByUserId(UserEntity user);

}
