package com.Gym.System.repository;

import com.Gym.System.entity.treinosEntity;
import com.Gym.System.entity.userEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface treinosRepository extends JpaRepository<treinosEntity, Long> {

    treinosEntity findByUserId(userEntity user);

}
