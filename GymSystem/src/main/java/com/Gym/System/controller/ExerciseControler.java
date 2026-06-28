package com.Gym.System.controller;

import com.Gym.System.dto.request.ExerciseRequestDTO;
import com.Gym.System.entity.ExerciseEntity;
import com.Gym.System.exception.NotFoundException;
import com.Gym.System.service.ExerciseService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@Getter
@Setter
@AllArgsConstructor
@RequestMapping(value = "/exercise")
public class ExerciseControler {

    private final ExerciseService exerciseService;

    @GetMapping
    public ResponseEntity<List<ExerciseEntity>> findAll() throws NotFoundException{
        return new ResponseEntity<>(exerciseService.findAll(), HttpStatus.OK);
    }

    @GetMapping(value = "/grupoMuscular/{grupoMuscular}")
    public ResponseEntity<List<ExerciseEntity>> findByGrupoMuscular(@PathVariable String grupoMuscular) throws NotFoundException {
        return new ResponseEntity<>(exerciseService.findByGrupoMuscularIgnoreCase(grupoMuscular), HttpStatus.OK);
    }

    @GetMapping(value = "/ExerciseName/{name}")
    public ResponseEntity<ExerciseEntity> findByExerciseNameIgnoreCase(@PathVariable String name) throws NotFoundException{
        return new ResponseEntity<>(exerciseService.findByExerciseNameIgnoreCase(name), HttpStatus.FOUND);
    }

    @GetMapping(value = "/id/{id}")
    public ResponseEntity<ExerciseEntity> findById(@PathVariable Long id) throws NotFoundException{
        return new ResponseEntity<>(exerciseService.findByExerciseID(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ExerciseEntity> postExercise(@Validated @RequestBody ExerciseRequestDTO exerciseDTO){
        return new ResponseEntity<>(exerciseService.cadastrarExercicio(exerciseDTO),  HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<Void> deleteExercise(@PathVariable Long id) throws NotFoundException{
        exerciseService.removeExercise(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
