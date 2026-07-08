package com.Gym.System.repository;

import com.Gym.System.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;


@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    UserEntity findByName(String nome);
    List<UserEntity> findByBirthday(LocalDate birthday);
    List<UserEntity> findByBirthdayBefore(LocalDate birthday);
    List<UserEntity> findByBirthdayAfter(LocalDate birthday);
    List<UserEntity> findByBirthdayBetween(LocalDate birthdayOne, LocalDate birthdayTwo);
}
