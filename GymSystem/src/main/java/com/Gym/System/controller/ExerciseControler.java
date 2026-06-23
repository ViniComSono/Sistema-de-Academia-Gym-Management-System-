package com.Gym.System.controller;

import com.Gym.System.dto.exerciseDTO;
import com.Gym.System.entity.exerciseEntity;
import com.Gym.System.service.exerciseService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@Getter
@Setter
@AllArgsConstructor
@RequestMapping(value = "/exercise")
public class ExerciseControler {

    private final exerciseService exerciseService;

    @GetMapping
    public ResponseEntity<List<exerciseEntity>> findAll(){
        return new ResponseEntity<>(exerciseService.findAll(), HttpStatus.OK);
    }

    @GetMapping(value = "/grupoMuscular/{grupoMuscular}")
    public ResponseEntity<List<exerciseEntity>> findByGrupoMuscular(@PathVariable String grupoMuscular){
        return new ResponseEntity<>(exerciseService.findByGrupoMuscularIgnoreCase(grupoMuscular), HttpStatus.OK);
    }

    @GetMapping(value = "/id/{id}")
    public ResponseEntity<exerciseEntity> findById(@PathVariable Long id){
        return new ResponseEntity<>(exerciseService.findByExerciseID(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<exerciseEntity> postExercise(@RequestBody exerciseDTO exerciseDTO){
        return new ResponseEntity<>(exerciseService.cadastrarExercicio(exerciseDTO),  HttpStatus.CREATED);
    }
}
