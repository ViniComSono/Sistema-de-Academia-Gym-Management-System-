package com.Gym.System.repository;

import com.Gym.System.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    UserEntity findByName(String nome);
}
