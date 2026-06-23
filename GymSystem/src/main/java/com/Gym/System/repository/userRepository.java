package com.Gym.System.repository;

import com.Gym.System.entity.userEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface userRepository extends JpaRepository<userEntity, Long> {

    userEntity findByNome(String nome);

}
