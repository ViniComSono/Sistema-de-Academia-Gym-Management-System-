package com.Gym.System.controller;

import com.Gym.System.dto.request.ExerciseRequestDTO;
import com.Gym.System.dto.response.ExerciseResponseDTO;
import com.Gym.System.exception.BadRequestException;
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
import java.util.Set;


@Controller
@Getter
@Setter
@AllArgsConstructor
@RequestMapping(value = "/exercise")
public class ExerciseControler {

    private final ExerciseService exerciseService;

    @GetMapping
    public ResponseEntity<Set<ExerciseResponseDTO>> findAll() throws NotFoundException{
        return new ResponseEntity<>(exerciseService.findAllResponse(), HttpStatus.OK);
    }

    @GetMapping(value = "/grupoMuscular/{grupoMuscular}")
    public ResponseEntity<Set<ExerciseResponseDTO>> findByGrupoMuscular(@PathVariable String grupoMuscular) throws NotFoundException {
        return new ResponseEntity<>(exerciseService.findByMuscleGroupIgnoreCaseResponse(grupoMuscular), HttpStatus.OK);
    }

    @GetMapping(value = "/ExerciseName/{name}")
    public ResponseEntity<ExerciseResponseDTO> findByExerciseNameIgnoreCase(@PathVariable String name) throws NotFoundException{
        return new ResponseEntity<>(exerciseService.findByExerciseNameIgnoreCaseResponse(name), HttpStatus.FOUND);
    }

    @GetMapping(value = "/id/{id}")
    public ResponseEntity<ExerciseResponseDTO> findById(@PathVariable Long id) throws NotFoundException{
        return new ResponseEntity<>(exerciseService.findByExerciseIdResponse(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ExerciseResponseDTO> postExercise(@Validated @RequestBody ExerciseRequestDTO exerciseDTO) throws BadRequestException {
        return new ResponseEntity<>(exerciseService.createExercicio(exerciseDTO),  HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<Void> deleteExercise(@PathVariable Long id) throws NotFoundException{
        exerciseService.removeExercise(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
