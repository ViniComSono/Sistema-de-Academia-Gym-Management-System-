package com.Gym.System.controller;

import com.Gym.System.dto.request.WorkOutRequestDTO;
import com.Gym.System.entity.WorkOutEntity;
import com.Gym.System.exception.NotFoundException;
import com.Gym.System.service.WorkOutService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@Controller
@Getter
@Setter
@AllArgsConstructor
@RequestMapping(value = "/treinos")
public class WorkOutController {

    private final WorkOutService workOutService;

    @GetMapping
    public ResponseEntity<List<WorkOutEntity>> findByAll() throws NotFoundException{
        return new ResponseEntity<>(workOutService.findByAll(), HttpStatus.FOUND);
    }

    @GetMapping(value = "/treinoId/{id}")
    public ResponseEntity<WorkOutEntity> findById(@PathVariable Long id) throws NotFoundException{
        return new ResponseEntity<>(workOutService.findById(id), HttpStatus.FOUND);
    }

    @GetMapping(value = "/user/{userId}")
    public ResponseEntity<Set<WorkOutEntity>> findByUserId(@PathVariable Long userId) throws NotFoundException{
        return new ResponseEntity<>(workOutService.findByUserId(userId), HttpStatus.FOUND);
    }

    @PostMapping
    public ResponseEntity<WorkOutEntity> createWorkOut(@RequestBody WorkOutRequestDTO workOutRequestDTO) throws NotFoundException{
        return new ResponseEntity<>(workOutService.createWorkOut(workOutRequestDTO), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<WorkOutEntity> editWorkOut(@RequestBody WorkOutPutDTO workOutPutDTO) throws NotFoundException{
        return new ResponseEntity<>(workOutService.editWorkOut(workOutPutDTO), HttpStatus.CREATED);
    }

    @PatchMapping(value = "/addUser")
    public ResponseEntity<WorkOutEntity> addWorkOutUser(@RequestBody WorkOutPutDTO workOutPutDTO) throws NotFoundException{
        return new ResponseEntity<>(workOutService.addWorkOutUsers(workOutPutDTO), HttpStatus.CREATED);
    }

    @PatchMapping(value = "/removeUser")
    public ResponseEntity<WorkOutEntity> removeWorkOutUser(@RequestBody WorkOutPutDTO workOutPutDTO) throws NotFoundException{
        return new ResponseEntity<>(workOutService.removeWorkOutUsers(workOutPutDTO), HttpStatus.CREATED);
    }

    @PatchMapping(value = "/addExercise")
    public ResponseEntity<WorkOutEntity> addExerciseOnWorkOut(@RequestBody WorkOutExercisesDTO workOutListDTO) throws NotFoundException{
        return new ResponseEntity<>(workOutService.addWorkOutExercise(workOutListDTO), HttpStatus.CREATED);
    }

    @PatchMapping(value = "/removeExercise")
    public ResponseEntity<WorkOutEntity> removeExerciseOnWorkOut(@RequestBody WorkOutExercisesDTO workOutListDTO) throws NotFoundException{
        return new ResponseEntity<>(workOutService.removeWorkOutExercise(workOutListDTO), HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteWorkOut(@PathVariable Long id) throws NotFoundException{
        workOutService.deleteWorkOut(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
