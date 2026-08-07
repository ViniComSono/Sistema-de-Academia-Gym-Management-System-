package com.Gym.System.repository;

import com.Gym.System.entity.ExerciseEntity;
import com.Gym.System.entity.WorkOutEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface WorkOutRepository extends JpaRepository<WorkOutEntity, Long> {

    List<WorkOutEntity> findByUserList_UserId(Long id);

    @Query("""
            select w
            from WorkOutEntity
            join w.users u
            """)
    List<WorkOutEntity> findByUserList(WorkOutEntity workOut);

    @Query("""
            select COUNT(w)
            from WorkOutEntity w
            join w.users u
            """)
    int HowManyUsersTheWorkOutHave(WorkOutEntity workOut);

    @Query("""
            select w
            from WorkOutEntity
            join w.exercises e
            where e.exerciseName = :exercise
            """)
    List<WorkOutEntity> findByExerciseName(@Param("exercise") String exercise);

    @Query("""
            select w
            from WorkOutEntity
            join w.exercises e
            where e.muscleGroup = :muscleGroup
            """)
    List<WorkOutEntity> findByMuscleGroup(@Param("muscleGroup") String muscleGroup);
}
