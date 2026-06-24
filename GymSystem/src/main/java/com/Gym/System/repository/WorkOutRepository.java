package com.Gym.System.repository;

import com.Gym.System.entity.WorkOutEntity;
import com.Gym.System.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkOutRepository extends JpaRepository<WorkOutEntity, Long> {

    List<WorkOutEntity> findByUserId(UserEntity user);

}
